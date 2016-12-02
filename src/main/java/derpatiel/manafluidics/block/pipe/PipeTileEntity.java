package derpatiel.manafluidics.block.pipe;

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
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jim on 11/26/2016.
 */
public class PipeTileEntity extends TileEntity implements ITickable {

    final FluidTank fluidTank;

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
        if((!this.getWorld().isRemote) && this.fluidTank.getFluidAmount()>0){
            FluidStack myFluid = fluidTank.getFluid();
            int maxFlow = Math.max(1,myFluid.amount/2);
            if(maxFlow>0){
                List<PipeTileEntity> adjacentSameTypePipes = new ArrayList<>();
                List<IFluidHandler> adjacentNonPipeHandlers = new ArrayList<>();

                for(EnumFacing facing : EnumFacing.VALUES){
                    if(PipeBlock.isSameTypePipe(worldObj.getBlockState(this.pos).getValue(PipeBlock.TYPE),this.pos.offset(facing),getWorld())) {
                        adjacentSameTypePipes.add((PipeTileEntity) worldObj.getTileEntity(this.pos.offset(facing)));
                    }
                    if(PipeBlock.canAccessNonPipeFluidHandler(getWorld(),pos.offset(facing),facing)){
                        IFluidHandler fluidHandler = this.getWorld().getTileEntity(pos.offset(facing)).getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY,facing.getOpposite());
                        adjacentNonPipeHandlers.add(fluidHandler);
                    }
                }

                List<IFluidHandler> pumpToHandlers = new ArrayList<>();
                for(IFluidHandler handler : adjacentNonPipeHandlers){
                    for(IFluidTankProperties properties : handler.getTankProperties()){
                        if(properties.getContents()==null || properties.getContents().getFluid().equals(myFluid.getFluid())) {
                            pumpToHandlers.add(handler);
                        }
                    }
                }

                //always pump to non-pipes first.  only pump into other pipes if no room.
                int pumpedFluid = 0;
                for (IFluidHandler handler : pumpToHandlers){
                    int amount = handler.fill(new FluidStack(myFluid.getFluid(),maxFlow/pumpToHandlers.size()),false);
                    if(amount>0){
                        amount = handler.fill(new FluidStack(myFluid.getFluid(),maxFlow/pumpToHandlers.size()),true);
                        this.fluidTank.drain(amount,true);
                        pumpedFluid+=amount;
                    }
                }
                if(pumpedFluid<maxFlow){
                    maxFlow=maxFlow-pumpedFluid;
                    int perPipePush = maxFlow/adjacentSameTypePipes.size();
                    if(perPipePush>0){
                        for(PipeTileEntity otherPipe : adjacentSameTypePipes){

                        }
                    }
                }
            }
        }
    }
}
