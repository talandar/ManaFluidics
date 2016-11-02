package derpatiel.manafluidics.registry;

import derpatiel.manafluidics.spell.SpellBase;
import derpatiel.manafluidics.spell.cantrip.CreateMana;
import derpatiel.manafluidics.spell.cantrip.CreateWater;

public class SpellRegistry {

    public static SpellBase createWater;
    public static SpellBase createMana;

    public static void init(){
        createWater = new CreateWater();
        createMana = new CreateMana();
    }

}
