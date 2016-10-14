package derpatiel.manafluidics.multiblock;

import derpatiel.manafluidics.block.multiTank.TankFormingTileEntity;
import derpatiel.manafluidics.block.multiTank.fluidTank.FluidTankTileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class FluidTankData extends MultiblockData {

    public static final String TYPE = "fluidTank";

    public FluidTankData(BlockPos masterPos, List<BlockPos> positions) {
        super(masterPos, positions);
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public boolean blockBroken(World world, BlockPos pos) {
        //we know that the broken block is part of the tank
        TankFormingTileEntity tile = (TankFormingTileEntity) world.getTileEntity(masterPos);
        if(tile!=null) {
            tile.unform();
            valid=false;
        }
        return true;
    }

}
