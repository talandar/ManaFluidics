package derpatiel.manafluidics.compat.jei.melting;

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
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class MeltingRecipeCategory implements IRecipeCategory {


    private static final int INPUT_SLOT=0;
    private static final int OUTPUT_SLOT=1;

    @Nonnull
    private final String localizedName = TextHelper.localize("jei.manafluidics.recipe.melting");

    @Nonnull
    private final IDrawable background = ManaFluidicsPlugin.jeiHelper.getGuiHelper().createDrawable(new ResourceLocation(ManaFluidics.MODID+":textures/gui/jei/drawnozzle.png"),0,37,84,36);

    @Override
    public String getUid() {
        return ManaFluidicsPlugin.MELT_RECIPE_CATEGORY_ID;
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

    @Override
    public void drawAnimations(Minecraft minecraft) {

    }

    @Override
    @Deprecated
    public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper) {
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
        recipeLayout.getItemStacks().init(INPUT_SLOT, true, 8, 8);
        recipeLayout.getFluidStacks().init(OUTPUT_SLOT, false, 59, 9, 16, 16, 5000, false, null);

        if (recipeWrapper instanceof MeltingRecipeJEI)
        {
            ItemStack inputItemStack = ingredients.getInputs(ItemStack.class).get(0).get(0);
            FluidStack outputFluidStack = ingredients.getOutputs(FluidStack.class).get(0);
            recipeLayout.getItemStacks().set(INPUT_SLOT, inputItemStack);
            recipeLayout.getFluidStacks().set(OUTPUT_SLOT, outputFluidStack);
        }
    }

    public static List<MeltingRecipeJEI> getRecipes() {
        ArrayList<MeltingRecipeJEI> recipes = new ArrayList<>();

        for(ItemStack stack : MaterialItemHelper.meltables.keySet()){
            MaterialItemHelper.MeltingInformation meltInfo = MaterialItemHelper.meltables.get(stack);
            MeltingRecipeJEI recipe = new MeltingRecipeJEI(stack,meltInfo.requiredHeat,meltInfo.result);
            recipes.add(recipe);
        }


        return recipes;
    }
}
