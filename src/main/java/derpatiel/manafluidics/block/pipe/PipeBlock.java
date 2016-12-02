package derpatiel.manafluidics.block.pipe;

import derpatiel.manafluidics.ManaFluidics;
import derpatiel.manafluidics.block.IDismantleable;
import derpatiel.manafluidics.block.ITankPart;
import derpatiel.manafluidics.block.MFTileBlock;
import derpatiel.manafluidics.enums.MaterialType;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import java.util.List;

/**
 * Created by Jim on 11/26/2016.
 */
public class PipeBlock extends MFTileBlock implements IDismantleable {

    public static final PropertyEnum<MaterialType> TYPE = PropertyEnum.create("type", MaterialType.class);
    public static final PropertyBool NORTH = PropertyBool.create("north");
    public static final PropertyBool SOUTH = PropertyBool.create("south");
    public static final PropertyBool EAST = PropertyBool.create("east");
    public static final PropertyBool WEST = PropertyBool.create("west");
    public static final PropertyBool UP = PropertyBool.create("up");
    public static final PropertyBool DOWN = PropertyBool.create("down");

    public PipeBlock(String unlocalizedName, Material material, float hardness, float resistance) {
        super(unlocalizedName, material, hardness, resistance);
        this.setDefaultState(this.blockState.getBaseState()
                .withProperty(TYPE,MaterialType.CRYSTAL)
                .withProperty(NORTH,false)
                .withProperty(SOUTH,false)
                .withProperty(EAST,false)
                .withProperty(WEST,false)
                .withProperty(UP,false)
                .withProperty(DOWN,false));
    }

    @Override
    public void registerItemModel(ItemBlock itemBlock){
        for(MaterialType type : MaterialType.VALUES) {
            ManaFluidics.proxy.registerItemRenderer(itemBlock, type.getID(), bareUnlocalizedName+"_"+type.getName());
        }
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
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] { TYPE,NORTH,SOUTH,EAST,WEST,UP,DOWN });
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getStateFromMeta(int meta) {
        MaterialType state=MaterialType.getById(meta);
        return getDefaultState().withProperty(TYPE, state);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        MaterialType type = state.getValue(TYPE);
        return type.getID();
    }

    @Override
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state);
    }

    @Override
    public boolean isVisuallyOpaque() {
        return false;
    }

    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
        for(MaterialType materialType : MaterialType.VALUES){
            list.add(new ItemStack(itemIn, 1, materialType.getID()));
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos){
        boolean north = shouldConnect(state,pos,worldIn,EnumFacing.NORTH);
        boolean south = shouldConnect(state,pos,worldIn,EnumFacing.SOUTH);
        boolean east = shouldConnect(state,pos,worldIn,EnumFacing.EAST);
        boolean west = shouldConnect(state,pos,worldIn,EnumFacing.WEST);
        boolean up = shouldConnect(state,pos,worldIn,EnumFacing.UP);
        boolean down = shouldConnect(state,pos,worldIn,EnumFacing.DOWN);

        return state.withProperty(NORTH, north).withProperty(SOUTH, south).withProperty(EAST, east).withProperty(WEST, west).withProperty(UP,up).withProperty(DOWN,down);
    }

    public static boolean shouldConnect(IBlockState state,BlockPos pos,IBlockAccess worldIn,EnumFacing direction){
        return isSameTypePipe(state.getValue(TYPE),pos.offset(direction),worldIn) || canAccessNonPipeFluidHandler(worldIn,pos.offset(direction),direction.getOpposite());
    }

    public static boolean isSameTypePipe(MaterialType type, BlockPos pos, IBlockAccess worldIn){
        IBlockState state = worldIn.getBlockState(pos);
        return state.getBlock() instanceof PipeBlock && state.getValue(TYPE)==type;
    }

    public static boolean canAccessNonPipeFluidHandler(IBlockAccess world, BlockPos pos, EnumFacing tankFacing){
        TileEntity tile = world.getTileEntity(pos);
        if(tile!=null){
            return !(tile instanceof PipeTileEntity) && tile.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY,tankFacing);
        }
        return false;
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(Item.getItemFromBlock(this), 1, this.getMetaFromState(world.getBlockState(pos)));
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        BlockPos other = pos.offset(side);
        return !(shouldConnect(blockState,other,blockAccess,side));
    }

    @Override
    public ItemStack getDismantledStack(World world, BlockPos pos, IBlockState state) {
        return new ItemStack(Item.getItemFromBlock(this), 1, this.getMetaFromState(world.getBlockState(pos)));
    }


    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new PipeTileEntity();
    }
}
