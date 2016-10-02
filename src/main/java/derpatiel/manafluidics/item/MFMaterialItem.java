package derpatiel.manafluidics.item;

import derpatiel.manafluidics.ManaFluidics;
import derpatiel.manafluidics.enums.MaterialType;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class MFMaterialItem extends MFMetaItem {

    public MFMaterialItem(String unlocalizedName) {
        super(unlocalizedName);	}

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        MaterialType type =  MaterialType.getById(stack.getMetadata());
        return super.getUnlocalizedName()+"."+type.getName();
    }

    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
        for(MaterialType type : MaterialType.VALUES)
            subItems.add(new ItemStack(itemIn, 1, type.getID()));
    }

    @Override
    public void registerItemModel() {
        for(MaterialType type : MaterialType.VALUES) {
            ManaFluidics.proxy.registerItemRenderer(this, type.getID(), bareUnlocalizedName+"_"+type.getName());
        }
    }
}
