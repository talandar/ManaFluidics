package derpatiel.manafluidics.enums;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;

public enum RedstoneActivation implements IStringSerializable{
    RAISING(0,"raising",true,false,"raising edge"),
    FALLING(1,"falling",true,false,"falling edge"),
    HIGH(2,"high",false,true,"signal high"),
    LOW(3,"low",false,true,"signal low"),
    IGNORED(4,"ignore",true,true,"ignored");

    private int id;
    private String name;
    private boolean pulse;
    private boolean toggle;
    private String chatDescription;
    public static final RedstoneActivation[] VALUES;


    private RedstoneActivation(int id, String name, boolean pulseActivation, boolean toggleActivation,String chatDescription){
        this.id = id;
        this.name = name;
        this.pulse = pulseActivation;
        this.toggle = toggleActivation;
        this.chatDescription=chatDescription;
    }

    static{
        RedstoneActivation[] vals = values();
        VALUES = new RedstoneActivation[vals.length];
        for(RedstoneActivation activation : vals){
            VALUES[activation.getId()]=activation;
        }
    }

    @Override
    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return getName();
    }

    public static RedstoneActivation getById(int metadata) {
        return VALUES[metadata%VALUES.length];
    }

    public boolean isPulseTrigger(){
        return pulse;
    }

    public boolean isToggleTrigger(){
        return toggle;
    }

    public String getChatDescription(){
        return chatDescription;
    }
}
