package derpatiel.manafluidics.block.runecraftingtable;

import derpatiel.manafluidics.block.castingchamber.CastingChamberItemHandler;
import derpatiel.manafluidics.block.castingchamber.CastingChamberTileEntity;
import derpatiel.manafluidics.capability.item.PullOnlySlot;
import derpatiel.manafluidics.item.MFMoldItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nullable;

public class RunecraftingTableContainer extends Container {

    private RunecraftingTileEntity te;

    public RunecraftingTableContainer(IInventory playerInventory, RunecraftingTileEntity te) {
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
                int x = 8 + col * 18;
                int y = row * 18 + 105;
                this.addSlotToContainer(new Slot(playerInventory, col + row * 9 + 9, x, y));
            }
        }

        // Slots for the hotbar
        for (int row = 0; row < 9; ++row) {
            int x = 8 + row * 18;
            int y = 58 + 105;
            this.addSlotToContainer(new Slot(playerInventory, row, x, y));
        }
    }

    private void addOwnSlots() {
        IItemHandler inventory = this.te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);



        // Add our own slots
        int slotIndex = 0;
        for(int row=0;row<3;row++){
            for(int col=0;col<3;col++){
                addSlotToContainer(new SlotItemHandler(inventory, slotIndex,30+(18*col),17+(18*row)));
                slotIndex++;
            }
        }
        //add base slot
        addSlotToContainer(new SheetOnlyItemSlot(inventory, slotIndex,48,75));
        slotIndex++;
        //add output slot
        addSlotToContainer(new PullOnlySlot(inventory, slotIndex, 124,45));
        slotIndex++;
    }

    @Nullable
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = null;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
                if (index < 2) {
                    if (!this.mergeItemStack(itemstack1, 1, this.inventorySlots.size(), true)) {
                        return null;
                    }
                } else if (!this.mergeIntoInventory(itemstack1, 0, 1, false)) {
                    return null;
                }

                if (itemstack1.stackSize == 0) {
                    itemstack.stackSize-=1;
                    //slot.putStack(null);
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
        if(stack.getItem() instanceof MFMoldItem) {
            int i = startIndex;

            if (reverseDirection) {
                i = endIndex - 1;
            }

            if (stack.isStackable()) {
                while (stack.stackSize > 0 && (!reverseDirection && i < endIndex || reverseDirection && i >= startIndex)) {
                    Slot slot = (Slot) this.inventorySlots.get(i);
                    ItemStack itemstack = slot.getStack();

                    if (itemstack == null) {
                        ItemStack newStack = stack.copy();
                        newStack.stackSize = 1;
                        slot.putStack(newStack);
                        stack.stackSize--;
                        slot.onSlotChanged();
                        flag = true;
                    }

                    if (reverseDirection) {
                        --i;
                    } else {
                        ++i;
                    }
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