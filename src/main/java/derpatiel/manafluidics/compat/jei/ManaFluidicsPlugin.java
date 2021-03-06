package derpatiel.manafluidics.compat.jei;

import derpatiel.manafluidics.ManaFluidics;
import derpatiel.manafluidics.compat.jei.castingchamber.CastingRecipeCategory;
import derpatiel.manafluidics.compat.jei.castingchamber.CastingRecipeHandler;
import derpatiel.manafluidics.compat.jei.drawnozzle.DrawingRecipeCategory;
import derpatiel.manafluidics.compat.jei.drawnozzle.DrawingRecipeHandler;
import derpatiel.manafluidics.compat.jei.floattable.FloatingRecipeCategory;
import derpatiel.manafluidics.compat.jei.floattable.FloatingRecipeHandler;
import derpatiel.manafluidics.compat.jei.melting.MeltingRecipeCategory;
import derpatiel.manafluidics.compat.jei.melting.MeltingRecipeHandler;
import derpatiel.manafluidics.compat.jei.runecrafting.RunecraftingRecipeCategory;
import derpatiel.manafluidics.compat.jei.runecrafting.RunecraftingRecipeHandler;
import derpatiel.manafluidics.registry.ModBlocks;
import mezz.jei.api.BlankModPlugin;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

@JEIPlugin
public class ManaFluidicsPlugin extends BlankModPlugin{

    public static final String DRAW_RECIPE_CATEGORY_ID= ManaFluidics.MODID+":drawnozzle";
    public static final String FLOAT_RECIPE_CATEGORY_ID= ManaFluidics.MODID+":floattable";
    public static final String MELT_RECIPE_CATEGORY_ID= ManaFluidics.MODID+":melting";
    public static final String CAST_RECIPE_CATEGORY_ID = ManaFluidics.MODID+":casting";
    public static final String RUNECRAFTING_RECIPE_CATEGORY_ID = ManaFluidics.MODID+":runecrafting";


    public static IJeiHelpers jeiHelper;

    @Override
    public void register(@Nonnull IModRegistry registry){
        jeiHelper = registry.getJeiHelpers();

        jeiHelper.getItemBlacklist().addItemToBlacklist(new ItemStack(ModBlocks.floatTable));
        jeiHelper.getItemBlacklist().addItemToBlacklist(new ItemStack(ModBlocks.summonedLight));



        registry.addRecipeCategories(new DrawingRecipeCategory(),new FloatingRecipeCategory(), new MeltingRecipeCategory(), new CastingRecipeCategory(), new RunecraftingRecipeCategory());

        registry.addRecipeHandlers(new DrawingRecipeHandler(), new FloatingRecipeHandler(), new MeltingRecipeHandler(), new CastingRecipeHandler(), new RunecraftingRecipeHandler());

        registry.addRecipes(DrawingRecipeCategory.getRecipies());
        registry.addRecipes(FloatingRecipeCategory.getRecipes());
        registry.addRecipes(MeltingRecipeCategory.getRecipes());
        registry.addRecipes(CastingRecipeCategory.getRecipes());
        registry.addRecipes(RunecraftingRecipeCategory.getRecipes());


        registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.drawNozzle),DRAW_RECIPE_CATEGORY_ID);
        registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.smeltingTankController),MELT_RECIPE_CATEGORY_ID);
        registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.castingChamber),CAST_RECIPE_CATEGORY_ID);
        registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.runecraftingTable),RUNECRAFTING_RECIPE_CATEGORY_ID);

    }
}
