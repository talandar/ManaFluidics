package derpatiel.manafluidics.spell.lvl2;

import derpatiel.manafluidics.spell.SpellAttribute;
import derpatiel.manafluidics.spell.SpellBase;
import derpatiel.manafluidics.spell.parameters.SpellParameterChoices;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import java.util.List;

public class DangerousLeap extends SpellBase {

    public DangerousLeap(){
        super("dangerousleap",2,20, SpellAttribute.AIR);
        needsClientActivation=true;
    }

    @Override
    public boolean doCast(World worldIn, EntityPlayer castingPlayer, boolean boosted, List<SpellParameterChoices> parameters) {
        castingPlayer.fallDistance=0;
        castingPlayer.motionY=4;
        return true;
    }
}
