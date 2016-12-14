package derpatiel.manafluidics.item.spell;

import com.google.common.collect.Lists;
import derpatiel.manafluidics.block.portableTank.PortableTankTileEntity;
import derpatiel.manafluidics.item.MFItem;
import derpatiel.manafluidics.spell.SpellBase;
import derpatiel.manafluidics.spell.SpellParameterChoices;
import derpatiel.manafluidics.spell.SpellParameters;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;

import java.util.List;

public class SpellWand extends MFItem {

    private SpellBase spell;

    public SpellWand(String name, SpellBase spell) {
        super(name);
        this.setMaxDamage(16);
        this.spell=spell;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer player, EnumHand hand) {
        if(!worldIn.isRemote) {
            boolean successfulCast = spell.cast(worldIn,player,true, Lists.newArrayList(new SpellParameterChoices(SpellParameters.entitytargeting,"spell.entitytarget.mob")));
            if(successfulCast){
                if(itemStackIn.attemptDamageItem(1, worldIn.rand)) {
                    itemStackIn.stackSize=0;
                }
                return ActionResult.newResult(EnumActionResult.SUCCESS,itemStackIn);
            }
        }
        return super.onItemRightClick(itemStackIn, worldIn, player, hand);
    }

    @Override
    public void addInformation(ItemStack itemstack, EntityPlayer player, List<String> list, boolean par4) {
        super.addInformation(itemstack, player, list, par4);
        list.addAll(spell.getDescriptionLines());
    }
}
