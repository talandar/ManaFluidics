package derpatiel.manafluidics.util;

import derpatiel.manafluidics.ManaFluidics;
import derpatiel.manafluidics.enums.MaterialType;
import derpatiel.manafluidics.registry.ModBlocks;
import derpatiel.manafluidics.registry.ModFluids;
import derpatiel.manafluidics.registry.ModItems;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.network.internal.HandshakeCompletionHandler;

import java.util.*;

public class MaterialItemHelper {

    public static final  Map<MaterialType,String> sheetResourceDomain = new HashMap<>();
    public static final Map<MaterialType,String> sheetResourceLocation = new HashMap<>();
    public static final Map<Fluid,MaterialType> fluidProductMap = new HashMap<>();
    public static final Map<MaterialType,Fluid> productFluidMap = new HashMap<>();

    public static final Map<Block,MeltingInformation> meltableBlocks = new HashMap<>();
    public static final Map<Item,MeltingInformation> meltableItems = new HashMap<>();

    static{


        registerMaterialFluid(ModFluids.moltenCrystal, MaterialType.CRYSTAL);
        registerMaterialFluid(FluidRegistry.LAVA, MaterialType.OBSIDIAN);
        registerMaterialFluid(ModFluids.moltenIron, MaterialType.IRON);
        registerMaterialFluid(ModFluids.moltenGold, MaterialType.GOLD);

        sheetResourceLocation.put(MaterialType.CRYSTAL, "blocks/sheet_crystal");
        sheetResourceLocation.put(MaterialType.GOLD, "blocks/gold_block");
        sheetResourceLocation.put(MaterialType.IRON, "blocks/iron_block");
        sheetResourceLocation.put(MaterialType.OBSIDIAN, "blocks/obsidian");

        sheetResourceDomain.put(MaterialType.CRYSTAL, ManaFluidics.MODID);
        sheetResourceDomain.put(MaterialType.GOLD, "minecraft");
        sheetResourceDomain.put(MaterialType.IRON, "minecraft");
        sheetResourceDomain.put(MaterialType.OBSIDIAN, "minecraft");

        /*
        200ticks/smelt for ores.  smelt time proportional to output quantity (100ticks/ingot)
        100HU/furnace
        ->20,000 heat/1000mb
        ->20heat/mb
         */

        meltableBlocks.put(ModBlocks.sheet, new MetaMeltingInformation(250));
        meltableBlocks.put(ModBlocks.tankBottom, new MeltingInformation(new FluidStack(FluidRegistry.LAVA,500)));
        meltableBlocks.put(Blocks.IRON_ORE, new MeltingInformation(new FluidStack(ModFluids.moltenIron,1000)));
        meltableBlocks.put(Blocks.IRON_BLOCK, new MeltingInformation(new FluidStack(ModFluids.moltenIron,4500)));
        meltableBlocks.put(Blocks.GOLD_BLOCK, new MeltingInformation(new FluidStack(ModFluids.moltenGold,4500)));
        meltableBlocks.put(Blocks.GOLD_ORE, new MeltingInformation(new FluidStack(ModFluids.moltenGold,1000)));
        meltableBlocks.put(Blocks.OBSIDIAN, new MeltingInformation(new FluidStack(FluidRegistry.LAVA,1000)));

        meltableItems.put(ModItems.manaCrystal, new MeltingInformation(new FluidStack(ModFluids.moltenCrystal,500)));
        meltableItems.put(ModItems.material_wire,new MetaMeltingInformation(125));
        meltableItems.put(ModItems.control_circuit,new MetaMeltingInformation(500));

        //TODO: lots of vanilla items, blocks where applicable
        meltableItems.put(Items.IRON_INGOT,new MeltingInformation(new FluidStack(ModFluids.moltenIron,500)));
        meltableItems.put(Items.GOLD_INGOT,new MeltingInformation(new FluidStack(ModFluids.moltenGold,500)));




    }

    private static void registerMaterialFluid(Fluid fluid, MaterialType type){
        fluidProductMap.put(fluid,type);
        productFluidMap.put(type,fluid);
    }

    public static ItemStack getWire(MaterialType type) {
        return getWire(type, 1);
    }

    public static ItemStack getWire(MaterialType type, int quantity){
        return new ItemStack(ModItems.material_wire,quantity,type.getID());
    }

    public static ItemStack getCircuit(MaterialType type){ return getCircuit(type,1);}
    public static ItemStack getCircuit(MaterialType type, int quantity){
        return new ItemStack(ModItems.control_circuit,quantity,type.getID());
    }

    public static ItemStack getSheet(MaterialType type) {
        return getSheet(type, 1);
    }
    public static ItemStack getSheet(MaterialType type, int quantity){
        return new ItemStack(ModBlocks.sheet,quantity,type.getID());
    }

    public static boolean isMeltable(ItemStack stack){
        if(stack==null || stack.stackSize==0)
            return false;
        Item item = stack.getItem();
        if(item instanceof ItemBlock){
            Block block = ((ItemBlock)item).getBlock();
            return meltableBlocks.keySet().contains(block);
        }else{//not an ItemBlock, just an Item
            return meltableItems.keySet().contains(item);
        }
    }

    public static int getMeltHeat(ItemStack stack){
        if(stack==null || stack.stackSize==0 || !isMeltable(stack))
            return -1;
        Item item = stack.getItem();
        if(item instanceof ItemBlock){
            Block block = ((ItemBlock)item).getBlock();
            return meltableBlocks.get(block).requiredHeat;

        }else{//not an ItemBlock, just an Item
            return meltableItems.get(item).requiredHeat;
        }
    }

    public static FluidStack getMeltOutput(ItemStack stack){
        if(stack==null || stack.stackSize==0 || !isMeltable(stack))
            return null;
        Item item = stack.getItem();
        MeltingInformation info=null;
        if(item instanceof ItemBlock){
            Block block = ((ItemBlock)item).getBlock();
            info = meltableBlocks.get(block);

        }else{//not an ItemBlock, just an Item
            info = meltableItems.get(item);
        }
        if(info==null) {
            return null;
        }
        if(info instanceof MetaMeltingInformation){
            MetaMeltingInformation metaInfo = (MetaMeltingInformation)info;
            MaterialType type = MaterialType.getById(stack.getMetadata());
            return new FluidStack(productFluidMap.get(type),metaInfo.materialFluidResult);

        }else{
            return info.result;
        }
    }

    public static String getIngotsString(FluidStack fluid) {
        if(fluid.getFluid()==FluidRegistry.LAVA){
            float blocks = ((float)(fluid.amount))/1000.0f;
            return blocks+" blocks";
        }
        //else not lava
        int mbFluid = fluid.amount;
        String ret="";
        String sep = "";
        if(mbFluid>4500){
            int blocks = mbFluid/4500;
            mbFluid-=(blocks*4500);
            ret=blocks+" blocks";
            sep=", ";
        }
        if(mbFluid>500){
            int ingots = mbFluid/500;
            mbFluid-=(ingots*500);
            ret+=sep+ingots+" ingots";
            sep=", ";
        }
        if(mbFluid>0){
            ret+=sep+mbFluid+"mb";
        }
        return ret;
    }

    public static class MeltingInformation{
        public final int requiredHeat;
        public final FluidStack result;

        public MeltingInformation(int requiredHeat,FluidStack result){
            this.requiredHeat=requiredHeat;
            this.result = result!=null ? result.copy() : null;
        }

        public MeltingInformation(FluidStack result){
            int resultQty = result.amount;
            this.requiredHeat = 20*resultQty;
            this.result=result;
        }
    }
    public static class MetaMeltingInformation extends MeltingInformation{
        public final int materialFluidResult;

        public MetaMeltingInformation(int requiredHeat, int materialFluidResult){
            super(requiredHeat,null);
            this.materialFluidResult = materialFluidResult;
        }

        public MetaMeltingInformation(int materialFluidResult){
            super(20*materialFluidResult,null);
            this.materialFluidResult=materialFluidResult;
        }
    }
}
