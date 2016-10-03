package derpatiel.manafluidics.block.fluidSpout;

import com.google.common.collect.Sets;
import derpatiel.manafluidics.block.IDismantleable;
import derpatiel.manafluidics.block.IRedstonePulsed;
import derpatiel.manafluidics.block.IRotateable;
import derpatiel.manafluidics.block.MFTileBlock;
import derpatiel.manafluidics.registry.ModBlocks;
import derpatiel.manafluidics.registry.ModItems;
import derpatiel.manafluidics.util.ChatUtil;
import derpatiel.manafluidics.util.LOG;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class FluidSpout extends MFTileBlock implements IDismantleable, IRotateable, IRedstonePulsed {

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
                    FluidSpoutTileEntity tile = (FluidSpoutTileEntity) world.getTileEntity(pos);
                    tile.setActivationType(getNextActivationType(tile.getActivationType()));
                    ChatUtil.sendNoSpam(entityplayer,"REDSTONE MODE: "+tile.getActivationType().getChatDescription());
                    return true;
                } else if(heldItem==null || (heldItem!=null && heldItem.getItem()!= ModItems.crystal_hammer)){
                    FluidSpoutTileEntity tile = (FluidSpoutTileEntity) world.getTileEntity(pos);
                    tile.triggerActivated();
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


}