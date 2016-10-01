package derpatiel.manafluidics.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IRotateable {

    public abstract void rotate(World worldIn, IBlockState currentState, BlockPos pos);
}
