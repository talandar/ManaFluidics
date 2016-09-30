package derpatiel.manafluidics.block.multiTank.fluidTank;

import derpatiel.manafluidics.util.RenderUtil;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidTankInfo;

public class FluidTankRenderer extends TileEntitySpecialRenderer<FluidTankTileEntity> {

    @Override
    public void renderTileEntityAt(FluidTankTileEntity tile, double x, double y, double z, float partialTicks, int destroyStage) {
        if (tile != null && tile.isFormed()) {

            FluidTankInfo tankInfo = tile.tank.getInfo();

            if (tankInfo.fluid != null && tankInfo.fluid.amount > 0) {

                int minX = tile.getMinX();
                int maxX = tile.getMaxX();

                int minY = tile.getTankBaseY();

                int minZ = tile.getMinZ();
                int maxZ = tile.getMaxZ();

                int minDrawY = minY - tile.getPos().getY() + 1;
                float height = (float) (tile.getHeight() - 0.01);
                float fluidFrac = ((float) tankInfo.fluid.amount) / ((float) tankInfo.capacity);
                if (fluidFrac > 1) {
                    fluidFrac = 1;
                }
                float maxDrawY = minDrawY + (fluidFrac * height);

                BlockPos from = new BlockPos(minX - tile.getPos().getX(), 0, minZ - tile.getPos().getZ());
                BlockPos to = new BlockPos(from.getX() + maxX - minX - 1, 0, from.getZ() + maxZ - minZ - 1);

                RenderUtil.renderStackedFluidCuboid(tankInfo.fluid, x + .5, y, z + .5, tile.getPos(), from, to, minDrawY, maxDrawY);
            }
        }
    }
}

