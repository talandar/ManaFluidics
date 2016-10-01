package derpatiel.manafluidics.block.fluidSpout;

import com.google.common.collect.Sets;
import derpatiel.manafluidics.block.IDismantleable;
import derpatiel.manafluidics.block.IRotateable;
import derpatiel.manafluidics.block.MFTileBlock;
import derpatiel.manafluidics.block.floatTable.FloatTableTileEntity;
import derpatiel.manafluidics.enums.CornerFacing;
import derpatiel.manafluidics.registry.ModBlocks;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Set;

public class FluidSpout extends MFTileBlock implements IDismantleable, IRotateable {

    public static final Set<EnumFacing> validFacings = Sets.newHashSet(EnumFacing.WEST,EnumFacing.EAST,EnumFacing.NORTH,EnumFacing.SOUTH);
    public static final PropertyDirection DIRECTION = PropertyDirection.create("direction", validFacings);

    public FluidSpout(String unlocalizedName, Material material, float hardness, float resistance) {
        super(unlocalizedName, material, hardness, resistance);
        this.setDefaultState(this.blockState.getBaseState().withProperty(DIRECTION, EnumFacing.EAST));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, DIRECTION);
    }


    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new FluidSpoutTileEntity();
    }

    @Override
    public ItemStack getDismantledStack(World world, BlockPos pos, IBlockState state) {
        return new ItemStack(ModBlocks.fluidSpout);
    }

    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(DIRECTION, EnumFacing.getFront(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(DIRECTION).getIndex();
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

    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getStateFromMeta(EnumFacing.EAST.getIndex());
    }

    @Override
    public void rotate(World worldIn, IBlockState currentState, BlockPos pos) {
        int curFacingIndex = currentState.getValue(DIRECTION).getIndex();
        EnumFacing nextFacing = EnumFacing.getFront(++curFacingIndex);
        while(!validFacings.contains(nextFacing)){
            nextFacing = EnumFacing.getFront(++curFacingIndex);
        }
        worldIn.setBlockState(pos,currentState.withProperty(DIRECTION,nextFacing));
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer entityplayer, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        //TODO
        return super.onBlockActivated(world, pos, state, entityplayer, hand, heldItem, side, hitX, hitY, hitZ);
    }
}