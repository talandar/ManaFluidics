package derpatiel.manafluidics.multiblock;

import derpatiel.manafluidics.util.NBTHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public abstract class MultiblockData {
    //needs to be extended by each specific multiblock so it knows how to break
    protected List<BlockPos> positions;
    protected BlockPos masterPos;


    public MultiblockData(BlockPos masterPos, List<BlockPos> positions){
        this.masterPos=masterPos;
        this.positions=positions;
    }

    public List<BlockPos> getBlockPositions(){
        return positions;
    }

    public BlockPos getMasterPos(){
        return masterPos;
    }

    public NBTTagCompound writeToNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("type", getType());
        tag.setLong("master", getMasterPos().toLong());
        NBTTagList list = NBTHelper.BlockPosArrayToTagList(positions);
        tag.setTag("list", list);
        return tag;
    }

    public abstract String getType();

    public abstract boolean blockBroken(World world, BlockPos pos);

    public static MultiblockData construct(NBTTagCompound tag) {
        String type = tag.getString("type");
        BlockPos master = BlockPos.fromLong(tag.getLong("master"));
        NBTTagList list = tag.getTagList("list", 4);
        List<BlockPos> positions = NBTHelper.TagListToBlockPosArray(list);

        MultiblockData data=null;
        switch(type){
            case FluidTankData.TYPE:
                data = new FluidTankData(master,positions);
                break;
        }
        return data;
    }

}