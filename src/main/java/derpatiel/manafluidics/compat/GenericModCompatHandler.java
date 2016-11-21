package derpatiel.manafluidics.compat;

import derpatiel.manafluidics.registry.ModFluids;
import derpatiel.manafluidics.registry.ModItems;
import derpatiel.manafluidics.util.MaterialItemHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

public class GenericModCompatHandler {
//copper, tin, aluminum, lead, silver, nickel

    public static boolean foundCopper = false;
    public static boolean foundTin = false;
    public static boolean foundAluminum = false;
    public static boolean foundLead = false;
    public static boolean foundSilver = false;
    public static boolean foundNickel = false;

    public static void init(FMLInitializationEvent event){

        foundCopper = checkOreAndIngot("Copper");
        foundTin = checkOreAndIngot("Tin");
        foundAluminum = checkOreAndIngot("Aluminum");
        foundLead = checkOreAndIngot("Lead");
        foundSilver = checkOreAndIngot("Silver");
        foundNickel = checkOreAndIngot("Nickel");


        if(foundAluminum) {
            makeOreDictRecipies("Aluminum", ModFluids.moltenAluminum);
        }

    }

    private static boolean checkOreAndIngot(String suffix){
        return OreDictionary.doesOreNameExist("ore"+suffix) && OreDictionary.doesOreNameExist("ingot"+suffix) && OreDictionary.doesOreNameExist("block"+suffix);
    }

    private static void makeOreDictRecipies(String dictName, Fluid moltenFluid){
        List<ItemStack> ores = OreDictionary.getOres("ore"+dictName);
        List<ItemStack> ingots = OreDictionary.getOres("ingot"+dictName);
        List<ItemStack> blocks = OreDictionary.getOres("block"+dictName);
        if(blocks.size()>0){
            MaterialItemHelper.castingProducts.get(ModItems.block_mold).put(new FluidStack(moltenFluid, 4500), blocks.get(0));
        }
        if(ingots.size()>0){
            MaterialItemHelper.castingProducts.get(ModItems.ingot_mold).put(new FluidStack(moltenFluid,500), ingots.get(0));
        }
    }
}
