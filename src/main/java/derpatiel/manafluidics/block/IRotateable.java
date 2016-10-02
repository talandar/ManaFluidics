package derpatiel.manafluidics.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Collection;

public interface IRotateable {

    public abstract void rotate(World worldIn, IBlockState currentState, BlockPos pos);

    public abstract Collection<EnumFacing> getValidFacings();

    public default EnumFacing getNextFacing(EnumFacing currentFacing){
        int curFacingIndex = currentFacing.getIndex();
        EnumFacing nextFacing = EnumFacing.getFront(++curFacingIndex);
        while(!getValidFacings().contains(nextFacing)){
            nextFacing = EnumFacing.getFront(++curFacingIndex);
        }
        return nextFacing;
    }
}
