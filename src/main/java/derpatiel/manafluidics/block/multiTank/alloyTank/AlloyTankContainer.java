package derpatiel.manafluidics.block.multiTank.alloyTank;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nullable;

public class AlloyTankContainer extends Container {

    private AlloyTankTileEntity te;

    public AlloyTankContainer(IInventory playerInventory, AlloyTankTileEntity te) {
        this.te = te;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }
}
