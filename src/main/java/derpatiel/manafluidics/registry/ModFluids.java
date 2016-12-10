package derpatiel.manafluidics.registry;

import derpatiel.manafluidics.ManaFluidics;
import derpatiel.manafluidics.block.MFBlockFluid;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.List;

public class ModFluids {

    public static final List<Fluid> allModFluids = new ArrayList<>();

    public static Fluid reactiveMana;
    public static Fluid moltenCrystal;
    public static Fluid moltenIron;
    public static Fluid moltenGold;
    public static Fluid energizedMana;
    public static Fluid moltenDiamond;
    public static Fluid moltenLapis;
    public static Fluid moltenRedstone;

    //alloys
    public static Fluid crystalIron;
    public static Fluid redCrystal;

    public static MFBlockFluid reactiveManaBlockFluid;
    public static MFBlockFluid moltenCrystalBlockFluid;
    public static MFBlockFluid moltenIronBlockFluid;
    public static MFBlockFluid moltenGoldBlockFluid;
    public static MFBlockFluid energizedManaBlockFluid;
    public static MFBlockFluid moltenDiamondBlockFluid;
    public static MFBlockFluid moltenLapisBlockFluid;
    public static MFBlockFluid moltenRedstoneBlockFluid;

    //alloys
    public static MFBlockFluid crystalIronBlockFluid;
    public static MFBlockFluid redCrystaBlockFluid;

    //note to self, uppermost metal ideas:
    //unobtanium
    //octiron


    //compat fluids
    //copper, tin, aluminum, lead, silver, nickel
    public static Fluid moltenCopper;
    public static Fluid moltenTin;
    public static Fluid moltenAluminum;
    public static Fluid moltenLead;
    public static Fluid moltenSilver;
    public static Fluid moltenNickel;

    public static MFBlockFluid moltenCopperBlockFluid;
    public static MFBlockFluid moltenTinBlockFluid;
    public static MFBlockFluid moltenAluminumBlockFluid;
    public static MFBlockFluid moltenLeadBlockFluid;
    public static MFBlockFluid moltenSilverBlockFluid;
    public static MFBlockFluid moltenNickelBlockFluid;

    public static void registerFluids() {

        reactiveMana = registerFluid("reactivemana",15,10000,6000);
        moltenCrystal = registerFluid("moltencrystal",15,1000,4000);
        moltenIron = registerFluid("molteniron", 15,1000,4000);
        moltenGold = registerFluid("moltengold",15,1000,4000);
        energizedMana = registerFluid("energizedmana",15,1000,4000);
        moltenDiamond = registerFluid("moltendiamond",15,1000,4000);
        moltenRedstone = registerFluid("moltenredstone",15,1000,4000);
        moltenLapis = registerFluid("moltenlapis",15,1000,4000);

        crystalIron = registerFluid("crystaliron",15,1000,4000);
        redCrystal = registerFluid("redcrystal",15,1000,4000);


        reactiveManaBlockFluid = registerBlockFluid(reactiveMana, MapColor.LAPIS, 2);
        moltenCrystalBlockFluid = registerBlockFluid(moltenCrystal, MapColor.DIAMOND, 2);
        moltenIronBlockFluid = registerBlockFluid(moltenIron, MapColor.IRON,4);
        moltenGoldBlockFluid = registerBlockFluid(moltenGold, MapColor.GOLD, 2);
        energizedManaBlockFluid = registerBlockFluid(energizedMana, MapColor.CYAN, 7);
        moltenDiamondBlockFluid = registerBlockFluid(moltenDiamond, MapColor.DIAMOND, 6);
        moltenRedstoneBlockFluid = registerBlockFluid(moltenRedstone, MapColor.RED, 7);
        moltenLapisBlockFluid = registerBlockFluid(moltenLapis, MapColor.BLUE, 3);

        crystalIronBlockFluid = registerBlockFluid(crystalIron, MapColor.RED, 4);
        redCrystaBlockFluid = registerBlockFluid(redCrystal, MapColor.PINK, 5);


        moltenCopper= registerCompatFluid("moltencopper");
        moltenTin= registerCompatFluid("moltentin");
        moltenAluminum= registerCompatFluid("moltenaluminum");
        moltenLead= registerCompatFluid("moltenlead");
        moltenSilver= registerCompatFluid("moltensilver");
        moltenNickel= registerCompatFluid("moltennickel");

        moltenCopperBlockFluid = registerBlockFluid(moltenCopper,MapColor.ADOBE,7);
        moltenTinBlockFluid = registerBlockFluid(moltenTin,MapColor.CLOTH,7);
        moltenAluminumBlockFluid = registerBlockFluid(moltenAluminum,MapColor.CLOTH,7);
        moltenLeadBlockFluid = registerBlockFluid(moltenLead,MapColor.BLUE,7);
        moltenSilverBlockFluid = registerBlockFluid(moltenSilver,MapColor.SILVER,7);
        moltenNickelBlockFluid = registerBlockFluid(moltenNickel,MapColor.SILVER,7);
    }

    private static Fluid registerCompatFluid(String fluidName){
        return registerFluid(fluidName,15,1000,1000);
    }
    private static Fluid registerFluid(String fluidName,int lumosity, int density, int viscosity){
        Fluid fluid = new Fluid(fluidName, new ResourceLocation(ManaFluidics.MODID, "blocks/"+fluidName+"_still"), new ResourceLocation(ManaFluidics.MODID, "blocks/"+fluidName+"_flow"));
        fluid.setLuminosity(lumosity);
        fluid.setDensity(density);
        fluid.setViscosity(viscosity);

        FluidRegistry.registerFluid(fluid);
        allModFluids.add(fluid);
        return fluid;
    }

    private static MFBlockFluid registerBlockFluid(Fluid fluid, MapColor color, int quanta) {
        MFBlockFluid blockFluid = new MFBlockFluid(fluid, new MaterialLiquid(color), fluid.getName(), quanta);
        GameRegistry.register(blockFluid);
        registerFluidRenderer(blockFluid);
        return blockFluid;
    }

    public static void registerFluidRenderer(MFBlockFluid fluidBlock) {
        ItemBlock itemBlock = new ItemBlock(fluidBlock);
        itemBlock.setRegistryName(fluidBlock.getRegistryName());
        itemBlock.setCreativeTab(ModItems.tabFluidics);
        GameRegistry.register(itemBlock);
        ManaFluidics.proxy.registerFluidStateMapper(fluidBlock);
        ManaFluidics.proxy.registerItemRenderer(itemBlock, 0, fluidBlock.getBareUnlocalizedName());
    }

    public static void registerBuckets() {
        for(Fluid fluid : allModFluids){
            FluidRegistry.addBucketForFluid(fluid);
        }
    }

}
