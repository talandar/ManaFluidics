package derpatiel.manafluidics.item.spell;

import derpatiel.manafluidics.item.MFItem;
import derpatiel.manafluidics.spell.SpellBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import java.util.List;

public class SingleSpellWand extends MFItem {

    private SpellBase spell;

    public SingleSpellWand(String name, SpellBase spell) {
        super(name);
        this.setMaxDamage(16);
        this.spell=spell;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer player, EnumHand hand) {
        if(!worldIn.isRemote) {
            boolean successfulCast = spell.cast(worldIn,player,itemStackIn);
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
