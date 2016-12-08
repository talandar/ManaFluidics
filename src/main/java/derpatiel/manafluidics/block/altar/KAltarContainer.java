package derpatiel.manafluidics.block.altar;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class KAltarContainer extends Container {

    private KnowledgeAltarTileEntity te;

    public KAltarContainer(KnowledgeAltarTileEntity te) {
        this.te = te;


    }
    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }
}