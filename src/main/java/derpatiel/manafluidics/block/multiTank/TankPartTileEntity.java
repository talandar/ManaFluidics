package derpatiel.manafluidics.block.multiTank;

import derpatiel.manafluidics.enums.TankPartState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

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

    public void setDirection(TankPartState state) {
        worldObj.setBlockState(getPos(), worldObj.getBlockState(getPos()).withProperty(MFTankEntityBlock.STATE,state));
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
        }
    }
}
