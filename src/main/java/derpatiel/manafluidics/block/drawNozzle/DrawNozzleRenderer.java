package derpatiel.manafluidics.block.drawNozzle;

import derpatiel.manafluidics.util.LOG;
import derpatiel.manafluidics.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class DrawNozzleRenderer extends TileEntitySpecialRenderer<DrawNozzleTileEntity> {

    @Override
    public void renderTileEntityAt(DrawNozzleTileEntity tile, double x, double y, double z, float partialTicks, int destroyStage) {
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

                //render bottom drawn bit
                frac = ((float) tile.extrudedQuantity) / ((float) DrawNozzleTileEntity.EXTRUSION_SIZE);
                frac *= 0.5f;//only draw half a block
                frac *= -1.0f;//draw beneath the block
                RenderUtil.renderFluidCuboid(liquid, tile.getPos(), x, y, z, 7.0f / 16.0f, frac, 7.0f / 16.0f, 9.0f / 16.0f, 0, 9.0f / 16.0f);
            }
        }
    }
}
