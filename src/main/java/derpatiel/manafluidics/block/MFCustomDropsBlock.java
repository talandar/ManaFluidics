package derpatiel.manafluidics.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class MFCustomDropsBlock extends MFBlock {

    private final Item itemDropped;
    private final int itemDroppedMeta;
    private final int numItemsDropped;
    private final boolean doFortune;

    public MFCustomDropsBlock(String unlocalizedName, Material material, float hardness, float resistance, Item itemDropped, int meta, int numItemsDropped, boolean doesFortune) {
        super(unlocalizedName, material, hardness, resistance);
        this.itemDropped = itemDropped;
        this.numItemsDropped = numItemsDropped;
        this.doFortune = doesFortune;
        this.itemDroppedMeta = meta;
    }

    /**
     * Get the quantity dropped based on the given fortune level
     */
    public int quantityDroppedWithBonus(int fortune, Random random)
    {
        return this.quantityDropped(random) + (doFortune ? random.nextInt(fortune + 1) : 0);
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random random)
    {
        return numItemsDropped;
    }

    @Nullable
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return itemDropped;
    }

    @Override
    public int damageDropped(IBlockState state) {
        return itemDroppedMeta;
    }
}
