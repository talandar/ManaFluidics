package derpatiel.manafluidics.proxy;

import derpatiel.manafluidics.ManaFluidics;
import derpatiel.manafluidics.capability.heat.CapabilityHeat;
import derpatiel.manafluidics.event.EventHandler;
import derpatiel.manafluidics.network.MFPacketHandler;
import derpatiel.manafluidics.registry.*;
import jline.internal.Log;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent event) {
        Log.info("preInit COMMON");
        CapabilityHeat.register();
        ModItems.registerItems();
        ModBlocks.registerBlocks();
        ModFluids.registerFluids();
        ModFluids.registerBuckets();
        ModTileEntities.registerTileEntities();
    }

    public void init(FMLInitializationEvent event) {
        Log.info("init COMMON");
        MFPacketHandler.init();
        MinecraftForge.EVENT_BUS.register(EventHandler.eventHandler);
        ModCrafting.registerCrafting();
        NetworkRegistry.INSTANCE.registerGuiHandler(ManaFluidics.instance, new ModGUIs());
        //register world gen
    }

    public void postInit(FMLPostInitializationEvent event) {
        Log.info("postInit COMMON");
    }

    public void registerItemRenderer(Item item, int meta, String name){
        //NO-OP
    }
}
