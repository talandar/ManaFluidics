package derpatiel.manafluidics.registry;

import derpatiel.manafluidics.ManaFluidics;
import derpatiel.manafluidics.item.MFItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemRegistry {

    public static MFItem manaCrystal;

    public static void registerItems() {

        manaCrystal = register(new MFItem("manaCrystal").setCreativeTab(tabFluidics));

    }

    private static MFItem register(MFItem item){
        GameRegistry.register(item);
        item.registerItemModel();
        return item;
    }

    public static final CreativeTabs tabFluidics = new CreativeTabs(ManaFluidics.MODID) {
        @Override public Item getTabIconItem() {
            return manaCrystal;//TODO: replace with mana crystal;
        }
    };


}
