package derpatiel.manafluidics.spell.lvl4;

import derpatiel.manafluidics.spell.SpellAttribute;
import derpatiel.manafluidics.spell.SpellBase;
import derpatiel.manafluidics.spell.parameters.SpellParameterChoices;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import java.util.Collection;

public class MajorHealing extends SpellBase {
    public MajorHealing() {
        super("majorhealing", 4, 30, SpellAttribute.HEALING);
    }

    @Override
    public boolean doCast(World worldIn, EntityPlayer castingPlayer, boolean boosted, Collection<SpellParameterChoices> parameters) {
        castingPlayer.heal(10.0f);
        return true;
    }
}
