package derpatiel.manafluidics.registry;

import derpatiel.manafluidics.enums.MaterialType;
import derpatiel.manafluidics.util.MaterialItemHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModCrafting {

    public static void registerCrafting() {

        GameRegistry.addRecipe(new ItemStack(ModItems.crystal_hammer),
                " CT",
                " SC",
                "S  ",
                'C', ModItems.manaCrystal,
                'T', Items.STRING,
                'S', Items.STICK);

        GameRegistry.addRecipe(new ItemStack(ModBlocks.drawNozzle),
                "S S",
                "SBS",
                " S ",
                'S', MaterialItemHelper.getSheet(MaterialType.IRON),
                'B', Items.BUCKET);

        GameRegistry.addRecipe(new ItemStack(ModBlocks.portableTank),
                " I ",
                "C C",
                " I ",
                'I', MaterialItemHelper.getSheet(MaterialType.IRON),
                'C', MaterialItemHelper.getSheet(MaterialType.CRYSTAL));

        for(MaterialType type : MaterialType.VALUES) {
            GameRegistry.addRecipe(MaterialItemHelper.getCircuit(type,1),
                    "RWR",
                    "WDW",
                    "RWR",
                    'R', Items.REDSTONE,
                    'W', MaterialItemHelper.getWire(type),
                    'D', Items.DIAMOND
            );
        }


        GameRegistry.addRecipe(new ItemStack(ModBlocks.fluidTankController),
                " O ",
                "OCO",
                " O ",
                'O', MaterialItemHelper.getSheet(MaterialType.IRON,1),
                'C', MaterialItemHelper.getCircuit(MaterialType.GOLD,1));

        GameRegistry.addRecipe(new ItemStack(ModBlocks.smeltingTankController),
                " O ",
                "OCO",
                " O ",
                'O', MaterialItemHelper.getSheet(MaterialType.OBSIDIAN,1),
                'C', MaterialItemHelper.getCircuit(MaterialType.GOLD,1));

        GameRegistry.addRecipe(new ItemStack(ModBlocks.furnaceHeater),
                " O ",
                "HFO",
                " O ",
                'O', MaterialItemHelper.getSheet(MaterialType.OBSIDIAN,1),
                'H', ModItems.heat_exchanger,
                'F', Blocks.FURNACE);

        GameRegistry.addRecipe(new ItemStack(ModItems.heat_exchanger),
                "W W",
                "W W",
                "W W",
                'W', MaterialItemHelper.getWire(MaterialType.OBSIDIAN));

        GameRegistry.addRecipe(new ItemStack(ModBlocks.fluidSpout),
                "S S",
                "SSS",
                "   ",
                'S', Blocks.STONE);

        GameRegistry.addRecipe(new ItemStack(ModBlocks.heatTankConnector),
                "",
                "SH",
                "",
                'H', ModItems.heat_exchanger,
                'S', MaterialItemHelper.getSheet(MaterialType.OBSIDIAN)
                );




        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.bucket_crystals),
                new ItemStack(ModItems.manaCrystal),
                new ItemStack(ModItems.manaCrystal),
                new ItemStack(ModItems.manaCrystal),
                new ItemStack(Items.BUCKET));

        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.bucket_iron),
                new ItemStack(Items.IRON_INGOT),
                new ItemStack(Items.IRON_INGOT),
                new ItemStack(Items.IRON_INGOT),
                new ItemStack(Items.BUCKET));

        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.bucket_gold),
                new ItemStack(Items.GOLD_INGOT),
                new ItemStack(Items.GOLD_INGOT),
                new ItemStack(Items.GOLD_INGOT),
                new ItemStack(Items.BUCKET));

        GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.heatTankConnector),
                MaterialItemHelper.getSheet(MaterialType.OBSIDIAN),
                ModItems.heat_exchanger);

        GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.fluidTankConnector),
                MaterialItemHelper.getSheet(MaterialType.IRON),
                ModBlocks.fluidSpout);

        GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.tankBottom),
                MaterialItemHelper.getSheet(MaterialType.OBSIDIAN),
                MaterialItemHelper.getSheet(MaterialType.OBSIDIAN));

        GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.itemTankConnector),
                MaterialItemHelper.getSheet(MaterialType.IRON),
                Blocks.CHEST);


        ItemStack filledCrystalBucket = new ItemStack(ForgeModContainer.getInstance().universalBucket);
        ((UniversalBucket) filledCrystalBucket.getItem()).fill(filledCrystalBucket, FluidRegistry.getFluidStack(ModFluids.moltenCrystal.getName().toLowerCase(), Fluid.BUCKET_VOLUME), true);
        GameRegistry.addSmelting(ModItems.bucket_crystals, filledCrystalBucket, 0.5F);

        ItemStack filledIronBucket = new ItemStack(ForgeModContainer.getInstance().universalBucket);
        ((UniversalBucket) filledIronBucket.getItem()).fill(filledIronBucket, FluidRegistry.getFluidStack(ModFluids.moltenIron.getName().toLowerCase(), Fluid.BUCKET_VOLUME), true);
        GameRegistry.addSmelting(ModItems.bucket_iron, filledIronBucket, 0.5F);

        ItemStack filledGoldBucket = new ItemStack(ForgeModContainer.getInstance().universalBucket);
        ((UniversalBucket) filledGoldBucket.getItem()).fill(filledGoldBucket, FluidRegistry.getFluidStack(ModFluids.moltenGold.getName().toLowerCase(), Fluid.BUCKET_VOLUME), true);
        GameRegistry.addSmelting(ModItems.bucket_gold, filledGoldBucket, 0.5F);
    }
}
