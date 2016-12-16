package derpatiel.manafluidics.gui;

import net.minecraft.item.ItemStack;

public abstract class PagedGuiPage {

    protected int guiLeft;
    protected int guiTop;

    protected PagedGui parent;

    private final ItemStack iconStack;

    public PagedGuiPage(ItemStack iconStack, PagedGui parent){
        this.iconStack=iconStack;
        this.parent=parent;
    }

    public void init(int guiLeft, int guiTop) {
        this.guiLeft=guiLeft;
        this.guiTop=guiTop;
        //re-add buttons, etc;
    }

    public abstract void draw(float partialTicks, int mouseX, int mouseY);
}
