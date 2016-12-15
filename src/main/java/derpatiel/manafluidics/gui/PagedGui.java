package derpatiel.manafluidics.gui;

import derpatiel.manafluidics.ManaFluidics;
import derpatiel.manafluidics.registry.ModGUIs;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public abstract class PagedGui extends GuiContainer {

    public static final int WIDTH = 216;
    public static final int HEIGHT = 256;

    private static final ResourceLocation background = new ResourceLocation(ManaFluidics.MODID, ModGUIs.PAGED_GUI_BG_LOC);


    private int selectedTab;
    private final List<PagedGuiPage> pages = new ArrayList<>();

    public PagedGui(Container inventorySlotsIn) {
        super(inventorySlotsIn);
        xSize = WIDTH;
        ySize = HEIGHT;
        selectedTab=0;
    }

    public void initGui()
    {
        super.initGui();
        addPages();
        //get buttons here
        for(PagedGuiPage page : pages){
            page.init(guiLeft,guiTop);
        }
    }

    protected abstract void addPages();

    public void addPage(PagedGuiPage page){
        pages.add(page);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(background);

        //draw background
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        //draw tab connector, so it looks like this one is selected
        drawTexturedModalRect(guiLeft+24,guiTop+15 + (27*selectedTab),216,0,3,27);

        pages.get(selectedTab).draw(partialTicks,mouseX,mouseY);
    }
}
