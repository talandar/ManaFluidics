package derpatiel.manafluidics.block.drawNozzle;

import derpatiel.manafluidics.block.MFTileBlock;
import derpatiel.manafluidics.registry.ModItems;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

import javax.annotation.Nullable;

public class DrawNozzle extends MFTileBlock<DrawNozzleTileEntity> {

    public DrawNozzle(String unlocalizedName, Material material, float hardness, float resistance) {
        super(unlocalizedName,material,hardness,resistance);
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new DrawNozzleTileEntity();
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        final IFluidHandler fluidHandler = getFluidHandler(worldIn, pos);

        if (fluidHandler != null) {
            // Try fill/empty the held fluid container from the tank
            boolean success = FluidUtil.interactWithFluidHandler(heldItem, fluidHandler, playerIn);
/*
            // If the contents changed or this is the off hand, send a chat message to the player
            if (!worldIn.isRemote && (success || hand == EnumHand.OFF_HAND)) {
                TestMod3.network.sendTo(new MessageFluidTankContents(fluidHandler.getTankProperties()), (EntityPlayerMP) playerIn);
            }
*/
            // If the held item is a fluid container, stop processing here so it doesn't try to place its contents
            return FluidUtil.getFluidHandler(heldItem) != null;
        }

        return false;
    }
}
