package derpatiel.manafluidics.block.fluidSpout;

import derpatiel.manafluidics.enums.RedstoneActivation;
import derpatiel.manafluidics.util.LOG;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class FluidSpoutTileEntity extends TileEntity implements ITickable {


    private static final int MOVE_PER_ACTIVATION = Fluid.BUCKET_VOLUME;
    private static final int FLUID_MOVED_PER_TICK = 50;

    private int fluidLeftToMove=0;
    private RedstoneActivation activationType;
    private boolean lastPowerState;

    private FluidStack movingFluid;

    public FluidSpoutTileEntity(){
        super();
        activationType=RedstoneActivation.RAISING;
        lastPowerState=false;
        movingFluid=null;
    }

    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        fluidLeftToMove = compound.getInteger("remaining");
        activationType = RedstoneActivation.getById(compound.getInteger("activation"));
        lastPowerState=compound.getBoolean("powerState");
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setInteger("remaining",fluidLeftToMove);
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
        if(fluidLeftToMove>0){
            LOG.info(fluidLeftToMove+"mb left to move");
            //need two fluidhandlers, the one on the facing, and below
            IFluidHandler adjHandler=null;
            IFluidHandler belowHandler=null;

            EnumFacing sourceDir = getWorld().getBlockState(getPos()).getValue(FluidSpout.DIRECTION);
            BlockPos adjBlock = getPos().offset(sourceDir);
            TileEntity adjTile = getWorld().getTileEntity(adjBlock);
            if(adjTile!=null && adjTile.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY,sourceDir.getOpposite())) {
                adjHandler = adjTile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, sourceDir.getOpposite());
            }

            BlockPos beneathBlock = getPos().offset(EnumFacing.DOWN);
            TileEntity beneathTile = getWorld().getTileEntity(beneathBlock);
            if(beneathTile!=null && beneathTile.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY,EnumFacing.UP)){
                belowHandler = beneathTile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY,EnumFacing.UP);
            }

            if(adjHandler!=null && belowHandler!=null){
                FluidStack drained = adjHandler.drain(FLUID_MOVED_PER_TICK,false);
                int actuallyDrained = belowHandler.fill(drained,false);
                if(actuallyDrained>0){
                    belowHandler.fill(drained,true);
                    adjHandler.drain(actuallyDrained,true);
                    if(actuallyDrained==FLUID_MOVED_PER_TICK){
                        if(adjHandler.getTankProperties().length>0) {
                            movingFluid = adjHandler.getTankProperties()[0].getContents();
                            fluidLeftToMove -= FLUID_MOVED_PER_TICK;
                        }else{
                            movingFluid=null;
                            fluidLeftToMove=0;
                        }
                    }else{
                        fluidLeftToMove=0;
                        movingFluid=null;
                    }
                }else{//could not move any
                    fluidLeftToMove=0;
                    movingFluid=null;
                }
            }else{
                //no movement possible.
                fluidLeftToMove=0;
                movingFluid=null;
            }
        }else{
            movingFluid=null;
        }

        if(activationType!=RedstoneActivation.IGNORED){
            boolean currentPowerState = worldObj.isBlockPowered(getPos());
            if(currentPowerState!=lastPowerState && ((activationType==RedstoneActivation.RAISING && currentPowerState)||(activationType==RedstoneActivation.FALLING && !currentPowerState))){
                triggerActivated();
            }
        }


        lastPowerState=worldObj.isBlockPowered(getPos());
    }

    public FluidStack getMovingFluid(){
        return movingFluid;
    }

    public void triggerActivated(){
        LOG.info("spout triggered!");
        if(fluidLeftToMove==0){
            fluidLeftToMove=MOVE_PER_ACTIVATION;
        }
    }

    public void setActivationType(RedstoneActivation activationType){
        this.activationType=activationType;
    }
    public RedstoneActivation getActivationType(){
        return this.activationType;
    }
}
