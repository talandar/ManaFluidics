package derpatiel.manafluidics.spell.lvl3;

import derpatiel.manafluidics.spell.SelfPotionSpell;
import derpatiel.manafluidics.spell.SpellAttribute;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;

/**
 * Created by Jim on 12/15/2016.
 */
public class QuickStep extends SelfPotionSpell {
    public QuickStep() {
        super("quickstep", 3, 35, SpellAttribute.AIR, SpellAttribute.ENCHANTMENT);
    }

    @Override
    public PotionEffect[] getPotionEffect(boolean boosted) {
        return new PotionEffect[]{ new PotionEffect(MobEffects.SPEED,boosted ? 45 * 20 : 30 * 20,3)};
    }
}
