package derpatiel.manafluidics.registry;

import derpatiel.manafluidics.ManaFluidics;
import derpatiel.manafluidics.block.drawNozzle.DrawNozzleRenderer;
import derpatiel.manafluidics.block.drawNozzle.DrawNozzleTileEntity;
import derpatiel.manafluidics.block.floatTable.FloatTableTileEntity;
import derpatiel.manafluidics.block.portableTank.PortableTankRenderer;
import derpatiel.manafluidics.block.portableTank.PortableTankTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModTileEntities {



    public static void registerTileEntities() {
        registerTileEntity(DrawNozzleTileEntity.class);
        registerTileEntity(PortableTankTileEntity.class);
        registerTileEntity(FloatTableTileEntity.class);
    }

    public static void registerRenderers()
    {
        ClientRegistry.bindTileEntitySpecialRenderer(DrawNozzleTileEntity.class, new DrawNozzleRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(PortableTankTileEntity.class, new PortableTankRenderer());
    }




    private static void registerTileEntity(Class<? extends TileEntity> tileEntityClass) {
        GameRegistry.registerTileEntity(tileEntityClass, ManaFluidics.MODID + ":" + tileEntityClass.getSimpleName().replaceFirst("TileEntity", ""));
    }
}
