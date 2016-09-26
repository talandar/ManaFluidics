package derpatiel.manafluidics.block.portableTank;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidRegistry;

import java.util.List;

public class PortableTankItemBlock extends ItemBlock {

    public PortableTankItemBlock(Block block) {
            super(block);
        }

    @Override
    public void addInformation(ItemStack itemstack, EntityPlayer player, List<String> list, boolean par4) {
        super.addInformation(itemstack, player, list, par4);
        NBTTagCompound compound = itemstack.getTagCompound();
        if (compound != null) {
            String fluidName = compound.getString("FluidName");
            if (fluidName != null && !fluidName.equals("")) {
                fluidName = FluidRegistry.getFluid(fluidName).getLocalizedName(null);
                int amt = compound.getInteger("Amount");
                list.add(fluidName);
                list.add(amt + "mB/" + PortableTankTileEntity.TANK_SIZE + "mB");
            }
        }
    }
}
