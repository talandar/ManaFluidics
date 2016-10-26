package derpatiel.manafluidics.block.castingchamber;

import derpatiel.manafluidics.item.MFMoldItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class CastingChamberItemHandler extends ItemStackHandler{

    public static final int MOLD_SLOT=0;
    public static final int OUTPUT_SLOT=1;
    public static final int[] UPGRADE_SLOTS=new int[]{2,3,4,5};


    private final CastingChamberTileEntity tile;

    public CastingChamberItemHandler(CastingChamberTileEntity tile){
        super(6);
        this.tile=tile;
    }

    @Override
    protected void onContentsChanged(int slot) {
        super.onContentsChanged(slot);
        tile.inventoryChanged(slot);
    }

    @Override
    protected int getStackLimit(int slot, ItemStack stack)
    {
        if(slot==OUTPUT_SLOT){
            return stack.getMaxStackSize();
        }else{
            return 1;
        }
    }

    @Nullable
    public MFMoldItem getMold(){
        ItemStack stack = getStackInSlot(MOLD_SLOT);
        if(stack==null || stack.stackSize==0 || !(stack.getItem() instanceof MFMoldItem)){
            return null;
        }
        return (MFMoldItem)stack.getItem();
    }


}
