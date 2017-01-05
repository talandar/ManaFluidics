package derpatiel.manafluidics.item.cast;

import com.google.common.collect.Lists;
import derpatiel.manafluidics.block.altar.SpellPrepSelectionPage;
import derpatiel.manafluidics.gui.PagedGui;
import derpatiel.manafluidics.gui.PagedGuiPage;
import derpatiel.manafluidics.network.MFPacketHandler;
import derpatiel.manafluidics.network.PacketChangeParam;
import derpatiel.manafluidics.network.PacketChangeSelection;
import derpatiel.manafluidics.player.MFPlayerKnowledge;
import derpatiel.manafluidics.player.PlayerKnowledgeHandler;
import derpatiel.manafluidics.spell.SpellBase;
import derpatiel.manafluidics.spell.parameters.SpellParameter;
import derpatiel.manafluidics.spell.parameters.SpellParameterChoices;
import derpatiel.manafluidics.util.LOG;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.NoteBlockEvent;

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
        for(SpellBase spell : spells){
            GuiButton button = new SpellSelectGuiButton(buttonId,guiLeft+30,guiTop+10+(25*buttonId), 217,20,spell);
            parent.addButton(button);
            buttonId++;
            if(!spell.getRequiredParameters().isEmpty()){
                //has parameters, more buttons!
                for(SpellParameter reqParam : spell.getRequiredParameters()) {
                    GuiButton paramButton = new SpellParamSelectGuiButton(buttonId, guiLeft + 40, guiTop + 10 + (25 * buttonId), 207, 20, spell,reqParam);
                    parent.addButton(paramButton);
                    buttonId++;
                }
            }
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
        if(button instanceof SpellSelectGuiButton){
            LOG.info("click spell button");
            SpellSelectGuiButton btn = (SpellSelectGuiButton)button;
            PlayerKnowledgeHandler.getPlayerKnowledge(player).selectPreparedSpell(btn.spell);
            MFPacketHandler.INSTANCE.sendToServer(new PacketChangeSelection(btn.spell.getRegName(),player.getUniqueID()));
            //TODO
        }else if(button instanceof SpellParamSelectGuiButton){
            LOG.info("click param button");
            SpellParamSelectGuiButton btn = (SpellParamSelectGuiButton)button;
            SpellParameterChoices choice = PlayerKnowledgeHandler.getPlayerKnowledge(player).getSpellParameters(btn.spell.getRegName()).get(btn.param);
            SpellParameterChoices replacementChoice = new SpellParameterChoices(choice.options,(choice.selectedOption+1)%choice.options.options.length);
            PlayerKnowledgeHandler.getPlayerKnowledge(player).setSpellParameter(btn.spell.getRegName(),replacementChoice);
            btn.choice=replacementChoice.selectedOption;
            MFPacketHandler.INSTANCE.sendToServer(new PacketChangeParam(btn.spell.getRegName(),replacementChoice,player.getUniqueID()));
        }
    }

    @Override
    public List<String> getHoverLabel() {
        return Lists.newArrayList();
    }

    @Override
    public void updateScreen() {
        SpellBase preparedSpell = PlayerKnowledgeHandler.getPlayerKnowledge(player).getSelectedSpell();
        for(GuiButton btn : parent.getButtonList()){
            if(btn instanceof SpellSelectGuiButton){
                btn.enabled = preparedSpell==null || ((SpellSelectGuiButton) btn).spell!=preparedSpell;
            }else if(btn instanceof SpellParamSelectGuiButton){
                btn.displayString=((SpellParamSelectGuiButton) btn).calcDisplayString();
            }
        }

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

        public SpellParamSelectGuiButton(int buttonId, int x, int y, int width, int height, SpellBase spell, SpellParameter param) {
            super(buttonId, x, y, width, height,"");
            this.spell = spell;
            this.param = param;
            MFPlayerKnowledge playerKnowledge = PlayerKnowledgeHandler.getPlayerKnowledge(player);
            for(SpellParameterChoices paramChoice : playerKnowledge.getSpellParameters(spell.getRegName()).values()){
                if(paramChoice.options==param){
                    this.choice=paramChoice.selectedOption;
                }
            }
            this.displayString = calcDisplayString();
        }

        public String calcDisplayString(){
            return param.getLocalizedName()+": "+param.getLocalizedChoiceName(choice);
        }
    }
}
