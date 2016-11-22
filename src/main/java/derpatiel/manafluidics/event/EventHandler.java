package derpatiel.manafluidics.event;

import derpatiel.manafluidics.block.ITankPart;
import derpatiel.manafluidics.block.floatTable.FloatTableTileEntity;
import derpatiel.manafluidics.command.MFCommand;
import derpatiel.manafluidics.enums.KnowledgeCategory;
import derpatiel.manafluidics.multiblock.MultiblockHandler;
import derpatiel.manafluidics.player.MFPlayerKnowledge;
import derpatiel.manafluidics.player.PlayerKnowledgeHandler;
import derpatiel.manafluidics.registry.ModBlocks;
import derpatiel.manafluidics.util.ChatUtil;
import derpatiel.manafluidics.util.LOG;
import derpatiel.manafluidics.util.TextHelper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.NoteBlockEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nullable;
import java.io.File;
import java.util.List;
import java.util.function.Predicate;

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

    @SubscribeEvent
    public void onPlayerEnter(PlayerEvent.LoadFromFile loadFromFile){
        PlayerKnowledgeHandler.onPlayerLoad(loadFromFile.getEntityPlayer());
    }

    @SubscribeEvent
    public void onPlayerLeave(PlayerEvent.SaveToFile saveToFile){
        PlayerKnowledgeHandler.onPlayerSave(saveToFile.getEntityPlayer());
    }

    @SubscribeEvent
    public void onPlayerCraft(net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent event){
        if(!event.player.worldObj.isRemote) {
            if(event.crafting.getItem() instanceof ItemBlock && ((ItemBlock)event.crafting.getItem()).getBlock()==ModBlocks.knowledgeAltar){
                MFPlayerKnowledge knowledge = PlayerKnowledgeHandler.getPlayerKnowledge(event.player);
                if(!knowledge.hasKnowledge(KnowledgeCategory.ALTAR_CRAFTED)){
                    ChatUtil.sendNoSpam(event.player, TextHelper.localize("altar.craftAltar.message"));
                }
                PlayerKnowledgeHandler.getPlayerKnowledge(event.player).addKnowledge(KnowledgeCategory.ALTAR_CRAFTED);
            }
        }
    }

    @SubscribeEvent
    public void onEntityDie(LivingDeathEvent event){
        if(!event.getEntityLiving().getEntityWorld().isRemote){
            Entity dyingEntity = event.getEntityLiving();
            DamageSource source = event.getSource();
            Entity sourceEntity = source.getSourceOfDamage();
            if(sourceEntity!=null && sourceEntity instanceof EntityPlayer) {
                EntityPlayer killingPlayer = (EntityPlayer)sourceEntity;
                MFPlayerKnowledge playerKnowledge = PlayerKnowledgeHandler.getPlayerKnowledge(killingPlayer);
                if(dyingEntity instanceof EntityCreeper){
                    playerKnowledge.addKnowledge(KnowledgeCategory.CREEPER_KILLED);
                }else if(dyingEntity instanceof EntityWither){
                    playerKnowledge.addKnowledge(KnowledgeCategory.WITHER_KILLED);
                }else if(dyingEntity instanceof EntityDragon){
                    dyingEntity.getEntityWorld().getPlayers(EntityPlayer.class,
                            input -> input.dimension==1 && input.getPositionVector().distanceTo(dyingEntity.getPositionVector())<100)
                            .forEach(p-> PlayerKnowledgeHandler.getPlayerKnowledge(p).addKnowledge(KnowledgeCategory.DRAGON_KILLED));
                }
            }
        }

    }
}
