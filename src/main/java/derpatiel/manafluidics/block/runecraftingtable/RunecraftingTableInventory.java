package derpatiel.manafluidics.block.runecraftingtable;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class RunecraftingTableInventory extends ItemStackHandler {

    public RunecraftingTableInventory(){
        super(11);
    }

    public static final int BASE_SLOT=9;
    public static final int OUTPUT_SLOT=10;

    @Override
    protected int getStackLimit(int slot, ItemStack stack) {
        return 1;
    }

}
