package derpatiel.manafluidics.block;

import net.minecraft.block.material.Material;

public class TankPartBlock extends MFDismantleableBlock implements  ITankPart {
    public TankPartBlock(String unlocalizedName, Material material, float hardness, float resistance) {
        super(unlocalizedName, material, hardness, resistance);
    }
}
