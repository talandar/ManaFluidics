package derpatiel.manafluidics.spell;

import derpatiel.manafluidics.spell.parameters.SpellParameterChoices;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by Jim on 12/11/2016.
 */
public abstract class SelfPotionSpell extends SpellBase {

    public SelfPotionSpell(String name, int level, int castingCost, SpellAttribute... attributes) {
        super(name, level, castingCost, attributes);
    }

    @Override
    public boolean doCast(World worldIn, EntityPlayer castingPlayer, boolean boosted, List<SpellParameterChoices> parameters) {
        for(PotionEffect potionEffect : getPotionEffect(boosted)) {
            castingPlayer.addPotionEffect(potionEffect);
        }
        return true;
    }

    public abstract PotionEffect[] getPotionEffect(boolean boosted);
}
