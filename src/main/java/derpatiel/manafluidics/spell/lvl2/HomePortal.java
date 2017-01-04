package derpatiel.manafluidics.spell.lvl2;

import derpatiel.manafluidics.spell.SpellAttribute;
import derpatiel.manafluidics.spell.SpellBase;
import derpatiel.manafluidics.spell.parameters.SpellParameterChoices;
import derpatiel.manafluidics.util.ChatUtil;
import derpatiel.manafluidics.util.TextHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Collection;

public class HomePortal extends SpellBase {
    public HomePortal() {
        super("homeportal", 2, 150, SpellAttribute.TELEPORTATION);
    }

    @Override
    public boolean doCast(World worldIn, EntityPlayer castingPlayer, boolean boosted, Collection<SpellParameterChoices> parameters) {
        BlockPos pos = castingPlayer.getBedLocation();
        if(pos!=null) {
            IBlockState bed = worldIn.getBlockState(pos);
            if(bed.getBlock()== Blocks.BED) {
                castingPlayer.setPositionAndUpdate(pos.getX() + 0.5f, pos.getY() + 0.75f, pos.getZ() + 0.5f);
            }else{
                ChatUtil.sendChat(castingPlayer, TextHelper.localize("spell.homeportal.message.nobed"));
            }
            return true;
        }
        return false;
    }
}
