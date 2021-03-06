package derpatiel.manafluidics.block.furnaceHeater;

import com.google.common.collect.Lists;
import derpatiel.manafluidics.ManaFluidics;
import derpatiel.manafluidics.capability.heat.CapabilityHeat;
import derpatiel.manafluidics.registry.ModGUIs;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

public class FurnaceHeaterGui  extends GuiContainer {
    public static final int WIDTH = 176;
    public static final int HEIGHT = 166;

    private static final ResourceLocation background = new ResourceLocation(ManaFluidics.MODID, ModGUIs.FURNACE_HEATER_LOC);

    private FurnaceHeaterTileEntity tile;

    public FurnaceHeaterGui(FurnaceHeaterTileEntity tileEntity, FurnaceHeaterContainer container) {
        super(container);
        tile = tileEntity;
        xSize = WIDTH;
        ySize = HEIGHT;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        float burnFrac = tile.getBurnFraction();
        int fracHeight = (int)(14.0f * burnFrac);
        drawTexturedModalRect(guiLeft + 81, guiTop + 21 + (14-fracHeight), xSize, 14-fracHeight, 14, 14);
        if(tile.isGenerating() && mouseX>=(guiLeft+79) && mouseX<=(guiLeft+79+16) && mouseY>=(guiTop+19) && mouseY<=(guiTop+19+16)){
            int heatProvided = tile.getCapability(CapabilityHeat.HEAT,null).getHeatProvided();
            drawHoveringText(Lists.newArrayList("Generating "+heatProvided+" Heat/Tick"),mouseX,mouseY);

        }
    }
}
