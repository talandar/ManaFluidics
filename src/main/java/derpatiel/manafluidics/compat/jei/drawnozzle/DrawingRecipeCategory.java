package derpatiel.manafluidics.compat.jei.drawnozzle;

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
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class DrawingRecipeCategory implements IRecipeCategory {


    private static final int INPUT_SLOT=0;
    private static final int OUTPUT_SLOT=1;

    @Nonnull
    private final String localizedName = TextHelper.localize("jei.manafluidics.recipe.drawnozzle");

    @Nonnull
    private final IDrawable background = ManaFluidicsPlugin.jeiHelper.getGuiHelper().createDrawable(new ResourceLocation(ManaFluidics.MODID+":textures/gui/jei/drawnozzle.png"),0,0,84,36);

    @Override
    public String getUid() {
        return ManaFluidicsPlugin.DRAW_RECIPE_CATEGORY_ID;
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
        recipeLayout.getFluidStacks().init(INPUT_SLOT, true, 9, 10, 16, 16, 125, true, null);
        recipeLayout.getItemStacks().init(OUTPUT_SLOT, false, 58, 9);

        if (recipeWrapper instanceof DrawingRecipeJEI)
        {
            FluidStack inputFluidStack = ingredients.getInputs(FluidStack.class).get(0).get(0);
            recipeLayout.getFluidStacks().set(INPUT_SLOT, inputFluidStack);
            recipeLayout.getItemStacks().set(OUTPUT_SLOT, ingredients.getOutputs(ItemStack.class).get(0));
        }
    }

    public static List<DrawingRecipeJEI> getRecipies() {
        ArrayList<DrawingRecipeJEI> recipies = new ArrayList<>();

        for(MaterialType type : MaterialType.VALUES){
            DrawingRecipeJEI recipe = new DrawingRecipeJEI(new FluidStack(MaterialItemHelper.productFluidMap.get(type),125),MaterialItemHelper.getWire(type));
            recipies.add(recipe);
        }

        return recipies;
    }
}
