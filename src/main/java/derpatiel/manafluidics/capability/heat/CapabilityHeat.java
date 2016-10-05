package derpatiel.manafluidics.capability.heat;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.energy.EnergyStorage;

import java.util.concurrent.Callable;

public class CapabilityHeat {

    @CapabilityInject(IHeatHandler.class)
    public static Capability<IHeatHandler> HEAT = null;

    public static void register()
    {
        CapabilityManager.INSTANCE.register(IHeatHandler.class, new Capability.IStorage<IHeatHandler>()
                {
                    @Override
                    public NBTBase writeNBT(Capability<IHeatHandler> capability, IHeatHandler instance, EnumFacing side)
                    {
                        return new NBTTagInt(0);//instance.getEnergyStored());
                    }

                    @Override
                    public void readNBT(Capability<IHeatHandler> capability, IHeatHandler instance, EnumFacing side, NBTBase nbt)
                    {
                        if (!(instance instanceof EnergyStorage))
                            throw new IllegalArgumentException("Can not deserialize to an instance that isn't the default implementation");
                        //((IHeatHandler)instance).energy = ((NBTTagInt)nbt).getInt();
                    }
                },
                new Callable<IHeatHandler>()
                {
                    @Override
                    public IHeatHandler call() throws Exception
                    {
                        return null;// new HeatHandler();
                    }
                });
    }
}
