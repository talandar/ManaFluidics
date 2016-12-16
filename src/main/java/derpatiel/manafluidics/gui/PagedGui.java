package derpatiel.manafluidics.gui;

import derpatiel.manafluidics.ManaFluidics;
import derpatiel.manafluidics.network.MFPacketHandler;
import derpatiel.manafluidics.network.PacketFluidAlloy;
import derpatiel.manafluidics.network.PacketFluidClick;
import derpatiel.manafluidics.registry.ModGUIs;
import derpatiel.manafluidics.registry.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class PagedGui extends GuiContainer {

    public static final int WIDTH = 253;
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
        pages.clear();
        buttonList.clear();
        addPages();
        //get buttons here
        for(PagedGuiPage page : pages){
            page.init(guiLeft,guiTop);
        }
    }

    protected abstract void addPages();

    public void addPage(PagedGuiPage page){
        pages.add(page);
        PageButton button = new PageButton(pages.size()-1);
        buttonList.add(button);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(background);

        //draw background
        drawTexturedModalRect(guiLeft+24, guiTop, 24, 0, xSize-24, ySize);
        //draw tab connector, so it looks like this one is selected
        int tab = 0;
        int tabOffset = 15;
        int itemTabOffsetX = 6;
        int itemTabOffsetY = 20;
        for(PagedGuiPage page : pages){
            drawTexturedModalRect(guiLeft,guiTop+tabOffset+(tab*27),0,tabOffset,24,27);
            GlStateManager.enableDepth();
            this.itemRender.renderItemAndEffectIntoGUI(this.mc.thePlayer,new ItemStack(ModItems.redcrystal_gem), guiLeft+itemTabOffsetX, guiTop+itemTabOffsetY+(27*tab));
            //this.itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, page.getIconItemStack(), i, j, null);
            mc.getTextureManager().bindTexture(background);
            tab++;
        }
        drawTexturedModalRect(guiLeft+24,guiTop+15 + (27*selectedTab),253,0,3,27);

        pages.get(selectedTab).draw(partialTicks,mouseX,mouseY);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if(button instanceof PageButton){
            //button id is page id
            selectedTab=button.id;
        }
    }

    public FontRenderer getFontRenderer(){
        return this.fontRendererObj;
    }

    private class PageButton extends GuiButton{

        public PageButton(int buttonId) {
            super(buttonId, guiLeft, 15+(27*buttonId), 24, 27, "");
        }

        @Override
        public void drawButton(@Nonnull Minecraft mc, int mouseX, int mouseY) {
            if (this.visible) {

                this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
                this.mouseDragged(mc, mouseX, mouseY);
            }
        }
    }
}