package derpatiel.manafluidics.block.pipe;

import derpatiel.manafluidics.block.drawNozzle.DrawNozzleTileEntity;
import derpatiel.manafluidics.util.RenderUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class PipeRenderer extends TileEntitySpecialRenderer<PipeTileEntity> {

    private static float FIX_Z_FIGHT=0.001f;

    @Override
    public void renderTileEntityAt(PipeTileEntity tile, double x, double y, double z, float partialTicks, int destroyStage) {
        FluidStack liquid;
        if (tile != null) {
            IBlockState state = getWorld().getBlockState(tile.getPos()).getActualState(getWorld(),tile.getPos());
            float frac = tile.getFluidFillPercent();

            if (frac > 0) {

                liquid = tile.fluidTank.getFluid();

                if (liquid != null) {
                    float wallThick = (5.0f / 16.0f);
                    float renderBottom = (5.0f/16.0f);

                    float renderHeight = renderBottom + (6.0f / 16.0f) * frac;

                    //draw center
                    RenderUtil.renderFluidCuboid(liquid, tile.getPos(), x, y, z, wallThick+FIX_Z_FIGHT, renderBottom+FIX_Z_FIGHT, wallThick+FIX_Z_FIGHT, 1.0f - wallThick-FIX_Z_FIGHT, renderHeight-FIX_Z_FIGHT, 1.0f - wallThick-FIX_Z_FIGHT, liquid.getFluid().getColor());

                    if(state.getValue(PipeBlock.EAST)){
                        RenderUtil.renderFluidCuboid(liquid, tile.getPos(), x, y, z, 11.0f/16.0f, renderBottom+FIX_Z_FIGHT, wallThick+FIX_Z_FIGHT, 1.0f, renderHeight-FIX_Z_FIGHT, 1.0f - wallThick-FIX_Z_FIGHT, liquid.getFluid().getColor());
                    }
                    if(state.getValue(PipeBlock.WEST)){
                        RenderUtil.renderFluidCuboid(liquid, tile.getPos(), x, y, z, 0.0f, renderBottom+FIX_Z_FIGHT, wallThick+FIX_Z_FIGHT, 5.0f/16.0f, renderHeight-FIX_Z_FIGHT, 1.0f - wallThick-FIX_Z_FIGHT, liquid.getFluid().getColor());
                    }
                    if(state.getValue(PipeBlock.NORTH)){
                        RenderUtil.renderFluidCuboid(liquid, tile.getPos(), x, y, z, wallThick+FIX_Z_FIGHT, renderBottom+FIX_Z_FIGHT, 0.0f, 1.0f - wallThick-FIX_Z_FIGHT, renderHeight-FIX_Z_FIGHT, 5.0f/16.0f, liquid.getFluid().getColor());
                    }
                    if(state.getValue(PipeBlock.SOUTH)){
                        RenderUtil.renderFluidCuboid(liquid, tile.getPos(), x, y, z, wallThick+FIX_Z_FIGHT, renderBottom+FIX_Z_FIGHT,11.0f/16.0f, 1.0f - wallThick-FIX_Z_FIGHT, renderHeight-FIX_Z_FIGHT, 1.0f, liquid.getFluid().getColor());
                    }
                    if(state.getValue(PipeBlock.UP)){
                        RenderUtil.renderFluidCuboid(liquid, tile.getPos(), x, y, z, wallThick+FIX_Z_FIGHT, 11.0/16.0f, wallThick+FIX_Z_FIGHT, 1.0f - wallThick-FIX_Z_FIGHT, (11.0f/16.0f) + ((5.0f/16.0f)*frac), 1.0f - wallThick-FIX_Z_FIGHT, liquid.getFluid().getColor());
                    }
                    if(state.getValue(PipeBlock.DOWN)){
                        RenderUtil.renderFluidCuboid(liquid, tile.getPos(), x, y, z, wallThick+FIX_Z_FIGHT, 0.0f, wallThick+FIX_Z_FIGHT, 1.0f - wallThick-FIX_Z_FIGHT, (5.0f/16.0f)*frac, 1.0f - wallThick-FIX_Z_FIGHT, liquid.getFluid().getColor());
                    }

                }
            }
        }
    }
}
