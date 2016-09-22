package derpatiel.manafluidics.util;

import derpatiel.manafluidics.ManaFluidics;
import derpatiel.manafluidics.enums.MaterialType;
import derpatiel.manafluidics.registry.ModBlocks;
import derpatiel.manafluidics.registry.ModFluids;
import derpatiel.manafluidics.registry.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import java.util.HashMap;
import java.util.Map;

public class MetaItemHelper {

    public static Map<MaterialType,String> sheetResourceDomain;
    public static Map<MaterialType,String> sheetResourceLocation;
    public static Map<Fluid,MaterialType> fluidProductMap;

    static{
        sheetResourceDomain = new HashMap<MaterialType, String>();
        sheetResourceLocation = new HashMap<MaterialType, String>();
        fluidProductMap = new HashMap<Fluid,MaterialType>();


        fluidProductMap.put(ModFluids.moltenCrystal, MaterialType.CRYSTAL);
        fluidProductMap.put(FluidRegistry.LAVA, MaterialType.OBSIDIAN);
        fluidProductMap.put(ModFluids.moltenIron, MaterialType.IRON);
        fluidProductMap.put(ModFluids.moltenGold, MaterialType.GOLD);


        sheetResourceLocation = new HashMap<MaterialType,String>();
        sheetResourceLocation.put(MaterialType.CRYSTAL, "blocks/sheet_crystal");
        sheetResourceLocation.put(MaterialType.GOLD, "blocks/gold_block");
        sheetResourceLocation.put(MaterialType.IRON, "blocks/iron_block");
        sheetResourceLocation.put(MaterialType.OBSIDIAN, "blocks/obsidian");

        sheetResourceDomain.put(MaterialType.CRYSTAL, ManaFluidics.MODID);
        sheetResourceDomain.put(MaterialType.GOLD, "minecraft");
        sheetResourceDomain.put(MaterialType.IRON, "minecraft");
        sheetResourceDomain.put(MaterialType.OBSIDIAN, "minecraft");
    }

    public static ItemStack getWire(MaterialType type){
        return new ItemStack(ModItems.material_wire,1,type.getID());
    }

    public static ItemStack getSheet(MaterialType type){
        return new ItemStack(ModBlocks.sheet,1,type.getID());
    }
}
