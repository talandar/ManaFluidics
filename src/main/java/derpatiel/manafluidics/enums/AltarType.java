package derpatiel.manafluidics.enums;

import com.google.common.collect.Lists;
import derpatiel.manafluidics.block.altar.construction.AltarConstructionData;
import derpatiel.manafluidics.spell.SpellAttribute;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public enum AltarType {
    ZIGGURAT(SpellAttribute.CONJURATION,SpellAttribute.FIRE),
    HENGE(SpellAttribute.NATURE,SpellAttribute.PROTECTION),
    HOURGLASS_FOCUS(SpellAttribute.HEALING,SpellAttribute.ENCHANTMENT),
    TOWER(SpellAttribute.AIR,SpellAttribute.EVOCATION),
    TEMPLE(SpellAttribute.TELEPORTATION,SpellAttribute.TRANSMUTATION,SpellAttribute.WATER),
    ;

    private List<SpellAttribute> boostedAttributes;

    public static final AltarType[] VALUES;


    static{
        AltarType[] vals = values();
        VALUES = new AltarType[vals.length];
        for(AltarType m : vals){
            VALUES[m.ordinal()]=m;
        }
    }

    AltarType(SpellAttribute... boostedAttributes){
        this.boostedAttributes = Lists.newArrayList(boostedAttributes);
    }

    public boolean boosts(SpellAttribute attribute){
        return boostedAttributes.contains(attribute);
    }

    public String localizationTag(String part){
        return "altar.type."+name().toLowerCase(Locale.US)+"."+part;
    }

    public List<SpellAttribute> boostedAttributes() {
        return boostedAttributes;
    }
}
