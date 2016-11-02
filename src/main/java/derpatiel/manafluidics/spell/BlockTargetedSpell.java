package derpatiel.manafluidics.spell;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public abstract class BlockTargetedSpell extends SpellBase {

    public int spellRange = 5;

    public BlockTargetedSpell(String name, int level, int castingCost) {
        super(name, level, castingCost);
    }

    public boolean doCast(World worldIn, EntityPlayer castingPlayer){
        boolean flag = false;
        if(!worldIn.isRemote) {
            RayTraceResult result = castingPlayer.rayTrace(spellRange, 1.0f);
            if (result.typeOfHit == RayTraceResult.Type.BLOCK) {
                BlockPos hitBlockPos = result.getBlockPos();
                EnumFacing hitFace = result.sideHit;
                flag = doCastOnHit(hitBlockPos,hitFace,worldIn,castingPlayer);
            }
        }
        return flag;
    }

    public abstract boolean doCastOnHit(BlockPos hitBlock, EnumFacing hitFace, World world, EntityPlayer player);
}
