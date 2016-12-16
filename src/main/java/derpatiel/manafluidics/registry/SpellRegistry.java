package derpatiel.manafluidics.registry;

import derpatiel.manafluidics.spell.SpellBase;
import derpatiel.manafluidics.spell.cantrip.CreateLight;
import derpatiel.manafluidics.spell.cantrip.CreateMana;
import derpatiel.manafluidics.spell.cantrip.CreateWater;
import derpatiel.manafluidics.spell.cantrip.SetFlame;
import derpatiel.manafluidics.spell.lvl1.EnderBag;
import derpatiel.manafluidics.spell.lvl1.MagicMissile;
import derpatiel.manafluidics.spell.lvl1.Barkskin;
import derpatiel.manafluidics.spell.lvl1.MinorHealing;
import derpatiel.manafluidics.spell.lvl2.HomePortal;
import derpatiel.manafluidics.spell.lvl2.Ironskin;
import derpatiel.manafluidics.spell.lvl3.Diamondskin;
import derpatiel.manafluidics.spell.lvl4.Invulnerability;

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
    public static SpellBase barkskin;
    public static SpellBase minorHeal;
    public static SpellBase enderBag;

    //lvl2
    public static SpellBase ironskin;
    public static SpellBase homePortal;

    //lvl3
    public static SpellBase diamondskin;

    //lvl4
    public static SpellBase invulnerability;

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
        barkskin = register(new Barkskin());
        minorHeal = register(new MinorHealing());
        enderBag = register(new EnderBag());

        //lvl2
        ironskin = register(new Ironskin());
        homePortal = register(new HomePortal());

        //lvl3
        diamondskin = register(new Diamondskin());

        //lvl4
        invulnerability = register(new Invulnerability());
    }

    public static SpellBase register(SpellBase spell){

        spellRegistryMap.put(spell.getRegName(),spell);
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

    public static List<SpellBase> getSpellsForLevel(int spellLevel) {
        return spellsByLevel.get(spellLevel);
    }
}
