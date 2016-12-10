package derpatiel.manafluidics.block.runecraftingtable;

import derpatiel.manafluidics.ManaFluidics;
import derpatiel.manafluidics.block.IDismantleable;
import derpatiel.manafluidics.block.MFTileBlock;
import derpatiel.manafluidics.block.castingchamber.CastingChamberTileEntity;
import derpatiel.manafluidics.registry.ModBlocks;
import derpatiel.manafluidics.registry.ModGUIs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * Created by Jim on 12/5/2016.
 */
public class RunecraftingTable extends MFTileBlock implements IDismantleable {
    public RunecraftingTable(String unlocalizedName, Material material, float hardness, float resistance) {
        super(unlocalizedName, material, hardness, resistance);
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new RunecraftingTileEntity();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (worldIn.isRemote)
        {
            return true;
        }
        else
        {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof RunecraftingTileEntity)
            {
                playerIn.openGui(ManaFluidics.instance, ModGUIs.RUNECRAFTING_TABLE_ID,worldIn,pos.getX(),pos.getY(), pos.getZ());
            }

            return true;
        }
    }

    @Override
    public ItemStack getDismantledStack(World world, BlockPos pos, IBlockState state) {
        return new ItemStack(ModBlocks.runecraftingTable);
    }
}
