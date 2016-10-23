package derpatiel.manafluidics.compat.jei.floattable;

import derpatiel.manafluidics.compat.jei.ManaFluidicsPlugin;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class FloatingRecipeHandler implements IRecipeHandler<FloatingRecipeJEI> {
    @Override
    public Class<FloatingRecipeJEI> getRecipeClass() {
        return FloatingRecipeJEI.class;
    }

    @Override
    public String getRecipeCategoryUid() {
        return ManaFluidicsPlugin.FLOAT_RECIPE_CATEGORY_ID;
    }

    @Override
    public String getRecipeCategoryUid(FloatingRecipeJEI recipe) {
        return ManaFluidicsPlugin.FLOAT_RECIPE_CATEGORY_ID;
    }

    @Override
    public IRecipeWrapper getRecipeWrapper(FloatingRecipeJEI recipe) {
        return recipe;
    }

    @Override
    public boolean isRecipeValid(FloatingRecipeJEI recipe) {
        return true;
    }
}
