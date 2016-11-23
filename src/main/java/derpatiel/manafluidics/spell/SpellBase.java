package derpatiel.manafluidics.spell;

import derpatiel.manafluidics.player.MFPlayerKnowledge;
import derpatiel.manafluidics.player.PlayerKnowledgeHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public abstract class SpellBase {

    private final int level;
    private final int castingCost;
    private final String name;
    private final SpellAttribute[] spellAttributes;

    public SpellBase(String name, int level, int castingCost, SpellAttribute... attributes){
        this.name=name;
        this.level=level;
        this.castingCost=castingCost;
        this.spellAttributes=attributes;
    }

    public boolean cast(World worldIn, EntityPlayer castingPlayer, boolean fromItem, List<SpellParameterChoices> parameters){
        for(SpellParameterOptions reqParam : getRequiredParameters()){
            boolean found=false;
            for(SpellParameterChoices choice : parameters){
                if(choice.options.equals(reqParam)){
                    found=true;
                }
            }
            if(!found)
                return false;
        }
        MFPlayerKnowledge playerData = PlayerKnowledgeHandler.getPlayerKnowledge(castingPlayer);
        if(!worldIn.isRemote && ((fromItem || playerData.canCast(this)) && doCast(worldIn,castingPlayer,PlayerKnowledgeHandler.getPlayerKnowledge(castingPlayer).isSpellBoosted(spellAttributes),parameters))){
            playerData.spellCast(this,fromItem);
            return true;
        }
        return false;
    }

    public abstract boolean doCast(World worldIn, EntityPlayer castingPlayer, boolean boosted,List<SpellParameterChoices> parameters);

    public List<SpellParameterOptions> getRequiredParameters(){
        return new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getLevel(){
        return level;
    }

    public int getCastingCost(){
        return castingCost;
    }
}
