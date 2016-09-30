package derpatiel.manafluidics.block.multiTank.fluidTank;

import derpatiel.manafluidics.block.multiTank.TankFormingTileEntity;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidTank;

public class FluidTankTileEntity extends TankFormingTileEntity {

    public static final int TANK_CAPACITY_PER_BLOCK=8* Fluid.BUCKET_VOLUME;

    public final FluidTank tank = new FluidTank(0);

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        if(this.isFormed()){
            AxisAlignedBB bb;
            bb = new AxisAlignedBB(this.getMinX(),this.getTankBaseY(),this.getMinZ(), this.getMaxX()+1,this.getMaxY(),this.getMaxZ()+1);
            return bb;
        }else{
            return super.getRenderBoundingBox();
        }

    }

    @Override
    public void newHeight() {
        notifyFormed();
    }

    @Override
    public void doUpdate() {

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
}
