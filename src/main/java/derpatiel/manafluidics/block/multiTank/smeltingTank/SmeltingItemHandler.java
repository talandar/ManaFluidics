package derpatiel.manafluidics.block.multiTank.smeltingTank;

import derpatiel.manafluidics.util.MaterialItemHelper;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.items.ItemStackHandler;

public class SmeltingItemHandler extends ItemStackHandler {

    private SmeltingTankTileEntity tile;

    private MeltProgress[] meltData;

    public SmeltingItemHandler(SmeltingTankTileEntity tile)
    {
        super(0);
        meltData = new MeltProgress[0];
        this.tile=tile;
    }

    @Override
    public void setSize(int size)
    {
        ItemStack[] oldStacks = stacks;
        MeltProgress[] oldProgress = meltData;
        stacks = new ItemStack[size];
        meltData = new MeltProgress[size];
        for(int i=0;i<Math.min(oldStacks.length,stacks.length);i++){
            stacks[i]=oldStacks[i];
            meltData[i]=oldProgress[i];
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
        ItemStack slotContents = getStackInSlot(slot);
        if(slotContents==null || slotContents.stackSize==0){
            meltData[slot]=null;
        }else{
            meltData[slot]=new MeltProgress(MaterialItemHelper.getMeltHeat(slotContents));
        }
    }

    @Override
    public NBTTagCompound serializeNBT()
    {
        NBTTagCompound compound = super.serializeNBT();
        NBTTagList nbtTagList = new NBTTagList();
        for (int i = 0; i < meltData.length; i++)
        {
            if (meltData[i] != null)
            {
                NBTTagCompound meltTag = new NBTTagCompound();
                meltTag.setInteger("Slot", i);
                meltData[i].writeToNBT(meltTag);
                nbtTagList.appendTag(meltTag);
            }
        }
        compound.setTag("meltData", nbtTagList);
        return compound;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        super.deserializeNBT(nbt);
        NBTTagList tagList = nbt.getTagList("meltData", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < tagList.tagCount(); i++)
        {
            NBTTagCompound heatTags = tagList.getCompoundTagAt(i);
            int slot = heatTags.getInteger("Slot");

            if (slot >= 0 && slot < stacks.length)
            {
                meltData[slot] = MeltProgress.loadMeltProgressFromNBT(heatTags);
            }
        }
    }

    @Override
    protected int getStackLimit(int slot, ItemStack stack)
    {
        return 1;
    }

    public MeltProgress getMeltProgressInSlot(int slot) {
        validateSlotIndex(slot);
        return this.meltData[slot];
    }

    public static class MeltProgress{
        private final int neededHeat;
        private int currentHeat;

        public MeltProgress(int neededHeat){
            this.neededHeat=neededHeat;
            currentHeat=0;
        }

        public void addHeat(int heat){
            if(neededHeat>0) {
                currentHeat += heat;
            }
        }

        public float meltPercent(){
            return Math.min(1.0f,((float)currentHeat)/((float)neededHeat));
        }

        public boolean isMelted(){
            return currentHeat>=neededHeat;
        }

        public void writeToNBT(NBTTagCompound meltTag) {
            meltTag.setInteger("neededHeat",neededHeat);
            meltTag.setInteger("currentHeat",currentHeat);
        }

        public static MeltProgress loadMeltProgressFromNBT(NBTTagCompound heatTags) {
            MeltProgress progress = new MeltProgress(heatTags.getInteger("neededHeat"));
            progress.currentHeat=heatTags.getInteger("currentHeat");
            return progress;
        }
    }
}
