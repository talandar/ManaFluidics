package derpatiel.manafluidics.spell.lvl1;

import derpatiel.manafluidics.spell.SpellAttribute;
import derpatiel.manafluidics.spell.SpellBase;
import derpatiel.manafluidics.spell.parameters.SpellParameterChoices;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import java.util.Collection;

public class EnderBag extends SpellBase {
    public EnderBag() {
        super("enderbag", 1, 15, SpellAttribute.CONJURATION,SpellAttribute.TELEPORTATION);
    }

    @Override
    public boolean doCast(World worldIn, EntityPlayer castingPlayer, boolean boosted, Collection<SpellParameterChoices> parameters) {
        castingPlayer.displayGUIChest(castingPlayer.getInventoryEnderChest());
        return true;
    }
}
