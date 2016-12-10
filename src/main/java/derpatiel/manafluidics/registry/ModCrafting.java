package derpatiel.manafluidics.registry;

import com.google.common.collect.Lists;
import derpatiel.manafluidics.UnfiredMoldRecipe;
import derpatiel.manafluidics.craft.RuneCraftingHandler;
import derpatiel.manafluidics.craft.RunecraftingRecipe;
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
            GameRegistry.addRecipe(MaterialItemHelper.getPipe(type,4),
                    "MGM",
                    'M',MaterialItemHelper.getSheet(type,1),
                    'G', Blocks.GLASS);

            RuneCraftingHandler.registerRecipe(RunecraftingRecipe.createRecipe(MaterialItemHelper.getCircuit(type,1),MaterialItemHelper.getSheet(MaterialType.IRON),
                    new ItemStack[]{
                        new ItemStack(Items.REDSTONE),MaterialItemHelper.getWire(type),new ItemStack(Items.REDSTONE),
                        MaterialItemHelper.getWire(type),new ItemStack(Items.DIAMOND),MaterialItemHelper.getWire(type),
                        new ItemStack(Items.REDSTONE),MaterialItemHelper.getWire(type),new ItemStack(Items.REDSTONE),
                    }));
        }

        GameRegistry.addRecipe(new ItemStack(ModBlocks.fluidPump),
                "CCC",
                "CBV",
                "CRC",
                'C',Blocks.COBBLESTONE,
                'B',Items.BUCKET,
                'V',ModBlocks.fluidSpout,
                'R',Items.REDSTONE);


        GameRegistry.addRecipe(new ItemStack(ModBlocks.fluidTankController),
                " O ",
                "OCO",
                " O ",
                'O', MaterialItemHelper.getSheet(MaterialType.IRON,1),
                'C', MaterialItemHelper.getCircuit(MaterialType.GOLD,1));

        GameRegistry.addRecipe(new ItemStack(ModBlocks.alloyTankController),
                " O ",
                "OCO",
                " O ",
                'O', MaterialItemHelper.getSheet(MaterialType.IRON,1),
                'C', MaterialItemHelper.getCircuit(MaterialType.CRYSTAL,1));

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


        GameRegistry.addRecipe(new ItemStack(ModBlocks.crystalBlock),
                "CCC",
                "CCC",
                "CCC",
                'C', ModItems.manaCrystal
        );
        GameRegistry.addRecipe(new ItemStack(ModBlocks.crystallineIronBlock),
                "CCC",
                "CCC",
                "CCC",
                'C', ModItems.crystal_iron_ingot
        );

        GameRegistry.addRecipe(new ItemStack(ModBlocks.redCrystalBlock),
                "CCC",
                "CCC",
                "CCC",
                'C', ModItems.redcrystal_gem
        );

        GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.castingChamber),
                "S S",
                "S X",
                "SSS",
                'X',ModItems.heat_exchanger,
                'S',Blocks.STONE);

        GameRegistry.addShapedRecipe(new ItemStack(ModItems.mana_wand),
                "C",
                "S",
                "S",
                'C',ModItems.manaCrystal,
                'S',Items.BLAZE_ROD
                );

        GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.knowledgeAltar),
                " B ",
                "GOG",
                "OOO",
                'B', Items.BOOK,
                'G', ModItems.manaCrystal,
                'O', ModItems.crystal_iron_ingot);

        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.manaCrystal,9),
                new ItemStack(ModBlocks.crystalBlock));

        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.redcrystal_gem,9),
                new ItemStack(ModBlocks.redCrystalBlock));

        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.crystal_iron_ingot,9),
                new ItemStack(ModBlocks.crystallineIronBlock));




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

        GameRegistry.addRecipe(new UnfiredMoldRecipe(new ItemStack(ModItems.unfired_block_mold),
                Lists.newArrayList(new ItemStack(Blocks.CLAY), new ItemStack(Blocks.IRON_BLOCK))));

        GameRegistry.addRecipe(new UnfiredMoldRecipe(new ItemStack(ModItems.unfired_block_mold),
                Lists.newArrayList(new ItemStack(Blocks.CLAY),new ItemStack(Blocks.GOLD_BLOCK))));

        GameRegistry.addRecipe(new UnfiredMoldRecipe(new ItemStack(ModItems.unfired_ingot_mold),
                Lists.newArrayList(new ItemStack(Items.CLAY_BALL),new ItemStack(Items.IRON_INGOT))));

        GameRegistry.addRecipe(new UnfiredMoldRecipe(new ItemStack(ModItems.unfired_ingot_mold),
                Lists.newArrayList(new ItemStack(Items.CLAY_BALL),new ItemStack(Items.GOLD_INGOT))));

        GameRegistry.addRecipe(new UnfiredMoldRecipe(new ItemStack(ModItems.unfired_gem_mold),
                Lists.newArrayList(new ItemStack(Items.CLAY_BALL),new ItemStack(Items.DIAMOND))));

        GameRegistry.addRecipe(new UnfiredMoldRecipe(new ItemStack(ModItems.unfired_gem_mold),
                Lists.newArrayList(new ItemStack(Items.CLAY_BALL),new ItemStack(ModItems.manaCrystal))));

        GameRegistry.addSmelting(ModItems.unfired_block_mold,new ItemStack(ModItems.block_mold),0.5f);
        GameRegistry.addSmelting(ModItems.unfired_ingot_mold,new ItemStack(ModItems.ingot_mold),0.5f);
        GameRegistry.addSmelting(ModItems.unfired_gem_mold, new ItemStack(ModItems.gem_mold),0.5f);

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
