package derpatiel.manafluidics.block.multiTank.smeltingTank;

import derpatiel.manafluidics.block.furnaceHeater.FurnaceHeaterTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nullable;

public class SmeltingTankContainer extends Container {

    private SmeltingTankTileEntity te;

    public SmeltingTankContainer(IInventory playerInventory, SmeltingTankTileEntity te) {
        this.te = te;

        // This container references items out of our own inventory (the 9 slots we hold ourselves)
        // as well as the slots from the player inventory so that the user can transfer items between
        // both inventories. The two calls below make sure that slots are defined for both inventories.
        addOwnSlots();
        addPlayerSlots(playerInventory);
    }

    private void addPlayerSlots(IInventory playerInventory) {
        // Slots for the main inventory
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                int x = 48 + col * 18;
                int y = row * 18 + 174;
                this.addSlotToContainer(new Slot(playerInventory, col + row * 9 + 9, x, y));
            }
        }

        // Slots for the hotbar
        for (int row = 0; row < 9; ++row) {
            int x = 48 + row * 18;
            int y = 232;
            this.addSlotToContainer(new Slot(playerInventory, row, x, y));
        }
    }

    private void addOwnSlots() {
        IItemHandler itemHandler = this.te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        int x = 12;
        int y = 7;

        // Add our own slots
        int slotIndex = 0;
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex, x, y));
            slotIndex++;
            x += 18;
            if(x>243){
                x=12;
                y+=18;
            }
        }
    }

    @Nullable
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = null;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            int size = this.te.itemHandler.getSlots();
            if (index < size) {
                if (!this.mergeItemStack(itemstack1, size, this.inventorySlots.size(), true)) {
                    return null;
                }
            } else if (!this.mergeIntoInventory(itemstack1, 0, size, false)) {
                return null;
            }

            if (itemstack1.stackSize == 0) {
                slot.putStack(null);
            } else {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }

    /**
     * Merges provided ItemStack with the first avaliable one in the container/player inventor between minIndex
     * (included) and maxIndex (excluded). Args : stack, minIndex, maxIndex, negativDirection. /!\ the Container
     * implementation do not check if the item is valid for the slot
     */
    protected boolean mergeIntoInventory(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection)
    {
        boolean flag = false;
        int i = startIndex;

        if (reverseDirection)
        {
            i = endIndex - 1;
        }

        if (stack.isStackable())
        {
            while (stack.stackSize > 0 && (!reverseDirection && i < endIndex || reverseDirection && i >= startIndex))
            {
                Slot slot = (Slot)this.inventorySlots.get(i);
                ItemStack itemstack = slot.getStack();

                if(itemstack==null){
                    ItemStack newStack = stack.copy();
                    newStack.stackSize=1;
                    slot.putStack(newStack);
                    stack.stackSize--;
                    slot.onSlotChanged();
                    flag=true;
                }

                if (reverseDirection)
                {
                    --i;
                }
                else
                {
                    ++i;
                }
            }
        }
        return flag;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }
}
