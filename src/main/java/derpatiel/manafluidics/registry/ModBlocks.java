package derpatiel.manafluidics.registry;

import derpatiel.manafluidics.ManaFluidics;
import derpatiel.manafluidics.block.*;
import derpatiel.manafluidics.block.drawNozzle.DrawNozzle;
import derpatiel.manafluidics.block.drawNozzle.DrawNozzleTileEntity;
import derpatiel.manafluidics.block.floatTable.FloatTable;
import derpatiel.manafluidics.block.fluidSpout.FluidSpout;
import derpatiel.manafluidics.block.multiTank.fluidTank.FluidTankController;
import derpatiel.manafluidics.block.multiTank.general.TankFluidConnection;
import derpatiel.manafluidics.block.multiTank.smeltingTank.SmeltingTankController;
import derpatiel.manafluidics.block.portableTank.PortableTank;
import derpatiel.manafluidics.block.portableTank.PortableTankItemBlock;
import derpatiel.manafluidics.enums.MaterialType;
import derpatiel.manafluidics.util.LOG;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.sound.sampled.Port;

public class ModBlocks {

    public static MFBlock crystalOre;
    public static MFBlock sheet;
    public static MFBlock tankBottom;
    public static MFBlock drawNozzle;
    public static MFBlock portableTank;
    public static MFBlock floatTable;
    public static MFBlock fluidTankConnector;
    public static MFBlock fluidSpout;

    public static MFBlock fluidTankController;
    public static MFBlock smeltingTankController;

    public static void registerBlocks(){
        LOG.info("registering blocks");
        crystalOre = register(new MFCustomDropsBlock("crystalore",Material.ROCK,1.0f,1.0f, ModItems.manaCrystal,0,2,true).setCreativeTab(ModItems.tabFluidics));
        sheet = register(new SheetBlock("sheet",Material.GLASS, 0.25f, 0.25f)).setCreativeTab(ModItems.tabFluidics);
        tankBottom = register(new MFDismantleableBlock("tankbottom",Material.ROCK,1.0f,1.0f).setCreativeTab(ModItems.tabFluidics));
        drawNozzle = register(new DrawNozzle("drawnozzle",Material.IRON,1.0f,1.0f).setCreativeTab(ModItems.tabFluidics));
        portableTank = register((PortableTank)new PortableTank("portabletank",Material.IRON,1.0f,1.0f).setCreativeTab(ModItems.tabFluidics));
        floatTable = register(new FloatTable("floattable",Material.IRON,1.0f,1.0f));//not in tab, not obtainable by itself
        fluidTankController = register(new FluidTankController("fluid_tank_controller",Material.IRON,1.0f,1.0f).setCreativeTab(ModItems.tabFluidics));
        fluidTankConnector = register(new TankFluidConnection("tank_basic_valve",Material.IRON,1.0f,1.0f).setCreativeTab(ModItems.tabFluidics));
        fluidSpout = register(new FluidSpout("fluidspout",Material.IRON,1.0f,1.0f).setCreativeTab(ModItems.tabFluidics));
        smeltingTankController = register(new SmeltingTankController("smeltingtank",Material.IRON,1.0f,1.0f).setCreativeTab(ModItems.tabFluidics));
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

    private static PortableTank register(PortableTank tank){
        ItemBlock itemBlock = new PortableTankItemBlock(tank);
        itemBlock.setRegistryName(tank.getRegistryName());
        return register(tank,itemBlock);
    }

    private static <T extends Block> T register(T block) {
        ItemBlock itemBlock = new ItemBlock(block);
        itemBlock.setRegistryName(block.getRegistryName());
        return register(block, itemBlock);
    }

}
