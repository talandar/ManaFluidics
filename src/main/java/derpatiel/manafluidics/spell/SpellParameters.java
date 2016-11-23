package derpatiel.manafluidics.spell;

import com.google.common.base.Predicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.player.EntityPlayer;


public class SpellParameters {

    public static final SpellParameterOptions entitytargeting;


    static{
        entitytargeting = new SpellParameterOptions("spell.entitytarget","spell.entitytarget.all","spell.entitytarget.mob","spell.entitytarget.neutral","spell.entitytarget.players");
    }

    public static Predicate<Entity> getEntityTargetingPredicate(EntityPlayer caster, String option){
        Predicate<Entity> predicate = new Predicate<Entity>() {

            @Override
            public boolean apply(Entity entityLivingBase) {
                if(option.equals("spell.entitytarget.all")){
                    return entityLivingBase instanceof EntityLivingBase;
                }else if(option.equals("spell.entitytarget.mob")){
                    return entityLivingBase instanceof EntityGhast || entityLivingBase instanceof EntityMob;
                }else if(option.equals("spell.entitytarget.neutral")){
                    return entityLivingBase instanceof EntityCreature || entityLivingBase instanceof EntityAmbientCreature;
                }else if(option.equals("spell.entitytarget.players")){
                    return entityLivingBase instanceof EntityPlayer && entityLivingBase!=caster;
                }else{
                    return false;
                }
            }
        };
        return predicate;
    }
}
