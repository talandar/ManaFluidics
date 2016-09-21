package derpatiel.manafluidics.registry;

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
/*
        GameRegistry.addRecipe(new ItemStack(ModBlocks.drawNozzle),
                "S S",
                "SBS",
                " S ",
                'S', MetaItemHelper.getSheet(EnumMaterialType.IRON),
                'B', Items.bucket);

        GameRegistry.addRecipe(new ItemStack(ModBlocks.portableTank),
                " I ",
                "C C",
                " I ",
                'I', MetaItemHelper.getSheet(EnumMaterialType.IRON),
                'C', MetaItemHelper.getSheet(EnumMaterialType.CRYSTAL));
*/

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
/*
        GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.tankBottom),
                MetaItemHelper.getSheet(EnumMaterialType.OBSIDIAN),
                MetaItemHelper.getSheet(EnumMaterialType.OBSIDIAN));
*/
        ItemStack filledCrystalBucket = new ItemStack(ForgeModContainer.getInstance().universalBucket);
        ((UniversalBucket) filledCrystalBucket.getItem()).fill(filledCrystalBucket, FluidRegistry.getFluidStack(ModFluids.moltenCrystal.getName().toLowerCase(), Fluid.BUCKET_VOLUME), true);
        GameRegistry.addSmelting(ModItems.bucket_crystals, filledCrystalBucket, 1.0F);

        ItemStack filledIronBucket = new ItemStack(ForgeModContainer.getInstance().universalBucket);
        ((UniversalBucket) filledIronBucket.getItem()).fill(filledIronBucket, FluidRegistry.getFluidStack(ModFluids.moltenIron.getName().toLowerCase(), Fluid.BUCKET_VOLUME), true);
        GameRegistry.addSmelting(ModItems.bucket_iron, filledIronBucket, 1.0F);

        ItemStack filledGoldBucket = new ItemStack(ForgeModContainer.getInstance().universalBucket);
        ((UniversalBucket) filledGoldBucket.getItem()).fill(filledGoldBucket, FluidRegistry.getFluidStack(ModFluids.moltenGold.getName().toLowerCase(), Fluid.BUCKET_VOLUME), true);
        GameRegistry.addSmelting(ModItems.bucket_gold, filledGoldBucket, 1.0F);
    }
}
