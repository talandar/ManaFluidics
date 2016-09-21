package derpatiel.manafluidics.block;

import derpatiel.manafluidics.ManaFluidics;
import derpatiel.manafluidics.registry.ModItems;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class MFBlockFluid extends BlockFluidClassic {

    private String bareUnlocalizedName;

    public MFBlockFluid(Fluid fluid, Material material, String name, int quantaPerBlock) {
        super(fluid, material);
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.bareUnlocalizedName = name;
        this.setQuantaPerBlock(quantaPerBlock);
        this.setCreativeTab(ModItems.tabFluidics);
    }

    public void registerItemModel(ItemBlock itemBlock){
        ManaFluidics.proxy.registerItemRenderer(itemBlock,0,bareUnlocalizedName);
    }

    public String getBareUnlocalizedName(){
        return bareUnlocalizedName;
    }

}
