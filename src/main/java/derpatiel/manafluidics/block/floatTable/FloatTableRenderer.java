package derpatiel.manafluidics.block.floatTable;

import derpatiel.manafluidics.enums.MaterialType;
import derpatiel.manafluidics.util.FluidRenderBounds;
import derpatiel.manafluidics.util.MetaItemHelper;
import derpatiel.manafluidics.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class FloatTableRenderer  extends TileEntitySpecialRenderer<FloatTableTileEntity> {

    @Override
    public void renderTileEntityAt(FloatTableTileEntity tile, double x, double y, double z, float partialTicks, int destroyStage) {

        FluidStack mana=null;
        FluidStack reactant=null;
        //render from y=0 to y=1
        //but 2/16 of the block are tank floor, so we have 2/16 to 1 (14/16) to work with
        //we want the top 4/16 for the sheets and reactant, so that really leaves 10/16 for the 3 blocks of mana
        //so, 3000mb = render at 12/16
        //       0mb = render at 2/16 (1mb slightly above to avoid z-fighting)

        if(tile!=null && tile.getParent()!=null){
            if(tile.getParent().hasReactant()){
                //render reactant
                //LOG.info("reactant present");
                reactant = tile.getParent().reactantTank.getFluid();
            }
            if(tile.getParent().hasMana()){
                //render mana
                //LOG.info("mana present");
                mana=tile.getParent().manaTank.getFluid();
            }//else nothing

            FluidRenderBounds bounds = tile.getFluidRenderBounds(true);


            if(mana!=null && mana.amount>0){
                RenderUtil.renderFluidCuboid(mana, tile.getPos(), x, y, z, bounds.x, bounds.y, bounds.z, bounds.x+bounds.w, bounds.y+bounds.h, bounds.z+bounds.d, mana.getFluid().getColor());
            }

            bounds = tile.getFluidRenderBounds(false);
            if(reactant!=null){
                int fluidColorInt = reactant.getFluid().getColor();
                Color fluidColor = new Color(fluidColorInt, true);

                if(tile.getParent().getHardeningFraction()>0){
                    int fluidAlpha = (255- ((int) (255 * tile.getParent().getHardeningFraction())));
                    fluidColor = new Color(fluidColor.getRed(), fluidColor.getGreen(), fluidColor.getBlue(), fluidAlpha);
                }

                fluidColorInt = fluidColor.getRGB();
                RenderUtil.renderFluidCuboid(reactant, tile.getPos(), x, y, z, bounds.x, bounds.y, bounds.z, bounds.x+bounds.w, bounds.y+bounds.h, bounds.z+bounds.d, fluidColorInt);
            }
            ItemStack sheets = tile.getParent().itemHandler.getStackInSlot(0);
            if(sheets!=null || tile.getParent().getHardeningFraction()>0){

                int alpha = (int) (255 * tile.getParent().getHardeningFraction());
                if(sheets!=null)
                    alpha=255;

                int numSheets=4;
                if(sheets!=null)
                    numSheets = sheets.stackSize;
                if(numSheets>tile.facing.getID()){
                    VertexBuffer wr = Tessellator.getInstance().getBuffer();
                    MaterialType sheetType;
                    if(reactant!=null && MetaItemHelper.fluidProductMap.containsKey(reactant.getFluid())){
                        sheetType = MetaItemHelper.fluidProductMap.get(reactant.getFluid());
                    }else if(sheets!=null){
                        sheetType = MaterialType.getById(sheets.getMetadata());
                    }else{
                        return;//bomb out - strange case
                    }
                    ResourceLocation sheetResource = new ResourceLocation(MetaItemHelper.sheetResourceDomain.get(sheetType),MetaItemHelper.sheetResourceLocation.get(sheetType));
                    RenderUtil.pre(x, y, z);
                    wr.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
                    Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
                    int brightness = Minecraft.getMinecraft().theWorld.getCombinedLight(tile.getPos(),0);
                    Color color = new Color(255, 255, 255, alpha);
                    RenderUtil.putTexturedCuboid(wr, sheetResource,
                            bounds.x, 			12.0f/16.0f, 	bounds.z,
                            bounds.x+bounds.w, 	1.001, 			bounds.z+bounds.d,
                            color.getRGB(), brightness);
                    Tessellator.getInstance().draw();
                    RenderUtil.post();
                }
            }


        }
    }
}
