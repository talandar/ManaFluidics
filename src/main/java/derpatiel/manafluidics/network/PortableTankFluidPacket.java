package derpatiel.manafluidics.network;

import derpatiel.manafluidics.block.multiTank.smeltingTank.SmeltingTankTileEntity;
import derpatiel.manafluidics.block.portableTank.PortableTank;
import derpatiel.manafluidics.block.portableTank.PortableTankTileEntity;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PortableTankFluidPacket implements IMessage {

    public FluidStack fluid;
    public BlockPos pos;

    public PortableTankFluidPacket(){}

    public PortableTankFluidPacket(BlockPos pos, FluidStack contents){
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

    public static class PortableTankFluidPacketMessageHandler implements IMessageHandler<PortableTankFluidPacket, IMessage> {

        @Override
        public IMessage onMessage(PortableTankFluidPacket message, MessageContext ctx) {
            PortableTankTileEntity tile = (PortableTankTileEntity)Minecraft.getMinecraft().theWorld.getTileEntity(message.pos);
            tile.fluidTank.setFluid(message.fluid);
            return null;
        }
    }
}
