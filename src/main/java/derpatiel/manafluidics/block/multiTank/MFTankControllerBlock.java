package derpatiel.manafluidics.block.multiTank;

import derpatiel.manafluidics.block.ITankPart;
import derpatiel.manafluidics.enums.TankPartState;
import derpatiel.manafluidics.registry.ModItems;
import derpatiel.manafluidics.util.LOG;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidTankInfo;

import javax.annotation.Nullable;

public abstract class MFTankControllerBlock<T extends TankFormingTileEntity> extends MFTankEntityBlock implements ITankPart {

    public MFTankControllerBlock(String unlocalizedName, Material material, float hardness, float resistance) {
        super(unlocalizedName, material, hardness, resistance);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer entityPlayer, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ){
        LOG.info("click on side "+side.getName());

        if(heldItem!=null && heldItem.getItem()== ModItems.crystal_hammer){
            if(state.getValue(STATE).equals(TankPartState.UNFORMED)){
                if(!world.isRemote){
                    TankFormingTileEntity te = (TankFormingTileEntity) world.getTileEntity(pos);
                    te.tryFormTank();
                    if(!te.isFormed()){
                        LOG.info("did not form: "+te.getUnformedReason());
                    }else{
                        te.displayParticles(EnumParticleTypes.REDSTONE);
                    }
                }
            }else{
                //already formed
                TankFormingTileEntity te = (TankFormingTileEntity) world.getTileEntity(pos);
                if(!world.isRemote){
                    te.checkNewBounds();
                }
                te.displayParticles(EnumParticleTypes.REDSTONE);
                /*
                FluidTankInfo info = te.getTankInfo(side)[0];
                if(info.fluid!=null){
                    LOG.info(info.fluid.amount+"mB of "+info.fluid.getLocalizedName());
                }else{
                    LOG.info("empty");
                }
                */
            }

        }
        return super.onBlockActivated(world,pos,state,entityPlayer,hand,heldItem,side,hitX,hitY,hitZ);
    }
}
