package derpatiel.manafluidics.enums;

import net.minecraft.util.IStringSerializable;

public enum TableFormationState implements IStringSerializable {
    UNFORMED(0,"unformed"),
    NW(1,"NW"),
    SW(2,"SW"),
    NE(3,"NE"),
    SE(4,"SE");

    private int ID;
    private String name;

    private TableFormationState(int id, String name){
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
        return null;
    }
}
