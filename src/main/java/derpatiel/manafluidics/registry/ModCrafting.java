package derpatiel.manafluidics.registry;

import derpatiel.manafluidics.enums.MaterialType;
import derpatiel.manafluidics.util.MaterialItemHelper;
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

        //
        GameRegistry.addRecipe(new ItemStack(ModBlocks.fluidTankController),
                " O ",
                "OCO",
                " O ",
                'O', MaterialItemHelper.getSheet(MaterialType.OBSIDIAN,1),
                'C', MaterialItemHelper.getCircuit(MaterialType.IRON,1));

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

        GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.tankBottom),
                MaterialItemHelper.getSheet(MaterialType.OBSIDIAN),
                MaterialItemHelper.getSheet(MaterialType.OBSIDIAN));

        /*
        public static MFBlock fluidTankConnector;
        public static MFBlock fluidSpout;
        */
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
