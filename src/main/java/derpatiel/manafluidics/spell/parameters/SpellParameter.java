package derpatiel.manafluidics.spell.parameters;

import com.google.common.base.Predicate;
import derpatiel.manafluidics.enums.AltarType;
import derpatiel.manafluidics.util.TextHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.player.EntityPlayer;


public enum SpellParameter {

    ENTITY_TARGETING("spell.entitytarget","spell.entitytarget.all","spell.entitytarget.mob","spell.entitytarget.neutral","spell.entitytarget.players");


    public static final SpellParameter[] VALUES;
    public final String name;
    public final String[] options;

    SpellParameter(String name, String... options){
        this.name=name;
        this.options=options;
    }

    public String getLocalizedName(){
        return TextHelper.localize(name);
    }
    public String getLocalizedChoiceName(int choice){
        return TextHelper.localize(options[choice]);
    }


    static{
        SpellParameter[] vals = values();
        VALUES = new SpellParameter[vals.length];
        for(SpellParameter m : vals){
            VALUES[m.ordinal()]=m;
        }
    }


    public static Predicate<Entity> getEntityTargetingPredicate(EntityPlayer caster, int option){
        Predicate<Entity> predicate = entityLivingBase -> {
            switch(option){
                case 0:
                    return entityLivingBase instanceof EntityLivingBase && !entityLivingBase.equals(caster);
                case 1:
                    return entityLivingBase instanceof EntityGhast || entityLivingBase instanceof EntityMob;
                case 2:
                    return entityLivingBase instanceof EntityCreature || entityLivingBase instanceof EntityAmbientCreature;
                case 3:
                    return entityLivingBase instanceof EntityPlayer && entityLivingBase!=caster;
                default:
                    return false;
            }
        };
        return predicate;
    }
}
