package derpatiel.manafluidics.block.multiTank.smeltingTank;

import derpatiel.manafluidics.ManaFluidics;
import derpatiel.manafluidics.registry.ModGUIs;
import derpatiel.manafluidics.util.RenderUtil;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class SmeltingTankGui  extends GuiContainer {

    //TODO: handle clicks to change bottom fluid
    //TODO: mouseover to describe fluid
    //TODO: display melt progress


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
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        IFluidTankProperties[] fluids = tile.tank.getTankProperties();
        int fluidBottom = 167 - guiTop;
        int fluidXStart = guiLeft+174;
        int fluidWidth = 70;

        int totalHeight = 160;
        int tankCapacity = tile.tank.getCapacity();
        for(IFluidTankProperties fluidData : fluids){
            int amount = fluidData.getContents().amount;
            int drawHeight = (int)(((float)amount)/((float)tankCapacity) * totalHeight);
            float startY = fluidBottom-drawHeight;
            RenderUtil.renderTiledFluid(fluidXStart,(int)startY,fluidWidth,drawHeight,1.0f,fluidData.getContents());
            fluidBottom-=drawHeight;
        }
    }
}
