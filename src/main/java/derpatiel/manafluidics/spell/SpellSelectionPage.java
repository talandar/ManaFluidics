package derpatiel.manafluidics.spell;

import derpatiel.manafluidics.gui.PagedGui;
import derpatiel.manafluidics.gui.PagedGuiPage;
import derpatiel.manafluidics.player.PlayerKnowledgeHandler;
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

    @Override
    public void draw(float partialTicks, int mouseX, int mouseY) {
        List<SpellBase> spells = PlayerKnowledgeHandler.getPlayerKnowledge(player).getPreparedSpells(spellLevel);
        parent.drawString(parent.getFontRenderer(),"TESTING LEVEL "+spellLevel,guiLeft+30,guiTop+10,0xFF555555);
        //remember, can draw from
        //guiLeft+30, guiTop+10 to something close to 248,250

    }
}
