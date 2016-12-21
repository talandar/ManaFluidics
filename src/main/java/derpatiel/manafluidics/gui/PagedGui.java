package derpatiel.manafluidics.gui;

import derpatiel.manafluidics.ManaFluidics;
import derpatiel.manafluidics.registry.ModGUIs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
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
        pages.get(selectedTab).init(guiLeft,guiTop);
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
            this.itemRender.renderItemAndEffectIntoGUI(this.mc.thePlayer,page.getIconStack(), guiLeft+itemTabOffsetX, guiTop+itemTabOffsetY+(27*tab));
            mc.getTextureManager().bindTexture(background);
            tab++;
        }
        drawTexturedModalRect(guiLeft+24,guiTop+15 + (27*selectedTab),253,0,3,27);

        pages.get(selectedTab).drawBG(partialTicks,mouseX,mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        pages.get(selectedTab).drawFG(mouseX-guiLeft,mouseY-guiTop);
        for(GuiButton button : buttonList) {
            if (button instanceof PageButton) {
                PageButton btn = (PageButton)button;
                if (btn.isMouseOver()) {
                    drawHoveringText(pages.get(btn.id).getHoverLabel(),mouseX-guiLeft,mouseY-guiTop);
                }
            }
        }
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        pages.get(selectedTab).updateScreen();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if(button instanceof PageButton){
            //button id is page id
            changeTab(button.id);
        }else{
            pages.get(selectedTab).actionPerformed(button);
        }
    }

    private void changeTab(int newTabId){
        selectedTab=newTabId;
        initGui();
    }

    public FontRenderer getFontRenderer(){
        return this.fontRendererObj;
    }

    public void addButton(GuiButton button){
        buttonList.add(button);
    }

    public List<GuiButton> getButtonList() {
        return buttonList;
    }

    private class PageButton extends GuiButton{

        public PageButton(int buttonId) {
            super(buttonId, guiLeft, guiTop+15+(27*buttonId), 24, 27, "");
        }

        @Override
        public void drawButton(@Nonnull Minecraft mc, int mouseX, int mouseY) {
            if (this.visible) {

                this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
                this.mouseDragged(mc, mouseX, mouseY);
            }
        }
    }

    public void drawHoveringText(List<String> textLines, int x, int y){
        this.drawHoveringText(textLines,x,y,fontRendererObj);
    }
}
