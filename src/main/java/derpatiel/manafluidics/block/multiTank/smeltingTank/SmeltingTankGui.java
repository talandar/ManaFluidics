package derpatiel.manafluidics.block.multiTank.smeltingTank;

import derpatiel.manafluidics.ManaFluidics;
import derpatiel.manafluidics.registry.ModGUIs;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

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
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        //float burnFrac = tile.getBurnFraction();
        //int fracHeight = (int)(14.0f * burnFrac);
        //drawTexturedModalRect(guiLeft + 81, guiTop + 21 + (14-fracHeight), xSize, 14-fracHeight, 14, 14);
    }
}
