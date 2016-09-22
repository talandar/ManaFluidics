package derpatiel.manafluidics.block;

import derpatiel.manafluidics.enums.MaterialType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public class SheetBlock extends MFBlock implements ITankPart, IMetaBlockName{

    public static final PropertyEnum<MaterialType> TYPE = PropertyEnum.create("type", MaterialType.class);
    public static final PropertyBool NORTH = PropertyBool.create("north");
    public static final PropertyBool SOUTH = PropertyBool.create("south");
    public static final PropertyBool EAST = PropertyBool.create("east");
    public static final PropertyBool WEST = PropertyBool.create("west");

    public SheetBlock(String unlocalizedName, Material material, float hardness, float resistance) {
        super(unlocalizedName,material,hardness,resistance);
        this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE,MaterialType.CRYSTAL));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] { TYPE,NORTH,SOUTH,EAST,WEST });
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

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
        for(MaterialType sheetType : MaterialType.values()){
            list.add(new ItemStack(itemIn, 1, sheetType.getID()));
        }
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos){
        boolean north = worldIn.getBlockState(pos.north()).getBlock() instanceof ITankPart;
        boolean south = worldIn.getBlockState(pos.south()).getBlock() instanceof ITankPart;
        boolean east = worldIn.getBlockState(pos.east()).getBlock() instanceof ITankPart;
        boolean west = worldIn.getBlockState(pos.west()).getBlock() instanceof ITankPart;

        return state.withProperty(NORTH, north).withProperty(SOUTH, south).withProperty(EAST, east).withProperty(WEST, west);
    }

    @Override
    public String getSpecialName(ItemStack stack) {
        return ((MaterialType)getStateFromMeta(stack.getMetadata()).getValue(TYPE)).getName();
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(Item.getItemFromBlock(this), 1, this.getMetaFromState(world.getBlockState(pos)));
    }

    public String getResourceLocation(int id){
        MaterialType state=MaterialType.getById(id);
        return super.getResourceLocation()+"_"+state.getName();
    }


}
