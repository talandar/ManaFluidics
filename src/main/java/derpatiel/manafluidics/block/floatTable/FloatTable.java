package derpatiel.manafluidics.block.floatTable;

import derpatiel.manafluidics.block.IDismantleable;
import derpatiel.manafluidics.block.MFTileBlock;
import derpatiel.manafluidics.enums.TableFormationState;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class FloatTable extends MFTileBlock<FloatTableTileEntity> implements IDismantleable {

    public static final PropertyEnum<TableFormationState> DIRECTION = PropertyEnum.create("direction", TableFormationState.class);
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
        TableFormationState facing=TableFormationState.NORTH_EAST;
        if(tile!=null){
            facing = ((FloatTableTileEntity)worldIn.getTileEntity(pos)).getDirectionForThisBlock();
        }
        return state.withProperty(DIRECTION, facing);
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
}
