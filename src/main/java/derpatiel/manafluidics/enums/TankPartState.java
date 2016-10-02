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
    public static final TankPartState[] VALUES;

    private TankPartState(int id, String name){
        this.id=id;
        this.name = name;
    }

    static{
        TankPartState[] vals = values();
        VALUES = new TankPartState[vals.length];
        for(TankPartState tps : vals){
            VALUES[tps.getID()]=tps;
        }
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
        return VALUES[metadata%VALUES.length];
    }
}
