package derpatiel.manafluidics.proxy;

import derpatiel.manafluidics.ManaFluidics;
import derpatiel.manafluidics.UnfiredMoldRecipe;
import derpatiel.manafluidics.capability.heat.CapabilityHeat;
import derpatiel.manafluidics.event.EventHandler;
import derpatiel.manafluidics.network.MFPacketHandler;
import derpatiel.manafluidics.registry.*;
import derpatiel.manafluidics.world.WorldGen;
import jline.internal.Log;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.RecipeSorter;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent event) {
        Log.info("preInit COMMON");
        CapabilityHeat.register();
        SpellRegistry.init();
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
        RecipeSorter.register(ManaFluidics.MODID+":UnfiredMoldRecipe", UnfiredMoldRecipe.class, RecipeSorter.Category.SHAPELESS,"after:forge:shapedore");
        ModCrafting.registerCrafting();
        NetworkRegistry.INSTANCE.registerGuiHandler(ManaFluidics.instance, new ModGUIs());
        GameRegistry.registerWorldGenerator(new WorldGen(),100);
    }

    public void postInit(FMLPostInitializationEvent event) {
        Log.info("postInit COMMON");
    }

    public void registerItemRenderer(Item item, int meta, String name){
        //NO-OP
    }
}
