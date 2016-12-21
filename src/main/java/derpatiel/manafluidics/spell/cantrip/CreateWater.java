package derpatiel.manafluidics.spell.cantrip;

import derpatiel.manafluidics.spell.BlockTargetedSpell;
import derpatiel.manafluidics.spell.SpellAttribute;
import derpatiel.manafluidics.spell.parameters.SpellParameterChoices;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class CreateWater extends BlockTargetedSpell {
    public CreateWater() {
        super("createwater", 0, 1, SpellAttribute.CONJURATION,SpellAttribute.WATER);
    }

    @Override
    public boolean doCastOnHit(BlockPos hitBlock, EnumFacing hitFace, World world, EntityPlayer player, boolean boosted, List<SpellParameterChoices> parameters) {
        BlockPos adjBlockPos = hitBlock.offset(hitFace);
        IBlockState state = world.getBlockState(adjBlockPos);
        if (state.getMaterial() == Material.AIR) {
            world.setBlockState(adjBlockPos, Blocks.WATER.getDefaultState(), 3);
            return true;
        }
        return false;
    }

}
