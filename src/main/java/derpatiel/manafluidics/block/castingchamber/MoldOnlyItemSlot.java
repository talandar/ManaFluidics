package derpatiel.manafluidics.block.castingchamber;

import derpatiel.manafluidics.item.MFMoldItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class MoldOnlyItemSlot extends SlotItemHandler {
    public MoldOnlyItemSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return stack!=null && stack.getItem() instanceof MFMoldItem;
    }
}
