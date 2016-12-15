package derpatiel.manafluidics.spell;

import derpatiel.manafluidics.spell.parameters.SpellParameterChoices;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import java.util.List;

public abstract class EntityCreatingSpell extends SpellBase {

    public EntityCreatingSpell(String name, int level, int castingCost, SpellAttribute... attributes) {
        super(name, level, castingCost, attributes);
    }

    @Override
    public boolean doCast(World worldIn, EntityPlayer castingPlayer, boolean boosted, List<SpellParameterChoices> parameters) {
        Entity createdEntity = getCreatedEntity(worldIn,castingPlayer,boosted,parameters);
        if(createdEntity!=null){
            worldIn.spawnEntityInWorld(createdEntity);
            return true;
        }
        return false;
    }

    protected abstract Entity getCreatedEntity(World world, EntityPlayer caster, boolean boosted,List<SpellParameterChoices> parameters);
}
