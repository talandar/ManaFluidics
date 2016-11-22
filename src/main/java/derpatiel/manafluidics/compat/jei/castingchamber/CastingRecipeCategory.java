package derpatiel.manafluidics.compat.jei.castingchamber;

import derpatiel.manafluidics.ManaFluidics;
import derpatiel.manafluidics.compat.jei.ManaFluidicsPlugin;
import derpatiel.manafluidics.enums.MaterialType;
import derpatiel.manafluidics.item.MFMoldItem;
import derpatiel.manafluidics.util.MaterialItemHelper;
import derpatiel.manafluidics.util.TextHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CastingRecipeCategory implements IRecipeCategory {


    private static final int MOLD_SLOT=0;
    private static final int FLUID_SLOT=1;
    private static final int OUTPUT_SLOT=2;

    @Nonnull
    private final String localizedName = TextHelper.localize("jei.manafluidics.recipe.castingchamber");

    @Nonnull
    private final IDrawable background = ManaFluidicsPlugin.jeiHelper.getGuiHelper().createDrawable(new ResourceLocation(ManaFluidics.MODID+":textures/gui/jei/drawnozzle.png"),0,80,84,40);

    @Override
    public String getUid() {
        return ManaFluidicsPlugin.CAST_RECIPE_CATEGORY_ID;
    }

    @Override
    public String getTitle() {
        return localizedName;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void drawExtras(Minecraft minecraft) {

    }

    @Nullable
    @Override
    public IDrawable getIcon() {
        return null;
    }


    @Override
    public void drawAnimations(Minecraft minecraft) {

    }

    @Override
    @Deprecated
    public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper) {
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
        recipeLayout.getFluidStacks().init(FLUID_SLOT, true, 9, 4, 16, 16, 4500, true, null);
        recipeLayout.getItemStacks().init(MOLD_SLOT,true, 8, 21 );
        recipeLayout.getItemStacks().init(OUTPUT_SLOT, false, 58, 11);

        if (recipeWrapper instanceof CastingRecipeJEI)
        {
            FluidStack inputFluidStack = ingredients.getInputs(FluidStack.class).get(0).get(0);
            recipeLayout.getFluidStacks().set(FLUID_SLOT, inputFluidStack);
            recipeLayout.getItemStacks().set(MOLD_SLOT,ingredients.getInputs(ItemStack.class).get(0));
            recipeLayout.getItemStacks().set(OUTPUT_SLOT, ingredients.getOutputs(ItemStack.class).get(0));
        }
    }

    public static List<CastingRecipeJEI> getRecipes() {
        ArrayList<CastingRecipeJEI> recipes = new ArrayList<>();

        for(MFMoldItem mold : MaterialItemHelper.castingProducts.keySet()){
            ItemStack moldStack = new ItemStack(mold);
            Map<FluidStack,ItemStack> moldRecipes = MaterialItemHelper.castingProducts.get(mold);
            for(FluidStack fluidStack : moldRecipes.keySet()){
                ItemStack output = moldRecipes.get(fluidStack);
                CastingRecipeJEI recipe = new CastingRecipeJEI(fluidStack,moldStack,output);
                recipes.add(recipe);
            }
        }
        return recipes;
    }
}
