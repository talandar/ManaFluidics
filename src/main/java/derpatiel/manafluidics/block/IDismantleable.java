package derpatiel.manafluidics.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IDismantleable {

    public ItemStack getDismantledStack(World world, BlockPos pos, IBlockState state);


}
