package derpatiel.manafluidics.block.floatTable;

import derpatiel.manafluidics.enums.MaterialType;
import derpatiel.manafluidics.enums.CornerFacing;
import derpatiel.manafluidics.util.FluidRenderBounds;
import derpatiel.manafluidics.util.MaterialItemHelper;
import derpatiel.manafluidics.util.NBTHelper;
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
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.ArrayList;
import java.util.List;

public class FloatTableTileEntity extends TileEntity implements ITickable {

    private static final int HARDENING_TIME = 5*20;//5 seconds

    final FloatTableFluidHandler fluidHandler;
    final FloatTableBelowFluidHandler beneathFluidHandler;
    final FloatTableItemHandler itemHandler;

    final FluidTank manaTank;
    protected final FluidTank reactantTank;

    CornerFacing facing;

    private boolean main;
    BlockPos parent;

    BlockPos NE;
    BlockPos NW;
    BlockPos SE;
    BlockPos SW;

    int timeSpentHardening=0;

    public FloatTableTileEntity(){
        fluidHandler = new FloatTableFluidHandler(this);
        beneathFluidHandler = new FloatTableBelowFluidHandler(this);
        itemHandler = new FloatTableItemHandler(this);
        facing = CornerFacing.NORTH_WEST;
        reactantTank = new FluidTank(Fluid.BUCKET_VOLUME);
        manaTank = new FluidTank(Fluid.BUCKET_VOLUME * 3);
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }



    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(getPos(), isMain()? 0 : 1, getUpdateTag());
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
        return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
        if(!main){
            return worldObj.getTileEntity(parent).getCapability(capability,facing);
        }
        if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            if(facing == EnumFacing.DOWN){
                return (T) beneathFluidHandler;
            }else {
                return (T) fluidHandler;
            }
        }else if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
            return (T)itemHandler;
        }
        return super.getCapability(capability, facing);
    }

    public FluidRenderBounds getFluidRenderBounds(boolean mana) {
        FluidRenderBounds bounds=new FluidRenderBounds();

        float wallWidth = 2.0f/16.0f;

        bounds.w=1.0f-wallWidth;
        bounds.d=1.0f-wallWidth;
        bounds.h=0.001f;

        if(this.facing== CornerFacing.NORTH_WEST){
            bounds.x=wallWidth;
            bounds.z=wallWidth;
        }else if(this.facing== CornerFacing.NORTH_EAST){
            bounds.x=0f;//wallWidth;
            bounds.z=wallWidth;//0f;
        }else if(this.facing== CornerFacing.SOUTH_WEST){
            bounds.x=wallWidth;//0f;
            bounds.z=0f;//wallWidth;
        }else{//SOUTHEAST
            bounds.x=0f;
            bounds.z=0f;
        }

        if(!mana && getParent().hasReactant()){
            float reactantFraction = ((float)getParent().reactantTank.getFluidAmount())/((float)getParent().reactantTank.getCapacity());
            bounds.y = (12.0f/16.0f)+(reactantFraction*(4.0f/16.0f));
        }
        if(mana && getParent().hasMana()){
            //render mana
            //LOG.info("mana present");
            float manaFraction = ((float)getParent().manaTank.getFluidAmount())/((float)getParent().manaTank.getCapacity());
            bounds.y = (2.0f/16.0f)+(manaFraction*(10.0f/16.0f));
        }

        return bounds;

    }


    public void setMainBlock(BlockPos pos){
        main=(pos==this.getPos());
        this.parent=pos;
    }

    public boolean isMain(){
        return this.main;
    }

    public FloatTableTileEntity getParent(){
        if(parent!=null)
            return (FloatTableTileEntity) this.getWorld().getTileEntity(parent);
        return null;
    }

    public void setOthers(List<BlockPos> others){
        BlockPos myPos = this.getPos();
        if(others.contains(myPos.north())){
            if(others.contains(myPos.east())){//have north and east, so SOUTH_WEST
                facing = CornerFacing.SOUTH_WEST;
                SW=myPos;
                NW=myPos.north();
                SE=myPos.east();
                NE=myPos.east().north();
            }else{//must have west (and north), so SE
                facing = CornerFacing.SOUTH_EAST;
                SE=myPos;
                SW=myPos.west();
                NE=myPos.north();
                NW=myPos.west().north();
            }
        }else{//must have south
            if(others.contains(myPos.east())){//have south and east, so NW
                facing = CornerFacing.NORTH_WEST;
                NW=myPos;
                SW=myPos.south();
                NE=myPos.east();
                SE=myPos.east().south();
            }else{//must have west (and south), so NE
                facing = CornerFacing.NORTH_EAST;
                NE=myPos;
                NW=myPos.west();
                SE=myPos.south();
                SW=myPos.west().south();
            }
        }
    }

    public CornerFacing getDirectionForThisBlock() {
        if(facing!=null){
            return facing;
        }
        return CornerFacing.NORTH_EAST;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setBoolean("main", main);
        compound.setInteger("facing", facing.getID());
        compound.setIntArray("NEPos", NBTHelper.BlockPosToIntArray(NE));
        compound.setIntArray("NWPos", NBTHelper.BlockPosToIntArray(NW));
        compound.setIntArray("SEPos", NBTHelper.BlockPosToIntArray(SE));
        compound.setIntArray("SWPos", NBTHelper.BlockPosToIntArray(SW));
        compound.setIntArray("parent",NBTHelper.BlockPosToIntArray(parent));
        compound.setTag("manaTank", manaTank.writeToNBT(new NBTTagCompound()));
        compound.setTag("reactantTank", reactantTank.writeToNBT(new NBTTagCompound()));
        if(itemHandler.sheets!=null){
            compound.setTag("sheets", itemHandler.sheets.writeToNBT(new NBTTagCompound()));
        }
        return compound;
    }


    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.main=compound.getBoolean("main");
        this.facing= CornerFacing.getById(compound.getInteger("facing"));
        this.NE=NBTHelper.IntArrayToBlockPos(compound.getIntArray("NEPos"));
        this.NW= NBTHelper.IntArrayToBlockPos(compound.getIntArray("NWPos"));
        this.SE=NBTHelper.IntArrayToBlockPos(compound.getIntArray("SEPos"));
        this.SW=NBTHelper.IntArrayToBlockPos(compound.getIntArray("SWPos"));
        this.parent=NBTHelper.IntArrayToBlockPos(compound.getIntArray("parent"));
        this.manaTank.readFromNBT(compound.getCompoundTag("manaTank"));
        this.reactantTank.readFromNBT(compound.getCompoundTag("reactantTank"));
        if(compound.hasKey("sheets")) {
            this.itemHandler.sheets = ItemStack.loadItemStackFromNBT(compound.getCompoundTag("sheets"));
        }else{
            this.itemHandler.sheets=null;
        }
    }

    @Override
    public void update() {

        if(main){
            if(manaTank.getCapacity()==manaTank.getFluidAmount() && reactantTank.getCapacity()==reactantTank.getFluidAmount()){
                timeSpentHardening++;
                if(timeSpentHardening>=HARDENING_TIME){
                    timeSpentHardening=0;
                    MaterialType sheetType = MaterialItemHelper.fluidProductMap.get(reactantTank.getFluid().getFluid());
                    reactantTank.drain(reactantTank.getCapacity(), true);//empty tank
                    itemHandler.floatTableHarden(sheetType);
                }
            }
        }
    }

    public List<BlockPos> getOthers() {
        List<BlockPos> others = new ArrayList<BlockPos>();
        others.add(NE);
        others.add(NW);
        others.add(SE);
        others.add(SW);
        others.remove(this.getPos());
        return others;
    }
    public boolean hasReactant() {
        return reactantTank.getFluidAmount()>0;
    }

    public boolean hasMana(){
        return manaTank.getFluidAmount()>0;
    }

    public float getHardeningFraction(){
        return ((float)this.timeSpentHardening)/((float)HARDENING_TIME);
    }

}
