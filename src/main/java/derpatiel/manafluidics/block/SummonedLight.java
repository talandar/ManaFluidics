package derpatiel.manafluidics.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;

public class SummonedLight extends MFBlock {
    public SummonedLight(String unlocalizedName, Material material, float hardness, float resistance) {
        super(unlocalizedName, material, hardness, resistance);
    }

    @Override
    public int getLightValue(IBlockState state) {
        return 15;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public boolean isVisuallyOpaque() {
        return false;
    }


}
