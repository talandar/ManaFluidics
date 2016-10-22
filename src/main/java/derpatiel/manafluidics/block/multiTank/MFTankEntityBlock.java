package derpatiel.manafluidics.block.multiTank;

import derpatiel.manafluidics.block.IDismantleable;
import derpatiel.manafluidics.block.ITankPart;
import derpatiel.manafluidics.block.MFTileBlock;
import derpatiel.manafluidics.enums.TankPartState;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public abstract class MFTankEntityBlock<T extends TankPartTileEntity> extends MFTileBlock implements IDismantleable, ITankPart {

    public static final PropertyEnum<TankPartState> STATE = PropertyEnum.create("direction", TankPartState.class);


    //UNFORMED,N,S,E,W
    private static final AxisAlignedBB[] facingBoxes = new AxisAlignedBB[]{
            new AxisAlignedBB(0.0D,0.0D,0.0D,1.0D,1.0D,1.0D),
            new AxisAlignedBB(0.0D,0.0D,0.0D,1.0D,1.0D,0.625D),
            new AxisAlignedBB(0.0D,0.0D,0.375D,1.0D,1.0D,1.0D),
            new AxisAlignedBB(0.375D,0.0D,0.0D,1.0D,1.0D,1.0D),
            new AxisAlignedBB(0.0D,0.0D,0.0D,0.625D,1.0D,1.0D),
    };

    public MFTankEntityBlock(String unlocalizedName, Material material, float hardness, float resistance) {
        super(unlocalizedName, material, hardness, resistance);
        this.setDefaultState(this.blockState.getBaseState().withProperty(STATE, TankPartState.UNFORMED));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] { STATE });
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(STATE,TankPartState.getById(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(STATE).getID();
    }

    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn)
    {
        addCollisionBoxToList(pos, entityBox, collidingBoxes,getBoundingBox(state,worldIn,pos));
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        TankPartState tankState = state.getValue(STATE);
        return facingBoxes[tankState.getID()];
    }

}
