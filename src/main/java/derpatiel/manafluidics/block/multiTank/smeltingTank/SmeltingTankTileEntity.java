package derpatiel.manafluidics.block.multiTank.smeltingTank;

import derpatiel.manafluidics.block.multiTank.TankFormingTileEntity;
import derpatiel.manafluidics.fluid.MultiTank;
import derpatiel.manafluidics.util.LOG;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class SmeltingTankTileEntity extends TankFormingTileEntity {

    public final MultiTank tank;
    public final SmeltingItemHandler itemHandler;

    private int heatThisTick=0;
    private int heatersThisTick =0;

    public SmeltingTankTileEntity(){
        tank = new MultiTank(this);
        itemHandler = new SmeltingItemHandler(this);
    }

    @Override
    public void doUpdate() {

        int heatConsumed = heatThisTick + (int)(0.1f * (heatersThisTick-1) * heatThisTick);
        heatThisTick=0;
        heatersThisTick =0;

        //check heat, add to melting, melt any needed items
        //add any items in tank location
    }

    @Override
    public void notifyFormed() {
        setCapacityBySize();
    }

    public void setCapacityBySize(){
        tank.setCapacity(getNumInteriorBlocks() * TANK_CAPACITY_PER_BLOCK);
        itemHandler.setSize(getNumInteriorBlocks());
    }

    @Override
    public void notifyUnformed() {
        tank.setCapacity(0);
        itemHandler.setSize(0);
    }

    @Override
    public boolean needsHeatInterface() {
        return true;
    }

    @Override
    public boolean needsIEMInterface() {
        return false;
    }

    @Override
    public boolean needsItemInterface() {
        return true;
    }

    @Override
    public void newHeight() {
        notifyFormed();
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("tank",tank.serializeNBT());
        compound.setTag("inventory",itemHandler.serializeNBT());
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        setCapacityBySize();
        tank.deserializeNBT(compound.getCompoundTag("tank"));
        itemHandler.deserializeNBT(compound.getCompoundTag("inventory"));
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if((isValidConnectionDirection(facing) || facing==null) && capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || capability== CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return true;
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if((isValidConnectionDirection(facing) || facing==null) && capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return (T) tank;
        }else if((isValidConnectionDirection(facing) || facing==null) && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
            return (T)itemHandler;
        }
        return super.getCapability(capability, facing);
    }

    public void addHeat(int heat){
        //each additional furnace is more efficient
        if(heat>0) {
            heatThisTick += heat;
            heatersThisTick++;
        }
    }

    public void onTankChanged(){
        //TODO: mark for update, send inventory... ?
    }
}
