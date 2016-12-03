package derpatiel.manafluidics.registry;

import derpatiel.manafluidics.spell.SpellBase;
import derpatiel.manafluidics.spell.cantrip.CreateLight;
import derpatiel.manafluidics.spell.cantrip.CreateMana;
import derpatiel.manafluidics.spell.cantrip.CreateWater;
import derpatiel.manafluidics.spell.cantrip.SetFlame;
import derpatiel.manafluidics.spell.lvl1.MagicMissile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpellRegistry {

    public static SpellBase createWater;
    public static SpellBase createMana;
    public static SpellBase summonLight;
    public static SpellBase setFlame;


    public static SpellBase magicMissile;

    private static Map<String,SpellBase> spellRegistryMap = new HashMap<>();
    private static Map<Integer,List<SpellBase>> spellsByLevel = new HashMap<>();

    public static void init(){
        createWater = register(new CreateWater());
        createMana = register(new CreateMana());
        summonLight = register(new CreateLight());
        setFlame = register(new SetFlame());

        magicMissile = register(new MagicMissile());
    }

    public static SpellBase register(SpellBase spell){

        spellRegistryMap.put(spell.getName(),spell);
        if(!spellsByLevel.containsKey(spell.getLevel())){
            spellsByLevel.put(spell.getLevel(),new ArrayList<>());
        }
        spellsByLevel.get(spell.getLevel()).add(spell);

        return spell;
    }

}
