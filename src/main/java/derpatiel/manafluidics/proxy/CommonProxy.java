package derpatiel.manafluidics.proxy;

import derpatiel.manafluidics.registry.ModBlocks;
import derpatiel.manafluidics.registry.ModCrafting;
import derpatiel.manafluidics.registry.ModItems;
import derpatiel.manafluidics.registry.ModFluids;
import jline.internal.Log;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent event) {
        Log.info("preInit COMMON");
        ModItems.registerItems();
        ModBlocks.registerBlocks();
        ModFluids.registerFluids();
        ModFluids.registerBuckets();
        //Register Tile Entitys
    }

    public void init(FMLInitializationEvent event) {
        Log.info("init COMMON");
        //Meta item helper
        //create event handler (if needed)
        ModCrafting.registerCrafting();
        //register world gen
    }

    public void postInit(FMLPostInitializationEvent event) {
        Log.info("postInit COMMON");
    }

    public void registerItemRenderer(Item item, int meta, String name){
        //NO-OP
    }
}
