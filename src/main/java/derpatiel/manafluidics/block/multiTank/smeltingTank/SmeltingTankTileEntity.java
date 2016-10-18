package derpatiel.manafluidics.block.multiTank.smeltingTank;

import derpatiel.manafluidics.block.multiTank.TankFormingTileEntity;
import derpatiel.manafluidics.fluid.MultiTank;
import derpatiel.manafluidics.util.LOG;
import derpatiel.manafluidics.util.MaterialItemHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.items.CapabilityItemHandler;

public class SmeltingTankTileEntity extends TankFormingTileEntity {

    public static final float HEAT_FALLOFF_VALUE = 0.7f;
    public static final int MAX_SLOTS = 81;

    public final MultiTank tank;
    public final SmeltingItemHandler itemHandler;

    private int heatThisTick=0;
    private int heatersThisTick =0;

    public SmeltingTankTileEntity(){
        tank = new MultiTank(this);
        itemHandler = new SmeltingItemHandler(this);
    }

    @Override
    public void doUpdate() {

        int heatConsumed = heatThisTick + (int)(0.1f * (heatersThisTick-1) * heatThisTick);
        heatThisTick=0;
        heatersThisTick =0;

        //TODO: grab items in tank space

        int slot=0;
        while(heatConsumed>0 && slot<itemHandler.getSlots()){
            SmeltingItemHandler.MeltProgress slotProgress = itemHandler.getMeltProgressInSlot(slot);
            if(slotProgress!=null){
                slotProgress.addHeat(heatConsumed);
                heatConsumed = (int)(heatConsumed * HEAT_FALLOFF_VALUE);
                if(slotProgress.isMelted()){
                    FluidStack stack = MaterialItemHelper.getMeltOutput(itemHandler.getStackInSlot(slot));
                    itemHandler.setStackInSlot(slot,null);
                    //if fluid gets lost, oh well!
                    tank.fill(stack,true);
                }
            }
            slot++;
        }
    }

    @Override
    public void notifyFormed() {
        setCapacityBySize();
    }

    public void setCapacityBySize(){
        tank.setCapacity(getNumInteriorBlocks() * TANK_CAPACITY_PER_BLOCK);
        //max inventory size=81 slots, due to gui constraints
        itemHandler.setSize(Math.min(getNumInteriorBlocks(),MAX_SLOTS));
    }

    @Override
    public void notifyUnformed() {
        tank.setCapacity(0);
        itemHandler.setSize(0);
    }

    @Override
    public boolean needsHeatInterface() {
        return true;
    }

    @Override
    public boolean needsIEMInterface() {
        return false;
    }

    @Override
    public boolean needsItemInterface() {
        return true;
    }

    @Override
    public void newHeight() {
        notifyFormed();
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("tank",tank.serializeNBT());
        compound.setTag("inventory",itemHandler.serializeNBT());
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        setCapacityBySize();
        tank.deserializeNBT(compound.getCompoundTag("tank"));
        itemHandler.deserializeNBT(compound.getCompoundTag("inventory"));
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if((isValidConnectionDirection(facing) || facing==null) && capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || capability== CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return true;
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if((isValidConnectionDirection(facing) || facing==null) && capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return (T) tank;
        }else if((isValidConnectionDirection(facing) || facing==null) && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
            return (T)itemHandler;
        }
        return super.getCapability(capability, facing);
    }

    public void addHeat(int heat){
        //each additional furnace is more efficient
        if(heat>0) {
            heatThisTick += heat;
            heatersThisTick++;
        }
    }

    public void onTankChanged(){
        //TODO: mark for update, send inventory... ?
    }

    public static class PacketFluidClick implements IMessage
    {
        private BlockPos tileToUpdate;
        private int fluidIndexToMove;

        public PacketFluidClick(){};

        public PacketFluidClick(BlockPos tile, int fluid){
            this.tileToUpdate=tile;
            this.fluidIndexToMove=fluid;
        }

        @Override
        public void toBytes(ByteBuf buf)
        {
            buf.writeLong(tileToUpdate.toLong());
            buf.writeInt(fluidIndexToMove);
        }

        @Override
        public void fromBytes(ByteBuf buf)
        {
            this.tileToUpdate = BlockPos.fromLong(buf.readLong());
            this.fluidIndexToMove=buf.readInt();
        }

        public static class Handler implements IMessageHandler<SmeltingTankTileEntity.PacketFluidClick, IMessage> {
            @Override
            public IMessage onMessage(SmeltingTankTileEntity.PacketFluidClick message, MessageContext ctx) {
                TileEntity tile = ctx.getServerHandler().playerEntity.getServer().getEntityWorld().getTileEntity(message.tileToUpdate);
                //TileEntity tile = ctx.getServerHandler().playerEntity.getServerWorld().getTileEntity(message.tileToUpdate);
                if (tile == null){
                    LOG.warn("null tile :(");
                }else if(!(tile instanceof SmeltingTankTileEntity)){
                    LOG.warn("tile not of right type :(");
                }
                LOG.info("moving fluid");
                SmeltingTankTileEntity smeltingTank = (SmeltingTankTileEntity)tile;
                smeltingTank.tank.moveFluidToBottom(message.fluidIndexToMove);
                smeltingTank.markForUpdate();
                return null;
            }
        }
    }
}
