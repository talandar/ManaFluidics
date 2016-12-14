package derpatiel.manafluidics.enums;

import com.google.common.collect.Lists;
import derpatiel.manafluidics.block.altar.construction.AltarConstructionData;
import derpatiel.manafluidics.spell.SpellAttribute;

import java.util.ArrayList;
import java.util.List;

public enum AltarType {
    ZIGGURAT(),
    HENGE(SpellAttribute.NATURE,SpellAttribute.PROTECTION),
    HOURGLASS_FOCUS(),
    TOWER(SpellAttribute.AIR),
    TEMPLE(),
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
}
