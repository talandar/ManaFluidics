package derpatiel.manafluidics.block.pipe;

import derpatiel.manafluidics.network.FluidChangedPacket;
import derpatiel.manafluidics.network.MFPacketHandler;
import net.minecraft.block.state.IBlockState;
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
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fluids.capability.templates.FluidHandlerConcatenate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jim on 11/26/2016.
 */
public class PipeTileEntity extends TileEntity implements ITickable {

    public final float TICK_FLUID_OUT_PER_CONNECTION=20f;

    public final FluidTank fluidTank;

    public PipeTileEntity(){
        fluidTank = new FluidTank(Fluid.BUCKET_VOLUME);
    }

    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        fluidTank.readFromNBT(compound);
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        fluidTank.writeToNBT(compound);
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
        if(facing==null || facing!=EnumFacing.DOWN) {
            return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
        }
        return super.hasCapability(capability,facing);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY && facing!=EnumFacing.DOWN)
            return (T) fluidTank;
        return super.getCapability(capability, facing);
    }

    @Override
    public void update() {
        if ((!this.getWorld().isRemote) && this.fluidTank.getFluidAmount() > 0) {
            FluidStack myFluid = fluidTank.getFluid();
            List<PipeTileEntity> adjacentSameTypePipes = new ArrayList<>();
            List<IFluidHandler> adjacentNonPipeHandlers = new ArrayList<>();

            for (EnumFacing facing : EnumFacing.VALUES) {
                if (PipeBlock.isSameTypePipe(worldObj.getBlockState(this.pos).getValue(PipeBlock.TYPE), this.pos.offset(facing), getWorld())) {
                    adjacentSameTypePipes.add((PipeTileEntity) worldObj.getTileEntity(this.pos.offset(facing)));
                }
                if (PipeBlock.canAccessNonPipeFluidHandler(getWorld(), pos.offset(facing), facing.getOpposite())) {
                    IFluidHandler fluidHandler = this.getWorld().getTileEntity(pos.offset(facing)).getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing.getOpposite());
                    adjacentNonPipeHandlers.add(fluidHandler);
                }
            }

            //always pump to non-pipes first.  only pump into other pipes if no room.
            int numoutputs = adjacentNonPipeHandlers.size()+adjacentSameTypePipes.size();
            boolean transferedSome = false;
            if(numoutputs>0) {
                int perOutputRate = Math.max(1,(int)Math.min(TICK_FLUID_OUT_PER_CONNECTION,Math.ceil(0.75f * (float)fluidTank.getFluidAmount()))/numoutputs);
                for (IFluidHandler handler : adjacentNonPipeHandlers) {
                    transferedSome |= (FluidUtil.tryFluidTransfer(handler, fluidTank, perOutputRate, true) != null);
                }
                for (PipeTileEntity pipe : adjacentSameTypePipes) {
                    if (pipe.fluidTank.getFluidAmount() < this.fluidTank.getFluidAmount()) {
                        transferedSome |= (FluidUtil.tryFluidTransfer(pipe.fluidTank, fluidTank, perOutputRate, true) != null);
                    }
                }
            }
            if(transferedSome){
                markDirty();
                MFPacketHandler.INSTANCE.sendToAll(new FluidChangedPacket(this.pos,this.fluidTank.getFluid()));
            }
        }
    }

    public float getFluidFillPercent(){
        return ((float)fluidTank.getFluidAmount())/((float)fluidTank.getCapacity());
    }
}
