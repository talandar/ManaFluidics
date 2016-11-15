package derpatiel.manafluidics.player;

import derpatiel.manafluidics.spell.SpellAttribute;
import derpatiel.manafluidics.util.LOG;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PlayerKnowledgeHandler {

    private static Map<EntityPlayer,MFPlayerKnowledge> playerMap = new HashMap<>();

    public static MFPlayerKnowledge getPlayerKnowledge(EntityPlayer player){
        if(playerMap.containsKey(player)) {
            return playerMap.get(player);
        }else {
            MFPlayerKnowledge knowledge = MFPlayerKnowledge.newPlayerKnowledge();
            playerMap.put(player, knowledge);
            return knowledge;
        }
    }

    public static boolean isSpellBoosted(EntityPlayer caster, SpellAttribute[] attributes){
        return false;
    }

    public static void onPlayerLoad(EntityPlayer entityPlayer) {
        NBTTagCompound tag = entityPlayer.getEntityData().getCompoundTag("ManaFluidics");
        MFPlayerKnowledge knowledge = MFPlayerKnowledge.fromNbt(tag);
        playerMap.put(entityPlayer,knowledge);
        LOG.info("LOAD: "+knowledge.toString());
    }

    public static void onPlayerSave(EntityPlayer entityPlayer) {
        MFPlayerKnowledge knowledge = playerMap.get(entityPlayer);
        NBTTagCompound tag = MFPlayerKnowledge.toNbt(knowledge);
        entityPlayer.getEntityData().setTag("ManaFluidics",tag);
        LOG.info("SAVE: "+knowledge.toString());
    }
}
