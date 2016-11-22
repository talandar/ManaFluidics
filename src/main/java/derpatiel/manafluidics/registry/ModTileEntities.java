package derpatiel.manafluidics.registry;

import derpatiel.manafluidics.ManaFluidics;
import derpatiel.manafluidics.block.altar.KnowledgeAltarRenderer;
import derpatiel.manafluidics.block.altar.KnowledgeAltarTileEntity;
import derpatiel.manafluidics.block.castingchamber.CastingChamberTileEntity;
import derpatiel.manafluidics.block.drawNozzle.DrawNozzleRenderer;
import derpatiel.manafluidics.block.drawNozzle.DrawNozzleTileEntity;
import derpatiel.manafluidics.block.floatTable.FloatTableRenderer;
import derpatiel.manafluidics.block.floatTable.FloatTableTileEntity;
import derpatiel.manafluidics.block.fluidSpout.FluidSpoutRenderer;
import derpatiel.manafluidics.block.fluidSpout.FluidSpoutTileEntity;
import derpatiel.manafluidics.block.furnaceHeater.FurnaceHeaterTileEntity;
import derpatiel.manafluidics.block.multiTank.alloyTank.AlloyTankRenderer;
import derpatiel.manafluidics.block.multiTank.alloyTank.AlloyTankTileEntity;
import derpatiel.manafluidics.block.multiTank.fluidTank.FluidTankRenderer;
import derpatiel.manafluidics.block.multiTank.fluidTank.FluidTankTileEntity;
import derpatiel.manafluidics.block.multiTank.general.fluid.FluidConnectionTileEntity;
import derpatiel.manafluidics.block.multiTank.general.heat.HeatConnectionTileEntity;
import derpatiel.manafluidics.block.multiTank.general.item.ItemConnectionTileEntity;
import derpatiel.manafluidics.block.multiTank.smeltingTank.SmeltingTankRenderer;
import derpatiel.manafluidics.block.multiTank.smeltingTank.SmeltingTankTileEntity;
import derpatiel.manafluidics.block.portableTank.PortableTankRenderer;
import derpatiel.manafluidics.block.portableTank.PortableTankTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
        registerTileEntity(HeatConnectionTileEntity.class);
        registerTileEntity(ItemConnectionTileEntity.class);
        registerTileEntity(CastingChamberTileEntity.class);
        registerTileEntity(KnowledgeAltarTileEntity.class);
        registerTileEntity(AlloyTankTileEntity.class);
    }

    @SideOnly(Side.CLIENT)
    public static void registerRenderers()
    {
        ClientRegistry.bindTileEntitySpecialRenderer(DrawNozzleTileEntity.class, new DrawNozzleRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(PortableTankTileEntity.class, new PortableTankRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(FloatTableTileEntity.class, new FloatTableRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(FluidTankTileEntity.class, new FluidTankRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(FluidSpoutTileEntity.class, new FluidSpoutRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(SmeltingTankTileEntity.class, new SmeltingTankRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(KnowledgeAltarTileEntity.class, new KnowledgeAltarRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(AlloyTankTileEntity.class, new AlloyTankRenderer());
    }




    private static void registerTileEntity(Class<? extends TileEntity> tileEntityClass) {
        GameRegistry.registerTileEntity(tileEntityClass, ManaFluidics.MODID + ":" + tileEntityClass.getSimpleName().replaceFirst("TileEntity", ""));
    }
}
