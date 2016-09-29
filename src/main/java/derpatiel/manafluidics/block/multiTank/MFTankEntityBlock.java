package derpatiel.manafluidics.block.multiTank;

import derpatiel.manafluidics.block.MFTileBlock;
import derpatiel.manafluidics.enums.TankPartState;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;

public abstract class MFTankEntityBlock<T extends TankPartTileEntity> extends MFTileBlock {

    public static final PropertyEnum<TankPartState> STATE = PropertyEnum.create("direction", TankPartState.class);

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


}
