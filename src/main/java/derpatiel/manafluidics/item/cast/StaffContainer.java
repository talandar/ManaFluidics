package derpatiel.manafluidics.item.cast;

import derpatiel.manafluidics.block.altar.KnowledgeAltarTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class StaffContainer extends Container {

    public StaffContainer() {
    }
    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }
}