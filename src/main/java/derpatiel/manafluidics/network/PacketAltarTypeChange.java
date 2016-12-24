package derpatiel.manafluidics.network;

import derpatiel.manafluidics.block.altar.KnowledgeAltarTileEntity;
import derpatiel.manafluidics.block.multiTank.smeltingTank.SmeltingTankTileEntity;
import derpatiel.manafluidics.enums.AltarType;
import derpatiel.manafluidics.player.PlayerKnowledgeHandler;
import derpatiel.manafluidics.registry.SpellRegistry;
import derpatiel.manafluidics.util.LOG;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.UUID;

public class PacketAltarTypeChange implements IMessage
{
    private AltarType newType;
    private BlockPos tileToUpdate;

    public PacketAltarTypeChange(){};

    public PacketAltarTypeChange(AltarType type, BlockPos tileLoc){
        this.newType=type;
        this.tileToUpdate=tileLoc;
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(newType.ordinal());
        buf.writeLong(tileToUpdate.toLong());
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        newType = AltarType.VALUES[buf.readInt()];
        tileToUpdate = BlockPos.fromLong(buf.readLong());
    }

    public static class ServerHandler implements IMessageHandler<PacketAltarTypeChange, IMessage> {
        @Override
        public IMessage onMessage(PacketAltarTypeChange message, MessageContext ctx) {

            TileEntity tile = ctx.getServerHandler().playerEntity.getServerWorld().getTileEntity(message.tileToUpdate);
            if (tile == null) {
                LOG.warn("null tile :(");
                return null;
            }

            if(tile instanceof KnowledgeAltarTileEntity) {
                ((KnowledgeAltarTileEntity)tile).setType(message.newType);
            }
            return null;
        }
    }

    public static class ClientHandler implements IMessageHandler<PacketAltarTypeChange, IMessage>{

        @Override
        public IMessage onMessage(PacketAltarTypeChange message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(new PacketAltarTypeChange.MessageRunnable(message));
            return null;
        }
    }

        private static class MessageRunnable implements Runnable {

            PacketAltarTypeChange message;

            public MessageRunnable(PacketAltarTypeChange message) {
                this.message = message;
            }

            @Override
            public void run() {
                TileEntity tile = Minecraft.getMinecraft().theWorld.getTileEntity(message.tileToUpdate);
                if(tile==null || !(tile instanceof KnowledgeAltarTileEntity)){
                    LOG.warn("received update altar packet for non altar!");
                }else{
                    ((KnowledgeAltarTileEntity)tile).setType(message.newType);
                }
            }
        }
}
