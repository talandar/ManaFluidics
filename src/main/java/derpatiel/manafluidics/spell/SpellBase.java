package derpatiel.manafluidics.spell;

import derpatiel.manafluidics.player.MFPlayerKnowledge;
import derpatiel.manafluidics.player.PlayerKnowledgeHandler;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public abstract class SpellBase {

    private final int level;
    private final int castingCost;
    private final String name;

    public SpellBase(String name, int level, int castingCost){
        this.name=name;
        this.level=level;
        this.castingCost=castingCost;
    }

    public boolean cast(World worldIn, EntityPlayer castingPlayer){
        if(doCast(worldIn,castingPlayer)){
            PlayerKnowledgeHandler.getPlayerKnowledge(castingPlayer).spellCast(this);
            return true;
        }
        return false;
    }

    public abstract boolean doCast(World worldIn, EntityPlayer castingPlayer);


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
