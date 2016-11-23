package derpatiel.manafluidics.spell;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.List;

public abstract class BlockTargetedSpell extends SpellBase {

    public int spellRange = 5;

    public BlockTargetedSpell(String name, int level, int castingCost, SpellAttribute... attributes) {
        super(name, level, castingCost,attributes);
    }

    @Override
    public boolean doCast(World worldIn, EntityPlayer castingPlayer, boolean boosted, List<SpellParameterChoices> parameters){
        boolean flag = false;
            RayTraceResult result = castingPlayer.rayTrace(spellRange, 1.0f);
            if (result.typeOfHit == RayTraceResult.Type.BLOCK) {
                BlockPos hitBlockPos = result.getBlockPos();
                EnumFacing hitFace = result.sideHit;
                flag = doCastOnHit(hitBlockPos,hitFace,worldIn,castingPlayer,boosted,parameters);
            }
        return flag;
    }

    public abstract boolean doCastOnHit(BlockPos hitBlock, EnumFacing hitFace, World world, EntityPlayer player, boolean boosted, List<SpellParameterChoices> parameters);
}
