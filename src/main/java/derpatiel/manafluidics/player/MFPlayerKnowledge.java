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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.*;

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
    private boolean dirty=false;

    private final Map<Integer,Set<SpellBase>> preppedSpells = new HashMap<>();


    private MFPlayerKnowledge(){
        knowledgeMap = KnowledgeCategory.getDefaultKnowledgeMap();
    }

    public void clearKnowledge(){
        knowledgeMap = KnowledgeCategory.getDefaultKnowledgeMap();
        storedSpellParams.clear();
        lastUsedAltar=null;
        preppedSpells.clear();
        playerLevel=0;
        levelChanged();
        dirty=true;
    }

    public static MFPlayerKnowledge fromNbt(NBTTagCompound tag) {
        MFPlayerKnowledge knowledge = new MFPlayerKnowledge();
        for (KnowledgeCategory cat : KnowledgeCategory.VALUES) {
            knowledge.setKnowledge(cat, tag.getBoolean(cat.name()));
        }
        readSpellParams(knowledge,tag.getCompoundTag("params"));
        if (tag.hasKey("altar")){
            knowledge.lastUsedAltar = AltarType.VALUES[tag.getInteger("altar")];
        }else{
            knowledge.lastUsedAltar=null;
        }
        readPreppedSpells(knowledge,tag.getCompoundTag("prepared"));
        knowledge.playerLevel = knowledge.calcPlayerLevel();
        knowledge.levelChanged();
        return knowledge;
    }

    public static NBTTagCompound toNbt(MFPlayerKnowledge knowledge){
        NBTTagCompound tag = new NBTTagCompound();
        for(KnowledgeCategory cat : KnowledgeCategory.VALUES){
            tag.setBoolean(cat.name(),knowledge.hasKnowledge(cat));
        }
        tag.setTag("params",writeSpellParams(knowledge));
        if(knowledge.lastUsedAltar!=null) {
            tag.setInteger("altar", knowledge.lastUsedAltar.ordinal());
        }
        tag.setTag("prepared",writePreppedSpells(knowledge));
        return tag;
    }

    private static void readSpellParams(MFPlayerKnowledge knowledge, NBTTagCompound paramsTag){
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
                knowledge.storedSpellParams.put(regName,choices);
            }
        }
    }

    private static NBTTagCompound writeSpellParams(MFPlayerKnowledge knowledge){
        NBTTagCompound paramsTag = new NBTTagCompound();
        for(String spellReg : knowledge.storedSpellParams.keySet()){
            List<SpellParameterChoices> choices = knowledge.storedSpellParams.get(spellReg);
            NBTTagCompound spellChoicesTag = new NBTTagCompound();
            for(SpellParameterChoices choice : choices){
                spellChoicesTag.setInteger(choice.options.ordinal()+"",choice.selectedOption);
            }

            paramsTag.setTag(spellReg,spellChoicesTag);
        }

        return paramsTag;
    }

    private static void readPreppedSpells(MFPlayerKnowledge knowledge, NBTTagCompound tag){
        //TODO
    }

    private static NBTTagCompound writePreppedSpells(MFPlayerKnowledge knowledge){
        //TODO
        return null;
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
        //mark dirty?
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
        dirty=true;
        LOG.info("set player knowledge: "+cat.name()+" to "+bool);
    }

    public void addKnowledge(KnowledgeCategory cat){
        this.setKnowledge(cat,true);
        dirty=true;
    }

    public void removeKnowledge(KnowledgeCategory cat){
        this.setKnowledge(cat,false);
        dirty=true;
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

    /**
     * tick the knowledge.  This handles mana updating, etc
     * @return the dirty flag.  if true, this sync to the client this tick.
     */
    public boolean tick(){
        currentMana=Math.min(currentMana+manaRegenByLevel[playerLevel],maxMana);
        //other tick stuff



        if(dirty){
            dirty=false;
            return true;
        }
        return false;
    }

    public List<SpellParameterChoices> getSpellParameters(String regName) {
        return storedSpellParams.get(regName);
    }

    public SpellBase getSelectedSpell() {
        return selectedSpell;
    }

    public Set<SpellBase> getPreparedSpells(int spellLevel) {
        return preppedSpells.get(spellLevel);
    }

    public List<SpellBase> getAvailableSpells(int spellLevel){
        return SpellRegistry.getSpellsForLevel(spellLevel);
    }

    public int getMaxPreparedSpells(int spellLevel) {
        return 5-spellLevel;
    }

    public void changePrep(SpellBase spell) {
        LOG.info("Server received change spell prep: "+spell.getRegName());
        boolean didWork=false;
        Set<SpellBase> spells = preppedSpells.get(spell.getLevel());
        if(spells.contains(spell)){
            spells.remove(spell);
            didWork=true;
        }else{
            if(spells.size()<getMaxPreparedSpells(spell.getLevel())){
                spells.add(spell);
                didWork=true;
            }
        }
        dirty=didWork;
    }
}
