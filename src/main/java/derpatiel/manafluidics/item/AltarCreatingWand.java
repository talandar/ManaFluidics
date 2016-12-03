package derpatiel.manafluidics.item;

import derpatiel.manafluidics.block.altar.construction.AltarConstructionData;
import derpatiel.manafluidics.enums.AltarType;
import derpatiel.manafluidics.registry.ModBlocks;
import derpatiel.manafluidics.util.ChatUtil;
import derpatiel.manafluidics.util.LOG;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.lwjgl.openal.AL;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Jim on 11/24/2016.
 */
public class AltarCreatingWand extends MFItem {
    public AltarCreatingWand(String name) {
        super(name);
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        if(worldIn.getBlockState(pos).getBlock() == ModBlocks.knowledgeAltar){

                LOG.info("CANNOT CRAFT YET!");
                //craftAltar(stack,pos);
        }else{
            ChatUtil.sendChat(playerIn,"Not an altar!");
        }
        return EnumActionResult.SUCCESS;
    }
}
