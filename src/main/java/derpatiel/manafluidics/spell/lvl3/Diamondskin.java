package derpatiel.manafluidics.spell.lvl3;

import derpatiel.manafluidics.spell.SelfPotionSpell;
import derpatiel.manafluidics.spell.SpellAttribute;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;

public class Diamondskin extends SelfPotionSpell {
    public Diamondskin() {
        super("diamondskin", 3, 70, SpellAttribute.TRANSMUTATION, SpellAttribute.PROTECTION);
    }

    @Override
    public PotionEffect getPotionEffect(boolean boosted) {
        return new PotionEffect(MobEffects.RESISTANCE,boosted ? 120 * 20 : 90 * 20, 3);
    }
}
