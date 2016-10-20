package derpatiel.manafluidics.compat.jei.drawnozzle;

import derpatiel.manafluidics.util.MaterialItemHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;

public class DrawingRecipeJEI extends BlankRecipeWrapper{

    @Nonnull
    private final FluidStack input;

    @Nonnull
    private final ItemStack output;

    public DrawingRecipeJEI(FluidStack input, ItemStack output){
        this.input=input;
        this.output=output;
    }


    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInput(FluidStack.class,input);
        ingredients.setOutput(ItemStack.class, MaterialItemHelper.getWire(MaterialItemHelper.fluidProductMap.get(input.getFluid())));
    }
}
