package derpatiel.manafluidics.block.runecraftingtable;

import derpatiel.manafluidics.block.SheetBlock;
import derpatiel.manafluidics.enums.MaterialType;
import derpatiel.manafluidics.registry.ModBlocks;
import net.minecraft.item.ItemBlock;
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

    public MaterialType getBaseType(){
        ItemStack stack = getStackInSlot(BASE_SLOT);
        if(stack!=null
                && stack.getItem() instanceof ItemBlock
                && ((ItemBlock)stack.getItem()).getBlock() instanceof SheetBlock){
            return MaterialType.VALUES[stack.getMetadata()];
        }
        return null;
    }
}
