package derpatiel.manafluidics.item.cast;

import com.google.common.collect.Lists;
import derpatiel.manafluidics.block.altar.SpellPrepSelectionPage;
import derpatiel.manafluidics.gui.PagedGui;
import derpatiel.manafluidics.gui.PagedGuiPage;
import derpatiel.manafluidics.player.PlayerKnowledgeHandler;
import derpatiel.manafluidics.spell.SpellBase;
import derpatiel.manafluidics.spell.parameters.SpellParameter;
import derpatiel.manafluidics.spell.parameters.SpellParameterChoices;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;
import java.util.Set;

/**
 * Created by Jim on 12/28/2016.
 */
public class SpellSelectionPage extends PagedGuiPage {

    private EntityPlayer player;
    private int spellLevel;

    public SpellSelectionPage(ItemStack iconStack, int level, EntityPlayer player, PagedGui parent){
        super(iconStack,parent);
        this.player=player;
        this.spellLevel=level;
    }

    @Override
    public void init(int guiLeft, int guiTop) {
        super.init(guiLeft,guiTop);
        //re-add buttons, etc;

        Set<SpellBase> spells = PlayerKnowledgeHandler.getPlayerKnowledge(player).getPreparedSpells(spellLevel);
        int buttonId=0;
        int spellNum=0;
        for(SpellBase spell : spells){
            GuiButton button = new SpellSelectGuiButton(buttonId,guiLeft+30,guiTop+10+(25*buttonId), 217,20,spell);
            parent.addButton(button);
            buttonId++;
        }

    }
    @Override
    public void drawFG(int mouseX, int mouseY) {

    }

    @Override
    public void drawBG(float partialTicks, int mouseX, int mouseY) {

    }

    @Override
    public void actionPerformed(GuiButton button) {

    }

    @Override
    public List<String> getHoverLabel() {
        return Lists.newArrayList();
    }

    @Override
    public void updateScreen() {

    }

    private class SpellSelectGuiButton extends GuiButton {

        private SpellBase spell;

        public SpellSelectGuiButton(int buttonId, int x, int y, int width, int height, SpellBase spell) {
            super(buttonId, x, y, width, height, spell.getName());
            this.spell = spell;
        }
    }
    private class SpellParamSelectGuiButton extends GuiButton {

        private SpellBase spell;
        private SpellParameter param;
        private int choice;

        public SpellParamSelectGuiButton(int buttonId, int x, int y, int width, int height, SpellBase spell, SpellParameter param, int choice) {
            super(buttonId, x, y, width, height, param.getLocalizedChoiceName(choice));
            this.spell = spell;
            this.param = param;
            this.choice = choice;
        }
    }
}
