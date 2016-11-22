package derpatiel.manafluidics.compat.jei.floattable;

import derpatiel.manafluidics.ManaFluidics;
import derpatiel.manafluidics.compat.jei.ManaFluidicsPlugin;
import derpatiel.manafluidics.enums.MaterialType;
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

public class FloatingRecipeCategory implements IRecipeCategory {


    private static final int INPUT_SLOT=0;
    private static final int OUTPUT_SLOT=1;

    @Nonnull
    private final String localizedName = TextHelper.localize("jei.manafluidics.recipe.floattable");

    @Nonnull
    private final IDrawable background = ManaFluidicsPlugin.jeiHelper.getGuiHelper().createDrawable(new ResourceLocation(ManaFluidics.MODID+":textures/gui/jei/drawnozzle.png"),0,0,84,36);

    @Override
    public String getUid() {
        return ManaFluidicsPlugin.FLOAT_RECIPE_CATEGORY_ID;
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
        recipeLayout.getFluidStacks().init(INPUT_SLOT, true, 9, 10, 16, 16, 1000, true, null);
        recipeLayout.getItemStacks().init(OUTPUT_SLOT, false, 58, 9);

        if (recipeWrapper instanceof FloatingRecipeJEI)
        {
            FluidStack inputFluidStack = ingredients.getInputs(FluidStack.class).get(0).get(0);
            recipeLayout.getFluidStacks().set(INPUT_SLOT, inputFluidStack);
            recipeLayout.getItemStacks().set(OUTPUT_SLOT, ingredients.getOutputs(ItemStack.class).get(0));
        }
    }

    public static List<FloatingRecipeJEI> getRecipes() {
        ArrayList<FloatingRecipeJEI> recipes = new ArrayList<>();

        for(MaterialType type : MaterialType.VALUES){
            FloatingRecipeJEI recipe = new FloatingRecipeJEI(new FluidStack(MaterialItemHelper.productFluidMap.get(type),1000),MaterialItemHelper.getSheet(type,4));
            recipes.add(recipe);
        }

        return recipes;
    }
}
