package derpatiel.manafluidics.spell;

import com.google.common.collect.Lists;
import derpatiel.manafluidics.gui.PagedGui;
import derpatiel.manafluidics.gui.PagedGuiPage;
import derpatiel.manafluidics.player.PlayerKnowledgeHandler;
import derpatiel.manafluidics.util.LOG;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

public class SpellPrepSelectionPage extends PagedGuiPage {

    private EntityPlayer player;
    private int spellLevel;

    public SpellPrepSelectionPage(ItemStack iconStack, int level, EntityPlayer player, PagedGui parent){
        super(iconStack,parent);
        this.player=player;
        this.spellLevel=level;
    }

    public void init(int guiLeft, int guiTop) {
        super.init(guiLeft,guiTop);
        //re-add buttons, etc;

        List<SpellBase> spells = PlayerKnowledgeHandler.getPlayerKnowledge(player).getAvailableSpells(spellLevel);
        int buttonId=0;
        for(SpellBase spell : spells){
            GuiButton button = new SpellGuiButton(buttonId,guiLeft+30,guiTop+10+(25*buttonId), 217,20,spell);
            parent.addButton(button);
            buttonId++;
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

    @Override
    public List<String> getHoverLabel() {
        return Lists.newArrayList(spellLevel==0 ? "Cantrips" : "Level "+spellLevel+" Spells");
    }

    @Override
    public void updateScreen() {
        List<SpellBase> prepared = PlayerKnowledgeHandler.getPlayerKnowledge(player).getPreparedSpells(spellLevel);
        for(GuiButton button : parent.getButtonList()){
            if(button instanceof SpellGuiButton){
                SpellGuiButton btn = (SpellGuiButton)button;
                if(prepared.contains(btn.spell)){
                    btn.enabled=false;
                }else{
                    btn.enabled=true;
                }
            }
        }
    }

    private class SpellGuiButton extends GuiButton{

        private SpellBase spell;

        public SpellGuiButton(int buttonId, int x, int y, int width, int height, SpellBase spell){
            super(buttonId,x,y,width,height, spell.getName());
            this.spell=spell;
        }

        @Override
        public boolean mousePressed(Minecraft mc, int mouseX, int mouseY)
        {
            return this.visible && mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
        }
    }
}
