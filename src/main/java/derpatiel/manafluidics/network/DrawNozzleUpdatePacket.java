package derpatiel.manafluidics.network;

import derpatiel.manafluidics.block.castingchamber.CastingChamberTileEntity;
import derpatiel.manafluidics.block.drawNozzle.DrawNozzleTileEntity;
import derpatiel.manafluidics.block.multiTank.fluidTank.FluidTankTileEntity;
import derpatiel.manafluidics.block.pipe.PipeTileEntity;
import derpatiel.manafluidics.block.portableTank.PortableTankTileEntity;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class DrawNozzleUpdatePacket implements IMessage {

    public FluidStack fluid;
    public BlockPos pos;
    public int drawProgress;

    public DrawNozzleUpdatePacket(){}

    public DrawNozzleUpdatePacket(BlockPos pos, FluidStack contents, int drawProgress){
        this.fluid=contents;
        this.drawProgress=drawProgress;
        this.pos=pos;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        long longPos = buf.readLong();
        drawProgress = buf.readInt();
        pos = BlockPos.fromLong(longPos);
        try {
            fluid = FluidStack.loadFluidStackFromNBT(ByteBufUtils.readTag(buf));
        }catch(IndexOutOfBoundsException ioobe){
            fluid=null;
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());
        buf.writeInt(drawProgress);
        if(fluid!=null) {
            ByteBufUtils.writeTag(buf, fluid.writeToNBT(new NBTTagCompound()));
        }
    }

    public static class DrawNozzleUpdatePacketHandler implements IMessageHandler<DrawNozzleUpdatePacket, IMessage> {

        @Override
        public IMessage onMessage(DrawNozzleUpdatePacket message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(new MessageRunnable(message));
            return null;
        }
    }

    private static class MessageRunnable implements Runnable {

        DrawNozzleUpdatePacket message;

        public MessageRunnable(DrawNozzleUpdatePacket message){
            this.message=message;
        }

        @Override
        public void run() {
            TileEntity te = Minecraft.getMinecraft().theWorld.getTileEntity(message.pos);
            if(te instanceof DrawNozzleTileEntity) {
                DrawNozzleTileEntity tile = (DrawNozzleTileEntity) te;
                tile.extrudedQuantity=message.drawProgress;
                tile.fluidTank.setFluid(message.fluid);
            }
        }
    }
}
