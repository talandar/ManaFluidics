package derpatiel.manafluidics.block.floatTable;

import derpatiel.manafluidics.registry.ModFluids;
import derpatiel.manafluidics.util.MaterialItemHelper;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import javax.annotation.Nullable;

public class FloatTableFluidHandler implements IFluidHandler{

    protected final FloatTableTileEntity tileEntity;

    public FloatTableFluidHandler(FloatTableTileEntity tileEntity){
        this.tileEntity = tileEntity;
    }

    @Override
    public IFluidTankProperties[] getTankProperties() {
        IFluidTankProperties[] manaProps = tileEntity.manaTank.getTankProperties();
        IFluidTankProperties[] reactantProps = tileEntity.reactantTank.getTankProperties();
        IFluidTankProperties[] ret = new IFluidTankProperties[manaProps.length+reactantProps.length];
        int iter=0;
        for(IFluidTankProperties prop : manaProps){
            ret[iter]=prop;
            iter++;
        }
        for(IFluidTankProperties prop : reactantProps){
            ret[iter]=prop;
            iter++;
        }
        return ret;
    }

    @Override
    public int fill(FluidStack resource, boolean doFill){
        if(tileEntity.itemHandler.sheets==null || tileEntity.itemHandler.sheets.stackSize==0){
            if(tileEntity.manaTank.getFluidAmount()==tileEntity.manaTank.getCapacity()){
                if(MaterialItemHelper.fluidProductMap.containsKey(resource.getFluid()) && (tileEntity.reactantTank.getFluidAmount()==0 || tileEntity.reactantTank.getFluid().getFluid()==resource.getFluid())){
                    return tileEntity.reactantTank.fill(resource, doFill);
                }else{
                    return 0;
                }
                //mana tank full, accept only reactants into reactant tank
            }else{
                //mana tank not full, accept only mana into mana tank.
                if(resource.getFluid()== ModFluids.reactiveMana){
                    return tileEntity.manaTank.fill(resource, doFill);
                }else{
                    return 0;
                }
            }
        }else{
            return 0;
        }
    }

    @Nullable
    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain) {
        return tileEntity.reactantTank.drain(resource,doDrain);
    }

    @Nullable
    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        return tileEntity.reactantTank.drain(maxDrain,doDrain);
    }

}
