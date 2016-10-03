package derpatiel.manafluidics.util;

import derpatiel.manafluidics.ManaFluidics;
import derpatiel.manafluidics.enums.MaterialType;
import derpatiel.manafluidics.registry.ModBlocks;
import derpatiel.manafluidics.registry.ModFluids;
import derpatiel.manafluidics.registry.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MetaItemHelper {

    public static final  Map<MaterialType,String> sheetResourceDomain = new HashMap<>();
    public static final Map<MaterialType,String> sheetResourceLocation = new HashMap<>();
    public static final Map<Fluid,MaterialType> fluidProductMap = new HashMap<>();

    static{

        fluidProductMap.put(ModFluids.moltenCrystal, MaterialType.CRYSTAL);
        fluidProductMap.put(FluidRegistry.LAVA, MaterialType.OBSIDIAN);
        fluidProductMap.put(ModFluids.moltenIron, MaterialType.IRON);
        fluidProductMap.put(ModFluids.moltenGold, MaterialType.GOLD);

        sheetResourceLocation.put(MaterialType.CRYSTAL, "blocks/sheet_crystal");
        sheetResourceLocation.put(MaterialType.GOLD, "blocks/gold_block");
        sheetResourceLocation.put(MaterialType.IRON, "blocks/iron_block");
        sheetResourceLocation.put(MaterialType.OBSIDIAN, "blocks/obsidian");

        sheetResourceDomain.put(MaterialType.CRYSTAL, ManaFluidics.MODID);
        sheetResourceDomain.put(MaterialType.GOLD, "minecraft");
        sheetResourceDomain.put(MaterialType.IRON, "minecraft");
        sheetResourceDomain.put(MaterialType.OBSIDIAN, "minecraft");
    }

    public static ItemStack getWire(MaterialType type) {
        return getWire(type, 1);
    }

    public static ItemStack getWire(MaterialType type, int quantity){
        return new ItemStack(ModItems.material_wire,quantity,type.getID());
    }

    public static ItemStack getCircuit(MaterialType type){ return getCircuit(type,1);}
    public static ItemStack getCircuit(MaterialType type, int quantity){
        return new ItemStack(ModItems.control_circuit,quantity,type.getID());
    }

    public static ItemStack getSheet(MaterialType type) {
        return getSheet(type, 1);
    }
    public static ItemStack getSheet(MaterialType type, int quantity){
        return new ItemStack(ModBlocks.sheet,quantity,type.getID());
    }
}
