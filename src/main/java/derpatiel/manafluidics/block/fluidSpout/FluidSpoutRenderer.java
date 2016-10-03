package derpatiel.manafluidics.block.fluidSpout;


import derpatiel.manafluidics.util.RenderUtil;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class FluidSpoutRenderer extends TileEntitySpecialRenderer<FluidSpoutTileEntity> {

    @Override
    public void renderTileEntityAt(FluidSpoutTileEntity tile, double x, double y, double z, float partialTicks, int destroyStage) {
        FluidStack movingFluid = tile.getMovingFluid();

        if(movingFluid!=null) {

            EnumFacing direction = getWorld().getBlockState(tile.getPos()).getValue(FluidSpout.DIRECTION);

            float volumeTop = 6.0f / 16.0f;
            float spoutBottom = 10.0f / 16.0f;

            switch(direction){
                case NORTH:{
                    float startX = 6.0f / 16.0f;
                    float endX = 10.0f / 16.0f;

                    float startZ = 1.0f / 16.0f;
                    float startZColumn = 9.0f/16.0f;
                    float endZ = 12.0f / 16.0f;

                    RenderUtil.renderFluidCuboid(movingFluid, tile.getPos(), x, y, z, startX, volumeTop, startZ, endX, spoutBottom, endZ);
                    RenderUtil.renderFluidCuboid(movingFluid, tile.getPos(),x,y,z,startX,0,startZColumn,endX,volumeTop,endZ);
                    break;
                }
                case EAST:{
                    float startZ = 6.0f / 16.0f;
                    float endZ = 10.0f / 16.0f;

                    float startX = 4.0f / 16.0f;
                    float startXColumn =4.0f/16.0f;
                    float endXColumn = 7.0f/16.0f;
                    float endX = 15.0f / 16.0f;

                    RenderUtil.renderFluidCuboid(movingFluid, tile.getPos(), x, y, z, startX, volumeTop, startZ, endX, spoutBottom, endZ);
                    RenderUtil.renderFluidCuboid(movingFluid, tile.getPos(),x,y,z,startXColumn,0,startZ,endXColumn,volumeTop,endZ);
                    break;
                }
                case WEST:{
                    float startZ = 6.0f / 16.0f;
                    float endZ = 10.0f / 16.0f;

                    float startX = 1.0f / 16.0f;
                    float startXColumn = 9.0f/16.0f;
                    float endXColumn = 12.0f/16.0f;
                    float endX = 12.0f / 16.0f;

                    RenderUtil.renderFluidCuboid(movingFluid, tile.getPos(), x, y, z, startX, volumeTop, startZ, endX, spoutBottom, endZ);
                    RenderUtil.renderFluidCuboid(movingFluid, tile.getPos(),x,y,z,startXColumn,0,startZ,endXColumn,volumeTop,endZ);
                    break;
                }
                case SOUTH:{
                    float startX = 6.0f / 16.0f;
                    float endX = 10.0f / 16.0f;

                    float startZ = 4.0f / 16.0f;
                    float startZColumn =4.0f/16.0f;
                    float endZColumn = 7.0f/16.0f;
                    float endZ = 15.0f / 16.0f;

                    RenderUtil.renderFluidCuboid(movingFluid, tile.getPos(), x, y, z, startX, volumeTop, startZ, endX, spoutBottom, endZ);
                    RenderUtil.renderFluidCuboid(movingFluid, tile.getPos(),x,y,z,startX,0,startZColumn,endX,volumeTop,endZColumn);
                    break;
                }
            }
        }
    }
}
