package derpatiel.manafluidics.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.ItemStack;

import java.util.List;

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

    public ItemStack getIconStack(){
        return iconStack;
    }

    public abstract void drawFG(int mouseX, int mouseY);
    public abstract void drawBG(float partialTicks, int mouseX, int mouseY);

    public abstract void actionPerformed(GuiButton button);

    public abstract List<String> getHoverLabel();

    public abstract void updateScreen();
}
