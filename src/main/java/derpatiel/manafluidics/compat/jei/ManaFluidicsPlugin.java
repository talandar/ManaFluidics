package derpatiel.manafluidics.compat.jei;

import derpatiel.manafluidics.ManaFluidics;
import derpatiel.manafluidics.compat.jei.drawnozzle.DrawingRecipeCategory;
import derpatiel.manafluidics.compat.jei.drawnozzle.DrawingRecipeHandler;
import derpatiel.manafluidics.compat.jei.floattable.FloatingRecipeCategory;
import derpatiel.manafluidics.compat.jei.floattable.FloatingRecipeHandler;
import derpatiel.manafluidics.compat.jei.melting.MeltingRecipeCategory;
import derpatiel.manafluidics.compat.jei.melting.MeltingRecipeHandler;
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


    public static IJeiHelpers jeiHelper;

    @Override
    public void register(@Nonnull IModRegistry registry){
        jeiHelper = registry.getJeiHelpers();

        jeiHelper.getItemBlacklist().addItemToBlacklist(new ItemStack(ModBlocks.floatTable));

        registry.addRecipeCategories(new DrawingRecipeCategory(),new FloatingRecipeCategory(), new MeltingRecipeCategory());

        registry.addRecipeHandlers(new DrawingRecipeHandler(), new FloatingRecipeHandler(), new MeltingRecipeHandler());

        registry.addRecipes(DrawingRecipeCategory.getRecipies());
        registry.addRecipes(FloatingRecipeCategory.getRecipies());
        registry.addRecipes(MeltingRecipeCategory.getRecipies());


        registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.drawNozzle),DRAW_RECIPE_CATEGORY_ID);
        registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.smeltingTankController),MELT_RECIPE_CATEGORY_ID);

    }
}
