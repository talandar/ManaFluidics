package derpatiel.manafluidics.block.fluidPump;

import derpatiel.manafluidics.block.fluidSpout.FluidSpout;
import derpatiel.manafluidics.enums.RedstoneActivation;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class FluidPumpTileEntity extends TileEntity implements ITickable {

    private static final int FLUID_MOVED_PER_TICK = 50;

    private RedstoneActivation activationType;
    private boolean lastPowerState;

    public FluidPumpTileEntity(){
        super();
        activationType=RedstoneActivation.HIGH;
        lastPowerState=false;
    }

    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        activationType = RedstoneActivation.getById(compound.getInteger("activation"));
        lastPowerState=compound.getBoolean("powerState");
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setInteger("activation",activationType.getId());
        compound.setBoolean("powerState",lastPowerState);
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
    public void update() {
        boolean hasRedstone = worldObj.isBlockPowered(getPos());
        EnumFacing sourceDir = getWorld().getBlockState(getPos()).getValue(FluidPump.DIRECTION);
        EnumFacing destDir = sourceDir.getOpposite();
        IFluidHandler sourceHandler = null;
        IFluidHandler destHandler = null;
        switch (activationType) {
            case DISABLED:
                //NO OP
                break;
            case HIGH:
                if (hasRedstone) {
                    sourceHandler = findHandler(sourceDir);
                    destHandler = findHandler(destDir);
                }
                break;
            case LOW:
                if (!hasRedstone) {
                    sourceHandler = findHandler(sourceDir);
                    destHandler = findHandler(destDir);
                }
                break;
            case IGNORED:
                //always do
                sourceHandler = findHandler(sourceDir);
                destHandler = findHandler(destDir);
                break;
        }
        if (sourceHandler != null && destHandler != null) {
            FluidStack drained = sourceHandler.drain(FLUID_MOVED_PER_TICK, false);
            int actuallyDrained = destHandler.fill(drained, false);
            if (actuallyDrained > 0) {
                destHandler.fill(drained, true);
                sourceHandler.drain(actuallyDrained, true);
            }
        }
    }

    private IFluidHandler findHandler(EnumFacing direction){
        BlockPos srcBlock = getPos().offset(direction);
        TileEntity srcTile = getWorld().getTileEntity(srcBlock);
        if(srcTile!=null && srcTile.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY,direction.getOpposite())) {
            return srcTile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, direction.getOpposite());
        }
        return null;
    }

    public void setActivationType(RedstoneActivation activationType){
        this.activationType=activationType;
    }
    public RedstoneActivation getActivationType(){
        return this.activationType;
    }
}
