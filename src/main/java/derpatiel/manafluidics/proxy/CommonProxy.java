package derpatiel.manafluidics.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent event){
        System.out.println("preInit COMMON");
    }
    public void init(FMLInitializationEvent event){
        System.out.println("init COMMON");
    }
    public void postInit(FMLPostInitializationEvent event){
        System.out.println("postInit COMMON");
    }
}
