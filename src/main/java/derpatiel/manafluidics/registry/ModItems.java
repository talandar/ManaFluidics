package derpatiel.manafluidics.registry;

import derpatiel.manafluidics.ManaFluidics;
import derpatiel.manafluidics.enums.MaterialType;
import derpatiel.manafluidics.item.MFItem;
import derpatiel.manafluidics.item.MFMaterialItem;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModItems {

    public static MFItem manaCrystal;
    public static MFItem bucket_crystals;
    public static MFItem bucket_iron;
    public static MFItem bucket_gold;
    public static MFItem crystal_hammer;
    public static MFItem material_wire;

    public static void registerItems() {

        manaCrystal = register(new MFItem("manaCrystal").setCreativeTab(tabFluidics));
        bucket_crystals = register(new MFItem("bucket_crystals").setCreativeTab(tabFluidics));
        bucket_iron = register(new MFItem("bucket_iron").setCreativeTab(tabFluidics));
        bucket_gold = register(new MFItem("bucket_gold").setCreativeTab(tabFluidics));
        crystal_hammer = register(new MFItem("crystalHammer").setCreativeTab(tabFluidics));
        material_wire = register(new MFMaterialItem("wire").setCreativeTab(tabFluidics));

        //no longer needed?
        //ModelBakery.registerItemVariants(material_wire, MaterialType.buildVariantList("wire"));
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
