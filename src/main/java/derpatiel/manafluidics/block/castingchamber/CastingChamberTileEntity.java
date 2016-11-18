package derpatiel.manafluidics.block.castingchamber;

import derpatiel.manafluidics.capability.item.SideWrappingItemHandler;
import derpatiel.manafluidics.item.MFMoldItem;
import derpatiel.manafluidics.util.MaterialItemHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
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
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.Map;

public class CastingChamberTileEntity extends TileEntity implements ITickable {

    private final CastingChamberItemHandler inventory;
    public final CastingChamberFluidTank tank;

    private final SideWrappingItemHandler upWrappedInventory;
    private final SideWrappingItemHandler sideWrappedInventories;
    private ItemStack coolingItem;

    private int coolingTime;
    private int coolingDone;

    public CastingChamberTileEntity(){
        inventory = new CastingChamberItemHandler(this);
        tank = new CastingChamberFluidTank(this,8 * Fluid.BUCKET_VOLUME);
        upWrappedInventory = new SideWrappingItemHandler(inventory,CastingChamberItemHandler.MOLD_SLOT);
        sideWrappedInventories = new SideWrappingItemHandler(inventory,CastingChamberItemHandler.OUTPUT_SLOT);
    }

    @Override
    public void update() {

        if(coolingItem!=null){
            coolingDone++;
            if(coolingDone>=coolingTime){
                if(inventory.insertItem(CastingChamberItemHandler.OUTPUT_SLOT,coolingItem,true)==null){
                    if(!worldObj.isRemote) {
                        inventory.insertItem(CastingChamberItemHandler.OUTPUT_SLOT, coolingItem, false);
                    }
                    coolingItem=null;
                    coolingDone=0;
                }
            }
        }


        MFMoldItem mold = inventory.getMold();
        FluidStack fluid = tank.getFluid();

        if(coolingItem==null && mold!=null && fluid!=null && fluid.amount>0){

            Map<FluidStack,ItemStack> moldProducts = MaterialItemHelper.castingProducts.get(mold);
            for(FluidStack fluidStack : moldProducts.keySet()){
                if(fluidStack.getFluid()==fluid.getFluid() && fluidStack.amount<= fluid.amount){
                    //we have a mold and enough of a valid fluid.
                    if(inventory.getStackInSlot(CastingChamberItemHandler.OUTPUT_SLOT)==null || ItemHandlerHelper.canItemStacksStack(inventory.getStackInSlot(CastingChamberItemHandler.OUTPUT_SLOT),moldProducts.get(fluidStack))) {
                        coolingTime = (int) (MaterialItemHelper.COOLING_CONSTANT * fluidStack.amount);
                        coolingDone = 0;
                        coolingItem = moldProducts.get(fluidStack).copy();
                        tank.drain(fluidStack.amount, true);
                    }
                }
            }

        }
    }

    private void moldChanged(){
        coolingDone=0;
        coolingItem=null;
    }

    public float coolingFraction(){
        if(coolingTime>0 && coolingDone>0){
            return ((float)coolingDone)/((float)coolingTime);
        }
        return 1;
    }

    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        inventory.deserializeNBT(compound.getCompoundTag("inventory"));
        tank.readFromNBT(compound.getCompoundTag("tank"));
        moldChanged();
        coolingDone=compound.getInteger("coolingDone");
        //coolingTime=compound.getInteger("coolingTime");
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("inventory",inventory.serializeNBT());
        compound.setTag("tank",tank.writeToNBT(new NBTTagCompound()));
        compound.setInteger("coolingDone",coolingDone);
        //compound.setInteger("coolingTime",coolingTime);
        return compound;
    }

    public void inventoryChanged(int slot){
        if(slot==inventory.MOLD_SLOT){
            moldChanged();
        }
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY){
            return true;
        }else if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY){
            return (T)tank;
        }else if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
            if(facing==null){
                return (T)inventory;
            }else if(facing==EnumFacing.UP)
                return (T)upWrappedInventory;
            else{
                return (T)sideWrappedInventories;
            }
        }
        return super.getCapability(capability, facing);
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
}
