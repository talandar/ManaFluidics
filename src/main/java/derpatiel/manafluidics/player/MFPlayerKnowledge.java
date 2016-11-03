package derpatiel.manafluidics.player;

import derpatiel.manafluidics.spell.SpellBase;
import derpatiel.manafluidics.util.ChatUtil;
import derpatiel.manafluidics.util.LOG;
import derpatiel.manafluidics.util.TextHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class MFPlayerKnowledge {

    private boolean craftedAltar=false;
    int spellXP=0;


    private MFPlayerKnowledge(){
    }

    public void clearKnowledge(){
        craftedAltar=false;
        spellXP=0;
    }

    public static MFPlayerKnowledge fromNbt(NBTTagCompound tag){
        MFPlayerKnowledge knowledge = new MFPlayerKnowledge();
        knowledge.craftedAltar=tag.getBoolean("craftedAltar");
        knowledge.spellXP = tag.getInteger("spellXP");

        return knowledge;
    }

    public static NBTTagCompound toNbt(MFPlayerKnowledge knowledge){
        NBTTagCompound tag = new NBTTagCompound();
        tag.setBoolean("craftedAltar",knowledge.craftedAltar);
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

    public void craftAltar() {
        craftedAltar=true;
    }

    public boolean hasCraftedAltar() {
        return craftedAltar;
    }
}
