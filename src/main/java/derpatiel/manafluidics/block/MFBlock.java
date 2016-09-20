package derpatiel.manafluidics.block;

import derpatiel.manafluidics.ManaFluidics;
import derpatiel.manafluidics.item.MFItem;
import derpatiel.manafluidics.registry.ItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;

public class MFBlock extends Block{

    private String bareUnlocalizedName;

    public MFBlock(String unlocalizedName, Material material, float hardness, float resistance){
        super(material);
        this.bareUnlocalizedName = unlocalizedName;
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(unlocalizedName);
        this.setCreativeTab(ItemRegistry.tabFluidics);
        this.setHardness(hardness);
        this.setResistance(resistance);
    }

    public void registerItemModel(ItemBlock itemBlock){
        ManaFluidics.proxy.registerItemRenderer(itemBlock,0,bareUnlocalizedName);
    }

    public String getResourceLocation(){
        return ManaFluidics.MODID+":"+bareUnlocalizedName;
    }

    public String getBareUnlocalizedName(){
        return bareUnlocalizedName;
    }


    @Override
    public MFBlock setCreativeTab(CreativeTabs tab){
        super.setCreativeTab(tab);
        return this;
    }

}
