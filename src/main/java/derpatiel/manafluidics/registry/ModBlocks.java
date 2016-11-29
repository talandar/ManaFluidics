package derpatiel.manafluidics.registry;

import derpatiel.manafluidics.block.*;
import derpatiel.manafluidics.block.altar.KnowledgeAltar;
import derpatiel.manafluidics.block.castingchamber.CastingChamber;
import derpatiel.manafluidics.block.drawNozzle.DrawNozzle;
import derpatiel.manafluidics.block.floatTable.FloatTable;
import derpatiel.manafluidics.block.fluidPump.FluidPump;
import derpatiel.manafluidics.block.fluidSpout.FluidSpout;
import derpatiel.manafluidics.block.furnaceHeater.FurnaceHeater;
import derpatiel.manafluidics.block.multiTank.alloyTank.AlloyTankController;
import derpatiel.manafluidics.block.multiTank.fluidTank.FluidTankController;
import derpatiel.manafluidics.block.multiTank.general.fluid.TankFluidConnection;
import derpatiel.manafluidics.block.multiTank.general.heat.TankHeatConnection;
import derpatiel.manafluidics.block.multiTank.general.item.TankItemConnection;
import derpatiel.manafluidics.block.multiTank.smeltingTank.SmeltingTankController;
import derpatiel.manafluidics.block.pipe.PipeBlock;
import derpatiel.manafluidics.block.portableTank.PortableTank;
import derpatiel.manafluidics.block.portableTank.PortableTankItemBlock;
import derpatiel.manafluidics.enums.MaterialType;
import derpatiel.manafluidics.util.LOG;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemMultiTexture;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.nio.channels.Pipe;

public class ModBlocks {

    public static MFBlock crystalOre;
    public static MFBlock crystalBlock;
    public static MFBlock sheet;
    public static MFBlock tankBottom;
    public static MFBlock drawNozzle;
    public static MFBlock portableTank;
    public static MFBlock floatTable;
    public static MFBlock fluidTankConnector;
    public static MFBlock heatTankConnector;
    public static MFBlock itemTankConnector;
    public static MFBlock fluidSpout;
    public static MFBlock furnaceHeater;
    public static MFBlock fluidTankController;
    public static MFBlock smeltingTankController;
    public static MFBlock castingChamber;
    public static MFBlock knowledgeAltar;
    public static MFBlock alloyTankController;
    public static MFBlock crystallineIronBlock;
    public static MFBlock fluidPump;
    public static MFBlock fluidPipe;
    public static MFBlock summonedLight;

    public static void registerBlocks(){
        LOG.info("registering blocks");
        crystalOre = register(new MFCustomDropsBlock("crystalore",Material.ROCK,1.0f,1.0f, ModItems.manaCrystal,0,2,true).setCreativeTab(ModItems.tabFluidics));
        crystalBlock = register(new MFTransparentBlock("crystalblock",Material.GLASS,0.5f,0.5f)).setCreativeTab(ModItems.tabFluidics);
        sheet = register(new SheetBlock("sheet",Material.GLASS, 0.25f, 0.25f)).setCreativeTab(ModItems.tabFluidics);
        tankBottom = register(new MFDismantleableBlock("tankbottom",Material.ROCK,1.0f,1.0f).setCreativeTab(ModItems.tabFluidics));
        drawNozzle = register(new DrawNozzle("drawnozzle",Material.IRON,1.0f,1.0f).setCreativeTab(ModItems.tabFluidics));
        portableTank = register((PortableTank)new PortableTank("portabletank",Material.IRON,1.0f,1.0f).setCreativeTab(ModItems.tabFluidics));
        floatTable = register(new FloatTable("floattable",Material.IRON,1.0f,1.0f));//not in tab, not obtainable by itself
        fluidTankController = register(new FluidTankController("fluid_tank_controller",Material.IRON,1.0f,1.0f).setCreativeTab(ModItems.tabFluidics));
        fluidTankConnector = register(new TankFluidConnection("tank_basic_valve",Material.IRON,1.0f,1.0f).setCreativeTab(ModItems.tabFluidics));
        heatTankConnector = register(new TankHeatConnection("tank_heat_valve",Material.IRON,1.0f,1.0f).setCreativeTab(ModItems.tabFluidics));
        itemTankConnector = register(new TankItemConnection("tank_item_valve",Material.IRON,1.0f,1.0f).setCreativeTab(ModItems.tabFluidics));
        fluidSpout = register(new FluidSpout("fluidspout",Material.IRON,1.0f,1.0f).setCreativeTab(ModItems.tabFluidics));
        smeltingTankController = register(new SmeltingTankController("smelting_tank_controller",Material.IRON,1.0f,1.0f).setCreativeTab(ModItems.tabFluidics));
        furnaceHeater = register(new FurnaceHeater("furnaceheater",Material.IRON,1.0f,1.0f).setCreativeTab(ModItems.tabFluidics));
        castingChamber = register(new CastingChamber("castingchamber",Material.IRON,1.0f,1.0f).setCreativeTab(ModItems.tabFluidics));
        knowledgeAltar = register(new KnowledgeAltar("knowledgealtar",Material.IRON,1.0f,1.0f).setCreativeTab(ModItems.tabFluidics));
        alloyTankController = register(new AlloyTankController("alloy_tank_controller",Material.IRON,1.0f,1.0f).setCreativeTab(ModItems.tabFluidics));
        crystallineIronBlock=register( new MFBlock("crystalline_iron_block",Material.IRON,1.0f,1.0f).setCreativeTab(ModItems.tabFluidics));
        fluidPump = register(new FluidPump("fluidpump",Material.IRON,1.0f,1.0f).setCreativeTab(ModItems.tabFluidics));
        fluidPipe = registerPipe((PipeBlock)new PipeBlock("fluidpipe", Material.IRON,1.0f,1.0f).setCreativeTab(ModItems.tabFluidics));
        summonedLight = register(new SummonedLight("summonedlight",Material.AIR,0.0001f,0.0001f).setCreativeTab(ModItems.tabFluidics));
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

    private static PipeBlock registerPipe(PipeBlock pipe){
        ItemBlock itemBlock = new ItemMultiTexture(pipe,pipe, MaterialType.getNames());
        itemBlock.setRegistryName(pipe.getRegistryName());
        return register(pipe,itemBlock);
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
