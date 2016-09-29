package derpatiel.manafluidics.multiblock;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultiblockHandler {

    private static Map<Integer, DimensionMultiblockData> dimensionData = new HashMap<Integer,DimensionMultiblockData>();

    public static void registerMultiblock(World world, MultiblockData data){
        dimensionData.get(world.provider.getDimension()).registerMultiblock(data);
    }

    /*
    public static void registerPipe(World world, BlockPos position){
        dimensionData.get(world.provider.getDimension()).registerPipe(world,position);
    }
    */

    public static void blockDestroyed(World world, BlockPos pos){
        dimensionData.get(world.provider.getDimension()).blockDestroyed(world,pos);
    }

    public static void updateMultiblockData(World world, MultiblockData data){
        dimensionData.get(world.provider.getDimension()).updateMultiblock(data);
    }

    public static void initWorld(World world) {
        DimensionMultiblockData data = (DimensionMultiblockData) world.getPerWorldStorage().getOrLoadData(DimensionMultiblockData.class, DimensionMultiblockData.DATA_IDENTIFIER);
        if(data==null){
            data = new DimensionMultiblockData(DimensionMultiblockData.DATA_IDENTIFIER);
            world.getPerWorldStorage().setData(DimensionMultiblockData.DATA_IDENTIFIER, data);
        }
        dimensionData.put(world.provider.getDimension(), data);
    }

    public static List<BlockPos> getTankLocationInfo(int dimensionId, BlockPos pos) {
        return dimensionData.get(dimensionId).getTankLocations(pos);
    }

    /*
    public static boolean arePipesInSameNetwork(World worldObj, BlockPos pos, BlockPos other) {
        return dimensionData.get(worldObj.provider.getDimension()).arePipesInSameNetwork(pos,other);
    }
    */
}
