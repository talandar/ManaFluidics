package derpatiel.manafluidics.block.furnaceHeater;

import derpatiel.manafluidics.block.IDismantleable;
import derpatiel.manafluidics.block.MFTileBlock;
import derpatiel.manafluidics.block.floatTable.FloatTableTileEntity;
import derpatiel.manafluidics.enums.CornerFacing;
import derpatiel.manafluidics.registry.ModBlocks;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
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
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;

import javax.annotation.Nullable;

public class FurnaceHeater extends MFTileBlock implements IDismantleable{

    public static final PropertyBool GENERATING = PropertyBool.create("generating");
    public static final PropertyDirection FACING = BlockHorizontal.FACING;


    public FurnaceHeater(String unlocalizedName, Material material, float hardness, float resistance) {
        super(unlocalizedName, material, hardness, resistance);
        this.setDefaultState(this.blockState.getBaseState().withProperty(GENERATING,false).withProperty(FACING,EnumFacing.NORTH));
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new FurnaceHeaterTileEntity();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, heldItem, side, hitX, hitY, hitZ);
    }

    @Override
    public ItemStack getDismantledStack(World world, BlockPos pos, IBlockState state) {
        return new ItemStack(ModBlocks.furnaceHeater);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, GENERATING,FACING);
    }

    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos){
        FurnaceHeaterTileEntity tile = ((FurnaceHeaterTileEntity)worldIn.getTileEntity(pos));
        boolean generating=false;
        if(tile!=null){
            generating=tile.isGenerating();
        }
        return state.withProperty(GENERATING, generating);
    }

    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing enumfacing = EnumFacing.getFront(meta);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y)
        {
            enumfacing = EnumFacing.NORTH;
        }

        return this.getDefaultState().withProperty(FACING, enumfacing);
    }

    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(FACING).getIndex();
    }

    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

}
