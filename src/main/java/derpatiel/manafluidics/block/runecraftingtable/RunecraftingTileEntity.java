package derpatiel.manafluidics.block.runecraftingtable;

import derpatiel.manafluidics.block.pipe.PipeBlock;
import derpatiel.manafluidics.network.FluidChangedPacket;
import derpatiel.manafluidics.network.MFPacketHandler;
import derpatiel.manafluidics.util.LOG;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPistonExtension;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockPistonStructureHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
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
import net.minecraftforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jim on 11/26/2016.
 */
public class RunecraftingTileEntity extends TileEntity implements ITickable {

    private boolean hadPistonHead;
    private IItemHandler craftingInventory = new RunecraftingTableInventory();

    public RunecraftingTileEntity(){
        hadPistonHead=false;
    }

    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        hadPistonHead = compound.getBoolean("pistonAbove");
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setBoolean("pistonAbove",hadPistonHead);
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

    public void craftOperation(){
        LOG.info("CRAFT");
    }

    @Override
    public void update() {
        if (!getWorld().isRemote) {
            IBlockState above = getWorld().getBlockState(this.getPos().offset(EnumFacing.UP));
            boolean isPistonAboveExtendedHead = false;
            if (above.getBlock() == Blocks.PISTON_HEAD) {
                if (above.getValue(BlockPistonExtension.TYPE) == BlockPistonExtension.EnumPistonType.DEFAULT && above.getValue(BlockPistonBase.FACING) == EnumFacing.DOWN) {
                    isPistonAboveExtendedHead = true;
                }
            }
            if (isPistonAboveExtendedHead && !hadPistonHead) {
                craftOperation();
            }
            hadPistonHead = isPistonAboveExtendedHead;
        }
    }
}
