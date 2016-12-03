package derpatiel.manafluidics.block.drawNozzle;

import derpatiel.manafluidics.network.DrawNozzleUpdatePacket;
import derpatiel.manafluidics.network.MFPacketHandler;
import derpatiel.manafluidics.registry.ModItems;
import derpatiel.manafluidics.util.MaterialItemHelper;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
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
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

public class DrawNozzleTileEntity extends TileEntity implements ITickable{

    public static final int TANK_SIZE=1 * Fluid.BUCKET_VOLUME;
    public static final int EXTRUSION_SIZE = Fluid.BUCKET_VOLUME / 8;
    public static final int EXTRUDE_PER_TICK = 5;

    public final FluidTank fluidTank;

    public int extrudedQuantity;
    private String lastFluidName;

    public DrawNozzleTileEntity(){
        super();
        fluidTank = new FluidTank(TANK_SIZE);
    }

    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        extrudedQuantity = compound.getInteger("extruded");
        fluidTank.readFromNBT(compound);
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        fluidTank.writeToNBT(compound);
        compound.setInteger("extruded",extrudedQuantity);
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
        if(!worldObj.isRemote && fluidTank.getFluid()!=null){
            Fluid fluid = fluidTank.getFluid().getFluid();
            if(MaterialItemHelper.fluidProductMap.containsKey(fluid)
                    && getWorld().getBlockState(getPos().down()).getMaterial()== Material.AIR){//only extrude if we have liquid, and the block below is air-like
                if(lastFluidName==null || !lastFluidName.equals(FluidRegistry.getFluidName(fluid))){
                    lastFluidName=FluidRegistry.getFluidName(fluid);
                    extrudedQuantity=0;
                }
                boolean needPacket=false;
                if(extrudedQuantity<EXTRUSION_SIZE){
                    //need to extrude
                    int maxExtrude = EXTRUSION_SIZE-extrudedQuantity;
                    maxExtrude = Math.min(maxExtrude, EXTRUDE_PER_TICK);
                    FluidStack drained = fluidTank.drain(maxExtrude, true);
                    extrudedQuantity+=drained.amount;
                    needPacket=true;
                }
                //now that we've done some work, check again.
                if(extrudedQuantity==EXTRUSION_SIZE) {
                    extrudedQuantity = 0;
                    ItemStack spawned = new ItemStack(ModItems.material_wire, 1, MaterialItemHelper.fluidProductMap.get(fluid).getID());
                    BlockPos spawnLoc = getPos().down();
                    EntityItem entityitem = new EntityItem(getWorld(), spawnLoc.getX() + 0.5f, spawnLoc.getY() + 0.5f, spawnLoc.getZ() + 0.5f, spawned);
                    entityitem.motionX = 0;
                    entityitem.motionY = 0;
                    entityitem.motionZ = 0;
                    getWorld().spawnEntityInWorld(entityitem);
                    needPacket = true;

                }
                if(needPacket){
                    MFPacketHandler.INSTANCE.sendToAll(new DrawNozzleUpdatePacket(pos,fluidTank.getFluid(),extrudedQuantity));
                }
            }//else nothing, not valid.
        }
    }


}
