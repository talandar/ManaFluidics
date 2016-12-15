package derpatiel.manafluidics.spell.cantrip;

import derpatiel.manafluidics.registry.ModFluids;
import derpatiel.manafluidics.registry.ModItems;
import derpatiel.manafluidics.spell.BlockTargetedSpell;
import derpatiel.manafluidics.spell.SpellAttribute;
import derpatiel.manafluidics.spell.parameters.SpellParameterChoices;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class CreateMana extends BlockTargetedSpell {
    public CreateMana() {
        super("createmana",0, 5, SpellAttribute.CONJURATION);
    }

    @Override
    public ItemStack getConsumedComponent() {
        return new ItemStack(ModItems.manaCrystal,1);
    }

    @Override
    public boolean doCastOnHit(BlockPos hitBlock, EnumFacing hitFace, World world, EntityPlayer player, boolean boosted, List<SpellParameterChoices> parameters) {
        BlockPos adjBlockPos = hitBlock.offset(hitFace);
        IBlockState state = world.getBlockState(adjBlockPos);
        if (state.getMaterial() == Material.AIR) {
            world.setBlockState(adjBlockPos, ModFluids.reactiveManaBlockFluid.getDefaultState(), 3);
            world.playSound((double)adjBlockPos.getX() + 0.5D, (double)adjBlockPos.getY() + 0.5D, (double)adjBlockPos.getZ() + 0.5D, SoundEvents.BLOCK_BREWING_STAND_BREW, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
            world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, (double)adjBlockPos.getX() + 0.5D, (double)adjBlockPos.getY()+1, (double)adjBlockPos.getZ() + 0.5D, 0.0D, 0.05D, 0.0D, new int[0]);
            return true;
        }
        return false;
    }

}
