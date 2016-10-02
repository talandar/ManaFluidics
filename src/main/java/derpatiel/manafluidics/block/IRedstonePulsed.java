package derpatiel.manafluidics.block;

import com.google.common.collect.Sets;
import derpatiel.manafluidics.enums.RedstoneActivation;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.util.EnumFacing;

public interface IRedstonePulsed {

    public static final PropertyEnum ACTIVATION = PropertyEnum.create("activated",RedstoneActivation.class, Sets.newHashSet(RedstoneActivation.RAISING,RedstoneActivation.FALLING,RedstoneActivation.IGNORED));

    public default RedstoneActivation getNextActivationType(RedstoneActivation currentType){
        int curActivationid = currentType.getId();
        RedstoneActivation nextType = RedstoneActivation.getById(++curActivationid);
        while(!nextType.isPulseTrigger()){
            nextType = RedstoneActivation.getById((++curActivationid));
        }
        return nextType;
    }

}
