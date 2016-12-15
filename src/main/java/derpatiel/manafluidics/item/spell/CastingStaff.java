package derpatiel.manafluidics.item.spell;

import derpatiel.manafluidics.item.MFItem;
import derpatiel.manafluidics.player.MFPlayerKnowledge;
import derpatiel.manafluidics.player.PlayerKnowledgeHandler;
import derpatiel.manafluidics.spell.SpellBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class CastingStaff extends MFItem {
    public CastingStaff(String name) {
        super(name);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer player, EnumHand hand) {
        if(!worldIn.isRemote) {
            if(player.isSneaking()){
                //open spell selection/etc
            }else{
                //cast the set spell.
                SpellBase spell = PlayerKnowledgeHandler.getPlayerKnowledge(player).getSelectedSpell();
                if(spell!=null) {
                    spell.cast(worldIn, player, itemStackIn);
                }
            }
        }
        return super.onItemRightClick(itemStackIn, worldIn, player, hand);
    }
}
