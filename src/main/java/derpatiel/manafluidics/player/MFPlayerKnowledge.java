package derpatiel.manafluidics.player;

import derpatiel.manafluidics.enums.AltarType;
import derpatiel.manafluidics.enums.KnowledgeCategory;
import derpatiel.manafluidics.registry.SpellRegistry;
import derpatiel.manafluidics.spell.SpellAttribute;
import derpatiel.manafluidics.spell.SpellBase;
import derpatiel.manafluidics.spell.parameters.SpellParameter;
import derpatiel.manafluidics.spell.parameters.SpellParameterChoices;
import derpatiel.manafluidics.util.LOG;
import derpatiel.manafluidics.util.MaterialItemHelper;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MFPlayerKnowledge {

    private static final float[] manaRegenByLevel = new float[]{0.0f,0.05f, 0.1f, 0.15f, 0.2f};
    private static final int[] maxManaByLevel = new int[]{0,100,200,300,400};

    private Map<KnowledgeCategory,Boolean> knowledgeMap;

    private final Map<String, List<SpellParameterChoices>> storedSpellParams = new HashMap<>();

    private AltarType lastUsedAltar;
    private int playerLevel=0;
    private float currentMana;
    private int maxMana;
    private SpellBase selectedSpell;


    private MFPlayerKnowledge(){
        knowledgeMap = KnowledgeCategory.getDefaultKnowledgeMap();
    }

    public void clearKnowledge(){
        knowledgeMap = KnowledgeCategory.getDefaultKnowledgeMap();
        storedSpellParams.clear();;
        lastUsedAltar=null;
        playerLevel=0;
        levelChanged();
    }

    public static MFPlayerKnowledge fromNbt(NBTTagCompound tag) {
        MFPlayerKnowledge knowledge = new MFPlayerKnowledge();
        for (KnowledgeCategory cat : KnowledgeCategory.VALUES) {
            knowledge.setKnowledge(cat, tag.getBoolean(cat.name()));
        }
        knowledge.readSpellParams(tag.getCompoundTag("params"));
        if (tag.hasKey("altar")){
            knowledge.lastUsedAltar = AltarType.VALUES[tag.getInteger("altar")];
        }else{
            knowledge.lastUsedAltar=null;
        }
        knowledge.playerLevel = knowledge.calcPlayerLevel();
        knowledge.levelChanged();

        return knowledge;
    }

    public static NBTTagCompound toNbt(MFPlayerKnowledge knowledge){
        NBTTagCompound tag = new NBTTagCompound();
        for(KnowledgeCategory cat : KnowledgeCategory.VALUES){
            tag.setBoolean(cat.name(),knowledge.hasKnowledge(cat));
        }
        tag.setTag("params",knowledge.writeSpellParams());
        if(knowledge.lastUsedAltar!=null) {
            tag.setInteger("altar", knowledge.lastUsedAltar.ordinal());
        }
        return tag;
    }

    private void readSpellParams(NBTTagCompound paramsTag){
        for(SpellBase spell : SpellRegistry.spells()){
            String regName = spell.getRegName();
            if(paramsTag.hasKey(regName)){
                NBTTagCompound selectedParams = paramsTag.getCompoundTag(regName);
                List<SpellParameterChoices> choices = new ArrayList<>();
                for(String paramOrdinalStr : selectedParams.getKeySet()){
                    int paramOrdinal = Integer.parseInt(paramOrdinalStr);
                    int paramChoice = selectedParams.getInteger(paramOrdinalStr);
                    choices.add(new SpellParameterChoices(SpellParameter.VALUES[paramOrdinal],paramChoice));
                }
                this.storedSpellParams.put(regName,choices);
            }
        }
    }

    private NBTTagCompound writeSpellParams(){
        NBTTagCompound paramsTag = new NBTTagCompound();
        for(String spellReg : storedSpellParams.keySet()){
            List<SpellParameterChoices> choices = storedSpellParams.get(spellReg);
            NBTTagCompound spellChoicesTag = new NBTTagCompound();
            for(SpellParameterChoices choice : choices){
                spellChoicesTag.setInteger(choice.options.ordinal()+"",choice.selectedOption);
            }

            paramsTag.setTag(spellReg,spellChoicesTag);
        }

        return paramsTag;
    }

    public static MFPlayerKnowledge newPlayerKnowledge() {
        return new MFPlayerKnowledge();
    }

    @Override
    public String toString(){
        return "player level "+playerLevel;
    }

    private void levelChanged(){
        float mult = 1.0f;
        if(lastUsedAltar==AltarType.ZIGGURAT)
            mult = 1.25f;
        maxMana = (int)(maxManaByLevel[playerLevel]*mult);
        currentMana=maxMana;
    }

    public boolean isSpellBoosted(SpellAttribute... attributes){
        if(lastUsedAltar!=null) {
            for (SpellAttribute attribute : attributes) {
                if (lastUsedAltar.boosts(attribute))
                    return true;
            }
        }
        return false;
    }

    public void spellCast(SpellBase spellBase,boolean fromItem) {
        LOG.info("cast "+spellBase.getName());
        //reduce mana if not cast from item
        //if cast from item, other upkeep might be needed
    }


    public boolean canCast(SpellBase spellBase) {
        //check mana levels, if prepared, etc
        return true;
    }

    public void setKnowledge(KnowledgeCategory cat, Boolean bool){
        int oldLevel = calcPlayerLevel();
        this.knowledgeMap.put(cat,bool);
        int newLevel = calcPlayerLevel();
        if(oldLevel!=newLevel){
            this.playerLevel=calcPlayerLevel();
            levelChanged();
        }
        LOG.info("set player knowledge: "+cat.name()+" to "+bool);
    }

    public void addKnowledge(KnowledgeCategory cat){
        this.setKnowledge(cat,true);
    }

    public void removeKnowledge(KnowledgeCategory cat){
        this.setKnowledge(cat,false);
    }


    public boolean hasKnowledge(KnowledgeCategory cat){
        return knowledgeMap.getOrDefault(cat,false);
    }

    public List<MaterialItemHelper.AlloyFormingRule> getAllowedAlloyRules(){

        List<MaterialItemHelper.AlloyFormingRule> rules = MaterialItemHelper.alloyRules.subList(0,Math.min(MaterialItemHelper.alloyRules.size(),calcPlayerLevel()+1));
        return rules;
    }

    public int calcPlayerLevel(){
        int level = 0;
        if(hasKnowledge(KnowledgeCategory.ALTAR_CRAFTED))
            level++;
        if(hasKnowledge(KnowledgeCategory.DRAGON_KILLED))
            level++;
        if(hasKnowledge(KnowledgeCategory.WITHER_KILLED))
            level++;
        if(hasKnowledge(KnowledgeCategory.MFBOSS_KILLED))
            level++;
        return level;
    }

    public void tick(){
      currentMana=Math.min(currentMana+manaRegenByLevel[playerLevel],maxMana);
    }

    public List<SpellParameterChoices> getSpellParameters(String regName) {
        return storedSpellParams.get(regName);
    }

    public SpellBase getSelectedSpell() {
        return selectedSpell;
    }

    public List<SpellBase> getPreparedSpells(int spellLevel) {
        //TODO spell preparation, etc
        List<SpellBase> available = new ArrayList<>(SpellRegistry.getSpellsForLevel(spellLevel));
        available.remove(0);
        available.remove(0);
        return available;
    }

    public List<SpellBase> getAvailableSpells(int spellLevel){
        return SpellRegistry.getSpellsForLevel(spellLevel);
    }
}
