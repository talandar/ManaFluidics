package derpatiel.manafluidics.block;

import com.google.common.collect.Sets;
import derpatiel.manafluidics.enums.RedstoneActivation;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;

public interface IRedstoneToggled {

    public static final PropertyEnum ACTIVATION = PropertyEnum.create("activated",RedstoneActivation.class, Sets.newHashSet(RedstoneActivation.LOW,RedstoneActivation.HIGH,RedstoneActivation.IGNORED));

    public default RedstoneActivation getNextActivationType(RedstoneActivation currentType){
        int curActivationId = currentType.getId();
        RedstoneActivation nextType = RedstoneActivation.getById(++curActivationId);
        while(!nextType.isToggleTrigger()){
            nextType = RedstoneActivation.getById((++curActivationId));
        }
        return nextType;
    }

}
