package derpatiel.manafluidics.compat.jei.runecrafting;

import derpatiel.manafluidics.ManaFluidics;
import derpatiel.manafluidics.compat.jei.ManaFluidicsPlugin;
import derpatiel.manafluidics.craft.RuneCraftingHandler;
import derpatiel.manafluidics.craft.RunecraftingRecipe;
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

public class RunecraftingRecipeCategory implements IRecipeCategory {

    private final int BASE_SLOT=9;
    private final int OUTPUT_SLOT=10;

    @Nonnull
    private final String localizedName = TextHelper.localize("jei.manafluidics.recipe.runecrafting");

    @Nonnull
    private final IDrawable background = ManaFluidicsPlugin.jeiHelper.getGuiHelper().createDrawable(new ResourceLocation(ManaFluidics.MODID+":textures/gui/jei/runecrafting.png"),0,0,124,88);

    @Override
    public String getUid() {
        return ManaFluidicsPlugin.RUNECRAFTING_RECIPE_CATEGORY_ID;
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
        int slot=0;
        for(int row=0;row<3;row++){
            for(int col=0;col<3;col++){
                recipeLayout.getItemStacks().init(slot,true, 6+(18*col), 6 + (18*row) );
                slot++;
            }
        }
        recipeLayout.getItemStacks().init(BASE_SLOT,true, 24, 64 );
        recipeLayout.getItemStacks().init(OUTPUT_SLOT, false, 100,34);

        if (recipeWrapper instanceof RunecraftingRecipeJEI)
        {
            for(int i=0;i<10;i++){
                List<ItemStack> slotInputList = ingredients.getInputs(ItemStack.class).get(i);
                if(slotInputList!=null && !slotInputList.isEmpty()) {
                    recipeLayout.getItemStacks().set(i, slotInputList.get(0));
                }
            }
            recipeLayout.getItemStacks().set(OUTPUT_SLOT, ingredients.getOutputs(ItemStack.class).get(0));
        }
    }

    public static List<RunecraftingRecipeJEI> getRecipes() {
        ArrayList<RunecraftingRecipeJEI> recipes = new ArrayList<>();

        for(RunecraftingRecipe recipe : RuneCraftingHandler.craftingRecipes){
            List<ItemStack> grid = new ArrayList<>();
            for(ItemStack stack : recipe.getInputGrid())
                grid.add(stack);
            grid.add(recipe.getRuneBase());
            RunecraftingRecipeJEI jeiRecipe = new RunecraftingRecipeJEI(grid,recipe.getOutput());

            recipes.add(jeiRecipe);
        }
        return recipes;
    }
}
