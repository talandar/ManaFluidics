package derpatiel.manafluidics.craft;

import derpatiel.manafluidics.block.runecraftingtable.RunecraftingTableInventory;
import derpatiel.manafluidics.enums.MaterialType;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jim on 12/8/2016.
 */
public class RuneCraftingHandler {

    public static final List<RunecraftingRecipe> craftingRecipes = new ArrayList<>();
    public static final Map<MaterialType,List<RunecraftingRecipe>> recipesByBaseType = new HashMap<>();
    static{
        for(MaterialType type : MaterialType.VALUES){
            recipesByBaseType.put(type, new ArrayList<>());
        }
    }


    public static void registerRecipe(RunecraftingRecipe recipe){
        craftingRecipes.add(recipe);
        recipesByBaseType.get(recipe.getSheetType()).add(recipe);
    }

    public static RunecraftingRecipe getMatchingRecipe(RunecraftingTableInventory inventory){
        MaterialType type = inventory.getBaseType();
        if(type!=null){
            for(RunecraftingRecipe recipe : recipesByBaseType.get(type)){
                if(matches(inventory,recipe)) {
                    return recipe;
                }
            }
        }
        return null;
    }

    public static boolean matches(RunecraftingTableInventory inventory, RunecraftingRecipe recipe){
        for(int slot=0;slot<9;slot++){
            ItemStack invStack = inventory.getStackInSlot(slot);
            ItemStack recipeStack = recipe.getInputGrid()[slot];
            if((invStack==null && recipeStack!=null) || (invStack!=null && !invStack.isItemEqual(recipeStack))){
                return false;
            }
        }
        return true;
    }

}
