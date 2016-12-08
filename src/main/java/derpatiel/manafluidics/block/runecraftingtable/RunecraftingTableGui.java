package derpatiel.manafluidics.block.runecraftingtable;

import derpatiel.manafluidics.ManaFluidics;
import derpatiel.manafluidics.block.castingchamber.CastingChamberTileEntity;
import derpatiel.manafluidics.registry.ModGUIs;
import derpatiel.manafluidics.util.MaterialItemHelper;
import derpatiel.manafluidics.util.RenderUtil;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public class RunecraftingTableGui extends GuiContainer {
    public static final int WIDTH = 176;
    public static final int HEIGHT = 187;

    private static final ResourceLocation background = new ResourceLocation(ManaFluidics.MODID, ModGUIs.RUNECRAFTING_TABLE_LOC);

    private RunecraftingTileEntity tile;

    public RunecraftingTableGui(RunecraftingTileEntity tileEntity, RunecraftingTableContainer container) {
        super(container);
        tile = tileEntity;
        xSize = WIDTH;
        ySize = HEIGHT;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }
}
