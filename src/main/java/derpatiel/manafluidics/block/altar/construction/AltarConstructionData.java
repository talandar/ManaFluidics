package derpatiel.manafluidics.block.altar.construction;

import net.minecraft.util.math.BlockPos;

import java.util.List;

public class AltarConstructionData {
    public String type;
    public List<AltarLevelData> levels;

    public static class AltarLevelData{
        public final List<BlockPos> structureBlocks;
        public final List<BlockPos> activeBlocks;

        public AltarLevelData(List<BlockPos> structureBlocks, List<BlockPos> activeBlocks){
            this.structureBlocks = structureBlocks;
            this.activeBlocks = activeBlocks;
        }
    }
}
