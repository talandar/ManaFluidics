package derpatiel.manafluidics;

import derpatiel.manafluidics.command.MFCommand;
import derpatiel.manafluidics.proxy.CommonProxy;
import net.minecraft.init.Blocks;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid =  ManaFluidics.MODID, version =  ManaFluidics.VERSION,  name = ManaFluidics.NAME, acceptedMinecraftVersions = "[1.10.2]")
public class ManaFluidics
{
    public static final String MODID = "manafluidics";
    public static final String VERSION = "1.0.0";
    public static final String NAME = "Mana Fluidics";

    static{
        FluidRegistry.enableUniversalBucket();
    }

    @Mod.Instance(MODID)
    public static ManaFluidics instance;

    @SidedProxy(serverSide = "derpatiel.manafluidics.proxy.CommonProxy", clientSide = "derpatiel.manafluidics.proxy.ClientProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event){
        proxy.postInit(event);
    }

    @Mod.EventHandler
    public void onServerLoad(FMLServerStartingEvent event){
        event.registerServerCommand(new MFCommand());
    }
}
