package derpatiel.manafluidics.block.furnaceHeater;

import derpatiel.manafluidics.capability.heat.CapabilityHeat;
import derpatiel.manafluidics.capability.heat.HeatProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class FurnaceHeaterTileEntity extends TileEntity implements ITickable {

    //100 ticks/smelt in a furnace
    //100 HU/t from burning in this inventory
    //therefore, 10000 HU/smelt
    private final HeatProvider heatProvider = new HeatProvider(100);
    private final ItemStackHandler itemHandler = new ItemStackHandler(1);

    private int ticksLeft=0;


    @Override
    public void update() {
        boolean wasGenerating = ticksLeft>0;
        if(ticksLeft>0) {
            ticksLeft--;
            heatProvider.generateHeat();
        }else{//ticks==0
            int fuelValue = TileEntityFurnace.getItemBurnTime(itemHandler.getStackInSlot(0));
            if(fuelValue>0){
                ItemStack burningStack = itemHandler.getStackInSlot(0);
                burningStack.stackSize--;
                if(burningStack.stackSize == 0) {
                    burningStack = burningStack.getItem().getContainerItem(burningStack);
                }
                if(burningStack!=null){
                    itemHandler.setStackInSlot(0,burningStack);
                }else{
                    itemHandler.setStackInSlot(0,null);
                }
                ticksLeft=fuelValue;
                markDirty();
            }
        }
        boolean isGenerating = ticksLeft>0;
        if(isGenerating!=wasGenerating){
            worldObj.setBlockState(getPos(),worldObj.getBlockState(getPos()).withProperty(FurnaceHeater.GENERATING,isGenerating),3);
        }
    }

    public boolean isGenerating(){
        return ticksLeft>0;
    }

    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        ticksLeft = compound.getInteger("ticksLeft");
        compound.setTag("inventory",itemHandler.serializeNBT());
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setInteger("ticksLeft",ticksLeft);
        itemHandler.deserializeNBT(compound.getCompoundTag("inventory"));
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
        if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || capability == CapabilityHeat.HEAT) {
            return true;
        }
        return super.hasCapability(capability,facing);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
        if (capability == CapabilityHeat.HEAT) {
            return (T) heatProvider;
        }else if(capability==CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
            return (T) itemHandler;
        }
        return super.getCapability(capability, facing);
    }
}
