package derpatiel.manafluidics.block.castingchamber;

import com.google.common.collect.Lists;
import derpatiel.manafluidics.ManaFluidics;
import derpatiel.manafluidics.block.furnaceHeater.FurnaceHeaterContainer;
import derpatiel.manafluidics.block.furnaceHeater.FurnaceHeaterTileEntity;
import derpatiel.manafluidics.block.multiTank.smeltingTank.SmeltingTankGui;
import derpatiel.manafluidics.capability.heat.CapabilityHeat;
import derpatiel.manafluidics.registry.ModGUIs;
import derpatiel.manafluidics.util.MaterialItemHelper;
import derpatiel.manafluidics.util.RenderUtil;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public class CastingChamberGui  extends GuiContainer {
    public static final int WIDTH = 176;
    public static final int HEIGHT = 166;

    private static final ResourceLocation background = new ResourceLocation(ManaFluidics.MODID, ModGUIs.CASTING_CHAMBER_LOC);

    private CastingChamberTileEntity tile;

    public CastingChamberGui(CastingChamberTileEntity tileEntity, CastingChamberContainer container) {
        super(container);
        tile = tileEntity;
        xSize = WIDTH;
        ySize = HEIGHT;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        //draw burndown
        float coolFrac = (1.0f - tile.coolingFraction());
        int fracHeight = (int)(14.0f * coolFrac);
        drawTexturedModalRect(guiLeft + 63, guiTop + 37 + (14-fracHeight), xSize, 14-fracHeight, 14, 14);

        //draw fluid
        FluidStack contents = tile.tank.getFluid();
        if(contents!=null && contents.amount>0) {
            float drawHeight = (((float) contents.amount) / ((float) tile.tank.getCapacity()) * 70.0f);
            int startY = (int) (78 - drawHeight);
            RenderUtil.renderTiledFluid(guiLeft + 131, guiTop + startY, 16, (int) Math.ceil(drawHeight), 1.0f, contents);
            if (mouseX >= (guiLeft + 131) && mouseX <= (guiLeft + 131 + 16) && mouseY >= (guiTop + 8) && mouseY <= (guiTop + 78)) {
                List<String> descriptions = new ArrayList<>();
                descriptions.add("Fluid: " + contents.getFluid().getLocalizedName(contents));
                descriptions.add(contents.amount+"mb.  ("+ MaterialItemHelper.getIngotsString(contents) +")");
                drawHoveringText(descriptions, mouseX, mouseY);

            }
        }
    }
}
