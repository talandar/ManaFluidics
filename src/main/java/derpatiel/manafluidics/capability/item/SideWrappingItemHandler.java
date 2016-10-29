package derpatiel.manafluidics.capability.item;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.HashMap;
import java.util.Map;

public class SideWrappingItemHandler implements IItemHandler {

    private Map<Integer,Integer> availableSlotMap = new HashMap<>();
    private IItemHandler baseHandler;

    public SideWrappingItemHandler(IItemHandler baseHandler, int... slots){
        this.baseHandler=baseHandler;
        int internalSlot = 0;
        for(int wrappedSlot : slots){
            availableSlotMap.put(internalSlot,wrappedSlot);
            internalSlot++;
        }
    }

    @Override
    public int getSlots() {
        return availableSlotMap.size();
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return baseHandler.getStackInSlot(availableSlotMap.get(slot));
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        return baseHandler.insertItem(availableSlotMap.get(slot),stack,simulate);
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        return baseHandler.extractItem(availableSlotMap.get(slot),amount,simulate);
    }
}
