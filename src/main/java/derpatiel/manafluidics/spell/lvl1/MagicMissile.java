package derpatiel.manafluidics.spell.lvl1;

import derpatiel.manafluidics.spell.EntityCreatingSpell;
import derpatiel.manafluidics.spell.EntityTargetingSpell;
import derpatiel.manafluidics.spell.ParameterSpell;
import derpatiel.manafluidics.spell.SpellAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class MagicMissile extends EntityCreatingSpell implements ParameterSpell, EntityTargetingSpell {
    public MagicMissile() {
        super("magicmissile", 1, 20, SpellAttribute.EVOCATION);
    }

    @Override
    protected Entity getCreatedEntity(World world, EntityPlayer caster, boolean boosted) {

        Vec3d casterLook = caster.getLookVec();
        EntityMagicMissile missile = new EntityMagicMissile(world,caster,casterLook.xCoord,casterLook.yCoord,casterLook.zCoord,boosted);
        missile.posX = caster.getPositionEyes(1.0f).xCoord + casterLook.xCoord * 4.0D;
        missile.posY = caster.getPositionEyes(1.0f).yCoord - 0.3f;
        missile.posZ = caster.getPositionEyes(1.0f).zCoord + casterLook.zCoord * 4.0D;
        return missile;
    }


    public class EntityMagicMissile extends EntityFireball {

        private boolean damageBoosted;

        public EntityMagicMissile(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ, boolean damageBoosted) {
            super(worldIn);
            this.damageBoosted=damageBoosted;
            this.shootingEntity = shooter;
            this.setSize(1.0F, 1.0F);
            this.setLocationAndAngles(shooter.posX, shooter.posY, shooter.posZ, shooter.rotationYaw, shooter.rotationPitch);
            this.setPosition(this.posX, this.posY, this.posZ);
            this.motionX = 0.0D;
            this.motionY = 0.0D;
            this.motionZ = 0.0D;
            double d0 = (double) MathHelper.sqrt_double(accelX * accelX + accelY * accelY + accelZ * accelZ);
            this.accelerationX = accelX / d0 * 0.1D;
            this.accelerationY = accelY / d0 * 0.1D;
            this.accelerationZ = accelZ / d0 * 0.1D;
        }

        @Override
        protected void onImpact(RayTraceResult result) {
            if(result.typeOfHit== RayTraceResult.Type.BLOCK) {
                this.setDead();
            }else if(result.typeOfHit== RayTraceResult.Type.ENTITY){
                result.entityHit.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)shootingEntity).setMagicDamage(),5.0f * (damageBoosted ? 1.25f : 1.0f));
                this.setDead();
            }
        }
    }
}
