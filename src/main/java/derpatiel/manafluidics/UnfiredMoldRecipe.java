package derpatiel.manafluidics;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapelessRecipes;

import java.util.List;

public class UnfiredMoldRecipe extends ShapelessRecipes {

    public UnfiredMoldRecipe(ItemStack output, List<ItemStack> inputList) {
        super(output, inputList);
    }

    @Override
    public ItemStack[] getRemainingItems(InventoryCrafting inv) {
        ItemStack[] aitemstack = new ItemStack[inv.getSizeInventory()];

        for (int i = 0; i < aitemstack.length; ++i)
        {
            ItemStack itemstack = inv.getStackInSlot(i);
            if(itemstack!=null) {
                Item item = itemstack.getItem();
                if (item == Items.CLAY_BALL || (item instanceof ItemBlock && ((ItemBlock) item).getBlock() == Blocks.CLAY)) {
                    aitemstack[i] = null;
                } else {
                    aitemstack[i] = itemstack.copy();
                    aitemstack[i].stackSize=1;
                }
            }else{
                aitemstack[i]=null;
            }
        }
        return aitemstack;
    }
}
