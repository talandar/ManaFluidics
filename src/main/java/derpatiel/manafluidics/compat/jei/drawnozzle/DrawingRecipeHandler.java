package derpatiel.manafluidics.compat.jei.drawnozzle;

import derpatiel.manafluidics.compat.jei.ManaFluidicsPlugin;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class DrawingRecipeHandler implements IRecipeHandler<DrawingRecipeJEI> {
    @Override
    public Class<DrawingRecipeJEI> getRecipeClass() {
        return DrawingRecipeJEI.class;
    }

    @Override
    public String getRecipeCategoryUid() {
        return ManaFluidicsPlugin.DRAW_RECIPE_CATEGORY_ID;
    }

    @Override
    public String getRecipeCategoryUid(DrawingRecipeJEI recipe) {
        return ManaFluidicsPlugin.DRAW_RECIPE_CATEGORY_ID;
    }

    @Override
    public IRecipeWrapper getRecipeWrapper(DrawingRecipeJEI recipe) {
        return recipe;
    }

    @Override
    public boolean isRecipeValid(DrawingRecipeJEI recipe) {
        return true;
    }
}
