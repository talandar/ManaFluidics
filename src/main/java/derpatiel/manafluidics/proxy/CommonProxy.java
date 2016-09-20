package derpatiel.manafluidics.proxy;

import derpatiel.manafluidics.item.MFItem;
import derpatiel.manafluidics.registry.BlockRegistry;
import derpatiel.manafluidics.registry.ItemRegistry;
import jline.internal.Log;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent event) {
        Log.info("preInit COMMON");
        ItemRegistry.registerItems();
        BlockRegistry.registerBlocks();
        //Regsiter Fluids
        //Register Buckets
        //Register Tile Entitys
    }

    public void init(FMLInitializationEvent event) {
        Log.info("init COMMON");
        //Meta item helper
        //create event handler (if needed)
        //register crafting
        //register world gen
    }

    public void postInit(FMLPostInitializationEvent event) {
        Log.info("postInit COMMON");
    }

    public void registerItemRenderer(Item item, int meta, String name){
        //NO-OP
    }
}
