package derpatiel.manafluidics.event;

import derpatiel.manafluidics.block.ITankPart;
import derpatiel.manafluidics.block.floatTable.FloatTableTileEntity;
import derpatiel.manafluidics.multiblock.MultiblockHandler;
import derpatiel.manafluidics.registry.ModBlocks;
import derpatiel.manafluidics.util.LOG;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class EventHandler {

    public static final EventHandler eventHandler = new EventHandler();

    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event){
        //only called on the server side!
        Block broken = event.getState().getBlock();
        if(broken instanceof ITankPart){// || broken instanceof PipeBlock){//this might break a tank
            LOG.info("broke a tank part");
            MultiblockHandler.blockDestroyed(event.getWorld(), event.getPos());
        }else if(broken==ModBlocks.floatTable){//this definitely breaks a float table
            FloatTableTileEntity tile = (FloatTableTileEntity) event.getWorld().getTileEntity(event.getPos());
            if(tile!=null){
                List<BlockPos> others = tile.getOthers();
                for(BlockPos other : others){
                    IBlockState otherBlockState = event.getWorld().getBlockState(other);
                    if(otherBlockState.getBlock()== ModBlocks.floatTable){
                        event.getWorld().setBlockState(other, Blocks.CAULDRON.getDefaultState());
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load loadEvent){
        if(!loadEvent.getWorld().isRemote){//run on server only
            MultiblockHandler.initWorld(loadEvent.getWorld());
        }
    }
}
