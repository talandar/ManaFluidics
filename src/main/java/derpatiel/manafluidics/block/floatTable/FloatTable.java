package derpatiel.manafluidics.block.floatTable;

import derpatiel.manafluidics.block.IDismantleable;
import derpatiel.manafluidics.block.MFTileBlock;
import derpatiel.manafluidics.enums.CornerFacing;
import derpatiel.manafluidics.util.LOG;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nullable;
import java.util.Random;

public class FloatTable extends MFTileBlock<FloatTableTileEntity> implements IDismantleable {

    public static final PropertyEnum<CornerFacing> DIRECTION = PropertyEnum.create("direction", CornerFacing.class);
    public static final PropertyBool MAIN = PropertyBool.create("main");

    public FloatTable(String unlocalizedName, Material material, float hardness, float resistance) {
        super(unlocalizedName, material, hardness, resistance);
        this.setDefaultState(this.blockState.getBaseState().withProperty(MAIN,false));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, DIRECTION,MAIN);
    }


    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(MAIN,meta==0);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        boolean main = state.getValue(MAIN);
        if(main)
            return 0;
        return 1;
    }

    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos){
        FloatTableTileEntity tile = ((FloatTableTileEntity)worldIn.getTileEntity(pos));
        CornerFacing facing= CornerFacing.NORTH_EAST;
        if(tile!=null){
            facing = ((FloatTableTileEntity)worldIn.getTileEntity(pos)).getDirectionForThisBlock();
        }
        return state.withProperty(DIRECTION, facing);
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
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(Items.CAULDRON);
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new FloatTableTileEntity();
    }

    @Override
    public ItemStack getDismantledStack(World world, BlockPos pos, IBlockState state) {
        return new ItemStack(Items.CAULDRON);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer entityplayer, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {

        LOG.info("right click event");
        FloatTableTileEntity tile = (FloatTableTileEntity)world.getTileEntity(pos);
        tile = tile.getParent();
        ItemStack sheets = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,side).getStackInSlot(0);
        if(sheets!=null){
            entityplayer.inventory.addItemStackToInventory(sheets);
            if(sheets.stackSize==0){
                tile.itemHandler.sheets=null;
            }
        }

        if (heldItem != null) {
            return FluidUtil.interactWithFluidHandler(heldItem,(side==EnumFacing.DOWN ? tile.beneathFluidHandler : tile.fluidHandler),entityplayer);
        }

        if (super.onBlockActivated(world, pos, state, entityplayer, hand, heldItem, side, hitX, hitY, hitZ)){
            return true;
        }


        return false;
    }

    //taken from BlockFurnace in minecraft's source code directly.
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
        FloatTableTileEntity tile = (FloatTableTileEntity)worldIn.getTileEntity(pos);
        if (tile.isMain() && tile.getHardeningFraction()>0){

            for(BlockPos tilePos : tile.getAllParts())
            {
                if (rand.nextDouble() < 0.08D)
                {
                    worldIn.playSound((double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
                }
                double x = ((double)tilePos.getX())+(rand.nextGaussian()*0.5f);
                double y = ((double)tilePos.getY()+1)+(rand.nextGaussian()*0.5f);
                double z = ((double)tilePos.getZ())+(rand.nextGaussian()*0.5f);
                if(rand.nextDouble() < 0.1D) {
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x, y, z, 0.0D, 0.05D, 0.0D, new int[0]);
                }

            }
        }
    }
}
