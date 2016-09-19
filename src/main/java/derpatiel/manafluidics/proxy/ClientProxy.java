package derpatiel.manafluidics.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
    public void preInit(FMLPreInitializationEvent event){
        super.preInit(event);
        System.out.println("preInit CLIENT");
    }
    public void init(FMLInitializationEvent event){
        super.init(event);
        System.out.println("init CLIENT");
    }
    public void postInit(FMLPostInitializationEvent event){
        super.postInit(event);
        System.out.println("postInit CLIENT");
    }
}
