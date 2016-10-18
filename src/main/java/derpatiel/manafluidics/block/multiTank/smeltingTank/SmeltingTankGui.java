package derpatiel.manafluidics.block.multiTank.smeltingTank;

import derpatiel.manafluidics.ManaFluidics;
import derpatiel.manafluidics.network.MFPacketHandler;
import derpatiel.manafluidics.registry.ModGUIs;
import derpatiel.manafluidics.util.LOG;
import derpatiel.manafluidics.util.MaterialItemHelper;
import derpatiel.manafluidics.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class SmeltingTankGui  extends GuiContainer {


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
    public void drawScreen(int x, int y, float partialTicks) {
        super.drawScreen(x, y, partialTicks);
        //drawn above the slots at this point
        for (int i = 0; i < buttonList.size(); i++) {
            if (buttonList.get(i) instanceof FluidButton) {
                FluidButton btn = (FluidButton) buttonList.get(i);
                if (btn.isMouseOver()) {
                    FluidStack stack = btn.getFluidData();
                    List<String> descriptions = new ArrayList<>();
                    descriptions.add("Fluid: " + stack.getFluid().getLocalizedName(stack));
                    descriptions.add(stack.amount+"mb.  ("+ MaterialItemHelper.getIngotsString(stack) +")");
                    if(btn.id!=0) {
                        descriptions.add("Click to move to output.");
                    }
                    drawHoveringText(descriptions, x, y, fontRendererObj);
                }
            }
        }
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
        int fluidTankRenderBottom = 167 + guiTop;
        float fluidBottom = fluidTankRenderBottom;
        int fluidXStart = guiLeft+174;
        int fluidWidth = 70;

        int totalHeight = 160;
        int tankCapacity = tile.tank.getCapacity();
        this.buttonList.clear();
        int fluidIndex=0;
        for(IFluidTankProperties fluidData : fluids){
            int amount = fluidData.getContents().amount;
            float drawHeight = (((float)amount)/((float)tankCapacity) * (float)totalHeight);

            int startY = (int)(fluidBottom-drawHeight);
            RenderUtil.renderTiledFluid(fluidXStart,startY,fluidWidth,(int)Math.ceil(drawHeight),1.0f,fluidData.getContents());
            FluidButton button = new FluidButton(fluidIndex,fluidXStart,startY,fluidWidth,(int)drawHeight,fluidData.getContents());
            buttonList.add(button);
            fluidBottom-=drawHeight;
            fluidIndex++;
        }
        //still behind the slots when drawing here
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {

        LOG.info("button clicked with id "+button.id+".  This equates to fluid "+tile.tank.getTankProperties()[button.id].getContents().getFluid().getName());
        MFPacketHandler.INSTANCE.sendToServer(new SmeltingTankTileEntity.PacketFluidClick(tile.getPos(),button.id));

    }

    private class FluidButton extends GuiButton{

        private FluidStack fluidData;

        @Override
        public void drawButton(Minecraft mc, int mouseX, int mouseY) {
            if (this.visible) {

                this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
                int i = this.getHoverState(this.hovered);
                this.mouseDragged(mc, mouseX, mouseY);
            }
        }

        public FluidButton(int buttonId, int x, int y, int widthIn, int heightIn, FluidStack fluidData) {
            super(buttonId, x, y, widthIn, heightIn, "");
            this.fluidData=fluidData;
        }


        public FluidStack getFluidData() {
            return fluidData;
        }
    }
}
