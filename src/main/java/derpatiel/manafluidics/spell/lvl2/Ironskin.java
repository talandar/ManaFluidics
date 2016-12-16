package derpatiel.manafluidics.spell.lvl2;

import derpatiel.manafluidics.spell.SelfPotionSpell;
import derpatiel.manafluidics.spell.SpellAttribute;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;

public class Ironskin extends SelfPotionSpell {
    public Ironskin() {
        super("ironskin", 2, 50, SpellAttribute.TRANSMUTATION, SpellAttribute.PROTECTION);
    }

    @Override
    public PotionEffect[] getPotionEffect(boolean boosted) {
        return new PotionEffect[]{ new PotionEffect(MobEffects.RESISTANCE,boosted ? 120 * 20 : 90 * 20, 1)};
    }
}
