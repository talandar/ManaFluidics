package derpatiel.manafluidics.util;

import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class NBTHelper {
    public static int[] BlockPosToIntArray(BlockPos pos){
        return new int[]{pos.getX(),pos.getY(),pos.getZ()};
    }

    public static BlockPos IntArrayToBlockPos(int[] pos){
        return new BlockPos(pos[0],pos[1],pos[2]);
    }

    public static NBTTagList BlockPosArrayToTagList(List<BlockPos> positions){
        NBTTagList list = new NBTTagList();
        for(BlockPos pos : positions){
            NBTTagLong lng = new NBTTagLong(pos.toLong());
            list.appendTag(lng);
        }
        return list;
    }

    public static List<BlockPos> TagListToBlockPosArray(NBTTagList list){
        List<BlockPos> positions = new ArrayList<BlockPos>();
        int count = list.tagCount();
        for(int i=0;i<count;i++){
            NBTTagLong lng = (NBTTagLong) list.get(i);
            positions.add(BlockPos.fromLong(lng.getLong()));
        }
        return positions;
    }
}
