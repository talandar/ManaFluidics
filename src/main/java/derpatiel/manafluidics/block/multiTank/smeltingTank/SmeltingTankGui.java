package derpatiel.manafluidics.block.multiTank.smeltingTank;

import derpatiel.manafluidics.ManaFluidics;
import derpatiel.manafluidics.registry.ModGUIs;
import derpatiel.manafluidics.util.RenderUtil;
import net.minecraft.block.material.MapColor;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.capability.IFluidTankProperties;


public class SmeltingTankGui  extends GuiContainer {

    //TODO: handle clicks to change bottom fluid
    //TODO: mouseover to describe fluid


    public static final int WIDTH = 256;
    public static final int HEIGHT = 256;

    private static final ResourceLocation background = new ResourceLocation(ManaFluidics.MODID, ModGUIs.SMELTING_TANK_LOC);

    private SmeltingTankTileEntity tile;

    public SmeltingTankGui(SmeltingTankTileEntity tileEntity, SmeltingTankContainer container) {
        super(container);
        tile = tileEntity;
        xSize = WIDTH;
        ySize = HEIGHT;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        //drawn above the slots at this point
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        //draw some stuff around the slots
        int x = 11;
        int y = 6;

        // Add our own slots
        int slotIndex = 0;
        for (int i = 0; i < tile.MAX_SLOTS; i++) {
            if (i < tile.itemHandler.getSlots()) {//draw the progress behind the item
                SmeltingItemHandler.MeltProgress progress = tile.itemHandler.getMeltProgressInSlot(slotIndex);
                if(progress!=null) {
                    float heatFrac = progress.meltPercent();
                    int drawHeight = (int) (16.0f * heatFrac);
                    drawGradientRect(x + 1 + guiLeft, y + guiTop + 1 + (16 - drawHeight), x + guiLeft + 1 + 16, y + guiTop + 1 + 16, 0xFFFF0000, 0xFFFFFF00);
                }

            } else {//otherwise, blank out the fake slots
                drawTexturedModalRect(x + guiLeft, y + guiTop, 210, 192, 18, 18);
            }
            x += 18;
            if (x > 172) {
                x = 11;
                y += 18;
            }
            slotIndex++;
        }

        IFluidTankProperties[] fluids = tile.tank.getTankProperties();
        int fluidBottom = 167 + guiTop;
        int fluidXStart = guiLeft+174;
        int fluidWidth = 70;

        int totalHeight = 161;
        int tankCapacity = tile.tank.getCapacity();
        for(IFluidTankProperties fluidData : fluids){
            int amount = fluidData.getContents().amount;
            int drawHeight = (int)(((float)amount)/((float)tankCapacity) * totalHeight);
            float startY = fluidBottom-drawHeight;
            RenderUtil.renderTiledFluid(fluidXStart,(int)startY,fluidWidth,drawHeight,1.0f,fluidData.getContents());
            fluidBottom-=drawHeight;
        }
        //still behind the slots when drawing here

    }
}
