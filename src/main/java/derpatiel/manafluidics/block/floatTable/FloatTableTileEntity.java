package derpatiel.manafluidics.block.floatTable;

import derpatiel.manafluidics.enums.TableFormationState;
import derpatiel.manafluidics.util.NBTHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.List;

public class FloatTableTileEntity extends TileEntity {

    FloatTableFluidHandler fluidHandler;

    TableFormationState facing;

    private boolean main;
    BlockPos parent;

    BlockPos NE;
    BlockPos NW;
    BlockPos SE;
    BlockPos SW;

    int timeSpentHardening=0;

    ItemStack sheets;

    public FloatTableTileEntity(){
        fluidHandler = new FloatTableFluidHandler();
        facing = TableFormationState.UNFORMED;
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
        return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
        //extra logic here, not all tanks are available everywhere...
        //also items are extract only
        //TODO
        return super.getCapability(capability, facing);
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
            if(others.contains(myPos.east())){//have north and east, so SW
                facing = TableFormationState.SW;
                SW=myPos;
                NW=myPos.north();
                SE=myPos.east();
                NE=myPos.east().north();
            }else{//must have west (and north), so SE
                facing = TableFormationState.SE;
                SE=myPos;
                SW=myPos.west();
                NE=myPos.north();
                NW=myPos.west().north();
            }
        }else{//must have south
            if(others.contains(myPos.east())){//have south and east, so NW
                facing = TableFormationState.NW;
                NW=myPos;
                SW=myPos.south();
                NE=myPos.east();
                SE=myPos.east().south();
            }else{//must have west (and south), so NE
                facing = TableFormationState.NE;
                NE=myPos;
                NW=myPos.west();
                SE=myPos.south();
                SW=myPos.west().south();
            }
        }
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
        compound.setTag("tank", fluidHandler.serializeNBT());
        if(sheets!=null){
            compound.setTag("sheets", sheets.writeToNBT(new NBTTagCompound()));
        }
        return compound;
    }


    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.main=compound.getBoolean("main");
        this.facing=TableFormationState.getById(compound.getInteger("facing"));
        this.NE=NBTHelper.IntArrayToBlockPos(compound.getIntArray("NEPos"));
        this.NW= NBTHelper.IntArrayToBlockPos(compound.getIntArray("NWPos"));
        this.SE=NBTHelper.IntArrayToBlockPos(compound.getIntArray("SEPos"));
        this.SW=NBTHelper.IntArrayToBlockPos(compound.getIntArray("SWPos"));
        this.parent=NBTHelper.IntArrayToBlockPos(compound.getIntArray("parent"));
        this.fluidHandler.deserializeNBT(compound.getCompoundTag("tank"));
        this.sheets=ItemStack.loadItemStackFromNBT(compound.getCompoundTag("sheets"));
    }


}
