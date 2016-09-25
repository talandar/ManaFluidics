package derpatiel.manafluidics.registry;

import derpatiel.manafluidics.ManaFluidics;
import derpatiel.manafluidics.block.MFBlock;
import derpatiel.manafluidics.block.MFCustomDropsBlock;
import derpatiel.manafluidics.block.MFTileBlock;
import derpatiel.manafluidics.block.SheetBlock;
import derpatiel.manafluidics.block.drawNozzle.DrawNozzle;
import derpatiel.manafluidics.block.drawNozzle.DrawNozzleTileEntity;
import derpatiel.manafluidics.block.portableTank.PortableTank;
import derpatiel.manafluidics.enums.MaterialType;
import derpatiel.manafluidics.util.LOG;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks {

    public static MFBlock crystalOre;
    public static MFBlock sheet;
    public static MFBlock tankBottom;
    public static MFBlock drawNozzle;
    public static MFBlock portableTank;

    public static void registerBlocks(){
        LOG.info("registering blocks");
        crystalOre = register(new MFCustomDropsBlock("crystalOre",Material.ROCK,1.0f,1.0f, ModItems.manaCrystal,0,2,true).setCreativeTab(ModItems.tabFluidics));
        sheet = register(new SheetBlock("sheet",Material.GLASS, 0.25f, 0.25f)).setCreativeTab(ModItems.tabFluidics);
        tankBottom = register(new MFBlock("tankBottom",Material.ROCK,1.0f,1.0f).setCreativeTab(ModItems.tabFluidics));
        drawNozzle = register(new DrawNozzle("drawNozzle",Material.IRON,1.0f,1.0f).setCreativeTab(ModItems.tabFluidics));
        portableTank = register(new PortableTank("portableTank",Material.IRON,1.0f,1.0f).setCreativeTab(ModItems.tabFluidics));
    }

    private static <T extends Block> T register(T block, ItemBlock itemBlock) {
        GameRegistry.register(block);
        GameRegistry.register(itemBlock);


        if (block instanceof MFBlock) {
            ((MFBlock) block).registerItemModel(itemBlock);
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
