package derpatiel.manafluidics.block.altar;

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
import java.util.Random;

public class KAltarGui extends GuiContainer {
    public static final int WIDTH = 216;
    public static final int HEIGHT = 256;

    private static final ResourceLocation background = new ResourceLocation(ManaFluidics.MODID, ModGUIs.KNOWLEDGE_ALTAR_LOC);

    private KnowledgeAltarTileEntity tile;
    private int selectedTab;
    private Random rand;

    public KAltarGui(KnowledgeAltarTileEntity tileEntity, KAltarContainer container) {
        super(container);
        tile = tileEntity;
        xSize = WIDTH;
        ySize = HEIGHT;
        selectedTab=0;
        rand = new Random();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(background);

        //draw background
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        //draw tab connector, so it looks like this one is selected
        drawTexturedModalRect(guiLeft+24,guiTop+15 + (27*selectedTab),216,0,3,27);

        switch(selectedTab) {
            case 0:
                drawAltarTypeSelection(mouseX, mouseY);
                break;
            case 1:
                drawSpellSelection(mouseX, mouseY);
                break;
        }

        if(rand.nextInt(50)==0)
            selectedTab=1-selectedTab;
    }

    private void drawAltarTypeSelection(int mouseX, int mouseY){

    }

    private void drawSpellSelection(int mouseX, int mouseY){

    }
}
