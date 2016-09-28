package derpatiel.manafluidics.enums;

import net.minecraft.util.IStringSerializable;

public enum TableFormationState implements IStringSerializable {
    NORTH_WEST(0,"northwest"),
    SOUTH_WEST(1,"southwest"),
    NORTH_EAST(2,"northeast"),
    SOUTH_EAST(3,"southeast");

    private final int ID;
    private final String name;

    TableFormationState(int id, String name){
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

    public static TableFormationState getById(int metadata) {
        for(TableFormationState s : values()){
            if(s.ID==metadata)
                return s;
        }
        return TableFormationState.NORTH_WEST;
    }
}
