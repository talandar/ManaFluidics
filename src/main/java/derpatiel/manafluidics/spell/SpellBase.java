package derpatiel.manafluidics.spell;

import com.google.common.collect.Lists;
import derpatiel.manafluidics.item.spell.SingleSpellWand;
import derpatiel.manafluidics.player.MFPlayerKnowledge;
import derpatiel.manafluidics.player.PlayerKnowledgeHandler;
import derpatiel.manafluidics.spell.parameters.SpellParameter;
import derpatiel.manafluidics.spell.parameters.SpellParameterChoices;
import derpatiel.manafluidics.util.ChatUtil;
import derpatiel.manafluidics.util.TextHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public abstract class SpellBase {

    private final int level;
    private final int castingCost;
    private final String regName;
    private final SpellAttribute[] spellAttributes;
    public boolean needsClientActivation;

    public SpellBase(String name, int level, int castingCost, SpellAttribute... attributes){
        this.regName =name;
        this.level=level;
        this.castingCost=castingCost;
        this.spellAttributes=attributes;
        this.needsClientActivation=false;
    }

    public boolean cast(World worldIn, EntityPlayer castingPlayer, ItemStack castItem){
        MFPlayerKnowledge playerData = PlayerKnowledgeHandler.getPlayerKnowledge(castingPlayer);
        boolean fromItem;
        List<SpellParameterChoices> parameters;
        if(castItem.getItem() instanceof SingleSpellWand){
            //use item parameters
            //TODO: store parameters on wand when creating wand. this temp hack will work for now...
            parameters = Lists.newArrayList(new SpellParameterChoices(SpellParameter.ENTITY_TARGETING,1));
            fromItem=true;
        }else{
            //use player parameters
            parameters = playerData.getSpellParameters(this.regName);
            fromItem=false;
        }
        for(SpellParameter reqParam : getRequiredParameters()){
            boolean found=false;
            for(SpellParameterChoices choice : parameters){
                if(choice.options.equals(reqParam)){
                    found=true;
                }
            }
            if(!found) {
                ChatUtil.sendChat(castingPlayer,TextHelper.localize("spell.missingparam.message"));
                return false;
            }
        }
        if((!worldIn.isRemote || needsClientActivation)
                && (fromItem || playerData.canCast(this))
                && playerHasRequiredMaterials(castingPlayer)){
            boolean casted = doCast(worldIn,castingPlayer,PlayerKnowledgeHandler.getPlayerKnowledge(castingPlayer).isSpellBoosted(spellAttributes),parameters);
            if(casted) {
                playerData.spellCast(this, fromItem);
                if(!castingPlayer.isCreative()) {
                    consumeRequiredMaterials(castingPlayer);
                }
            }
            return true;
        }
        return false;
    }

    public ItemStack getConsumedComponent(){
        return null;
    }

    private boolean playerHasRequiredMaterials(EntityPlayer castingPlayer){
        ItemStack component = getConsumedComponent();
        if(component==null || castingPlayer.isCreative())
            return true;
        for(int slot=0;slot<castingPlayer.inventory.getSizeInventory();slot++) {
            ItemStack stack = castingPlayer.inventory.getStackInSlot(slot);
            if(stack!=null && stack.isItemEqual(component))
                return true;
        }
        return false;
    }

    private void consumeRequiredMaterials(EntityPlayer castingPlayer) {
        ItemStack component = getConsumedComponent();
        if (component == null)
            return;
        for (int slot = 0; slot < castingPlayer.inventory.getSizeInventory(); slot++) {
            ItemStack stack = castingPlayer.inventory.getStackInSlot(slot);
            if (stack != null && stack.isItemEqual(component)) {
                castingPlayer.inventory.decrStackSize(slot, 1);
                return;
            }
        }
    }

    public abstract boolean doCast(World worldIn, EntityPlayer castingPlayer, boolean boosted,List<SpellParameterChoices> parameters);

    public List<SpellParameter> getRequiredParameters(){
        return new ArrayList<>();
    }

    public String getName() {
        return TextHelper.localize("spell."+regName+".name");
    }

    public String getRegName(){
        return this.regName;
    }

    public int getLevel(){
        return level;
    }

    public int getCastingCost(){
        return castingCost;
    }

    public List<String> getDescriptionLines(){
        List<String> descriptions = new ArrayList<>();
        descriptions.add(getName());
        descriptions.add(TextHelper.localizeEffect("spell.details.message",this.getLevel(),this.getCastingCost()));
        String attributes = "";
        String sep = "";
        for(SpellAttribute attribute : this.spellAttributes){
            attributes = attributes + sep + attribute.friendlyName();
            sep = ", ";
        }
        descriptions.add(attributes);
        ItemStack stack = getConsumedComponent();
        if(stack!=null){
            descriptions.add(TextHelper.localizeEffect("spell.component.message",stack.getDisplayName()));
        }
        descriptions.add(TextHelper.localize("spell."+regName+".description"));

        return descriptions;
    }
}
