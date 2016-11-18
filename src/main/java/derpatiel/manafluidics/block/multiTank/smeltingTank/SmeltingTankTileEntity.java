package derpatiel.manafluidics.block.multiTank.smeltingTank;

import derpatiel.manafluidics.block.multiTank.TankFormingTileEntity;
import derpatiel.manafluidics.fluid.MultiTank;
import derpatiel.manafluidics.util.LOG;
import derpatiel.manafluidics.util.MaterialItemHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import java.util.List;

public class SmeltingTankTileEntity extends TankFormingTileEntity {

    public static final float HEAT_FALLOFF_VALUE = 0.7f;
    public static final int MAX_SLOTS = 81;

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

        int heatConsumed = heatThisTick;//+ (int)(0.1f * (heatersThisTick-1) * heatThisTick); disable for now - too much bonus
        heatThisTick=0;
        heatersThisTick =0;

        //future option: items don't despawn in tank?
        if(!worldObj.isRemote) {
            AxisAlignedBB boundingBox = getRenderBoundingBox();
            List<EntityItem> itemsInTank = worldObj.getEntitiesWithinAABB(EntityItem.class, boundingBox);
            for (EntityItem item : itemsInTank) {
                ItemStack droppedStack = item.getEntityItem();
                int slot = 0;
                while (droppedStack != null && droppedStack.stackSize > 0 && slot < itemHandler.getSlots()) {
                    droppedStack = itemHandler.insertItem(slot, droppedStack, false);
                    slot++;
                }
                if (droppedStack == null || droppedStack.stackSize == 0) {
                    worldObj.removeEntity(item);
                } else {
                    item.setEntityItemStack(droppedStack);
                }
            }
        }

        int slot=0;
        while(heatConsumed>0 && slot<itemHandler.getSlots()){
            SmeltingItemHandler.MeltProgress slotProgress = itemHandler.getMeltProgressInSlot(slot);
            if(slotProgress!=null){
                slotProgress.addHeat(heatConsumed);
                heatConsumed = (int)(heatConsumed * HEAT_FALLOFF_VALUE);
                if(slotProgress.isMelted()){
                    FluidStack stack = MaterialItemHelper.getMeltOutput(itemHandler.getStackInSlot(slot));
                    itemHandler.setStackInSlot(slot,null);
                    //if fluid gets lost, oh well!
                    tank.fill(stack,true);
                }
            }
            slot++;
        }
    }

    @Override
    public void notifyFormed() {
        setCapacityBySize();
    }

    public void setCapacityBySize(){
        tank.setCapacity(getNumInteriorBlocks() * TANK_CAPACITY_PER_BLOCK);
        //max inventory size=81 slots, due to gui constraints
        itemHandler.setSize(Math.min(getNumInteriorBlocks(),MAX_SLOTS));
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
}
