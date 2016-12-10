package derpatiel.manafluidics.compat.jei.runecrafting;

import derpatiel.manafluidics.compat.jei.ManaFluidicsPlugin;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class RunecraftingRecipeHandler implements IRecipeHandler<RunecraftingRecipeJEI> {
    @Override
    public Class<RunecraftingRecipeJEI> getRecipeClass() {
        return RunecraftingRecipeJEI.class;
    }

    @Override
    public String getRecipeCategoryUid() {
        return ManaFluidicsPlugin.RUNECRAFTING_RECIPE_CATEGORY_ID;
    }

    @Override
    public String getRecipeCategoryUid(RunecraftingRecipeJEI recipe) {
        return ManaFluidicsPlugin.RUNECRAFTING_RECIPE_CATEGORY_ID;
    }

    @Override
    public IRecipeWrapper getRecipeWrapper(RunecraftingRecipeJEI recipe) {
        return recipe;
    }

    @Override
    public boolean isRecipeValid(RunecraftingRecipeJEI recipe) {
        return true;
    }
}
