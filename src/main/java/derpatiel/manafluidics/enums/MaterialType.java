package derpatiel.manafluidics.enums;

import net.minecraft.util.IStringSerializable;

public enum MaterialType implements IStringSerializable {
    CRYSTAL(0, "crystal"),
    OBSIDIAN(1, "obsidian"),
    IRON(2,"iron"),
    GOLD(3,"gold"),
    REDSTONE(4,"redstone"),
    DIAMOND(5,"diamond"),
    LAPIS(6,"lapis")
    ;

    private final int ID;
    private final String name;
    public static final MaterialType[] VALUES;

    MaterialType(int ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    static{
        MaterialType[] vals = values();
        VALUES = new MaterialType[vals.length];
        for(MaterialType m : vals){
            VALUES[m.getID()]=m;
        }
    }

    public static String[] getNames() {
        String[] names = new String[VALUES.length];
        for(MaterialType type : VALUES){
            names[type.getID()]=type.getName();
        }
        return names;
    }

    @Override
    public String getName() {
        return name;
    }

    public int getID() {
        return ID;
    }

    @Override
    public String toString() {
        return getName();
    }

    public static MaterialType getById(int metadata) {
        return VALUES[metadata%VALUES.length];
    }
}
