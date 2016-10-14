package derpatiel.manafluidics.block.floatTable;

import derpatiel.manafluidics.enums.MaterialType;
import derpatiel.manafluidics.util.MaterialItemHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.IItemHandler;

public class FloatTableItemHandler implements IItemHandler {

    ItemStack sheets;
    TileEntity tile;

    public FloatTableItemHandler(TileEntity tile){
        this.tile=tile;
    }

    @Override
    public int getSlots() {
        return 1;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        if(slot==0){
            return sheets;
        }
        return null;
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        return null;
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if(slot!=0 || sheets==null) {
            return null;
        }
        ItemStack stack = sheets;
        if(simulate) {
            stack = ItemStack.copyItemStack(sheets);
        }
        ItemStack extracted = stack.splitStack(amount);
        if(!simulate && stack.stackSize==0){
            sheets=null;
        }
        tile.markDirty();
        IBlockState state = tile.getWorld().getBlockState(tile.getPos());
        tile.getWorld().notifyBlockUpdate(tile.getPos(), state, state, 3);
        return extracted;
    }

    public void floatTableHarden(MaterialType type){
        sheets = MaterialItemHelper.getSheet(type,4);
    }
}
