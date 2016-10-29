package derpatiel.manafluidics.network;

import derpatiel.manafluidics.block.castingchamber.CastingChamberTileEntity;
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

public class FluidChangedPacket implements IMessage {

    public FluidStack fluid;
    public BlockPos pos;

    public FluidChangedPacket(){}

    public FluidChangedPacket(BlockPos pos, FluidStack contents){
        this.fluid=contents;
        this.pos=pos;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        long longPos = buf.readLong();
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
        if(fluid!=null) {
            ByteBufUtils.writeTag(buf, fluid.writeToNBT(new NBTTagCompound()));
        }
    }

    public static class PortableTankFluidPacketMessageHandler implements IMessageHandler<FluidChangedPacket, IMessage> {

        @Override
        public IMessage onMessage(FluidChangedPacket message, MessageContext ctx) {
            TileEntity te = Minecraft.getMinecraft().theWorld.getTileEntity(message.pos);
            if(te instanceof PortableTankTileEntity) {
                PortableTankTileEntity tile = (PortableTankTileEntity) te;
                tile.fluidTank.setFluid(message.fluid);
            }else if(te instanceof CastingChamberTileEntity){
                CastingChamberTileEntity tile = (CastingChamberTileEntity)te;
                tile.tank.setFluid(message.fluid);
            }
            return null;
        }
    }
}
