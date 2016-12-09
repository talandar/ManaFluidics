package derpatiel.manafluidics.craft;

import derpatiel.manafluidics.block.SheetBlock;
import derpatiel.manafluidics.enums.MaterialType;
import derpatiel.manafluidics.registry.ModBlocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

/**
 * Created by Jim on 12/8/2016.
 */
public class RunecraftingRecipe {

    private ItemStack output;
    private ItemStack[] gridInputs;
    private ItemStack runeBase;

    private RunecraftingRecipe(){}

    public static RunecraftingRecipe createRecipe(ItemStack output, ItemStack runeBase, ItemStack[] grid){
        if(output==null
                || runeBase == null
                || !(runeBase.getItem() instanceof ItemBlock)
                || ((ItemBlock)runeBase.getItem()).getBlock() != ModBlocks.sheet
                || grid.length!=9)
            return null;
        RunecraftingRecipe recipe = new RunecraftingRecipe();
        recipe.output = output.copy();
        recipe.runeBase = runeBase.copy();
        ItemStack[] inputs = new ItemStack[9];
        for(int i=0;i<inputs.length;i++){
            inputs[i]=grid[i].copy();
        }
        recipe.gridInputs = inputs;
        return recipe;
    }

    public ItemStack getOutput(){
        return output.copy();
    }

    public ItemStack[] getInputGrid(){
        ItemStack[] inputs = new ItemStack[9];
        for(int i=0;i<inputs.length;i++){
            inputs[i]=gridInputs[i].copy();
        }
        return inputs;
    }

    public ItemStack getBaseSheet(){
        return runeBase.copy();
    }

    public MaterialType getSheetType(){
        return MaterialType.VALUES[runeBase.getMetadata()];
    }
}
