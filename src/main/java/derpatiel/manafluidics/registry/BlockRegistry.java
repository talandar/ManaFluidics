package derpatiel.manafluidics.registry;

import derpatiel.manafluidics.block.MFBlock;
import derpatiel.manafluidics.block.MFCustomDropsBlock;
import derpatiel.manafluidics.util.LOG;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockRegistry {

    public static MFBlock crystalOre;

    public static void registerBlocks(){
        LOG.info("registering blocks");
        crystalOre = register(new MFCustomDropsBlock("crystalOre",Material.ROCK,1.0f,1.0f,ItemRegistry.manaCrystal,0,2,true).setCreativeTab(ItemRegistry.tabFluidics));
    }

    private static <T extends Block> T register(T block, ItemBlock itemBlock) {
        GameRegistry.register(block);
        GameRegistry.register(itemBlock);

        if (block instanceof MFBlock) {
            ((MFBlock)block).registerItemModel(itemBlock);
        }

        return block;
    }

    private static <T extends Block> T register(T block) {
        ItemBlock itemBlock = new ItemBlock(block);
        itemBlock.setRegistryName(block.getRegistryName());
        return register(block, itemBlock);
    }
}
