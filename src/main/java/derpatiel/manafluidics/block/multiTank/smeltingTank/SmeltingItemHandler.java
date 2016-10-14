package derpatiel.manafluidics.block.multiTank.smeltingTank;

import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.ItemStackHandler;

public class SmeltingItemHandler extends ItemStackHandler {

    private SmeltingTankTileEntity tile;

    public SmeltingItemHandler(SmeltingTankTileEntity tile)
    {
        super(0);
        this.tile=tile;
    }

    @Override
    public void setSize(int size)
    {
        ItemStack[] oldStacks = stacks;
        stacks = new ItemStack[size];
        for(int i=0;i<Math.min(oldStacks.length,stacks.length);i++){
            stacks[i]=oldStacks[i];
        }
        if(oldStacks.length>stacks.length){
            for(int i=stacks.length;i<oldStacks.length;i++){
                if(oldStacks[i]!=null && oldStacks[i].stackSize>0) {
                    BlockPos dropPos = tile.getCenterPos();
                    InventoryHelper.spawnItemStack(tile.getWorld(), dropPos.getX(), dropPos.getY(), dropPos.getZ(), oldStacks[i]);
                }
            }
        }
    }

    @Override
    protected void onContentsChanged(int slot)
    {
        //TODO
    }

    @Override
    public NBTTagCompound serializeNBT()
    {
        NBTTagCompound compound = super.serializeNBT();
        //TODO
        return compound;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        super.deserializeNBT(nbt);
        //TODO
    }

    @Override
    protected int getStackLimit(int slot, ItemStack stack)
    {
        return 1;
    }
}
