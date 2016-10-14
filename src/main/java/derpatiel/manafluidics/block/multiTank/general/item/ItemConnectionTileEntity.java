package derpatiel.manafluidics.block.multiTank.general.item;

import derpatiel.manafluidics.block.multiTank.TankPartTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;

public class ItemConnectionTileEntity extends TankPartTileEntity {

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if(parentLoc!=null) {
            if (isValidConnectionDirection(facing)) {
                if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                    return getParentTile().hasCapability(capability, null);
            }
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if(parentLoc!=null) {
            if (isValidConnectionDirection(facing)) {
                if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
                    return getParentTile().getCapability(capability, null);
                }
            }
        }
        return super.getCapability(capability, facing);
    }
}
