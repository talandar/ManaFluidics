package derpatiel.manafluidics.block.multiTank.alloyTank;

import derpatiel.manafluidics.ManaFluidics;
import derpatiel.manafluidics.network.MFPacketHandler;
import derpatiel.manafluidics.network.PacketFluidClick;
import derpatiel.manafluidics.registry.ModGUIs;
import derpatiel.manafluidics.util.LOG;
import derpatiel.manafluidics.util.MaterialItemHelper;
import derpatiel.manafluidics.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class AlloyTankGui extends GuiContainer {


    public static final int WIDTH = 176;
    public static final int HEIGHT = 166;

    private static final ResourceLocation background = new ResourceLocation(ManaFluidics.MODID, ModGUIs.ALLOY_TANK_LOC);

    private AlloyTankTileEntity tile;
    private EntityPlayer accessingPlayer;

    private  GuiButton formAlloyButton;

    public AlloyTankGui(EntityPlayer accessingPlayer, AlloyTankTileEntity tileEntity, AlloyTankContainer container) {
        super(container);
        this.accessingPlayer=accessingPlayer;
        tile = tileEntity;
        xSize = WIDTH;
        ySize = HEIGHT;
    }

    @Override
    public void drawScreen(int x, int y, float partialTicks) {
        super.drawScreen(x, y, partialTicks);
        //drawn above the slots at this point
        for(GuiButton button : buttonList) {
            if (button instanceof FluidButton) {
                FluidButton btn = (FluidButton) button;
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
    public void initGui() {
        super.initGui();
        formAlloyButton = new GuiButton(-1,guiLeft+8,guiTop+59,120,20,"Form Alloys");
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        IFluidTankProperties[] fluids = tile.tank.getTankProperties();
        int fluidBottom = 78 + guiTop;
        int fluidXStart = guiLeft+131;
        int fluidWidth = 37;

        int totalHeight = 69;
        int tankCapacity = tile.tank.getCapacity();
        this.buttonList.clear();
        int fluidIndex=0;
        for(IFluidTankProperties fluidData : fluids){
            if(fluidData.getContents()!=null) {
                int amount = fluidData.getContents().amount;
                float drawHeight = (((float) amount) / ((float) tankCapacity) * (float) totalHeight);

                int startY = (int) (fluidBottom - drawHeight);
                RenderUtil.renderTiledFluid(fluidXStart, startY, fluidWidth, (int) Math.ceil(drawHeight), 1.0f, fluidData.getContents());
                FluidButton button = new FluidButton(fluidIndex, fluidXStart, startY, fluidWidth, (int) drawHeight, fluidData.getContents());
                buttonList.add(button);
                fluidBottom -= drawHeight;
                fluidIndex++;
            }
        }
        buttonList.add(formAlloyButton);
        //still behind the slots when drawing here

        //TODO: display which alloys are available to the player
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if(button.id>=0) {
            MFPacketHandler.INSTANCE.sendToServer(new PacketFluidClick(tile.getPos(), button.id));
        }else{
            LOG.info("clicked alloy button!");
        }
    }

    private class FluidButton extends GuiButton{

        private FluidStack fluidData;

        @Override
        public void drawButton(@Nonnull Minecraft mc, int mouseX, int mouseY) {
            if (this.visible) {

                this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
                this.mouseDragged(mc, mouseX, mouseY);
            }
        }

        FluidButton(int buttonId, int x, int y, int widthIn, int heightIn, FluidStack fluidData) {
            super(buttonId, x, y, widthIn, heightIn, "");
            this.fluidData=fluidData;
        }


        FluidStack getFluidData() {
            return fluidData;
        }
    }
}
