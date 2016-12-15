package derpatiel.manafluidics.gui;

public abstract class PagedGuiPage {

    private int guiLeft;
    private int guiTop;

    public PagedGuiPage(){
    }

    public void init(int guiLeft, int guiTop) {
        this.guiLeft=guiLeft;
        this.guiTop=guiTop;
        //re-add buttons, etc;
    }

    public abstract void draw(float partialTicks, int mouseX, int mouseY);
}
