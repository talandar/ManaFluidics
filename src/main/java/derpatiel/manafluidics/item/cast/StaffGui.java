package derpatiel.manafluidics.item.cast;

import derpatiel.manafluidics.block.altar.KAltarGui;
import derpatiel.manafluidics.block.altar.SpellPrepSelectionPage;
import derpatiel.manafluidics.gui.PagedGui;
import derpatiel.manafluidics.player.MFPlayerKnowledge;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

/**
 * Created by Jim on 12/28/2016.
 */
public class StaffGui extends PagedGui {

    private EntityPlayer player;

    public StaffGui(Container inventorySlotsIn, EntityPlayer player) {
        super(inventorySlotsIn);
        this.player=player;
    }

    @Override
    protected void addPages() {
        for(int i=0;i<5;i++) {
            addPage(new SpellSelectionPage(MFPlayerKnowledge.iconsByLevel[i], i, player,this));
        }
    }
}
