package derpatiel.manafluidics.block.multiTank.smeltingTank;

import derpatiel.manafluidics.util.RenderUtil;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class SmeltingTankRenderer extends TileEntitySpecialRenderer<SmeltingTankTileEntity> {

    @Override
    public void renderTileEntityAt(SmeltingTankTileEntity tile, double x, double y, double z, float partialTicks, int destroyStage) {
        if (tile != null && tile.isFormed()) {
            IFluidTankProperties[] tankProperties = tile.tank.getTankProperties();

            float startY = tile.getTankBaseY();
            float minDrawY = startY - tile.getPos().getY() + 1;
            float totalCapacity = tile.tank.getCapacity();

            for (IFluidTankProperties props : tankProperties) {

                int amount = props.getContents().amount;


                int minX = tile.getMinX();
                int maxX = tile.getMaxX();

                int minZ = tile.getMinZ();
                int maxZ = tile.getMaxZ();


                float height = (float) (tile.getHeight() - 0.01);
                float fluidFrac = ((float) amount) / ((float) totalCapacity);
                if (fluidFrac > 1) {
                    fluidFrac = 1;
                }
                float maxDrawY = minDrawY + (fluidFrac * height);

                BlockPos from = new BlockPos(minX - tile.getPos().getX(), 0, minZ - tile.getPos().getZ());
                BlockPos to = new BlockPos(from.getX() + maxX - minX - 1, 0, from.getZ() + maxZ - minZ - 1);

                RenderUtil.renderStackedFluidCuboid(props.getContents(), x + .5, y, z + .5, tile.getPos(), from, to, minDrawY, maxDrawY);

                minDrawY=maxDrawY;
            }
        }
    }
}
