package derpatiel.manafluidics.spell.lvl1;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import derpatiel.manafluidics.spell.*;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSpectralArrow;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.realms.Tezzelator;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class MagicMissile extends EntityCreatingSpell {

    private static final int MM_TTL = 6*20;

    public MagicMissile() {
        super("magicmissile", 1, 20, SpellAttribute.EVOCATION);
    }

    @Override
    protected Entity getCreatedEntity(World world, EntityPlayer caster, boolean boosted, List<SpellParameterChoices> parameters) {

        Vec3d casterLook = caster.getLookVec();
        EntityMagicMissile missile = new EntityMagicMissile(world);
        missile.setup(caster,boosted);
        missile.setTargetingSelector(SpellParameters.getEntityTargetingPredicate(caster,parameters.get(0).selectedOption));
        missile.posX = caster.getPositionEyes(1.0f).xCoord + casterLook.xCoord * 4.0D;
        missile.posY = caster.getPositionEyes(1.0f).yCoord - 0.3f;
        missile.posZ = caster.getPositionEyes(1.0f).zCoord + casterLook.zCoord * 4.0D;
        missile.findTarget();
        if(missile.target==null)
            missile=null;
        return missile;
    }

    @Override
    public List<SpellParameterOptions> getRequiredParameters() {
        List<SpellParameterOptions> list = new ArrayList<>();
        list.add(SpellParameters.entitytargeting);
        return list;
    }

    public static class EntityMagicMissile extends EntityArrow {

        private boolean damageBoosted;
        private EntityLivingBase shooter;
        private Predicate<Entity> targetSelector;
        private Entity target;


        public EntityMagicMissile(World worldIn){
            super(worldIn);
            this.setSize(1.0F, 1.0F);
            this.setPosition(this.posX, this.posY, this.posZ);
            this.motionX = 0.0D;
            this.motionY = 0.0D;
            this.motionZ = 0.0D;
            this.pickupStatus=PickupStatus.DISALLOWED;
        }

        @Override
        public void onUpdate() {
            super.onUpdate();
            if(inGround || inWater || this.ticksExisted>MM_TTL) {
                this.setDead();
            }
            if(target!=null && target.isDead){
                this.setDead();
            }
            Vec3d myPos = this.getPositionVector();
            if(target!=null) {
                Vec3d targetPos = target.getPositionEyes(1.0f);
                Vec3d ray = targetPos.subtract(myPos);
                ray = ray.normalize();
                this.setThrowableHeading(ray.xCoord, ray.yCoord, ray.zCoord, 1.0f, 0.1f);
            }
        }

        public void setup(EntityLivingBase shooter, boolean damageBoosted) {
            this.damageBoosted=damageBoosted;
            this.shooter=shooter;
            this.setLocationAndAngles(shooter.posX, shooter.posY, shooter.posZ, shooter.rotationYaw, shooter.rotationPitch);
        }

        public void setTargetingSelector(Predicate<Entity> predicate){
            this.targetSelector= Predicates.and(predicate, EntitySelectors.NOT_SPECTATING, EntitySelectors.IS_ALIVE);
        }

        public void findTarget(){
            AxisAlignedBB boundingBox = this.getEntityBoundingBox().expandXyz(20);
            List<Entity> validTargets = worldObj.getEntitiesInAABBexcluding(this,boundingBox,targetSelector);
            double shortestDistance = Double.MAX_VALUE;
            for(Entity possibleTarget : validTargets){
                double dist = possibleTarget.getPositionVector().distanceTo(this.getPositionVector());
                if(dist<shortestDistance){
                    shortestDistance=dist;
                    target=possibleTarget;
                }
            }
            if(target==null)
                this.setDead();
        }

        @Override
        public void writeEntityToNBT(NBTTagCompound compound) {
            super.writeEntityToNBT(compound);
            compound.setBoolean("damageBoosted",damageBoosted);
            if(target!=null)
                compound.setUniqueId("target",target.getUniqueID());

            if(shooter!=null) {
                compound.setUniqueId("shooter", shooter.getUniqueID());
            }
        }

        @Override
        public void readEntityFromNBT(NBTTagCompound compound) {
            super.readEntityFromNBT(compound);
            this.damageBoosted=compound.getBoolean("damageBoosted");
            UUID targetUUID = compound.getUniqueId("target");
            List<Entity> matchingEntities = worldObj.getLoadedEntityList().stream().filter(entity -> entity.getUniqueID().equals(targetUUID)).collect(Collectors.toList());
            if(matchingEntities.size()>0) {
                target = matchingEntities.get(0);
            }else{
                //couldn't find the target, so die
                this.setDead();
            }
            UUID shooterUUID = compound.getUniqueId("shooter");
            matchingEntities = worldObj.getLoadedEntityList().stream().filter(entity -> entity.getUniqueID().equals(shooterUUID)).collect(Collectors.toList());
            if(matchingEntities.size()>0) {
                shooter = (EntityLivingBase)matchingEntities.get(0);
            }
        }

        @Override
        protected ItemStack getArrowStack() {
            return null;
        }
    }

    public static class RenderMagicMissile extends RenderArrow<EntityMagicMissile> {

        public static final Factory FACTORY = new Factory();

        public RenderMagicMissile(RenderManager renderManagerIn) {
            super(renderManagerIn);
        }

        @Override
        protected ResourceLocation getEntityTexture(EntityMagicMissile entity) {

            return RenderSpectralArrow.RES_SPECTRAL_ARROW;
        }

        public static class Factory implements IRenderFactory<EntityMagicMissile> {

            @Override
            public Render<? super EntityMagicMissile> createRenderFor(RenderManager manager) {
                return new RenderMagicMissile(manager);
            }

        }
    }
}
