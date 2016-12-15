package derpatiel.manafluidics.block.altar;

import derpatiel.manafluidics.ManaFluidics;
import derpatiel.manafluidics.block.castingchamber.CastingChamberTileEntity;
import derpatiel.manafluidics.gui.PagedGui;
import derpatiel.manafluidics.gui.PagedGuiPage;
import derpatiel.manafluidics.registry.ModGUIs;
import derpatiel.manafluidics.util.MaterialItemHelper;
import derpatiel.manafluidics.util.RenderUtil;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KAltarGui extends PagedGui {

    private KnowledgeAltarTileEntity tile;
    private Random rand;

    public KAltarGui(KnowledgeAltarTileEntity tileEntity, KAltarContainer container) {
        super(container);
        tile = tileEntity;
        rand = new Random();
    }

    private void drawAltarTypeSelection(int mouseX, int mouseY){

    }

    private void drawSpellSelection(int mouseX, int mouseY){

    }

    @Override
    protected void addPages() {
        addPage(new PagedGuiPage() {
            @Override
            public void draw(float partialTicks, int mouseX, int mouseY) {

            }
        });
    }
}
