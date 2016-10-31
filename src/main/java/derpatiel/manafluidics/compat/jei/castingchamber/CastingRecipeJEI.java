package derpatiel.manafluidics.compat.jei.castingchamber;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;

public class CastingRecipeJEI extends BlankRecipeWrapper{

    @Nonnull
    private final FluidStack input;

    @Nonnull
    private final ItemStack mold;

    @Nonnull
    private final ItemStack output;

    public CastingRecipeJEI(FluidStack input, ItemStack moldInput, ItemStack output){
        this.input=input;
        this.mold=moldInput;
        this.output=output;
    }


    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInput(FluidStack.class,input);
        ingredients.setInput(ItemStack.class,mold);
        ingredients.setOutput(ItemStack.class, output);
    }
}
