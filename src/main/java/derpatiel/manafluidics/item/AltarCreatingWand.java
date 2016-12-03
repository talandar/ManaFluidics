package derpatiel.manafluidics.item;

import derpatiel.manafluidics.block.altar.KnowledgeAltarTileEntity;
import derpatiel.manafluidics.block.altar.construction.AltarConstructionData;
import derpatiel.manafluidics.enums.AltarType;
import derpatiel.manafluidics.registry.ModBlocks;
import derpatiel.manafluidics.util.ChatUtil;
import derpatiel.manafluidics.util.LOG;
import derpatiel.manafluidics.util.MaterialItemHelper;
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

        if(!worldIn.isRemote) {
            if (worldIn.getBlockState(pos).getBlock() == ModBlocks.knowledgeAltar) {
                craftAltar(worldIn, playerIn, pos);
            } else {
                ChatUtil.sendChat(playerIn, "Not an altar!");
            }
        }
        return EnumActionResult.SUCCESS;
    }

    private void craftAltar(World world, EntityPlayer player, BlockPos pos){
        KnowledgeAltarTileEntity tile = (KnowledgeAltarTileEntity)world.getTileEntity(pos);
        AltarType type = tile.type;
        if(type!=null) {
            int validLevel = tile.getAltarValidLevel(world);
            LOG.info("currently of type " + type.name() + ", level " + validLevel);
            AltarConstructionData data = AltarConstructionData.dataMap.get(type);
            AltarConstructionData.AltarLevelData levelData = data.getDataForAltarLevel(validLevel+1);
            if(levelData!=null){
                for(BlockPos offset : levelData.structureBlocks){
                    world.setBlockState(pos.add(offset.getX(),offset.getY(),offset.getZ()),Blocks.STONEBRICK.getDefaultState(),3);
                }
                for(BlockPos offset : levelData.activeBlocks){
                    IBlockState setTo = MaterialItemHelper.getAlloyBlockForLevel(validLevel+1).getDefaultState();
                    world.setBlockState(pos.add(offset.getX(),offset.getY(),offset.getZ()),setTo,3);
                }
            }
        }else{
            ChatUtil.sendChat(player,"No type set. Use Altar GUI to set type first.");
        }
    }
}
