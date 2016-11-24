package derpatiel.manafluidics.block.portableTank;

import derpatiel.manafluidics.network.MFPacketHandler;
import derpatiel.manafluidics.network.FluidChangedPacket;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class PortableTankTileEntity extends TileEntity implements ITickable{

    public static final int TANK_SIZE=8 * Fluid.BUCKET_VOLUME;
    public static final int MAX_DRAIN_PER_TICK = 100;//mb/tick

    public final FluidTank fluidTank;
    public boolean exporting=false;

    public PortableTankTileEntity(){
        super();
        fluidTank = new PortableTankFluidTank(TANK_SIZE);
    }

    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        readPortableData(compound);
        exporting = compound.getBoolean("exporting");
    }

    public NBTTagCompound readPortableData(NBTTagCompound compound){
        fluidTank.readFromNBT(compound);
        return compound;
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        writePortableData(compound);
        compound.setBoolean("exporting",exporting);
        return compound;
    }

    public NBTTagCompound writePortableData(NBTTagCompound compound){
        fluidTank.writeToNBT(compound);
        return compound;
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }



    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(getPos(), 0, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        readFromNBT(pkt.getNbtCompound());
        final IBlockState state = getWorld().getBlockState(getPos());
        getWorld().notifyBlockUpdate(getPos(), state, state, 3);
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
        return oldState.getBlock() != newSate.getBlock();
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
    {
        return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
            return (T) fluidTank;
        return super.getCapability(capability, facing);
    }

    @Override
    public void update() {
        if(!worldObj.isRemote && exporting && this.fluidTank.getFluidAmount()>0){
            TileEntity below = worldObj.getTileEntity(getPos().down());

            if(below!=null && below.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY,EnumFacing.UP)){
                IFluidHandler belowTank = below.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY,EnumFacing.UP);
                int drained = belowTank.fill(fluidTank.getFluid(),false);
                if(drained>0) {
                    if (drained > MAX_DRAIN_PER_TICK) {
                        FluidStack fluidStack = new FluidStack(fluidTank.getFluid().getFluid(), MAX_DRAIN_PER_TICK);
                        drained = belowTank.fill(fluidStack, true);
                        fluidTank.drain(drained, true);
                    } else {
                        drained = belowTank.fill(fluidTank.getFluid(), true);
                        fluidTank.drain(drained, true);
                    }
                    MFPacketHandler.INSTANCE.sendToAll(new FluidChangedPacket(pos,fluidTank.getFluid()));
                    markDirty();
                }
            }
        }
    }

    public void switchExport() {
        exporting = !exporting;
        getWorld().setBlockState(getPos(), getWorld().getBlockState(getPos()).withProperty(PortableTank.EXPORT, exporting));
    }


    private class PortableTankFluidTank extends FluidTank {
        public PortableTankFluidTank(int tankSize) {
            super(tankSize);
        }

        @Override
        public void onContentsChanged(){
            markDirty();
            MFPacketHandler.INSTANCE.sendToAll(new FluidChangedPacket(pos,getFluid()));
        }
    }
}
