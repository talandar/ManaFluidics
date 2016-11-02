package derpatiel.manafluidics.player;

import derpatiel.manafluidics.spell.SpellBase;
import derpatiel.manafluidics.util.LOG;
import net.minecraft.nbt.NBTTagCompound;

public class MFPlayerKnowledge {

    int spellXP;


    private MFPlayerKnowledge(){

    }

    public static MFPlayerKnowledge fromNbt(NBTTagCompound tag){
        MFPlayerKnowledge knowledge = new MFPlayerKnowledge();
        knowledge.spellXP = tag.getInteger("spellXP");

        return knowledge;
    }

    public static NBTTagCompound toNbt(MFPlayerKnowledge knowledge){
        NBTTagCompound tag = new NBTTagCompound();
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
}
