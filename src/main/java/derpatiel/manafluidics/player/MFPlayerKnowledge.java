package derpatiel.manafluidics.player;

import derpatiel.manafluidics.enums.KnowledgeCategory;
import derpatiel.manafluidics.spell.SpellBase;
import derpatiel.manafluidics.util.LOG;
import derpatiel.manafluidics.util.MaterialItemHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;
import java.util.Map;

public class MFPlayerKnowledge {

    private Map<KnowledgeCategory,Boolean> knowledgeMap;
    int spellXP=0;


    private MFPlayerKnowledge(){
        knowledgeMap = KnowledgeCategory.getDefaultKnowledgeMap();
    }

    public void clearKnowledge(){
        knowledgeMap = KnowledgeCategory.getDefaultKnowledgeMap();
        spellXP=0;
    }

    public static MFPlayerKnowledge fromNbt(NBTTagCompound tag){
        MFPlayerKnowledge knowledge = new MFPlayerKnowledge();
        for(KnowledgeCategory cat : KnowledgeCategory.VALUES){
            knowledge.setKnowledge(cat,tag.getBoolean(cat.name()));
        }
        knowledge.spellXP = tag.getInteger("spellXP");

        return knowledge;
    }

    public static NBTTagCompound toNbt(MFPlayerKnowledge knowledge){
        NBTTagCompound tag = new NBTTagCompound();
        for(KnowledgeCategory cat : KnowledgeCategory.VALUES){
            tag.setBoolean(cat.name(),knowledge.hasKnowledge(cat));
        }
        tag.setInteger("spellXP",knowledge.spellXP);
        return tag;
    }

    public static MFPlayerKnowledge newPlayerKnowledge() {
        return new MFPlayerKnowledge();
    }

    @Override
    public String toString(){
        return "spell XP: "+spellXP;
    }

    public void spellCast(SpellBase spellBase) {
        spellXP++;
        LOG.info("cast "+spellBase.getName());
    }

    public void setKnowledge(KnowledgeCategory cat, Boolean bool){
        this.knowledgeMap.put(cat,bool);
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

    private long getSeed() {
        //TODO: add randomness to this, or otherwise all players on a server will have identical altars
        return Minecraft.getMinecraft().theWorld.getSeed();
    }

    public List<MaterialItemHelper.AlloyFormingRule> getAllowedAlloyRules(){
        return MaterialItemHelper.alloyRules;
    }
}
