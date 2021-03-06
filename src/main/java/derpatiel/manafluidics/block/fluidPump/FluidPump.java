package derpatiel.manafluidics.block.fluidPump;

import com.google.common.collect.Sets;
import derpatiel.manafluidics.block.IDismantleable;
import derpatiel.manafluidics.block.IRedstoneToggled;
import derpatiel.manafluidics.block.IRotateable;
import derpatiel.manafluidics.block.MFTileBlock;
import derpatiel.manafluidics.registry.ModBlocks;
import derpatiel.manafluidics.util.ChatUtil;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by Jim on 11/24/2016.
 */
public class FluidPump extends MFTileBlock implements IDismantleable, IRotateable, IRedstoneToggled {

    public static final Set<EnumFacing> validFacings = Sets.newHashSet(EnumFacing.UP, EnumFacing.DOWN, EnumFacing.WEST,EnumFacing.EAST,EnumFacing.NORTH,EnumFacing.SOUTH);
    public static final PropertyDirection DIRECTION = PropertyDirection.create("direction", validFacings);

    //   N/S,E/W,U/D
    private static final AxisAlignedBB[] facingBoxes = new AxisAlignedBB[]{
            new AxisAlignedBB(0.3125D, 0.3125D, 0.0D, 0.6875D, 0.6875D, 0.5625),
            new AxisAlignedBB(0.3125D, 0.3125D, 0.4375D, 0.6875D, 0.6875D, 1.0D),
            new AxisAlignedBB(0.4375D, 0.3125D, 0.3125D, 1.0D, 0.6875D, 0.6875D),
            new AxisAlignedBB(0.0D, 0.3125D, 0.3125D, 0.5625, 0.6875D, 0.6875D),
    };


    public FluidPump(String unlocalizedName, Material material, float hardness, float resistance) {
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
        return new FluidPumpTileEntity();
    }

    @Override
    public ItemStack getDismantledStack(World world, BlockPos pos, IBlockState state) {
        return new ItemStack(ModBlocks.fluidPump);
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

    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facingHit, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        EnumFacing faceToUse = EnumFacing.EAST;
        if(validFacings.contains(facingHit.getOpposite())){
            faceToUse=facingHit.getOpposite();
        }
        List<EnumFacing> facingsWithFluidHandlers = new ArrayList<>();
        for(EnumFacing testFacing : validFacings){
            TileEntity maybeFluidHandler = worldIn.getTileEntity(pos.offset(testFacing));
            if(maybeFluidHandler!=null && maybeFluidHandler.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY,testFacing.getOpposite())){
                facingsWithFluidHandlers.add(testFacing);
            }
        }
        if(facingsWithFluidHandlers.size()==1 || (facingsWithFluidHandlers.size()>1 && !facingsWithFluidHandlers.contains(facingHit.getOpposite()))){
            faceToUse=facingsWithFluidHandlers.get(0);
        }
        return this.getStateFromMeta(faceToUse.getIndex());
    }

    @Override
    public void rotate(World worldIn, IBlockState currentState, BlockPos pos) {
        EnumFacing nextFacing = getNextFacing(currentState.getValue(DIRECTION));
        worldIn.setBlockState(pos,currentState.withProperty(DIRECTION,nextFacing));
    }

    @Override
    public Collection<EnumFacing> getValidFacings() {
        return validFacings;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer entityplayer, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if(entityplayer.isSneaking()){
            return super.onBlockActivated(world, pos, state, entityplayer, hand, heldItem, side, hitX, hitY, hitZ);
        }else {
            if (hand == EnumHand.MAIN_HAND) {
                //only trigger once!
                if (heldItem!=null && heldItem.getItem() == Items.REDSTONE) {
                    FluidPumpTileEntity tile = (FluidPumpTileEntity) world.getTileEntity(pos);
                    tile.setActivationType(getNextActivationType(tile.getActivationType()));
                    ChatUtil.sendNoSpam(entityplayer,"REDSTONE MODE: "+tile.getActivationType().getChatDescription());
                    return true;
                } else{
                    return false;
                }
            }else{
                return true;
            }
        }
    }

    @Override
    public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
        return false;
    }

    @SuppressWarnings("deprecation")
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn)
    {
        addCollisionBoxToList(pos, entityBox, collidingBoxes,getBoundingBox(state,worldIn,pos));
    }

    @SuppressWarnings("deprecation")
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return super.getBoundingBox(state,source,pos);
        /*
        state = this.getActualState(state, source, pos);
        EnumFacing facing = state.getValue(DIRECTION);
        switch(facing){
            case NORTH:
                return facingBoxes[0];
            case SOUTH:
                return facingBoxes[0];
            case EAST:
                return facingBoxes[1];
            case WEST:
                return facingBoxes[1];
            case UP:
                return facingBoxes[2];
            case DOWN:
                return facingBoxes[2];
        }
        return null;
        */
    }


}
