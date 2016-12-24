package derpatiel.manafluidics.block.altar;

import com.google.common.collect.Lists;
import derpatiel.manafluidics.gui.PagedGui;
import derpatiel.manafluidics.gui.PagedGuiPage;
import derpatiel.manafluidics.network.MFPacketHandler;
import derpatiel.manafluidics.network.PacketChangePrep;
import derpatiel.manafluidics.player.MFPlayerKnowledge;
import derpatiel.manafluidics.player.PlayerKnowledgeHandler;
import derpatiel.manafluidics.spell.SpellBase;
import derpatiel.manafluidics.util.LOG;
import derpatiel.manafluidics.util.TextHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;
import java.util.Set;

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
            MFPacketHandler.INSTANCE.sendToServer(new PacketChangePrep(((SpellGuiButton) button).spell.getRegName(),player.getUniqueID()));
        }

    }

    @Override
    public List<String> getHoverLabel() {
        List<String> hoverLabels = Lists.newArrayList();
        if(spellLevel==0){
            hoverLabels.add(TextHelper.localize("spell.level0.name"));
        }else{
            hoverLabels.add(TextHelper.localizeEffect("spell.leveldescription",spellLevel));
        }
        MFPlayerKnowledge knowledge = PlayerKnowledgeHandler.getPlayerKnowledge(player);
        int prepared = knowledge.getPreparedSpells(spellLevel).size();
        int canPrepare = knowledge.getMaxPreparedSpells(spellLevel);
        String prepend = "&7";
        if(prepared>=canPrepare){
            prepend="&4";
        }
        hoverLabels.add(TextHelper.getFormattedText(prepend)+TextHelper.localize("altar.preparedSpells.message",prepared,canPrepare));
        return hoverLabels;
    }

    @Override
    public void updateScreen() {
        Set<SpellBase> prepared = PlayerKnowledgeHandler.getPlayerKnowledge(player).getPreparedSpells(spellLevel);
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
