package derpatiel.manafluidics.item;

import derpatiel.manafluidics.ManaFluidics;
import derpatiel.manafluidics.block.IDismantleable;
import derpatiel.manafluidics.block.IRotateable;
import derpatiel.manafluidics.block.floatTable.FloatTable;
import derpatiel.manafluidics.block.floatTable.FloatTableTileEntity;
import derpatiel.manafluidics.event.EventHandler;
import derpatiel.manafluidics.registry.ModBlocks;
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

import java.util.ArrayList;
import java.util.List;

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

            BlockEvent.BreakEvent event = new BlockEvent.BreakEvent(worldIn, pos, worldIn.getBlockState(pos), playerIn);
            EventHandler.eventHandler.onBlockBreak(event);

            worldIn.destroyBlock(pos, false);
        }

        if(!playerIn.isSneaking() && block instanceof IRotateable){
            ((IRotateable) block).rotate(worldIn,worldIn.getBlockState(pos),pos);
        }

        if(playerIn.isSneaking() && block == Blocks.CRAFTING_TABLE) {
            worldIn.destroyBlock(pos, false);
            worldIn.setBlockState(pos, ModBlocks.runecraftingTable.getDefaultState());
        }
        if(!playerIn.isSneaking() && block == Blocks.CAULDRON) {
            //determine if this is the right shape for a floating table,
            //if it is, turn the cauldrons into the table
            boolean north, south, east, west = false;

            List<BlockPos> cauldrons = new ArrayList<>();
            north = worldIn.getBlockState(pos.north()).getBlock() == Blocks.CAULDRON;
            south = worldIn.getBlockState(pos.south()).getBlock() == Blocks.CAULDRON;
            east = worldIn.getBlockState(pos.east()).getBlock() == Blocks.CAULDRON;
            west = worldIn.getBlockState(pos.west()).getBlock() == Blocks.CAULDRON;

            if (north)
                cauldrons.add(pos.north());
            if (east)
                cauldrons.add(pos.east());
            if (south)
                cauldrons.add(pos.south());
            if (west)
                cauldrons.add(pos.west());

            if (cauldrons.size() == 2) {
                BlockPos last = cauldrons.get(1).add((cauldrons.get(0).subtract(pos)));
                if (worldIn.getBlockState(last).getBlock() == Blocks.CAULDRON) {
                    cauldrons.add(last);
                    cauldrons.add(pos);
                }

                for (BlockPos c : cauldrons) {
                    worldIn.destroyBlock(c, false);
                    worldIn.setBlockState(c, ModBlocks.floatTable.getDefaultState().withProperty(FloatTable.MAIN, (c == pos)));
                }
                for (BlockPos c : cauldrons) {
                    ((FloatTableTileEntity) worldIn.getTileEntity(c)).setOthers(cauldrons);
                    ((FloatTableTileEntity) worldIn.getTileEntity(c)).setMainBlock(pos);
                }
            }
        }

        return super.onItemUse(stack, playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }
}
