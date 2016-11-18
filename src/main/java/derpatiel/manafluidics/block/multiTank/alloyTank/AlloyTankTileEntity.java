package derpatiel.manafluidics.block.multiTank.alloyTank;

import derpatiel.manafluidics.block.multiTank.TankFormingTileEntity;
import derpatiel.manafluidics.fluid.MultiTank;
import derpatiel.manafluidics.util.LOG;
import derpatiel.manafluidics.util.MaterialItemHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
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

import java.util.ArrayList;
import java.util.List;

public class AlloyTankTileEntity extends TankFormingTileEntity {

    public final MultiTank tank;

    public AlloyTankTileEntity(){
        tank = new MultiTank(this);
    }

    @Override
    public void doUpdate() {

    }

    public void doAlloy(EntityPlayer player, MaterialItemHelper.AlloyFormingRule selectedRule){

        LOG.info("player clicked alloy form button: "+player.getName());
        LOG.info("tried to make alloy of "+ selectedRule.output.getLocalizedName());

        boolean succeeded = true;
        while(succeeded){
            for(FluidStack ingredient : selectedRule.inputs) {
                FluidStack input = tank.drain(ingredient,false);
                if(input==null || input.amount!=ingredient.amount)
                    succeeded=false;
            }
            if(succeeded){
                for(FluidStack ingredient : selectedRule.inputs) {
                    tank.drain(ingredient,true);
                }
                tank.fill(selectedRule.output.copy(),true);
            }
        }

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
    public boolean needsHeatInterface() {
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
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        setCapacityBySize();
        tank.deserializeNBT(compound.getCompoundTag("tank"));
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if((isValidConnectionDirection(facing) || facing==null) && capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
            return true;
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if((isValidConnectionDirection(facing) || facing==null) && capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return (T) tank;
        }
        return super.getCapability(capability, facing);
    }
}
