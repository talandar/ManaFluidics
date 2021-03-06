package derpatiel.manafluidics.block.castingchamber;

import derpatiel.manafluidics.ManaFluidics;
import derpatiel.manafluidics.block.IDismantleable;
import derpatiel.manafluidics.block.MFTileBlock;
import derpatiel.manafluidics.block.furnaceHeater.FurnaceHeaterTileEntity;
import derpatiel.manafluidics.registry.ModBlocks;
import derpatiel.manafluidics.registry.ModGUIs;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

public class CastingChamber extends MFTileBlock implements IDismantleable {

    public CastingChamber(String unlocalizedName, Material material, float hardness, float resistance) {
        super(unlocalizedName, material, hardness, resistance);
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new CastingChamberTileEntity();
    }

    @Override
    public ItemStack getDismantledStack(World world, BlockPos pos, IBlockState state) {
        return new ItemStack(ModBlocks.castingChamber);
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        TileEntity tileentity = worldIn.getTileEntity(pos);

        if (tileentity instanceof CastingChamberTileEntity){
            IItemHandler inventory = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,null);
            for(int slot=0;slot<inventory.getSlots();slot++) {
                ItemStack dropStack = inventory.getStackInSlot(slot);
                if (dropStack != null) {
                    InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), dropStack);
                }
            }
            worldIn.updateComparatorOutputLevel(pos, this);
        }

        super.breakBlock(worldIn, pos, state);
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

            if (tileentity instanceof CastingChamberTileEntity)
            {
                playerIn.openGui(ManaFluidics.instance, ModGUIs.CASTING_CHAMBER_ID,worldIn,pos.getX(),pos.getY(), pos.getZ());
            }

            return true;
        }
    }
}
