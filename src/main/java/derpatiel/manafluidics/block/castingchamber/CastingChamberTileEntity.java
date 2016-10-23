package derpatiel.manafluidics.block.castingchamber;

import net.minecraft.inventory.IInventoryChangedListener;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraftforge.fluids.FluidTank;

public class CastingChamberTileEntity extends TileEntity implements ITickable {

    private final InventoryBasic moldInventory = new InventoryBasic("mold",false,1);
    private final InventoryBasic outputInventory = new InventoryBasic("output",false,1);
    private final InventoryBasic upgradeInventory = new InventoryBasic("upgrades",false,4);
    private final FluidTank tank = new FluidTank(0);

    private IInventoryChangedListener moldChangedListener;

    public CastingChamberTileEntity(){
        moldChangedListener = new MoldChangedListener(this);
        moldInventory.addInventoryChangeListener(moldChangedListener);
    }

    @Override
    public void update() {
        //TODO
    }

    private void moldChanged(){
        //TODO
    }


    private class MoldChangedListener implements IInventoryChangedListener{

        private CastingChamberTileEntity parentTile;

        public MoldChangedListener(CastingChamberTileEntity tile){
            this.parentTile=tile;
        }

        @Override
        public void onInventoryChanged(InventoryBasic invBasic) {
            this.parentTile.moldChanged();
        }
    }
}
