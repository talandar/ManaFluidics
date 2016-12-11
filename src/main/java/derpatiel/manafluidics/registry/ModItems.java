package derpatiel.manafluidics.registry;

import derpatiel.manafluidics.ManaFluidics;
import derpatiel.manafluidics.item.*;
import derpatiel.manafluidics.item.spell.SpellWand;
import derpatiel.manafluidics.spell.SpellBase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.List;

public class ModItems {

    public static MFItem manaCrystal;
    public static MFItem bucket_crystals;
    public static MFItem bucket_iron;
    public static MFItem bucket_gold;
    public static MFItem crystal_hammer;
    public static MFItem material_wire;
    public static MFItem control_circuit;
    public static MFItem heat_exchanger;
    public static MFItem ingot_mold;
    public static MFItem unfired_ingot_mold;
    public static MFItem block_mold;
    public static MFItem unfired_block_mold;
    public static MFItem gem_mold;
    public static MFItem unfired_gem_mold;
    public static MFItem mana_wand;
    public static MFItem crystal_iron_ingot;
    public static MFItem redcrystal_gem;
    public static MFItem admin_altar_wand;
    public static MFItem debug_wand;

    public static List<MFItem> spellWands = new ArrayList<>();

    public static void registerItems() {

        manaCrystal = register(new MFItem("manacrystal").setCreativeTab(tabFluidics));
        bucket_crystals = register(new MFItem("bucket_crystals").setCreativeTab(tabFluidics));
        bucket_iron = register(new MFItem("bucket_iron").setCreativeTab(tabFluidics));
        bucket_gold = register(new MFItem("bucket_gold").setCreativeTab(tabFluidics));
        crystal_hammer = register(new MFHammer("crystalhammer").setCreativeTab(tabFluidics));
        material_wire = register(new MFMaterialItem("wire").setCreativeTab(tabFluidics));
        control_circuit = register(new MFMaterialItem("controlcircuit").setCreativeTab(tabFluidics));
        heat_exchanger = register(new MFItem("heat_exchanger").setCreativeTab(tabFluidics));
        ingot_mold = register(new MFMoldItem("ingot_mold").setCreativeTab(tabFluidics));
        unfired_ingot_mold = register(new MFItem("unfired_ingot_mold").setCreativeTab(tabFluidics));
        block_mold = register(new MFMoldItem("block_mold").setCreativeTab(tabFluidics));
        unfired_gem_mold = register(new MFItem("unfired_gem_mold").setCreativeTab(tabFluidics));
        gem_mold = register(new MFMoldItem("gem_mold").setCreativeTab(tabFluidics));
        unfired_block_mold = register(new MFItem("unfired_block_mold").setCreativeTab(tabFluidics));
        mana_wand = register(new SpellWand("mana_wand",SpellRegistry.createMana).setCreativeTab(tabFluidics));
        crystal_iron_ingot = register(new MFItem("crystal_iron_ingot").setCreativeTab(tabFluidics));
        redcrystal_gem = register(new MFItem("redcrystal_gem").setCreativeTab(tabFluidics));
        admin_altar_wand = register(new AltarCreatingWand("admin_altar_wand").setCreativeTab(tabFluidics));
        debug_wand = register(new AltarStructureLoggingWand("debug_wand").setCreativeTab(tabFluidics));

        for(SpellBase base : SpellRegistry.spells()){
            spellWands.add(register(new SpellWand(base.getName()+"_wand",base).setCreativeTab(tabFluidics)));
        }

    }

    private static MFItem register(MFItem item){
        GameRegistry.register(item);
        item.registerItemModel();
        return item;
    }

    public static final CreativeTabs tabFluidics = new CreativeTabs(ManaFluidics.MODID) {
        @Override public Item getTabIconItem() {
            return manaCrystal;
        }
    };


}
