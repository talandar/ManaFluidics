package derpatiel.manafluidics.item.spell;

import derpatiel.manafluidics.item.MFItem;
import derpatiel.manafluidics.registry.ModFluids;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.Random;

public class ManaWand extends MFItem {
    public ManaWand(String name) {
        super(name);
        this.setMaxDamage(16);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer player, EnumHand hand) {
        RayTraceResult result = player.rayTrace(5,1.0f);
        BlockPos hitBlockPos = result.getBlockPos();
        IBlockState hitBlockState = worldIn.getBlockState(hitBlockPos);
        if(hitBlockState.getMaterial()==Material.AIR) {
            return doCast(hitBlockPos, itemStackIn, worldIn, player, hand);
        }

        BlockPos adjBlockPos = result.getBlockPos().offset(result.sideHit);
        IBlockState state = worldIn.getBlockState(adjBlockPos);
        if(state.getMaterial()==Material.AIR){
            return doCast(adjBlockPos,itemStackIn,worldIn,player,hand);
        }
        return super.onItemRightClick(itemStackIn, worldIn, player, hand);
    }

    public ActionResult<ItemStack> doCast(BlockPos blockToCast,ItemStack itemStackIn, World worldIn, EntityPlayer player, EnumHand hand){
        worldIn.setBlockState(blockToCast, ModFluids.reactiveManaBlockFluid.getDefaultState(), 3);
        if(itemStackIn.attemptDamageItem(1, worldIn.rand)) {
            itemStackIn.stackSize=0;
        }
        return ActionResult.newResult(EnumActionResult.SUCCESS,itemStackIn);
    }

    @Override
    public Item setMaxDamage(int maxDamageIn) {
        return super.setMaxDamage(maxDamageIn);
    }
}
