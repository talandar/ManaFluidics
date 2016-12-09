package derpatiel.manafluidics.block.runecraftingtable;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class RunecraftingTableInventory extends ItemStackHandler {

    RunecraftingTileEntity tile;

    public RunecraftingTableInventory(RunecraftingTileEntity te){
        super(11);
        this.tile = te;
    }

    public static final int BASE_SLOT=9;
    public static final int OUTPUT_SLOT=10;

    @Override
    protected int getStackLimit(int slot, ItemStack stack) {
        return 1;
    }

    @Override
    protected void onContentsChanged(int slot) {
        super.onContentsChanged(slot);
        tile.markDirty();
    }
}
