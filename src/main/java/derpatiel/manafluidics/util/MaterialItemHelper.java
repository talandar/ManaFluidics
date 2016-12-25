package derpatiel.manafluidics.util;

import derpatiel.manafluidics.ManaFluidics;
import derpatiel.manafluidics.block.MFBlock;
import derpatiel.manafluidics.enums.MaterialType;
import derpatiel.manafluidics.item.MFMoldItem;
import derpatiel.manafluidics.registry.ModBlocks;
import derpatiel.manafluidics.registry.ModFluids;
import derpatiel.manafluidics.registry.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.network.internal.HandshakeCompletionHandler;
import org.omg.DynamicAny.DynEnum;

import java.util.*;

public class MaterialItemHelper {

    public static final float COOLING_CONSTANT = 0.05f;

    public static final  Map<MaterialType,String> sheetResourceDomain = new HashMap<>();
    public static final Map<MaterialType,String> sheetResourceLocation = new HashMap<>();
    public static final Map<Fluid,MaterialType> fluidProductMap = new HashMap<>();
    public static final Map<MaterialType,Fluid> productFluidMap = new HashMap<>();

    //public static final Map<Block,MeltingInformation> meltableBlocks = new HashMap<>();
    //public static final Map<Item,MeltingInformation> meltableItems = new HashMap<>();
    public static final Map<ItemStack,MeltingInformation> meltables = new HashMap<>();

    public static final Map<MFMoldItem,Map<FluidStack,ItemStack>> castingProducts = new HashMap<>();

    public static final List<AlloyFormingRule> alloyRules;

    static{


        registerMaterialFluid(ModFluids.moltenCrystal, MaterialType.CRYSTAL);
        registerMaterialFluid(FluidRegistry.LAVA, MaterialType.OBSIDIAN);
        registerMaterialFluid(ModFluids.moltenIron, MaterialType.IRON);
        registerMaterialFluid(ModFluids.moltenGold, MaterialType.GOLD);
        registerMaterialFluid(ModFluids.moltenRedstone, MaterialType.REDSTONE);
        registerMaterialFluid(ModFluids.moltenDiamond, MaterialType.DIAMOND);
        registerMaterialFluid(ModFluids.moltenLapis,MaterialType.LAPIS);

        sheetResourceLocation.put(MaterialType.CRYSTAL, "blocks/sheet_crystal");
        sheetResourceLocation.put(MaterialType.GOLD, "blocks/gold_block");
        sheetResourceLocation.put(MaterialType.IRON, "blocks/iron_block");
        sheetResourceLocation.put(MaterialType.OBSIDIAN, "blocks/obsidian");
        sheetResourceLocation.put(MaterialType.DIAMOND, "blocks/diamond_block");
        sheetResourceLocation.put(MaterialType.LAPIS, "blocks/lapis_block");
        sheetResourceLocation.put(MaterialType.REDSTONE, "blocks/redstone_block");

        sheetResourceDomain.put(MaterialType.CRYSTAL, ManaFluidics.MODID);
        sheetResourceDomain.put(MaterialType.GOLD, "minecraft");
        sheetResourceDomain.put(MaterialType.IRON, "minecraft");
        sheetResourceDomain.put(MaterialType.OBSIDIAN, ManaFluidics.MODID);
        sheetResourceDomain.put(MaterialType.DIAMOND, "minecraft");
        sheetResourceDomain.put(MaterialType.LAPIS, "minecraft");
        sheetResourceDomain.put(MaterialType.REDSTONE, "minecraft");

        /*
        200ticks/smelt for ores.  smelt time proportional to output quantity (100ticks/ingot)
        100HU/furnace
        ->20,000 heat/1000mb
        ->20heat/mb
         */

        meltables.put(new ItemStack(ModBlocks.tankBottom), new MeltingInformation(new FluidStack(FluidRegistry.LAVA,500)));
        meltables.put(new ItemStack(Blocks.IRON_ORE), new MeltingInformation(new FluidStack(ModFluids.moltenIron,1000)));
        meltables.put(new ItemStack(Blocks.IRON_BLOCK), new MeltingInformation(new FluidStack(ModFluids.moltenIron,4500)));
        meltables.put(new ItemStack(Blocks.GOLD_BLOCK), new MeltingInformation(new FluidStack(ModFluids.moltenGold,4500)));
        meltables.put(new ItemStack(Blocks.GOLD_ORE), new MeltingInformation(new FluidStack(ModFluids.moltenGold,1000)));
        meltables.put(new ItemStack(Blocks.OBSIDIAN), new MeltingInformation(new FluidStack(FluidRegistry.LAVA,1000)));
        meltables.put(new ItemStack(ModBlocks.crystallineIronBlock), new MeltingInformation(new FluidStack(ModFluids.crystalIron,4500)));
        meltables.put(new ItemStack(ModBlocks.crystalBlock), new MeltingInformation(new FluidStack(ModFluids.moltenCrystal,4500)));
        meltables.put(new ItemStack(ModBlocks.redCrystalBlock), new MeltingInformation(new FluidStack(ModFluids.redCrystal,4500)));

        meltables.put(new ItemStack(ModItems.manaCrystal), new MeltingInformation(new FluidStack(ModFluids.moltenCrystal,500)));
        meltables.put(new ItemStack(ModItems.redcrystal_gem), new MeltingInformation(new FluidStack(ModFluids.redCrystal,500)));
        meltables.put(new ItemStack(ModItems.crystal_iron_ingot),new MeltingInformation(new FluidStack(ModFluids.crystalIron,500)));

        meltables.put(new ItemStack(Items.IRON_INGOT),new MeltingInformation(new FluidStack(ModFluids.moltenIron,500)));
        meltables.put(new ItemStack(Items.GOLD_INGOT),new MeltingInformation(new FluidStack(ModFluids.moltenGold,500)));
        meltables.put(new ItemStack(Blocks.REDSTONE_BLOCK), new MeltingInformation(new FluidStack(ModFluids.moltenRedstone,4500)));
        meltables.put(new ItemStack(Items.REDSTONE), new MeltingInformation(new FluidStack(ModFluids.moltenRedstone,500)));
        meltables.put(new ItemStack(Blocks.DIAMOND_BLOCK), new MeltingInformation(new FluidStack(ModFluids.moltenDiamond,4500)));
        meltables.put(new ItemStack(Items.DIAMOND), new MeltingInformation(new FluidStack(ModFluids.moltenDiamond,500)));
        meltables.put(new ItemStack(Blocks.LAPIS_BLOCK), new MeltingInformation(new FluidStack(ModFluids.moltenLapis,4500)));
        meltables.put(new ItemStack(Items.DYE,1, EnumDyeColor.BLUE.getDyeDamage()), new MeltingInformation(new FluidStack(ModFluids.moltenLapis,500)));

        for(MaterialType type : MaterialType.VALUES) {
            meltables.put(getSheet(type),new MeltingInformation(new FluidStack(productFluidMap.get(type),250)));
            meltables.put(getWire(type),new MeltingInformation(new FluidStack(productFluidMap.get(type),125)));
            meltables.put(getCircuit(type),new MeltingInformation(new FluidStack(productFluidMap.get(type),500)));
        }

        Map<FluidStack,ItemStack> blockMoldItems = new HashMap<>();
        blockMoldItems.put(new FluidStack(FluidRegistry.LAVA,1000), new ItemStack(Blocks.OBSIDIAN));
        blockMoldItems.put(new FluidStack(ModFluids.moltenGold,4500), new ItemStack(Blocks.GOLD_BLOCK));
        blockMoldItems.put(new FluidStack(ModFluids.moltenIron,4500), new ItemStack(Blocks.IRON_BLOCK));
        blockMoldItems.put(new FluidStack(ModFluids.moltenCrystal,4500), new ItemStack(ModBlocks.crystalBlock));
        blockMoldItems.put(new FluidStack(ModFluids.crystalIron,4500), new ItemStack(ModBlocks.crystallineIronBlock));
        blockMoldItems.put(new FluidStack(ModFluids.redCrystal,4500), new ItemStack(ModBlocks.redCrystalBlock));
        blockMoldItems.put(new FluidStack(ModFluids.moltenRedstone,4500), new ItemStack(Blocks.REDSTONE_BLOCK));
        blockMoldItems.put(new FluidStack(ModFluids.moltenDiamond,4500), new ItemStack(Blocks.DIAMOND_BLOCK));
        blockMoldItems.put(new FluidStack(ModFluids.moltenLapis,4500), new ItemStack(Blocks.LAPIS_BLOCK));

        Map<FluidStack,ItemStack> ingotMoldItems = new HashMap<>();
        ingotMoldItems.put(new FluidStack(ModFluids.moltenGold,500), new ItemStack(Items.GOLD_INGOT));
        ingotMoldItems.put(new FluidStack(ModFluids.moltenIron,500), new ItemStack(Items.IRON_INGOT));
        ingotMoldItems.put(new FluidStack(ModFluids.crystalIron,500), new ItemStack(ModItems.crystal_iron_ingot));

        Map<FluidStack,ItemStack> gemMoldItems = new HashMap<>();
        gemMoldItems.put(new FluidStack(ModFluids.moltenCrystal,500), new ItemStack(ModItems.manaCrystal));
        gemMoldItems.put(new FluidStack(ModFluids.redCrystal,500), new ItemStack(ModItems.redcrystal_gem));
        gemMoldItems.put(new FluidStack(ModFluids.moltenDiamond,500), new ItemStack(Items.DIAMOND));
        gemMoldItems.put(new FluidStack(ModFluids.moltenLapis,500), new ItemStack(Items.DYE,1, EnumDyeColor.BLUE.getDyeDamage()));

        castingProducts.put((MFMoldItem)ModItems.block_mold,blockMoldItems);
        castingProducts.put((MFMoldItem)ModItems.ingot_mold,ingotMoldItems);
        castingProducts.put((MFMoldItem)ModItems.gem_mold,gemMoldItems);


        alloyRules = new ArrayList<>();

        AlloyFormingRule crystalIronRule = new AlloyFormingRule();
        crystalIronRule.inputs = new FluidStack[]{new FluidStack(ModFluids.moltenCrystal,500),new FluidStack(ModFluids.moltenIron,500)};
        crystalIronRule.output=new FluidStack(ModFluids.crystalIron,1000);
        alloyRules.add(crystalIronRule);

        AlloyFormingRule energizedManaRule = new AlloyFormingRule();
        energizedManaRule.inputs = new FluidStack[]{new FluidStack(ModFluids.moltenCrystal,500),new FluidStack(ModFluids.reactiveMana,500)};
        energizedManaRule.output=new FluidStack(ModFluids.energizedMana,1000);
        alloyRules.add(energizedManaRule);

        AlloyFormingRule redCrystalRule = new AlloyFormingRule();
        redCrystalRule.inputs = new FluidStack[]{new FluidStack(ModFluids.moltenRedstone,500),new FluidStack(ModFluids.moltenCrystal,500)};
        redCrystalRule.output = new FluidStack(ModFluids.redCrystal,1000);
        alloyRules.add(redCrystalRule);

        //testRule = new AlloyFormingRule();
        //testRule.inputs = new FluidStack[]{new FluidStack(ModFluids.moltenIron,500),new FluidStack(ModFluids.moltenGold,500)};
        //testRule.output = new FluidStack(FluidRegistry.LAVA,1000);
        //alloyRules.add(testRule);

        //testRule = new AlloyFormingRule();
        //testRule.inputs = new FluidStack[]{new FluidStack(ModFluids.moltenIron,500),new FluidStack(ModFluids.moltenGold,500)};
        //testRule.output = new FluidStack(FluidRegistry.LAVA,1000);
        //alloyRules.add(testRule);

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


    public static ItemStack getPipe(MaterialType type) {
        return getPipe(type, 1);
    }
    public static ItemStack getPipe(MaterialType type, int quantity){
        return new ItemStack(ModBlocks.fluidPipe,quantity,type.getID());
    }

    public static boolean isMeltable(ItemStack stack){
        if(stack==null || stack.stackSize==0)
            return false;
        for(ItemStack checkStack : meltables.keySet()){
            if(ItemStack.areItemsEqual(stack,checkStack)){
                return true;
            }
        }
        return false;
    }

    public static int getMeltHeat(ItemStack stack){
        if(stack==null || stack.stackSize==0 || !isMeltable(stack))
            return -1;
        for(ItemStack checkStack : meltables.keySet()){
            if(ItemStack.areItemsEqual(stack,checkStack)){
                return meltables.get(checkStack).requiredHeat;
            }
        }
        return -1;
    }

    public static FluidStack getMeltOutput(ItemStack stack){
        if(stack==null || stack.stackSize==0 || !isMeltable(stack))
            return null;
        for(ItemStack checkStack : meltables.keySet()){
            if(ItemStack.areItemsEqual(stack,checkStack)){
                return meltables.get(checkStack).result;
            }
        }
        return null;
    }

    public static String getIngotsString(FluidStack fluid) {
        if(fluid.getFluid()==FluidRegistry.LAVA){
            int blocks = (fluid.amount)/1000;
            int mbLeft = fluid.amount-(blocks*1000);
            String ret = TextHelper.localize("fluid.blockcount.message",blocks);
            if(mbLeft>0)
                ret+=", "+TextHelper.localize("fluid.remainingmb.message",mbLeft);
            return ret;
        }
        if(fluid.getFluid()==ModFluids.moltenRedstone){
            int blocks = (fluid.amount)/4500;
            int mbLeft = fluid.amount-(blocks*4500);
            String ret = TextHelper.localize("fluid.blockcount.message",blocks);
            if(mbLeft>0)
                ret+=", "+TextHelper.localize("fluid.remainingmb.message",mbLeft);
            return ret;
        }
        //else not lava
        int mbFluid = fluid.amount;
        String ret="";
        String sep = "";
        if(mbFluid>=4500){
            int blocks = mbFluid/4500;
            mbFluid-=(blocks*4500);
            ret=TextHelper.localize("fluid.blockcount.message",blocks);

            sep=", ";
        }
        if(mbFluid>=500){
            int ingots = mbFluid/500;
            mbFluid-=(ingots*500);
            if(isGemFluid(fluid.getFluid())){
                ret+=sep+TextHelper.localize("fluid.gemcount.message",ingots);
            }else{
                ret+=sep+TextHelper.localize("fluid.ingotcount.message", ingots);
            }
            sep=", ";
        }
        if(mbFluid>0){
            ret+=sep+TextHelper.localize("fluid.remainingmb.message",mbFluid);
        }
        return ret;
    }

    public static boolean isGemFluid(Fluid fluid){
        return
                fluid!=null && (
                        ModFluids.moltenCrystal==fluid
                        || ModFluids.moltenDiamond==fluid
                        || ModFluids.moltenLapis==fluid
                        || ModFluids.redCrystal==fluid
                );
    }

    public static MFBlock getAlloyBlockForLevel(int level){
        switch (level){
            case 1:
                return ModBlocks.crystallineIronBlock;
            case 2:
                return ModBlocks.redCrystalBlock;
            default:
                return ModBlocks.crystallineIronBlock;
        }
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

    public static class AlloyFormingRule{
        public FluidStack[] inputs;
        public FluidStack output;
    }
}
