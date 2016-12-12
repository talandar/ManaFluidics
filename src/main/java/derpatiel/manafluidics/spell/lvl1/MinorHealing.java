package derpatiel.manafluidics.spell.lvl1;

import derpatiel.manafluidics.spell.SpellAttribute;
import derpatiel.manafluidics.spell.SpellBase;
import derpatiel.manafluidics.spell.SpellParameterChoices;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import java.util.List;

public class MinorHealing extends SpellBase {
    public MinorHealing() {
        super("minorhealing", 1, 30, SpellAttribute.HEALING);
    }

    @Override
    public boolean doCast(World worldIn, EntityPlayer castingPlayer, boolean boosted, List<SpellParameterChoices> parameters) {
        castingPlayer.heal(2.0f);
        return true;
    }
}
