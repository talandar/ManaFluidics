package derpatiel.manafluidics.block.runecraftingtable;

import derpatiel.manafluidics.block.SheetBlock;
import derpatiel.manafluidics.item.MFMoldItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SheetOnlyItemSlot extends SlotItemHandler {
    public SheetOnlyItemSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return stack!=null && stack.getItem() instanceof ItemBlock && ((ItemBlock)stack.getItem()).getBlock() instanceof SheetBlock ;
    }
}
