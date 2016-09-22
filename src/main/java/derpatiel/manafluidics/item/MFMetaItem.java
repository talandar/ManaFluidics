package derpatiel.manafluidics.item;

import derpatiel.manafluidics.ManaFluidics;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public abstract class MFMetaItem extends MFItem {

    public MFMetaItem(String unlocalizedName) {
        super(unlocalizedName);
        this.setHasSubtypes(true);
    }

    @Override
    public abstract String getUnlocalizedName(ItemStack stack);

    @Override
    public abstract void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems);

    @Override
    public abstract void registerItemModel();
}
