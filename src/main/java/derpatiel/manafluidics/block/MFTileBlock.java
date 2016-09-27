package derpatiel.manafluidics.block;

import derpatiel.manafluidics.block.drawNozzle.DrawNozzleTileEntity;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

import javax.annotation.Nullable;

public abstract class MFTileBlock<TE extends TileEntity> extends MFBlock {

    public MFTileBlock(String unlocalizedName, Material material, float hardness, float resistance) {
        super(unlocalizedName, material, hardness, resistance);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @SuppressWarnings("deprecation")
    @Override
    public abstract EnumBlockRenderType getRenderType(IBlockState state);


    @Override
    public abstract TileEntity createTileEntity(World world, IBlockState state);


    /**
     * Get the {@link TileEntity} at the specified position.
     *
     * @param world The World
     * @param pos   The position
     * @return The TileEntity
     */
    @SuppressWarnings("unchecked")
    @Nullable
    protected TE getTileEntity(IBlockAccess world, BlockPos pos) {
        return (TE) world.getTileEntity(pos);
    }

    /**
     * Get a capability handler from an {@link ICapabilityProvider} if it exists.
     *
     * @param provider   The provider
     * @param capability The capability
     * @param facing     The facing
     * @param <T>        The handler type
     * @return The handler, if any.
     */
    @Nullable
    public static <T> T getCapability(@Nullable ICapabilityProvider provider, Capability<T> capability, @Nullable EnumFacing facing) {
        return provider != null && provider.hasCapability(capability, facing) ? provider.getCapability(capability, facing) : null;
    }


    @Nullable
    protected IFluidHandler getFluidHandler(IBlockAccess world, BlockPos pos) {
        return getCapability(getTileEntity(world, pos), CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
    }
}
