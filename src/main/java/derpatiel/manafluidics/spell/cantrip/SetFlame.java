package derpatiel.manafluidics.spell.cantrip;

import derpatiel.manafluidics.registry.ModFluids;
import derpatiel.manafluidics.spell.BlockTargetedSpell;
import derpatiel.manafluidics.spell.SpellAttribute;
import derpatiel.manafluidics.spell.SpellParameterChoices;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class SetFlame extends BlockTargetedSpell {
    public SetFlame() {
        super("setflame",0, 5, SpellAttribute.FIRE,SpellAttribute.EVOCATION);
    }

    @Override
    public boolean doCastOnHit(BlockPos hitBlock, EnumFacing hitFace, World world, EntityPlayer player, boolean boosted, List<SpellParameterChoices> parameters) {
        BlockPos adjBlockPos = hitBlock.offset(hitFace);
        IBlockState state = world.getBlockState(adjBlockPos);
        if (state.getMaterial() == Material.AIR) {
            world.playSound(player, adjBlockPos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, 0.4F + 0.8F);
            world.setBlockState(adjBlockPos, Blocks.FIRE.getDefaultState(), 11);
            return true;
        }
        return false;
    }

}
