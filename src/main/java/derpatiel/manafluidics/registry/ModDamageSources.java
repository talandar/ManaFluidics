package derpatiel.manafluidics.registry;

import net.minecraft.util.DamageSource;

public class ModDamageSources {

    public static DamageSource moltenLiquid;

    static{
        moltenLiquid = new DamageSource("molten liquid");
        moltenLiquid.setDamageBypassesArmor().setFireDamage().setDifficultyScaled().setDamageIsAbsolute();
    }

}
