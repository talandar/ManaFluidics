package derpatiel.manafluidics.spell;

import derpatiel.manafluidics.gui.PagedGui;
import derpatiel.manafluidics.gui.PagedGuiPage;
import derpatiel.manafluidics.player.PlayerKnowledgeHandler;
import derpatiel.manafluidics.util.LOG;
import derpatiel.manafluidics.util.TextHelper;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

public class SpellSelectionPage extends PagedGuiPage {

    private EntityPlayer player;
    private int spellLevel;

    public SpellSelectionPage(ItemStack iconStack, int level, EntityPlayer player, PagedGui parent){
        super(iconStack,parent);
        this.player=player;
        this.spellLevel=level;
    }

    public void init(int guiLeft, int guiTop) {
        super.init(guiLeft,guiTop);
        //re-add buttons, etc;

        List<SpellBase> spells = PlayerKnowledgeHandler.getPlayerKnowledge(player).getPreparedSpells(spellLevel);
        int buttonid=0;
        for(SpellBase spell : spells){
            GuiButton button = new SpellGuiButton(buttonid,guiLeft+30,guiTop+10+(25*buttonid), 217,20,spell);
            parent.addButton(button);
            buttonid++;
        }

    }

    @Override
    public void drawBG(float partialTicks, int mouseX, int mouseY){

    }

    @Override
    public void drawFG(int mouseX, int mouseY) {
        //List<SpellBase> spells = PlayerKnowledgeHandler.getPlayerKnowledge(player).getPreparedSpells(spellLevel);
        //parent.drawString(parent.getFontRenderer(),"TESTING LEVEL "+spellLevel,guiLeft+30,guiTop+10,0xFF555555);
        //remember, can draw from
        //guiLeft+30, guiTop+10 to something close to 248,250
        for(GuiButton button : parent.getButtonList()) {
            if (button instanceof SpellGuiButton) {
                SpellGuiButton btn = (SpellGuiButton) button;
                if (btn.isMouseOver()) {
                    parent.drawHoveringText(btn.spell.getDescriptionLines(), mouseX, mouseY);
                }
            }
        }
    }

    @Override
    public void actionPerformed(GuiButton button) {
        if(button instanceof  SpellGuiButton){
            LOG.info("spell selected: "+((SpellGuiButton)button).spell.getName());
        }

    }

    private class SpellGuiButton extends GuiButton{

        private SpellBase spell;

        public SpellGuiButton(int buttonId, int x, int y, int width, int height, SpellBase spell){
            super(buttonId,x,y,width,height, spell.getName());
            this.spell=spell;
        }
    }
}
