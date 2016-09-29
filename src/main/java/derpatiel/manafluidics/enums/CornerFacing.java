package derpatiel.manafluidics.enums;

import net.minecraft.util.IStringSerializable;

public enum CornerFacing implements IStringSerializable {
    NORTH_WEST(0,"northwest"),
    SOUTH_WEST(1,"southwest"),
    NORTH_EAST(2,"northeast"),
    SOUTH_EAST(3,"southeast");

    private final int ID;
    private final String name;

    CornerFacing(int id, String name){
        this.ID = id;
        this.name = name;
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

    public static CornerFacing getById(int metadata) {
        for(CornerFacing s : values()){
            if(s.ID==metadata)
                return s;
        }
        return CornerFacing.NORTH_WEST;
    }
}
