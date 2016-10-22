package derpatiel.manafluidics.block.portableTank;

import derpatiel.manafluidics.block.IDismantleable;
import derpatiel.manafluidics.block.MFTileBlock;
import derpatiel.manafluidics.block.drawNozzle.DrawNozzleTileEntity;
import derpatiel.manafluidics.enums.MaterialType;
import derpatiel.manafluidics.registry.ModBlocks;
import derpatiel.manafluidics.registry.ModItems;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;

import javax.annotation.Nullable;
import java.util.List;

public class PortableTank extends MFTileBlock<PortableTankTileEntity> implements IDismantleable{

    public static final PropertyBool EXPORT = PropertyBool.create("export");

    private static final AxisAlignedBB boundingBox = new AxisAlignedBB(0.0625D,0.0D,0.0625D,0.9375D,1.0D,0.9375D);

    public PortableTank(String unlocalizedName, Material material, float hardness, float resistance) {
        super(unlocalizedName,material,hardness,resistance);
        this.setDefaultState(this.blockState.getBaseState().withProperty(EXPORT, false));
    }


    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] { EXPORT });
    }

    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getStateFromMeta(int meta) {
        boolean exporting=(meta==1);
        return getDefaultState().withProperty(EXPORT, exporting);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        if(state.getValue(EXPORT))
            return 1;
        return 0;
    }

    @Override
    public boolean isVisuallyOpaque() {
        return false;
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state);
    }

    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos){
        return state;
    }


    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new PortableTankTileEntity();
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

            // If the held item is a fluid container, stop processing here so it doesn't try to place its contents
            if(FluidUtil.getFluidHandler(heldItem) != null){
                return true;
            }
        }

        if(heldItem.getItem()== ModItems.crystal_hammer){
            getTileEntity(worldIn,pos).switchExport();
            return true;
        }

        return false;
    }

    @Override
    public ItemStack getDismantledStack(World world, BlockPos pos, IBlockState state) {
        ItemStack stack = new ItemStack(ModBlocks.portableTank,1,0);
        NBTTagCompound tags = new NBTTagCompound();
        PortableTankTileEntity te = (PortableTankTileEntity) world.getTileEntity(pos);
        if(te.fluidTank.getFluidAmount()>0){
            te.writePortableData(tags);
            stack.setTagCompound(tags);
        }

        return stack;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
                                ItemStack stack) {

        PortableTankTileEntity te = (PortableTankTileEntity)worldIn.getTileEntity(pos);
        NBTTagCompound compound = stack.getTagCompound();
        if(compound!=null){
            te.readPortableData(compound);
        }
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    }

    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn)
    {
        addCollisionBoxToList(pos, entityBox, collidingBoxes,getBoundingBox(state,worldIn,pos));
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return boundingBox;
    }
}
