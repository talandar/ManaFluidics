package derpatiel.manafluidics.registry;

import derpatiel.manafluidics.ManaFluidics;
import derpatiel.manafluidics.block.MFBlockFluid;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModFluids {

    public static Fluid reactiveMana;
    public static Fluid moltenCrystal;
    public static Fluid moltenIron;
    public static Fluid moltenGold;
    public static Fluid energizedMana;

    public static MFBlockFluid reactiveManaBlockFluid;
    public static MFBlockFluid moltenCrystalBlockFluid;
    public static MFBlockFluid moltenIronBlockFluid;
    public static MFBlockFluid moltenGoldBlockFluid;
    public static MFBlockFluid energizedManaBlockFluid;


    public static void registerFluids() {

        reactiveMana = new Fluid("reactivemana", new ResourceLocation(ManaFluidics.MODID, "blocks/reactivemana_still"), new ResourceLocation(ManaFluidics.MODID, "blocks/reactivemana_flow"));
        reactiveMana.setLuminosity(15);
        reactiveMana.setDensity(1000000);
        reactiveMana.setViscosity(60000);

        moltenCrystal = new Fluid("moltencrystal", new ResourceLocation(ManaFluidics.MODID, "blocks/moltencrystal_still"), new ResourceLocation(ManaFluidics.MODID, "blocks/moltencrystal_flow"));
        moltenCrystal.setLuminosity(15);
        moltenCrystal.setDensity(1000);
        moltenCrystal.setViscosity(4000);

        moltenIron = new Fluid("molteniron", new ResourceLocation(ManaFluidics.MODID, "blocks/molteniron_still"), new ResourceLocation(ManaFluidics.MODID, "blocks/molteniron_flow"));
        moltenIron.setLuminosity(15);
        moltenIron.setDensity(1000);
        moltenIron.setViscosity(4000);

        moltenGold = new Fluid("moltengold", new ResourceLocation(ManaFluidics.MODID, "blocks/moltengold_still"), new ResourceLocation(ManaFluidics.MODID, "blocks/moltengold_flow"));
        moltenGold.setLuminosity(15);
        moltenGold.setDensity(1000);
        moltenGold.setViscosity(4000);

        energizedMana = new Fluid("energizedmana", new ResourceLocation(ManaFluidics.MODID, "blocks/energizedmana_still"), new ResourceLocation(ManaFluidics.MODID, "blocks/energizedmana_flow"));
        energizedMana.setLuminosity(15);
        energizedMana.setDensity(1000);
        energizedMana.setViscosity(4000);


        FluidRegistry.registerFluid(reactiveMana);
        FluidRegistry.registerFluid(moltenCrystal);
        FluidRegistry.registerFluid(moltenIron);
        FluidRegistry.registerFluid(moltenGold);
        FluidRegistry.registerFluid(energizedMana);

        reactiveManaBlockFluid = new MFBlockFluid(reactiveMana, new MaterialLiquid(MapColor.LAPIS), "reactivemana", 2);

        moltenCrystalBlockFluid = new MFBlockFluid(moltenCrystal, new MaterialLiquid(MapColor.DIAMOND), "moltencrystal", 2);

        moltenIronBlockFluid = new MFBlockFluid(moltenIron, new MaterialLiquid(MapColor.IRON), "molteniron", 4);

        moltenGoldBlockFluid = new MFBlockFluid(moltenGold, new MaterialLiquid(MapColor.GOLD), "moltengold", 2);

        energizedManaBlockFluid = new MFBlockFluid(energizedMana, new MaterialLiquid(MapColor.CYAN), "energizedmana", 7);

        GameRegistry.register(reactiveManaBlockFluid);
        GameRegistry.register(moltenCrystalBlockFluid);
        GameRegistry.register(moltenIronBlockFluid);
        GameRegistry.register(moltenGoldBlockFluid);
        GameRegistry.register(energizedManaBlockFluid);

        StateMap.Builder bldr = new StateMap.Builder();
        bldr.ignore(BlockFluidBase.LEVEL);


        ModelLoader.setCustomStateMapper(reactiveManaBlockFluid, bldr.build());
        ModelLoader.setCustomStateMapper(moltenCrystalBlockFluid, bldr.build());
        ModelLoader.setCustomStateMapper(moltenIronBlockFluid, bldr.build());
        ModelLoader.setCustomStateMapper(moltenGoldBlockFluid, bldr.build());
        ModelLoader.setCustomStateMapper(energizedManaBlockFluid, bldr.build());

        registerFluidRenderer(reactiveManaBlockFluid);
        registerFluidRenderer(moltenCrystalBlockFluid);
        registerFluidRenderer(moltenIronBlockFluid);
        registerFluidRenderer(moltenGoldBlockFluid);
        registerFluidRenderer(energizedManaBlockFluid);

    }

    public static void registerFluidRenderer(MFBlockFluid fluidBlock) {
        ItemBlock itemBlock = new ItemBlock(fluidBlock);
        itemBlock.setRegistryName(fluidBlock.getRegistryName());
        itemBlock.setCreativeTab(ModItems.tabFluidics);
        GameRegistry.register(itemBlock);
        ManaFluidics.proxy.registerItemRenderer(itemBlock, 0, fluidBlock.getBareUnlocalizedName());
    }

    public static void registerBuckets() {
        FluidRegistry.addBucketForFluid(reactiveMana);
        FluidRegistry.addBucketForFluid(moltenCrystal);
        FluidRegistry.addBucketForFluid(moltenIron);
        FluidRegistry.addBucketForFluid(moltenGold);
        FluidRegistry.addBucketForFluid(energizedMana);
    }

}
