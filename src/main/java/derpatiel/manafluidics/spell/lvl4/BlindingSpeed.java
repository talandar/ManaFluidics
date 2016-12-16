package derpatiel.manafluidics.spell.lvl4;

import derpatiel.manafluidics.spell.SelfPotionSpell;
import derpatiel.manafluidics.spell.SpellAttribute;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;

/**
 * Created by Jim on 12/15/2016.
 */
public class BlindingSpeed extends SelfPotionSpell {
    public BlindingSpeed() {
        super("blindingspeed", 4, 35, SpellAttribute.AIR, SpellAttribute.ENCHANTMENT);
    }

    @Override
    public PotionEffect[] getPotionEffect(boolean boosted) {
        return new PotionEffect[]{
                new PotionEffect(MobEffects.SPEED,boosted ? 45 * 20 : 30 * 20,6),
                new PotionEffect(MobEffects.BLINDNESS,boosted ? 45 * 20 : 30 * 20,0)
        };
    }
}
