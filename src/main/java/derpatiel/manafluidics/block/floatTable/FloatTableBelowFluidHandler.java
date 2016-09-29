package derpatiel.manafluidics.block.floatTable;

import derpatiel.manafluidics.registry.ModFluids;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import javax.annotation.Nullable;

public class FloatTableBelowFluidHandler extends FloatTableFluidHandler{

    public FloatTableBelowFluidHandler(FloatTableTileEntity tileEntity){
        super(tileEntity);
    }

    @Nullable
    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain) {
        if(tileEntity.reactantTank.getFluidAmount()>0){
            return tileEntity.reactantTank.drain(resource,doDrain);
        }else{//nothing in reactant tank, can drain from mana tank because we're accessing from below
            return tileEntity.manaTank.drain(resource, doDrain);
        }
    }

    @Nullable
    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        if(tileEntity.reactantTank.getFluidAmount()>0){
            return tileEntity.reactantTank.drain(maxDrain,doDrain);
        }else{//nothing in reactant tank, can drain from mana tank because we're accessing from below
            return tileEntity.manaTank.drain(maxDrain, doDrain);
        }
    }


}
