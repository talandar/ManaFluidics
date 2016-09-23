package derpatiel.manafluidics.registry;

import derpatiel.manafluidics.block.MFBlock;
import derpatiel.manafluidics.block.MFCustomDropsBlock;
import derpatiel.manafluidics.block.SheetBlock;
import derpatiel.manafluidics.enums.MaterialType;
import derpatiel.manafluidics.util.LOG;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemMultiTexture;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks {

    public static MFBlock crystalOre;
    public static MFBlock sheet;

    public static void registerBlocks(){
        LOG.info("registering blocks");
        crystalOre = register(new MFCustomDropsBlock("crystalOre",Material.ROCK,1.0f,1.0f, ModItems.manaCrystal,0,2,true).setCreativeTab(ModItems.tabFluidics));
        sheet = register(new SheetBlock("sheet",Material.GLASS, 0.25f, 0.25f)).setCreativeTab(ModItems.tabFluidics);
    }

    private static <T extends Block> T register(T block, ItemBlock itemBlock) {
        GameRegistry.register(block);
        GameRegistry.register(itemBlock);


        if (block instanceof MFBlock) {
            ((MFBlock)block).registerItemModel(itemBlock);
        }

        return block;
    }

    private static SheetBlock register(SheetBlock sheet){
        ItemBlock itemBlock = new ItemMultiTexture(sheet,sheet, MaterialType.getNames());
        itemBlock.setRegistryName(sheet.getRegistryName());
        return register(sheet,itemBlock);
    }

    private static <T extends Block> T register(T block) {
        ItemBlock itemBlock = new ItemBlock(block);
        itemBlock.setRegistryName(block.getRegistryName());
        return register(block, itemBlock);
    }
}
