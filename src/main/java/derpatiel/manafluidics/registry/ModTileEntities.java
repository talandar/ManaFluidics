package derpatiel.manafluidics.registry;

import derpatiel.manafluidics.ManaFluidics;
import derpatiel.manafluidics.block.drawNozzle.DrawNozzleRenderer;
import derpatiel.manafluidics.block.drawNozzle.DrawNozzleTileEntity;
import derpatiel.manafluidics.block.floatTable.FloatTableRenderer;
import derpatiel.manafluidics.block.floatTable.FloatTableTileEntity;
import derpatiel.manafluidics.block.fluidSpout.FluidSpoutRenderer;
import derpatiel.manafluidics.block.fluidSpout.FluidSpoutTileEntity;
import derpatiel.manafluidics.block.furnaceHeater.FurnaceHeaterTileEntity;
import derpatiel.manafluidics.block.multiTank.fluidTank.FluidTankRenderer;
import derpatiel.manafluidics.block.multiTank.fluidTank.FluidTankTileEntity;
import derpatiel.manafluidics.block.multiTank.general.fluid.FluidConnectionTileEntity;
import derpatiel.manafluidics.block.multiTank.smeltingTank.SmeltingTankTileEntity;
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
        registerTileEntity(FluidTankTileEntity.class);
        registerTileEntity(FluidConnectionTileEntity.class);
        registerTileEntity(FluidSpoutTileEntity.class);
        registerTileEntity(SmeltingTankTileEntity.class);
        registerTileEntity(FurnaceHeaterTileEntity.class);
    }

    public static void registerRenderers()
    {
        ClientRegistry.bindTileEntitySpecialRenderer(DrawNozzleTileEntity.class, new DrawNozzleRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(PortableTankTileEntity.class, new PortableTankRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(FloatTableTileEntity.class, new FloatTableRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(FluidTankTileEntity.class, new FluidTankRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(FluidSpoutTileEntity.class, new FluidSpoutRenderer());
    }




    private static void registerTileEntity(Class<? extends TileEntity> tileEntityClass) {
        GameRegistry.registerTileEntity(tileEntityClass, ManaFluidics.MODID + ":" + tileEntityClass.getSimpleName().replaceFirst("TileEntity", ""));
    }
}
