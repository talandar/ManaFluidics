package derpatiel.manafluidics.multiblock;

import derpatiel.manafluidics.util.NBTHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DimensionMultiblockData extends WorldSavedData {

    public static final String DATA_IDENTIFIER="ManaFluidicsMultiblockData";

    private final Map<BlockPos, MultiblockData> tankPositionMap = new HashMap<BlockPos, MultiblockData>();
    private final List<MultiblockData> tankDataList = new ArrayList<MultiblockData>();

    private final List<BlockPos> pipeLocations = new ArrayList<BlockPos>();

    //private final PipeData pipeData = new PipeData();

    public DimensionMultiblockData(String ignored){
        super(DATA_IDENTIFIER);
    }

    public void registerMultiblock(MultiblockData data){
        registerMultiblockWithoutUpdate(data);
        markDirty();
    }

    private void registerMultiblockWithoutUpdate(MultiblockData data){
        tankDataList.add(data);
        for(BlockPos pos : data.getBlockPositions()){
            tankPositionMap.put(pos, data);
        }
    }

    public void updateMultiblock(MultiblockData data) {
        for(BlockPos pos : data.getBlockPositions()){
            tankPositionMap.put(pos, data);
        }
    }

    public void blockDestroyed(World world, BlockPos pos) {
        boolean broken=false;
        if(tankPositionMap.containsKey(pos)){
            MultiblockData data = tankPositionMap.get(pos);
            broken = data.blockBroken(world,pos);
            if(broken){
                for(BlockPos remove : data.getBlockPositions()){
                    tankPositionMap.remove(remove);
                }
                tankDataList.remove(data);
                markDirty();
            }
        }
        /*
        if(pipeLocations.contains(pos)){
            pipeData.pipeDestroyed(world,pos);
            pipeLocations.remove(pos);
        }
        */
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        NBTTagList list = nbt.getTagList("tanks", 10);
        int count = list.tagCount();
        for(int i=0;i<count;i++){
            NBTTagCompound tag = list.getCompoundTagAt(i);
            MultiblockData data = MultiblockData.construct(tag);
            registerMultiblockWithoutUpdate(data);
        }

        //pipeData.readFromNBT(nbt.getCompoundTag("pipeData"));
        pipeLocations.clear();
        pipeLocations.addAll(NBTHelper.TagListToBlockPosArray(nbt.getTagList("pipeLocations", 4)));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        NBTTagList list = new NBTTagList();
        for(MultiblockData data : tankDataList){
            NBTTagCompound tag = data.writeToNBT();
            list.appendTag(tag);
        }
        nbt.setTag("tanks", list);

        //NBTTagCompound pipeCompound = pipeData.writeToNBT();
        //nbt.setTag("pipeData", pipeCompound);
        //NBTTagList pipeLocationTags = NBTHelper.BlockPosArrayToTagList(pipeLocations);
        //nbt.setTag("pipeLocations", pipeLocationTags);
        return nbt;
    }


    public List<BlockPos> getTankLocations(BlockPos masterPos){
        return tankPositionMap.get(masterPos).positions;
    }


    /*
    public void registerPipe(World world, BlockPos position) {
        pipeData.pipeAdded(world, position);
        pipeLocations.add(position);
    }

    public boolean arePipesInSameNetwork(BlockPos pos, BlockPos other) {
        //it's ok to use the == here because we really are asking about the same object
        return pipeData.getNetworkForLocation(pos)==pipeData.getNetworkForLocation(other);
    }
    */
}
