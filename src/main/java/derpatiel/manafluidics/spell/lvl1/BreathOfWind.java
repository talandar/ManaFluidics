package derpatiel.manafluidics.spell.lvl1;

import derpatiel.manafluidics.spell.SpellAttribute;
import derpatiel.manafluidics.spell.SpellBase;
import derpatiel.manafluidics.spell.parameters.SpellParameterChoices;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.List;

public class BreathOfWind extends SpellBase {

    public BreathOfWind(){
        super("breathofwind",1,10, SpellAttribute.AIR,SpellAttribute.NATURE);
        this.needsClientActivation=true;
    }

    @Override
    public boolean doCast(World worldIn, EntityPlayer castingPlayer, boolean boosted, Collection<SpellParameterChoices> parameters) {
        AxisAlignedBB effectedBox = castingPlayer.getEntityBoundingBox().expand(5,5,5);
        List<Entity> effectedEntities = worldIn.getEntitiesWithinAABB(Entity.class,effectedBox);
        Vec3d forceDir = castingPlayer.getLook(0);
        forceDir = forceDir.scale(1.0d);
        for(Entity entity : effectedEntities){
            entity.addVelocity(forceDir.xCoord,forceDir.yCoord,forceDir.zCoord);
        }
        return true;
    }
}
