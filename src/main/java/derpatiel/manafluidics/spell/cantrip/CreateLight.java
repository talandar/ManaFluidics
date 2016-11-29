package derpatiel.manafluidics.spell.cantrip;

import derpatiel.manafluidics.registry.ModBlocks;
import derpatiel.manafluidics.registry.ModFluids;
import derpatiel.manafluidics.spell.BlockTargetedSpell;
import derpatiel.manafluidics.spell.SpellAttribute;
import derpatiel.manafluidics.spell.SpellParameterChoices;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class CreateLight extends BlockTargetedSpell {
    public CreateLight(){
        super("createlight", 0, 1, SpellAttribute.CONJURATION,SpellAttribute.AIR,SpellAttribute.FIRE);
    }

    @Override
    public boolean doCastOnHit(BlockPos hitBlock, EnumFacing hitFace, World world, EntityPlayer player, boolean boosted, List<SpellParameterChoices> parameters) {
        BlockPos adjBlockPos = hitBlock.offset(hitFace);
        IBlockState state = world.getBlockState(adjBlockPos);
        if (state.getMaterial() == Material.AIR) {
            world.setBlockState(adjBlockPos, ModBlocks.summonedLight.getDefaultState(), 3);
            return true;
        }
        return false;
    }
}
