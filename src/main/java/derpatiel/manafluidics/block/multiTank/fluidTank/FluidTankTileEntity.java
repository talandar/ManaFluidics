package derpatiel.manafluidics.block.multiTank.fluidTank;

import derpatiel.manafluidics.block.multiTank.TankFormingTileEntity;
import derpatiel.manafluidics.network.FluidChangedPacket;
import derpatiel.manafluidics.network.MFPacketHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

public class FluidTankTileEntity extends TankFormingTileEntity {

    public final FluidTank tank = new MultiblockUpdatingFluidTank(0);

    @Override
    public void newHeight() {
        notifyFormed();
    }

    @Override
    public void doUpdate() {
    }

    @Override
    public boolean needsHeatInterface() {
        return false;
    }

    @Override
    public boolean needsItemInterface() {
        return false;
    }

    @Override
    public void notifyFormed() {
        setCapacityBySize();
    }

    public void setCapacityBySize(){
        tank.setCapacity(getNumInteriorBlocks() * TANK_CAPACITY_PER_BLOCK);
    }

    @Override
    public void notifyUnformed() {
        tank.setCapacity(0);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("tank",tank.writeToNBT(new NBTTagCompound()));
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        setCapacityBySize();
        tank.readFromNBT(compound.getCompoundTag("tank"));
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if((isValidConnectionDirection(facing) || facing==null) && capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
            return true;
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if((isValidConnectionDirection(facing) || facing==null) && capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
            return (T)tank;
        return super.getCapability(capability, facing);
    }

    private class MultiblockUpdatingFluidTank extends FluidTank{

        public MultiblockUpdatingFluidTank(int capacity) {
            super(capacity);
        }

        @Override
        protected void onContentsChanged() {
            super.onContentsChanged();
            MFPacketHandler.INSTANCE.sendToAll(new FluidChangedPacket(pos,tank.getFluid()));
        }
    }
}
