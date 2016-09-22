package derpatiel.manafluidics.enums;

import derpatiel.manafluidics.ManaFluidics;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;

public enum MaterialType implements IStringSerializable {
    CRYSTAL(0, "crystal"),
    OBSIDIAN(1, "obsidian"),
    IRON(2,"iron"),
    GOLD(3,"gold");

    private int ID;
    private String name;

    private MaterialType(int ID, String name) {
        this.ID = ID;
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

    public static MaterialType getById(int metadata) {
        for(MaterialType t : values()){
            if(t.ID==metadata)
                return t;
        }
        return null;
    }

    public static ResourceLocation[] buildVariantList(String baseName) {
        ResourceLocation[] variants = new ResourceLocation[values().length];
        for(MaterialType t : values()){
            variants[t.getID()]=new ResourceLocation(ManaFluidics.MODID, baseName+"_"+t.getName());
        }
        return variants;
    }
}
