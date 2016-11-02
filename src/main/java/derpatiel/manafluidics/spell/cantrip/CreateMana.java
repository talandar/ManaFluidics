package derpatiel.manafluidics.spell.cantrip;

import derpatiel.manafluidics.registry.ModFluids;
import derpatiel.manafluidics.spell.BlockTargetedSpell;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CreateMana extends BlockTargetedSpell {
    public CreateMana() {
        super("Create Mana",0, 5);
    }

    @Override
    public boolean doCastOnHit(BlockPos hitBlock, EnumFacing hitFace, World world, EntityPlayer player) {
        BlockPos adjBlockPos = hitBlock.offset(hitFace);
        IBlockState state = world.getBlockState(adjBlockPos);
        if (state.getMaterial() == Material.AIR) {
            world.setBlockState(adjBlockPos, ModFluids.reactiveManaBlockFluid.getDefaultState(), 3);
            return true;
        }
        return false;
    }

}
