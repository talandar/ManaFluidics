package derpatiel.manafluidics.block.altar.construction;

import com.google.common.collect.Lists;
import derpatiel.manafluidics.enums.AltarType;
import derpatiel.manafluidics.util.LOG;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AltarConstructionData {

    public static Map<AltarType, AltarConstructionData> dataMap = new HashMap<>();
    public static AltarConstructionData zigguratData;
    public static AltarConstructionData hengeData;
    public static AltarConstructionData hourglassData;
    public static AltarConstructionData towerData;
    public static AltarConstructionData templeData;

    public AltarType type;
    public List<AltarLevelData> levels = new ArrayList<>();

    public static class AltarLevelData{
        public final List<BlockPos> structureBlocks;
        public final List<BlockPos> activeBlocks;

        public AltarLevelData(List<BlockPos> structureBlocks, List<BlockPos> activeBlocks){
            this.structureBlocks = structureBlocks;
            this.activeBlocks = activeBlocks;
        }
    }

    public AltarLevelData getDataForAltarLevel(int level){
        if(level==0){
            return new AltarLevelData(Lists.newArrayList(),Lists.newArrayList());
        }else if(level-1>=levels.size()) {
            return null;
        }else{
            return levels.get(level-1);
        }
    }


    public static void initAltarData(){

        LOG.info("READING ALTAR DATA");
        List<BlockPos> structureBlocks;
        List<BlockPos> magicBlocks;
        AltarLevelData levelData;

        zigguratData = readFile(AltarType.ZIGGURAT);
        hengeData=readFile(AltarType.HENGE);
        hourglassData = readFile(AltarType.HOURGLASS_FOCUS);
        towerData = readFile(AltarType.TOWER);
        templeData = readFile(AltarType.TEMPLE);

        dataMap.put(AltarType.ZIGGURAT,zigguratData);
        dataMap.put(AltarType.HOURGLASS_FOCUS,hourglassData);
        dataMap.put(AltarType.TOWER,towerData);
        dataMap.put(AltarType.HENGE,hengeData);
        dataMap.put(AltarType.TEMPLE,templeData);

        LOG.info("DONE READING ALTAR DATA");
    }

    private static AltarConstructionData readFile(AltarType type){
        AltarConstructionData data = new AltarConstructionData();
        data.type=type;

        String path = "assets/manafluidics/altars/"+type.name();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(AltarConstructionData.class.getClassLoader().getResourceAsStream(path)))){
            List<AltarLevelData> levelList = readLevels(reader);
            cleanupActiveBlocks(levelList);
            data.levels=levelList;
            if(!data.isRotationallySymmetrical()){
                data=null;
                LOG.error("Altar type "+type.name()+" was not symmetrical.  This is not allowed.");
            }
            LOG.info("Read altar type "+type.name()+".  Type has "+data.levels.size()+" levels.");
        }catch(IOException ioe){
            LOG.error("Couldn't load data for type "+type.name()+".  This Altar will not work!.  "+ioe.getMessage());
            data=null;
        }catch(NullPointerException npe){
            LOG.error("No file found for altar type "+type.name());
            data=null;
        }
        return data;
    }

    private static void cleanupActiveBlocks(List<AltarLevelData> levels){
        List<BlockPos> earlierLevelActiveBlocks = new ArrayList<>();
        for(AltarLevelData level : levels){
            for(BlockPos pos : earlierLevelActiveBlocks){
                level.activeBlocks.remove(pos);
            }
            for(BlockPos pos : level.activeBlocks){
                earlierLevelActiveBlocks.add(pos);
            }
        }

    }

    private boolean isRotationallySymmetrical() {
        boolean valid=true;
        for(AltarLevelData level : this.levels){
            if(valid){
                for(BlockPos testBlock : level.activeBlocks){
                    if(!hasMirrors(level.activeBlocks,testBlock)){
                        valid=false;
                    }
                }
                for(BlockPos testBlock : level.structureBlocks){
                    if(!hasMirrors(level.structureBlocks,testBlock)){
                        valid=false;
                    }
                }
            }
        }
        return valid;
    }

    private boolean hasMirrors(List<BlockPos> positions, BlockPos block){
        //assume input pos in list, that's where we should have gotten it
        BlockPos testPos = block;
        for(int i=0;i<3;i++){
            testPos = rotate90(testPos);
            boolean found=false;
            for(BlockPos listPos : positions){
                found |= listPos.equals(testPos);
            }
            if(!found)
                return false;
        }
        return true;
    }

    private BlockPos rotate90(BlockPos pos){
        return new BlockPos(pos.getZ()*-1,pos.getY(),pos.getX());
    }

    private static List<AltarLevelData> readLevels(BufferedReader reader) throws IOException{
        String line;
        int nextLevelToRead = 1;
        List<AltarLevelData> readLevels = new ArrayList<>();
        AltarReadingState state=AltarReadingState.NEED_LEVEL_NUM;
        List<BlockPos> magicBlocks = new ArrayList<>();
        List<BlockPos> structureBlocks = new ArrayList<>();
        while((line = reader.readLine())!=null){
            switch(state) {
                case NEED_LEVEL_NUM:
                    if (line.equalsIgnoreCase("#1#")) {
                        state = AltarReadingState.NEED_MAGIC_MARKER;
                        nextLevelToRead++;
                    } else {
                        throw new IOException("Expected level marker but found " + line);
                    }
                    break;
                case NEED_MAGIC_MARKER:
                    if (line.equalsIgnoreCase("#magic#")) {
                        state = AltarReadingState.READING_MAGIC;
                    } else {
                        throw new IOException("Expected magic blocks marker but found " + line);
                    }
                    break;
                case READING_MAGIC:
                    if (line.equalsIgnoreCase("#structure#")) {
                        state = AltarReadingState.READING_STRUCTURE;
                    }else{
                        BlockPos posRead = readBlockPos(line);
                        if(posRead==null){
                            throw new IOException("Could not read blockPos "+line);
                        }
                        magicBlocks.add(posRead);

                    }
                    break;
                case READING_STRUCTURE:
                    if(line.equalsIgnoreCase("#"+nextLevelToRead+"#")){
                        //finish old level, start new level
                        AltarLevelData levelData = new AltarLevelData(structureBlocks,magicBlocks);
                        state=AltarReadingState.NEED_MAGIC_MARKER;
                        readLevels.add(levelData);
                        structureBlocks = new ArrayList<>();
                        magicBlocks = new ArrayList<>();
                        nextLevelToRead++;
                    }else{
                        BlockPos posRead = readBlockPos(line);
                        if(posRead==null){
                            throw new IOException("Could not read blockPos "+line);
                        }
                        structureBlocks.add(posRead);
                    }
            }
        }
        //hit eof, add the last level we read
        AltarLevelData levelData = new AltarLevelData(structureBlocks,magicBlocks);
        readLevels.add(levelData);
        return readLevels;
    }

    private static BlockPos readBlockPos(String line){
        String[] parts = line.split(",");
        BlockPos readPos=null;
        try {
            readPos = new BlockPos(
                    Integer.parseInt(parts[0].trim()),
                    Integer.parseInt(parts[1].trim()),
                    Integer.parseInt(parts[2].trim()));
        }catch(Exception e){
            throw new IllegalArgumentException("Could not read block pos at line "+line);
        }
        return readPos;
    }

    private enum AltarReadingState{
        NEED_LEVEL_NUM,
        NEED_MAGIC_MARKER,
        READING_MAGIC,
        READING_STRUCTURE
    }
}
