package derpatiel.manafluidics.registry;

import derpatiel.manafluidics.block.furnaceHeater.FurnaceHeaterContainer;
import derpatiel.manafluidics.block.furnaceHeater.FurnaceHeaterGui;
import derpatiel.manafluidics.block.furnaceHeater.FurnaceHeaterTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class ModGUIs implements IGuiHandler {

    public static final int FURNACE_HEATER_ID = 1;
    public static final String FURNACE_HEATER_LOC = "textures/gui/furnaceheater.png";

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof FurnaceHeaterTileEntity) {
            return new FurnaceHeaterContainer(player.inventory, (FurnaceHeaterTileEntity) te);
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
        }
        return null;
    }

}