package derpatiel.manafluidics.spell;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public abstract class EntityCreatingSpell extends SpellBase {

    public EntityCreatingSpell(String name, int level, int castingCost, SpellAttribute... attributes) {
        super(name, level, castingCost, attributes);
    }

    @Override
    public boolean doCast(World worldIn, EntityPlayer castingPlayer, boolean boosted) {
        Entity createdEntity = getCreatedEntity(worldIn,castingPlayer,boosted);
        if(createdEntity!=null){
            worldIn.spawnEntityInWorld(createdEntity);
            return true;
        }
        return false;
    }

    protected abstract Entity getCreatedEntity(World world, EntityPlayer caster, boolean boosted);
}
