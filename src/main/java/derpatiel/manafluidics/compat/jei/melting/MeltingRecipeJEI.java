package derpatiel.manafluidics.compat.jei.melting;

import com.google.common.collect.Lists;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class MeltingRecipeJEI extends BlankRecipeWrapper{

    @Nonnull
    private final FluidStack output;

    @Nonnull
    private final ItemStack input;

    @Nonnull
    private final int heatInput;


    public MeltingRecipeJEI(ItemStack input, int heatInput, FluidStack output){
        this.input=input;
        this.heatInput=heatInput;
        this.output=output;
    }


    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInput(ItemStack.class,input);
        ingredients.setOutput(FluidStack.class, output);
    }

    @Nullable
    @Override
    public List<String> getTooltipStrings(int x, int y) {
        if(x>=34 && x<=49 && y>=11 && y<=26){
            return Lists.newArrayList(heatInput+" Heat Units");
        }
        return super.getTooltipStrings(x, y);
    }
}
