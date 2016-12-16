package derpatiel.manafluidics.spell.lvl4;

import derpatiel.manafluidics.spell.SpellAttribute;
import derpatiel.manafluidics.spell.SpellBase;
import derpatiel.manafluidics.spell.parameters.SpellParameterChoices;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import java.util.List;

public class MajorHealing extends SpellBase {
    public MajorHealing() {
        super("majorhealing", 1, 30, SpellAttribute.HEALING);
    }

    @Override
    public boolean doCast(World worldIn, EntityPlayer castingPlayer, boolean boosted, List<SpellParameterChoices> parameters) {
        castingPlayer.heal(10.0f);
        return true;
    }
}
