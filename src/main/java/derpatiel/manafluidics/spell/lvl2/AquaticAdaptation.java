package derpatiel.manafluidics.spell.lvl2;

import derpatiel.manafluidics.spell.SelfPotionSpell;
import derpatiel.manafluidics.spell.SpellAttribute;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;

public class AquaticAdaptation extends SelfPotionSpell {
    public AquaticAdaptation() {
        super("aquaticadaptation", 2, 20, SpellAttribute.TRANSMUTATION, SpellAttribute.WATER);
    }

    @Override
    public PotionEffect[] getPotionEffect(boolean boosted) {
        return new PotionEffect[]{
                //TODO: find a way of giving aqua affinity as well :(
                new PotionEffect(MobEffects.WATER_BREATHING,boosted ? 45 * 20 : 30 * 20, 1,false,false),
        };
    }

    /* use the below in the effect to make it work!  new potion effect!
        make sure to only apply if not already using aquaafinity
            if (this.isInsideOfMaterial(Material.WATER) && !EnchantmentHelper.getAquaAffinityModifier(this))
        {
            f /= 5.0F;
        }

        if (!this.onGround)
        {
            f /= 5.0F;
        }

        f = net.minecraftforge.event.ForgeEventFactory.getBreakSpeed(this, state, f, pos);
     */
}
