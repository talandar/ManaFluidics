package derpatiel.manafluidics.network;

import derpatiel.manafluidics.block.multiTank.alloyTank.AlloyTankTileEntity;
import derpatiel.manafluidics.block.multiTank.smeltingTank.SmeltingTankTileEntity;
import derpatiel.manafluidics.util.LOG;
import derpatiel.manafluidics.util.MaterialItemHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.UUID;

public class PacketFluidAlloy implements IMessage
{
    private BlockPos tileToUpdate;
    private UUID playerUUID;
    private int selectedAlloyId;

    public PacketFluidAlloy(){};

    public PacketFluidAlloy(BlockPos tile, UUID playerUUID, int selectedAlloy){
        this.tileToUpdate=tile;
        this.playerUUID = playerUUID;
        this.selectedAlloyId=selectedAlloy;
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeLong(tileToUpdate.toLong());
        ByteBufUtils.writeUTF8String(buf,playerUUID.toString());
        buf.writeInt(selectedAlloyId);
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.tileToUpdate = BlockPos.fromLong(buf.readLong());
        this.playerUUID = UUID.fromString(ByteBufUtils.readUTF8String(buf));
        this.selectedAlloyId=buf.readInt();
    }

    public static class Handler implements IMessageHandler<PacketFluidAlloy, IMessage> {
        @Override
        public IMessage onMessage(PacketFluidAlloy message, MessageContext ctx) {

            TileEntity tile = ctx.getServerHandler().playerEntity.getServerWorld().getTileEntity(message.tileToUpdate);
            if (tile == null) {
                LOG.warn("null tile :(");
                return null;
            }
            EntityPlayer player = ctx.getServerHandler().playerEntity.getServerWorld().getPlayerEntityByUUID(message.playerUUID);

            MaterialItemHelper.AlloyFormingRule rule = MaterialItemHelper.alloyRules.get(message.selectedAlloyId);

            ((AlloyTankTileEntity)tile).doAlloy(player,rule);

            return null;
        }
    }
}
