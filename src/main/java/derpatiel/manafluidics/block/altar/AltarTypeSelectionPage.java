package derpatiel.manafluidics.block.altar;

import com.google.common.collect.Lists;
import derpatiel.manafluidics.enums.AltarType;
import derpatiel.manafluidics.gui.PagedGui;
import derpatiel.manafluidics.gui.PagedGuiPage;
import derpatiel.manafluidics.network.MFPacketHandler;
import derpatiel.manafluidics.network.PacketAltarTypeChange;
import derpatiel.manafluidics.player.PlayerKnowledgeHandler;
import derpatiel.manafluidics.registry.ModBlocks;
import derpatiel.manafluidics.spell.SpellAttribute;
import derpatiel.manafluidics.spell.SpellBase;
import derpatiel.manafluidics.util.TextHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import scala.collection.parallel.ParIterableLike;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Jim on 12/23/2016.
 */
public class AltarTypeSelectionPage extends PagedGuiPage {

    public AltarTypeSelectionPage(PagedGui parent) {
        super(new ItemStack(ModBlocks.knowledgeAltar), parent);
    }

    public void init(int guiLeft, int guiTop) {
        super.init(guiLeft,guiTop);
        //re-add buttons, etc;

        for(AltarType type : AltarType.VALUES){
            GuiButton button = new AltarTypeSelectionPage.AltarTypeGuiButton(type.ordinal(),guiLeft+30,guiTop+10+(25*type.ordinal()), 217,20,type);
            parent.addButton(button);
        }

    }


    @Override
    public void drawFG(int mouseX, int mouseY) {
        for(GuiButton button : parent.getButtonList()) {
            if (button instanceof AltarTypeGuiButton) {
                AltarTypeGuiButton btn = (AltarTypeGuiButton) button;
                if (btn.isMouseOver()) {
                    List<String> lines = new ArrayList<>();
                    lines.add(TextHelper.localize(btn.type.localizationTag("name")));
                    lines.add(TextHelper.localize(btn.type.localizationTag("description")));
                    String boostLine = TextHelper.localize("altar.boosts.message");
                    String sep = " ";
                    for(SpellAttribute attribute : btn.type.boostedAttributes()){
                        boostLine=boostLine+sep+attribute.friendlyName();
                        sep=", ";
                    }
                    lines.add(boostLine);

                    parent.drawHoveringText(lines, mouseX, mouseY);
                }
            }
        }
    }

    @Override
    public void drawBG(float partialTicks, int mouseX, int mouseY) {

    }

    @Override
    public void actionPerformed(GuiButton button) {
        if(button instanceof AltarTypeGuiButton){
            AltarTypeGuiButton btn = ((AltarTypeGuiButton)button);
            MFPacketHandler.INSTANCE.sendToServer(new PacketAltarTypeChange(btn.type,((KAltarGui)parent).tile.getPos()));
        }
    }

    @Override
    public List<String> getHoverLabel() {
        List<String> lines = new ArrayList<>();
        lines.add(TextHelper.localize("altar.typeSelection.message"));
        return lines;
    }

    @Override
    public void updateScreen() {
        AltarType type = ((KAltarGui)parent).tile.type;
        for(GuiButton button : parent.getButtonList()){
            if(button instanceof AltarTypeGuiButton){
                AltarTypeGuiButton btn = (AltarTypeGuiButton)button;
                btn.enabled=!btn.type.equals(type);
            }
        }
    }

    private class AltarTypeGuiButton extends GuiButton{

        private AltarType type;

        public AltarTypeGuiButton(int buttonId, int x, int y, int width, int height, AltarType type){
            super(buttonId,x,y,width,height, TextHelper.localize(type.localizationTag("name")));
            this.type=type;
        }
    }
}
