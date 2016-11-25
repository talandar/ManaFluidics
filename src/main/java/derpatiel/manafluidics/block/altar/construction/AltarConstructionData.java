package derpatiel.manafluidics.block.altar.construction;

import derpatiel.manafluidics.enums.AltarType;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class AltarConstructionData {

    public static AltarConstructionData zigguratData;
    public static AltarConstructionData hengeData;
    public static AltarConstructionData hourglassData;
    public static AltarConstructionData towerData;
    public static AltarConstructionData templeData;

    public AltarType type;
    public List<AltarLevelData> levels;

    public static class AltarLevelData{
        public final List<BlockPos> structureBlocks;
        public final List<BlockPos> activeBlocks;

        public AltarLevelData(List<BlockPos> structureBlocks, List<BlockPos> activeBlocks){
            this.structureBlocks = structureBlocks;
            this.activeBlocks = activeBlocks;
        }
    }


    static{

        List<BlockPos> structureBlocks;
        List<BlockPos> magicBlocks;
        AltarLevelData levelData;

        zigguratData = new AltarConstructionData();
        zigguratData.type=AltarType.ZIGGURAT;

        structureBlocks = new ArrayList<>();
        magicBlocks = new ArrayList<>();
        //INSERT HERE
        levelData = new AltarLevelData(structureBlocks,magicBlocks);
        zigguratData.levels.add(levelData);



    }
}
