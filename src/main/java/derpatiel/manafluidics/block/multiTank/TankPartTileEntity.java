package derpatiel.manafluidics.block.multiTank;

import derpatiel.manafluidics.enums.TankPartState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class TankPartTileEntity extends TileEntity {

    protected BlockPos parentLoc;

    public void markForUpdate() {
        markDirty();
        IBlockState state = getWorld().getBlockState(getPos());
        getWorld().notifyBlockUpdate(getPos(), state, state, 3);
    }

    public void setParent(BlockPos parentTankController){
        this.parentLoc=parentTankController;
    }

    public void clearParent() {
        this.parentLoc=null;
        worldObj.setBlockState(getPos(), worldObj.getBlockState(getPos()).withProperty(MFTankEntityBlock.STATE, TankPartState.UNFORMED));
        markForUpdate();
    }

    public BlockPos getParent() {
        return this.parentLoc;
    }

    public TankFormingTileEntity getParentTile(){
        TankFormingTileEntity tile = null;
        if(parentLoc!=null){
            tile = (TankFormingTileEntity) worldObj.getTileEntity(parentLoc);
        }
        return tile;

    }

    public void setDirection(TankPartState state) {
        worldObj.setBlockState(getPos(), worldObj.getBlockState(getPos()).withProperty(MFTankEntityBlock.STATE,state));
    }

    public boolean isValidConnectionDirection(EnumFacing facing){
        TankPartState state = worldObj.getBlockState(getPos()).getValue(MFTankEntityBlock.STATE);
        if(facing==null){
            return false;
        }
        switch(facing){
            case EAST:
                return state==TankPartState.EAST;
            case NORTH:
                return state==TankPartState.NORTH;
            case SOUTH:
                return state==TankPartState.SOUTH;
            case WEST:
                return state==TankPartState.WEST;
            default:
                return false;
        }
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
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        if(parentLoc!=null){
            compound.setLong("parent", parentLoc.toLong());
        }
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if(compound.hasKey("parent")){
            parentLoc=BlockPos.fromLong(compound.getLong("parent"));
        }else{
            parentLoc=null;
        }
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
        return oldState.getBlock() != newSate.getBlock();
    }
}
