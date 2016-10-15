package derpatiel.manafluidics.block.multiTank.general.heat;

import derpatiel.manafluidics.block.TankPartBlock;
import derpatiel.manafluidics.block.multiTank.MFTankEntityBlock;
import derpatiel.manafluidics.block.multiTank.TankPartTileEntity;
import derpatiel.manafluidics.block.multiTank.smeltingTank.SmeltingTankTileEntity;
import derpatiel.manafluidics.capability.heat.CapabilityHeat;
import derpatiel.manafluidics.capability.heat.HeatProvider;
import derpatiel.manafluidics.capability.heat.IHeatHandler;
import derpatiel.manafluidics.enums.TankPartState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

public class HeatConnectionTileEntity extends TankPartTileEntity implements ITickable {


    @Override
    public void update() {
        if (parentLoc != null) {
            IBlockState blockState = worldObj.getBlockState(getPos());
            TankPartState partState = blockState.getValue(MFTankEntityBlock.STATE);
            if (partState == TankPartState.UNFORMED) {
                return;
            }
            BlockPos heaterBlock = null;
            EnumFacing heaterFacing = null;
            switch (partState) {
                case EAST:
                    heaterBlock = getPos().offset(EnumFacing.EAST);
                    heaterFacing = EnumFacing.WEST;
                    break;
                case WEST:
                    heaterBlock = getPos().offset(EnumFacing.WEST);
                    heaterFacing = EnumFacing.EAST;
                    break;
                case NORTH:
                    heaterBlock = getPos().offset(EnumFacing.NORTH);
                    heaterFacing = EnumFacing.SOUTH;
                    break;
                case SOUTH:
                    heaterBlock = getPos().offset(EnumFacing.SOUTH);
                    heaterFacing = EnumFacing.NORTH;
                    break;
            }
            if (heaterBlock != null) {
                TileEntity heatTile = worldObj.getTileEntity(heaterBlock);
                if (heatTile != null && heatTile.hasCapability(CapabilityHeat.HEAT, heaterFacing)) {
                    IHeatHandler heatProvider = heatTile.getCapability(CapabilityHeat.HEAT, heaterFacing);
                    int consumedHeat = heatProvider.consumeHeat();
                    SmeltingTankTileEntity parentTile = (SmeltingTankTileEntity)this.getParentTile();
                    if(parentTile!=null) {
                        parentTile.addHeat(consumedHeat);
                    }
                }
            }
        }
    }
}
