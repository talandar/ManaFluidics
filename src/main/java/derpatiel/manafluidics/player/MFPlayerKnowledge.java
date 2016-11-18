package derpatiel.manafluidics.player;

import derpatiel.manafluidics.enums.AltarType;
import derpatiel.manafluidics.enums.KnowledgeCategory;
import derpatiel.manafluidics.spell.SpellAttribute;
import derpatiel.manafluidics.spell.SpellBase;
import derpatiel.manafluidics.util.LOG;
import derpatiel.manafluidics.util.MaterialItemHelper;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;
import java.util.Map;

public class MFPlayerKnowledge {

    private static final int[] maxManaByLevel = new int[]{0,10,20,30,40};

    private Map<KnowledgeCategory,Boolean> knowledgeMap;
    private AltarType lastUsedAltar;
    private int playerLevel=0;
    private int currentMana;
    private int maxMana;


    private MFPlayerKnowledge(){
        knowledgeMap = KnowledgeCategory.getDefaultKnowledgeMap();
    }

    public void clearKnowledge(){
        knowledgeMap = KnowledgeCategory.getDefaultKnowledgeMap();
        lastUsedAltar=null;
        playerLevel=0;
        levelChanged();
    }

    public static MFPlayerKnowledge fromNbt(NBTTagCompound tag) {
        MFPlayerKnowledge knowledge = new MFPlayerKnowledge();
        for (KnowledgeCategory cat : KnowledgeCategory.VALUES) {
            knowledge.setKnowledge(cat, tag.getBoolean(cat.name()));
        }
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
        if(knowledge.lastUsedAltar!=null) {
            tag.setInteger("altar", knowledge.lastUsedAltar.ordinal());
        }
        return tag;
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

    public void setKnowledge(KnowledgeCategory cat, Boolean bool){
        int oldLevel = calcPlayerLevel();
        this.knowledgeMap.put(cat,bool);
        int newLevel = calcPlayerLevel();
        if(oldLevel!=newLevel){
            this.playerLevel=calcPlayerLevel();
            levelChanged();
        }
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

    public boolean canCast(SpellBase spellBase) {
        //check mana levels, if prepared, etc
        return true;
    }
}
