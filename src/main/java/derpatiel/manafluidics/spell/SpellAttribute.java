package derpatiel.manafluidics.spell;

import derpatiel.manafluidics.util.TextHelper;
import org.apache.commons.lang3.StringUtils;

public enum SpellAttribute {

    EVOCATION("attribute.evocation.name","1"),
    TRANSMUTATION("attribute.transmutation.name","3"),
    PROTECTION("attribute.protection.name","8"),
    ENCHANTMENT("attribute.enchantment.name","9"),
    CONJURATION("attribute.conjuration.name","d"),
    HEALING("attribute.healing.name","6"),
    NATURE("attribute.nature.name","2"),
    AIR("attribute.air.name","f"),
    FIRE("attribute.fire.name","4"),
    TELEPORTATION("attribute.teleportation.name","5"),;

    private String regName;
    private String colorCode;

    private SpellAttribute(String regName, String colorCode){
        this.regName=regName;
        this.colorCode = colorCode;
    }

    public String friendlyName() {
        return TextHelper.localizeEffect("&"+colorCode+TextHelper.localize(regName)+"&r");
    }

    public String getRegName(){
        return regName;
    }

    public String getColorCode(){
        return colorCode;
    }
}
