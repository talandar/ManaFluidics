package derpatiel.manafluidics.block.portableTank;

import derpatiel.manafluidics.util.RenderUtil;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.fluids.FluidStack;

public class PortableTankRenderer extends TileEntitySpecialRenderer<PortableTankTileEntity> {

    @Override
    public void renderTileEntityAt(PortableTankTileEntity tile, double x, double y, double z, float partialTicks, int destroyStage) {
        FluidStack liquid=null;

        if(tile!=null){
            float frac = ((float)tile.fluidTank.getFluidAmount())/((float)tile.fluidTank.getCapacity());
            liquid = tile.fluidTank.getFluid();

            if(liquid!=null){
                float wallThick=1.0f/16.0f+0.01f;

                float renderHeight = 1.0f/16.0f + (14.0f/16.0f)*frac;

                //render tank contents
                RenderUtil.renderFluidCuboid(liquid, tile.getPos(), x, y, z, wallThick, 1.0f/16.0f, wallThick, 1.0f-wallThick, renderHeight, 1.0f-wallThick, liquid.getFluid().getColor());
            }
        }
    }
}
