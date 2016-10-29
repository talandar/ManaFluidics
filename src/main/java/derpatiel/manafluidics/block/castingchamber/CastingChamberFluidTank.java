package derpatiel.manafluidics.block.castingchamber;

import derpatiel.manafluidics.network.FluidChangedPacket;
import derpatiel.manafluidics.network.MFPacketHandler;
import net.minecraftforge.fluids.FluidTank;

public class CastingChamberFluidTank extends FluidTank {
    private final CastingChamberTileEntity tile;

    public CastingChamberFluidTank(CastingChamberTileEntity tile, int capacity) {
        super(capacity);
        this.tile=tile;
    }

    @Override
    protected void onContentsChanged() {
        MFPacketHandler.INSTANCE.sendToAll(new FluidChangedPacket(tile.getPos(),tile.tank.getFluid()));
    }
}
