package derpatiel.manafluidics.enums;

import net.minecraft.util.IStringSerializable;

public enum TankPartState implements IStringSerializable {
    UNFORMED(0,"unformed"),
    NORTH(1,"north"),
    SOUTH(2,"south"),
    EAST(3,"east"),
    WEST(4,"west");

    private int id;
    private String name;

    private TankPartState(int id, String name){
        this.id=id;
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public int getID() {
        return id;
    }

    @Override
    public String toString() {
        return name;
    }

    public static TankPartState getById(int metadata) {
        for(TankPartState t : values()){
            if(t.id==metadata)
                return t;
        }
        return null;
    }
}
