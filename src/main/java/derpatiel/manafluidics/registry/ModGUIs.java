package derpatiel.manafluidics.registry;

import derpatiel.manafluidics.block.altar.KAltarContainer;
import derpatiel.manafluidics.block.altar.KAltarGui;
import derpatiel.manafluidics.block.altar.KnowledgeAltarTileEntity;
import derpatiel.manafluidics.block.castingchamber.CastingChamberContainer;
import derpatiel.manafluidics.block.castingchamber.CastingChamberGui;
import derpatiel.manafluidics.block.castingchamber.CastingChamberTileEntity;
import derpatiel.manafluidics.block.furnaceHeater.FurnaceHeaterContainer;
import derpatiel.manafluidics.block.furnaceHeater.FurnaceHeaterGui;
import derpatiel.manafluidics.block.furnaceHeater.FurnaceHeaterTileEntity;
import derpatiel.manafluidics.block.multiTank.alloyTank.AlloyTankContainer;
import derpatiel.manafluidics.block.multiTank.alloyTank.AlloyTankGui;
import derpatiel.manafluidics.block.multiTank.alloyTank.AlloyTankTileEntity;
import derpatiel.manafluidics.block.multiTank.smeltingTank.SmeltingTankContainer;
import derpatiel.manafluidics.block.multiTank.smeltingTank.SmeltingTankGui;
import derpatiel.manafluidics.block.multiTank.smeltingTank.SmeltingTankTileEntity;
import derpatiel.manafluidics.block.runecraftingtable.RunecraftingTableContainer;
import derpatiel.manafluidics.block.runecraftingtable.RunecraftingTableGui;
import derpatiel.manafluidics.block.runecraftingtable.RunecraftingTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class ModGUIs implements IGuiHandler {

    public static final int FURNACE_HEATER_ID = 1;
    public static final String FURNACE_HEATER_LOC = "textures/gui/furnaceheater.png";

    public static final int SMELTING_TANK_ID = 2;
    public static final String SMELTING_TANK_LOC = "textures/gui/smeltingtank.png";

    public static final int CASTING_CHAMBER_ID = 3;
    public static final String CASTING_CHAMBER_LOC = "textures/gui/castingchamber.png";

    public static final int ALLOY_TANK_ID = 4;
    public static final String ALLOY_TANK_LOC = "textures/gui/alloytank.png";

    public static final int KNOWLEDGE_ALTAR_ID = 4;

    public static final int RUNECRAFTING_TABLE_ID = 5;
    public static final String RUNECRAFTING_TABLE_LOC = "textures/gui/runecraftingtable.png";

    public static final String PAGED_GUI_BG_LOC = "textures/gui/pagedbg.png";

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof FurnaceHeaterTileEntity) {
            return new FurnaceHeaterContainer(player.inventory, (FurnaceHeaterTileEntity) te);
        }else if(te instanceof SmeltingTankTileEntity){
            SmeltingTankTileEntity containerTileEntity = (SmeltingTankTileEntity)te;
            return new SmeltingTankContainer(player.inventory,containerTileEntity);
        }else if(te instanceof CastingChamberTileEntity){
            CastingChamberTileEntity containerTileEntity = (CastingChamberTileEntity)te;
            return new CastingChamberContainer(player.inventory,containerTileEntity);
        }else if(te instanceof AlloyTankTileEntity){
            AlloyTankTileEntity containerTileEntity = (AlloyTankTileEntity)te;
            return new AlloyTankContainer(player.inventory,containerTileEntity);
        }else if(te instanceof KnowledgeAltarTileEntity){
            KnowledgeAltarTileEntity containerTileEntity = (KnowledgeAltarTileEntity)te;
            return new KAltarContainer(containerTileEntity);
        }else if(te instanceof RunecraftingTileEntity){
            RunecraftingTileEntity containerTileEntity = (RunecraftingTileEntity)te;
            return new RunecraftingTableContainer(player.inventory,containerTileEntity);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof FurnaceHeaterTileEntity) {
            FurnaceHeaterTileEntity containerTileEntity = (FurnaceHeaterTileEntity) te;
            return new FurnaceHeaterGui(containerTileEntity, new FurnaceHeaterContainer(player.inventory, containerTileEntity));
        }else if(te instanceof SmeltingTankTileEntity){
            SmeltingTankTileEntity containerTileEntity = (SmeltingTankTileEntity)te;
            return new SmeltingTankGui(containerTileEntity, new SmeltingTankContainer(player.inventory,containerTileEntity));
        }else if(te instanceof CastingChamberTileEntity){
            CastingChamberTileEntity containerTileEntity = (CastingChamberTileEntity)te;
            return new CastingChamberGui(containerTileEntity, new CastingChamberContainer(player.inventory,containerTileEntity));
        }else if(te instanceof AlloyTankTileEntity){
            AlloyTankTileEntity containerTileEntity = (AlloyTankTileEntity)te;
            return new AlloyTankGui(player,containerTileEntity, new AlloyTankContainer(player.inventory,containerTileEntity));
        }else if(te instanceof KnowledgeAltarTileEntity) {
            KnowledgeAltarTileEntity containerTileEntity = (KnowledgeAltarTileEntity) te;
            return new KAltarGui(containerTileEntity, new KAltarContainer((containerTileEntity)),player);
        }else if(te instanceof RunecraftingTileEntity) {
            RunecraftingTileEntity containerTileEntity = (RunecraftingTileEntity) te;
            return new RunecraftingTableGui(containerTileEntity, new RunecraftingTableContainer(player.inventory,containerTileEntity));
        }
        return null;
    }

}
