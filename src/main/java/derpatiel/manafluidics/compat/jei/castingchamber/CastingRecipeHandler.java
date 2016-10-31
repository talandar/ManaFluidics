package derpatiel.manafluidics.compat.jei.castingchamber;

import derpatiel.manafluidics.compat.jei.ManaFluidicsPlugin;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class CastingRecipeHandler implements IRecipeHandler<CastingRecipeJEI> {
    @Override
    public Class<CastingRecipeJEI> getRecipeClass() {
        return CastingRecipeJEI.class;
    }

    @Override
    public String getRecipeCategoryUid() {
        return ManaFluidicsPlugin.CAST_RECIPE_CATEGORY_ID;
    }

    @Override
    public String getRecipeCategoryUid(CastingRecipeJEI recipe) {
        return ManaFluidicsPlugin.CAST_RECIPE_CATEGORY_ID;
    }

    @Override
    public IRecipeWrapper getRecipeWrapper(CastingRecipeJEI recipe) {
        return recipe;
    }

    @Override
    public boolean isRecipeValid(CastingRecipeJEI recipe) {
        return true;
    }
}
