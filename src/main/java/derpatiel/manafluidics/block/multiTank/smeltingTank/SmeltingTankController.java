package derpatiel.manafluidics.block.multiTank.smeltingTank;

import derpatiel.manafluidics.block.multiTank.MFTankControllerBlock;
import derpatiel.manafluidics.block.multiTank.fluidTank.FluidTankTileEntity;
import derpatiel.manafluidics.registry.ModBlocks;
import derpatiel.manafluidics.util.LOG;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.FluidTankProperties;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import javax.annotation.Nullable;

public class SmeltingTankController extends MFTankControllerBlock {

    public SmeltingTankController(String unlocalizedName, Material material, float hardness, float resistance) {
        super(unlocalizedName, material, hardness, resistance);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (heldItem != null) {
            SmeltingTankTileEntity tile = ((SmeltingTankTileEntity)worldIn.getTileEntity(pos));
            IFluidHandler handler = tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY,null);
            for(IFluidTankProperties tankProperties : handler.getTankProperties()){
                LOG.info(tankProperties.getContents().getFluid().getName()+": "+tankProperties.getContents().amount+"mb");
            }
            /* TODO: ui, etc
            if(heldItem.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null)) {
                tile.markForUpdate();
                return FluidUtil.interactWithFluidHandler(heldItem, tile.tank, playerIn);
            }
            */
        }
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, heldItem, side, hitX, hitY, hitZ);
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new SmeltingTankTileEntity();
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
    public boolean isVisuallyOpaque() {
        return false;
    }

    @Override
    public ItemStack getDismantledStack(World world, BlockPos pos, IBlockState state) {
        return new ItemStack(ModBlocks.smeltingTankController);
    }

}
