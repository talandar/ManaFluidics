package derpatiel.manafluidics.block.pipe;

import derpatiel.manafluidics.block.drawNozzle.DrawNozzleTileEntity;
import derpatiel.manafluidics.util.RenderUtil;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class PipeRenderer extends TileEntitySpecialRenderer<PipeTileEntity> {

    @Override
    public void renderTileEntityAt(PipeTileEntity tile, double x, double y, double z, float partialTicks, int destroyStage) {
        FluidStack liquid;
        //render from y=12/16 to y=1
        if (tile != null) {
            float frac = ((float) tile.fluidTank.getFluidAmount()) / ((float) tile.fluidTank.getCapacity());
            liquid = tile.fluidTank.getFluid();

            if (liquid != null) {
                float wallThick = 1.0f / 16.0f;

                float renderHeight = 12.0f / 16.0f + (4.0f / 16.0f) * frac;

                //render tank contents
                RenderUtil.renderFluidCuboid(liquid, tile.getPos(), x, y, z, wallThick, 12.0f / 16.0f, wallThick, 1.0f - wallThick, renderHeight, 1.0f - wallThick, liquid.getFluid().getColor());
            }
        }
    }
}
