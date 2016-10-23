package derpatiel.manafluidics.compat.jei.melting;

import derpatiel.manafluidics.compat.jei.ManaFluidicsPlugin;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class MeltingRecipeHandler implements IRecipeHandler<MeltingRecipeJEI> {
    @Override
    public Class<MeltingRecipeJEI> getRecipeClass() {
        return MeltingRecipeJEI.class;
    }

    @Override
    public String getRecipeCategoryUid() {
        return ManaFluidicsPlugin.MELT_RECIPE_CATEGORY_ID;
    }

    @Override
    public String getRecipeCategoryUid(MeltingRecipeJEI recipe) {
        return ManaFluidicsPlugin.MELT_RECIPE_CATEGORY_ID;
    }

    @Override
    public IRecipeWrapper getRecipeWrapper(MeltingRecipeJEI recipe) {
        return recipe;
    }

    @Override
    public boolean isRecipeValid(MeltingRecipeJEI recipe) {
        return true;
    }
}
