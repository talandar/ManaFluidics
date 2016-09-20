package derpatiel.manafluidics.item;

import derpatiel.manafluidics.ManaFluidics;
import derpatiel.manafluidics.registry.ItemRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class MFItem extends Item {

    public String bareUnlocalizedName;

    public MFItem(String name) {
        this.bareUnlocalizedName = name;
        setUnlocalizedName(name);
        setRegistryName(name);
    }


    public String getResourceLocation(){
        return ManaFluidics.MODID+":"+bareUnlocalizedName;
    }

    public String getBareUnlocalizedName(){
        return bareUnlocalizedName;
    }

    public void registerItemModel(){
        ManaFluidics.proxy.registerItemRenderer(this,0,bareUnlocalizedName);
    }

    @Override
    public MFItem setCreativeTab(CreativeTabs tab){
        super.setCreativeTab(tab);
        return this;
    }
}
