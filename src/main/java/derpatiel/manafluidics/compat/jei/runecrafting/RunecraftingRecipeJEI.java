package derpatiel.manafluidics.compat.jei.runecrafting;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class RunecraftingRecipeJEI extends BlankRecipeWrapper{

    @Nonnull
    private final List<ItemStack> inputs = new ArrayList<>();

    @Nonnull
    private final ItemStack output;

    public RunecraftingRecipeJEI(List<ItemStack> grid, ItemStack output){
        for(ItemStack stack : grid){
            inputs.add(stack);
        }
        this.output=output;
    }


    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputs(ItemStack.class,inputs);
        ingredients.setOutput(ItemStack.class, output);
    }
}
