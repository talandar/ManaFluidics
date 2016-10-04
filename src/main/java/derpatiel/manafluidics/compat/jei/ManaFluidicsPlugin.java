package derpatiel.manafluidics.compat.jei;

import derpatiel.manafluidics.registry.ModBlocks;
import mezz.jei.api.BlankModPlugin;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

@JEIPlugin
public class ManaFluidicsPlugin extends BlankModPlugin{

    public static IJeiHelpers jeiHelper;

    @Override
    public void register(@Nonnull IModRegistry registry){
        jeiHelper = registry.getJeiHelpers();

        jeiHelper.getItemBlacklist().addItemToBlacklist(new ItemStack(ModBlocks.floatTable));

    }
}
