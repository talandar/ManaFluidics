package derpatiel.manafluidics.player;

import derpatiel.manafluidics.network.MFPacketHandler;
import derpatiel.manafluidics.network.PacketKnowledgeSync;
import derpatiel.manafluidics.spell.SpellAttribute;
import derpatiel.manafluidics.util.LOG;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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

    public static void syncToClient(EntityPlayer player) {
        MFPacketHandler.INSTANCE.sendTo(new PacketKnowledgeSync(PlayerKnowledgeHandler.getPlayerKnowledge(player)), (EntityPlayerMP) player);
    }

    @SideOnly(Side.CLIENT)
    public static void syncFromServer(MFPlayerKnowledge knowledge) {
        //this should only happen on the client.  if this happens on the server, everything goes to crap!
        for(EntityPlayer player : playerMap.keySet()){
            playerMap.put(player,knowledge);
        }
    }
}
