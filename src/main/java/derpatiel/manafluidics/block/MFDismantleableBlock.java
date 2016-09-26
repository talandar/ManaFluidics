package derpatiel.manafluidics.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MFDismantleableBlock extends MFBlock implements IDismantleable{

    public MFDismantleableBlock(String unlocalizedName, Material material, float hardness, float resistance){
        super(unlocalizedName,material,hardness,resistance);
    }

    @Override
    public ItemStack getDismantledStack(World world, BlockPos pos, IBlockState state) {
        return new ItemStack(this);
    }
}
