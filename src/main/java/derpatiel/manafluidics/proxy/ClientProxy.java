package derpatiel.manafluidics.proxy;

import derpatiel.manafluidics.ManaFluidics;
import derpatiel.manafluidics.block.MFBlockFluid;
import derpatiel.manafluidics.registry.ModTileEntities;
import derpatiel.manafluidics.util.LOG;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

    private static StateMap.Builder bldr = new StateMap.Builder();

    static {
        bldr.ignore(BlockFluidBase.LEVEL);
    }

    @Override
    public void preInit(FMLPreInitializationEvent event){
        super.preInit(event);
        LOG.info("preInit CLIENT");
    }

    @Override
    public void init(FMLInitializationEvent event){
        super.init(event);
        LOG.info("init CLIENT");
        ModTileEntities.registerRenderers();
    }

    @Override
    public void postInit(FMLPostInitializationEvent event){
        super.postInit(event);
        LOG.info("postInit CLIENT");
    }

    @Override
    public void registerItemRenderer(Item item, int meta, String name){
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(ManaFluidics.MODID + ":" + name, "inventory"));
    }

    @Override
    public void registerFluidStateMapper(MFBlockFluid fluidBlock) {
        ModelLoader.setCustomStateMapper(fluidBlock, bldr.build());
    }
}
