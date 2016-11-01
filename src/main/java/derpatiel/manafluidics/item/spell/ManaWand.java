package derpatiel.manafluidics.item.spell;

import derpatiel.manafluidics.item.MFItem;
import derpatiel.manafluidics.registry.ModFluids;
import derpatiel.manafluidics.util.LOG;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class ManaWand extends MFItem {
    public ManaWand(String name) {
        super(name);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer player, EnumHand hand) {
        RayTraceResult result = player.rayTrace(5,1.0f);
        BlockPos hitFace = result.getBlockPos().offset(result.sideHit);
        IBlockState state = worldIn.getBlockState(hitFace);
        if(state.getBlock()== Blocks.WATER){
            if(state.getValue(BlockStaticLiquid.LEVEL)==0) {
                worldIn.setBlockState(hitFace, ModFluids.reactiveManaBlockFluid.getDefaultState(), 3);
                return ActionResult.newResult(EnumActionResult.SUCCESS,itemStackIn);
            }
        }
        return super.onItemRightClick(itemStackIn, worldIn, player, hand);
    }
}
