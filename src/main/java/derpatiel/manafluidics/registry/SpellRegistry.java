package derpatiel.manafluidics.registry;

import derpatiel.manafluidics.spell.SpellBase;
import derpatiel.manafluidics.spell.cantrip.CreateLight;
import derpatiel.manafluidics.spell.cantrip.CreateMana;
import derpatiel.manafluidics.spell.cantrip.CreateWater;
import derpatiel.manafluidics.spell.cantrip.SetFlame;
import derpatiel.manafluidics.spell.lvl1.MagicMissile;
import derpatiel.manafluidics.spell.lvl2.Barkskin;
import derpatiel.manafluidics.spell.lvl3.Ironskin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpellRegistry {

    //cantrip
    public static SpellBase createWater;
    public static SpellBase createMana;
    public static SpellBase summonLight;
    public static SpellBase setFlame;

    //lvl1
    public static SpellBase magicMissile;

    //lvl2
    public static SpellBase barkskin;

    //lvl3
    public static SpellBase ironskin;

    private static Map<String,SpellBase> spellRegistryMap = new HashMap<>();
    private static Map<Integer,List<SpellBase>> spellsByLevel = new HashMap<>();
    private static List<SpellBase> allSpells = new ArrayList<>();

    public static void init(){
        //cantrip
        createWater = register(new CreateWater());
        createMana = register(new CreateMana());
        summonLight = register(new CreateLight());
        setFlame = register(new SetFlame());

        //lvl1
        magicMissile = register(new MagicMissile());

        //lvl2
        barkskin = register(new Barkskin());

        //lvl3
        ironskin = register(new Ironskin());
    }

    public static SpellBase register(SpellBase spell){

        spellRegistryMap.put(spell.getName(),spell);
        if(!spellsByLevel.containsKey(spell.getLevel())){
            spellsByLevel.put(spell.getLevel(),new ArrayList<>());
        }
        spellsByLevel.get(spell.getLevel()).add(spell);

        allSpells.add(spell);

        return spell;
    }

    public static List<SpellBase> spells(){
        return allSpells;
    }

}
