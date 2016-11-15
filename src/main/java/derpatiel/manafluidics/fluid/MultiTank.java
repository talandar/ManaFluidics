package derpatiel.manafluidics.fluid;

import derpatiel.manafluidics.block.multiTank.TankPartTileEntity;
import derpatiel.manafluidics.block.multiTank.smeltingTank.SmeltingTankTileEntity;
import derpatiel.manafluidics.util.LOG;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.capability.FluidTankProperties;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * Taken and adapted from Tinker's Construct SmelteryTank class under the MIT License
 * This version initially retrieved 10/10/2016 as git hash c660d69..., committed September 28, 2016 from https://github.com/SlimeKnights/TinkersConstruct
 * Modified to work with my smelting tank and class structure, but mostly the same
 */
public class MultiTank implements IFluidTank, IFluidHandler, INBTSerializable<NBTTagCompound> {

    protected final TankPartTileEntity parent;
    protected List<FluidStack> liquids; // currently contained liquids in the smeltery
    protected int maxCapacity;

    public MultiTank(TankPartTileEntity parent) {
        liquids = new ArrayList<>();
        maxCapacity = 0;
        this.parent = parent;
    }

    public void setCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public List<FluidStack> getFluids() {
        return liquids;
    }

    public void setFluids(List<FluidStack> fluids) {
        this.liquids = fluids;
        parent.markForUpdate();
    }

    @Nullable
    @Override
    public FluidStack getFluid() {
        return liquids.size() > 0 ? liquids.get(0) : null;
    }

    @Override
    public int getFluidAmount() {
        int cap = 0;
        for(FluidStack liquid : liquids) {
            cap += liquid.amount;
        }

        return cap;
    }

    @Override
    public int getCapacity() {
        return maxCapacity;
    }

    @Override
    public FluidTankInfo getInfo() {
        FluidStack fluid = getFluid();
        int capacity = getCapacity() - getFluidAmount();
        if(fluid != null) {
            capacity += fluid.amount;
        }
        return new FluidTankInfo(fluid, capacity);
    }

    @Override
    public IFluidTankProperties[] getTankProperties() {
        IFluidTankProperties[] properties = new IFluidTankProperties[liquids.size()];
        for(int i = 0; i < liquids.size(); i++) {
            boolean first = i == 0;
            int capacity = liquids.get(i).amount;
            if(first) {
                capacity += getCapacity() - getFluidAmount();
            }
            properties[i] = new FluidTankProperties(liquids.get(i), capacity, first, first);
        }

        return properties;
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        // check how much space is left in the smeltery
        int used = getFluidAmount();

        int usable = Math.min(maxCapacity - used, resource.amount);

        if(!doFill) {
            return usable;
        }

        // check if we already have the given liquid
        for(FluidStack liquid : liquids) {
            if(liquid.isFluidEqual(resource)) {
                // yup. add it
                liquid.amount += usable;
                parent.markForUpdate();
                return usable;
            }
        }

        // not present yet, add it
        resource = resource.copy();
        resource.amount = usable;
        liquids.add(resource);
        parent.markForUpdate();
        return usable;
    }

    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        if(liquids.isEmpty()) {
            return null;
        }

        FluidStack liquid = new FluidStack(liquids.get(0), maxDrain);
        return drain(liquid, doDrain);
    }

    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain) {
        // search for the resource
        ListIterator<FluidStack> iter = liquids.listIterator();
        while(iter.hasNext()) {
            FluidStack liquid = iter.next();
            if(liquid.isFluidEqual(resource)) {
                int drainable = Math.min(resource.amount, liquid.amount);
                if(doDrain) {
                    liquid.amount -= drainable;
                    if(liquid.amount <= 0) {
                        iter.remove();
                    }
                    parent.markForUpdate();
                }

                // return drained amount
                resource = resource.copy();
                resource.amount = drainable;
                return resource;
            }
        }

        // nothing drained
        return null;
    }

  /* Saving and loading */
  @Override
  public NBTTagCompound serializeNBT() {
      NBTTagCompound tag = new NBTTagCompound();
      NBTTagList taglist = new NBTTagList();

      for(FluidStack liquid : liquids) {
          if(FluidRegistry.getFluidName(liquid.getFluid()) == null) {
              LOG.error("Error trying to save fluids inside foundry tank! Invalid Liquid found! Tank contents:");
              for(FluidStack liquid2 : liquids) {
                  LOG.error("  " + liquid2.getUnlocalizedName() + "/" + liquid2.amount + "mb");
              }
              continue;
          }

          NBTTagCompound fluidTag = new NBTTagCompound();
          liquid.writeToNBT(fluidTag);
          taglist.appendTag(fluidTag);
      }

      tag.setTag("Liquids", taglist);
      tag.setInteger("LiquidCapacity", maxCapacity);
      return tag;
  }

    @Override
    public void deserializeNBT(NBTTagCompound tag) {
        NBTTagList taglist = tag.getTagList("Liquids", 10);

        liquids.clear();
        for(int i = 0; i < taglist.tagCount(); i++) {
            NBTTagCompound fluidTag = taglist.getCompoundTagAt(i);
            FluidStack liquid = FluidStack.loadFluidStackFromNBT(fluidTag);
            if(liquid != null) {
                liquids.add(liquid);
            }
        }

        maxCapacity = tag.getInteger("LiquidCapacity");
    }

    /** Moves the fluid with the passed index to the beginning/bottom of the fluid tank stack */
    public void moveFluidToBottom(int index) {
        if(index < liquids.size()) {
            FluidStack fluid = liquids.get(index);
            liquids.remove(index);
            liquids.add(0, fluid);
        }
    }
}
