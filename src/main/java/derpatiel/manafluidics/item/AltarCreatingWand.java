package derpatiel.manafluidics.item;

import derpatiel.manafluidics.ManaFluidics;
import derpatiel.manafluidics.enums.AltarType;
import derpatiel.manafluidics.registry.ModBlocks;
import derpatiel.manafluidics.util.ChatUtil;
import derpatiel.manafluidics.util.LOG;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.*;

/**
 * Created by Jim on 11/24/2016.
 */
public class AltarCreatingWand extends MFItem {
    public AltarCreatingWand(String name) {
        super(name);
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        int offsetStart = -32;
        int offsetEnd = 33;

        Set<BlockPos> normalBlocks = new HashSet<>();
        Set<BlockPos> magicblocks = new HashSet<>();
        BlockPos altarBlock;

        if(worldIn.getBlockState(pos).getBlock() == ModBlocks.knowledgeAltar){
            altarBlock = pos;
            for(int xoff=offsetStart;xoff<offsetEnd;xoff++) {
                for(int yoff=offsetStart;yoff<offsetEnd;yoff++) {
                    for(int zoff=offsetStart;zoff<offsetEnd;zoff++) {
                        BlockPos offset = new BlockPos(xoff,yoff,zoff);
                        BlockPos test = altarBlock.add(xoff,yoff,zoff);
                        IBlockState state = worldIn.getBlockState(test);
                        if (state.getMaterial() != Material.AIR) {
                            if (state.getBlock() == Blocks.DIAMOND_BLOCK) {
                                magicblocks.add(offset);
                            } else if(state.getBlock()==Blocks.STONEBRICK){
                                normalBlocks.add(offset);
                            }
                        }
                    }
                }
            }
            ChatUtil.sendChat(playerIn,"found "+normalBlocks.size()+" normal blocks, "+magicblocks.size()+" magic blocks");
            StringBuilder builder = new StringBuilder("Altar Data");
            String nl = "\r\n";
            builder.append(nl);
            builder.append("structureBlocks = new ArrayList<>();");
            builder.append(nl);
            builder.append("magicBlocks = new ArrayList<>();");
            builder.append(nl);
            for(BlockPos offset : magicblocks){
                builder.append("magicBlocks.add(new BlockPos("+offset.getX()+","+offset.getY()+","+offset.getZ()+"));");
                builder.append(nl);
            }
            for(BlockPos offset : normalBlocks){
                builder.append("structureBlocks.add(new BlockPos("+offset.getX()+","+offset.getY()+","+offset.getZ()+"));");
                builder.append(nl);
            }
            builder.append("levelData = new AltarLevelData(structureBlocks,magicBlocks);");


            LOG.info(builder.toString());
        }else{
            ChatUtil.sendChat(playerIn,"Not an altar!");
        }




        return super.onItemUse(stack, playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }
}
