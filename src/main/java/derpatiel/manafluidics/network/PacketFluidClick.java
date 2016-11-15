package derpatiel.manafluidics.network;

import derpatiel.manafluidics.block.multiTank.alloyTank.AlloyTankTileEntity;
import derpatiel.manafluidics.block.multiTank.smeltingTank.SmeltingTankTileEntity;
import derpatiel.manafluidics.util.LOG;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketFluidClick implements IMessage
{
    private BlockPos tileToUpdate;
    private int fluidIndexToMove;

    public PacketFluidClick(){};

    public PacketFluidClick(BlockPos tile, int fluid){
        this.tileToUpdate=tile;
        this.fluidIndexToMove=fluid;
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeLong(tileToUpdate.toLong());
        buf.writeInt(fluidIndexToMove);
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.tileToUpdate = BlockPos.fromLong(buf.readLong());
        this.fluidIndexToMove=buf.readInt();
    }

    public static class Handler implements IMessageHandler<PacketFluidClick, IMessage> {
        @Override
        public IMessage onMessage(PacketFluidClick message, MessageContext ctx) {

            TileEntity tile = ctx.getServerHandler().playerEntity.getServerWorld().getTileEntity(message.tileToUpdate);
            if (tile == null) {
                LOG.warn("null tile :(");
                return null;
            }

            if(tile instanceof SmeltingTankTileEntity) {
                SmeltingTankTileEntity smeltingTank = (SmeltingTankTileEntity) tile;
                smeltingTank.tank.moveFluidToBottom(message.fluidIndexToMove);
                smeltingTank.markForUpdate();
            }else if(tile instanceof AlloyTankTileEntity){
                AlloyTankTileEntity alloyTank = (AlloyTankTileEntity)tile;
                alloyTank.tank.moveFluidToBottom(message.fluidIndexToMove);
                alloyTank.markForUpdate();
            }

            return null;
        }
    }
}
