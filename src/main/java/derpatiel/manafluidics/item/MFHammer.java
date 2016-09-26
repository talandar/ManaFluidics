package derpatiel.manafluidics.item;

import derpatiel.manafluidics.block.IDismantleable;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;

public class MFHammer extends MFItem {

    public MFHammer(String unlocalizedName){
        super(unlocalizedName);
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        Block block = worldIn.getBlockState(pos).getBlock();
        //attempt dismantle
        if(playerIn.isSneaking() && (block instanceof IDismantleable || block.equals(Blocks.OBSIDIAN))){


            ItemStack dismantledStack = null;

            if(block instanceof IDismantleable){
                dismantledStack = ((IDismantleable)block).getDismantledStack(worldIn, pos, worldIn.getBlockState(pos));
            }else{
                //it's obsidian
                dismantledStack = new ItemStack(Blocks.OBSIDIAN);
            }

            boolean added = playerIn.inventory.addItemStackToInventory(dismantledStack);
            if(!added){
                EntityItem entityitem = new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), dismantledStack);
                if(!worldIn.isRemote){
                    worldIn.spawnEntityInWorld(entityitem);
                }
            }

            //call event handler on our mod - in case we broke something important.
            //normally this handler fires only on the server, but in this case, we can call it
            //on the client to make sure things sync correctly
            /* not sure if we still need this
            if(!worldIn.isRemote){
                BlockEvent.BreakEvent event = new BreakEvent(worldIn, pos, worldIn.getBlockState(pos), playerIn);
                ManaFluidics.eventHandler.onBlockBreak(event);
            }
            */
            worldIn.destroyBlock(pos, false);
        }
        return super.onItemUse(stack, playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }
}
