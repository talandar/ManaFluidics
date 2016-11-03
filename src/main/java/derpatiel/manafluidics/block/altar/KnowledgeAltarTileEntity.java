package derpatiel.manafluidics.block.altar;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class KnowledgeAltarTileEntity extends TileEntity implements ITickable {

    public int tickCount;
    public float pageFlip;
    public float pageFlipPrev;
    public float flipT;
    public float flipA;
    public float bookSpread;
    public float bookSpreadPrev;
    public float bookRotation;
    public float bookRotationPrev;
    public float tRot;

/*
    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(getPos(), 0, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        readFromNBT(pkt.getNbtCompound());
        final IBlockState state = getWorld().getBlockState(getPos());
        getWorld().notifyBlockUpdate(getPos(), state, state, 3);
    }
*/
    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
        return oldState.getBlock() != newSate.getBlock();
    }

    @Override
    public void update(){
    this.bookSpreadPrev = this.bookSpread;
        this.bookRotationPrev = this.bookRotation;
    EntityPlayer entityplayer = this.worldObj.getClosestPlayer((double)((float)this.pos.getX() + 0.5F), (double)((float)this.pos.getY() + 0.5F), (double)((float)this.pos.getZ() + 0.5F), 3.0D, false);

        if (entityplayer != null)
    {
        double d0 = entityplayer.posX - (double)((float)this.pos.getX() + 0.5F);
        double d1 = entityplayer.posZ - (double)((float)this.pos.getZ() + 0.5F);
        this.tRot = (float) MathHelper.atan2(d1, d0);
        this.bookSpread += 0.1F;

        if (this.bookSpread < 0.5F || worldObj.rand.nextInt(40) == 0)
        {
            float f1 = this.flipT;

            while (true)
            {
                this.flipT += (float)(worldObj.rand.nextInt(4) - worldObj.rand.nextInt(4));

                if (f1 != this.flipT)
                {
                    break;
                }
            }
        }
    }
        else
    {
        this.tRot += 0.02F;
        this.bookSpread -= 0.1F;
    }

        while (this.bookRotation >= (float)Math.PI)
    {
        this.bookRotation -= ((float)Math.PI * 2F);
    }

        while (this.bookRotation < -(float)Math.PI)
    {
        this.bookRotation += ((float)Math.PI * 2F);
    }

        while (this.tRot >= (float)Math.PI)
    {
        this.tRot -= ((float)Math.PI * 2F);
    }

        while (this.tRot < -(float)Math.PI)
    {
        this.tRot += ((float)Math.PI * 2F);
    }

    float f2;

        for (f2 = this.tRot - this.bookRotation; f2 >= (float)Math.PI; f2 -= ((float)Math.PI * 2F))
    {
        ;
    }

        while (f2 < -(float)Math.PI)
    {
        f2 += ((float)Math.PI * 2F);
    }

        this.bookRotation += f2 * 0.4F;
        this.bookSpread = MathHelper.clamp_float(this.bookSpread, 0.0F, 1.0F);
        ++this.tickCount;
        this.pageFlipPrev = this.pageFlip;
    float f = (this.flipT - this.pageFlip) * 0.4F;
    float f3 = 0.2F;
    f = MathHelper.clamp_float(f, -0.2F, 0.2F);
        this.flipA += (f - this.flipA) * 0.9F;
        this.pageFlip += this.flipA;
}

}
