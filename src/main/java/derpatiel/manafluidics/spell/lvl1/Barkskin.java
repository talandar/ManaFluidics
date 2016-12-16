package derpatiel.manafluidics.spell.lvl1;

import derpatiel.manafluidics.spell.SelfPotionSpell;
import derpatiel.manafluidics.spell.SpellAttribute;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;

/**
 * Created by Jim on 12/11/2016.
 */
public class Barkskin extends SelfPotionSpell {
    public Barkskin() {
        super("barkskin", 1, 20, SpellAttribute.TRANSMUTATION, SpellAttribute.PROTECTION);
    }

    @Override
    public PotionEffect[] getPotionEffect(boolean boosted) {
        return new PotionEffect[]{ new PotionEffect(MobEffects.RESISTANCE,boosted ? 120 * 20 : 90 * 20,0,false,false)};
    }
}
