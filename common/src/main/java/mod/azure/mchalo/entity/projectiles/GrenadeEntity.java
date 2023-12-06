package mod.azure.mchalo.entity.projectiles;

import mod.azure.azurelib.animatable.GeoEntity;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.animation.RawAnimation;
import mod.azure.azurelib.core.object.PlayState;
import mod.azure.azurelib.network.packet.EntityPacket;
import mod.azure.azurelib.util.AzureLibUtil;
import mod.azure.mchalo.CommonMod;
import mod.azure.mchalo.platform.Services;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;

public class GrenadeEntity extends AbstractArrow implements GeoEntity {
    protected String type;
    private static final EntityDataAccessor<Boolean> SPINNING = SynchedEntityData.defineId(GrenadeEntity.class, EntityDataSerializers.BOOLEAN);
    private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);

    public GrenadeEntity(EntityType<? extends GrenadeEntity> entityType, Level world) {
        super(entityType, world);
        this.pickup = AbstractArrow.Pickup.DISALLOWED;
    }

    public GrenadeEntity(Level world, LivingEntity owner) {
        super(Services.ENTITIES_HELPER.getGrenadeEntity(), owner, world);
    }

    protected GrenadeEntity(EntityType<? extends GrenadeEntity> type, double x, double y, double z, Level world) {
        this(type, world);
    }

    protected GrenadeEntity(EntityType<? extends GrenadeEntity> type, LivingEntity owner, Level world) {
        this(type, owner.getX(), owner.getEyeY() - 0.10000000149011612D, owner.getZ(), world);
        this.setOwner(owner);
        this.pickup = AbstractArrow.Pickup.DISALLOWED;
    }

    public GrenadeEntity(Level world, LivingEntity user, boolean spinning) {
        super(Services.ENTITIES_HELPER.getGrenadeEntity(), user, world);
        this.entityData.set(SPINNING, spinning);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SPINNING, false);
    }

    public boolean isSpinning() {
        return (Boolean) this.entityData.get(SPINNING);
    }

    public void setSpinning(boolean spin) {
        this.entityData.set(SPINNING, spin);
    }

    @Override
    public void registerControllers(ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, event -> {
            if (inGroundTime == 0) return event.setAndContinue(RawAnimation.begin().thenLoop("spin"));
            return PlayState.STOP;
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return EntityPacket.createPacket(this);
    }

    @Override
    public void remove(@NotNull RemovalReason reason) {
        var areaeffectcloudentity = new AreaEffectCloud(this.level(), this.getX(), this.getY(), this.getZ());
        areaeffectcloudentity.setParticle(ParticleTypes.EXPLOSION);
        areaeffectcloudentity.setRadius(CommonMod.config.mauler_bullet_damage + 2);
        areaeffectcloudentity.setDuration(1);
        areaeffectcloudentity.absMoveTo(this.getX(), this.getEyeY(), this.getZ());
        this.level().addFreshEntity(areaeffectcloudentity);
        this.explode();
        super.remove(reason);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.tickCount >= 80) this.remove(Entity.RemovalReason.DISCARDED);
        if (getDeltaMovement().x == 0) setSpinning(false);
        if (!onGround()) setSpinning(true);
    }

    public SoundEvent hitSound = this.getDefaultHitGroundSoundEvent();

    @Override
    public void setSoundEvent(@NotNull SoundEvent soundIn) {
        this.hitSound = soundIn;
    }

    @Override
    protected @NotNull SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.SOUL_ESCAPE;
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        if (!this.level().isClientSide && this.tickCount >= 45) {
            this.entityData.set(SPINNING, false);
            this.remove(Entity.RemovalReason.DISCARDED);
        }
        this.setSoundEvent(SoundEvents.GENERIC_EXPLODE);
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        if (!this.level().isClientSide) {
            this.entityData.set(SPINNING, false);
            this.remove(Entity.RemovalReason.DISCARDED);
        }
    }

    protected void explode() {
        var aabb = new AABB(this.blockPosition().above()).inflate(6D, 6D, 6D);
        this.level().getEntities(this, aabb).forEach(e -> {
            if (e.isAlive() && e instanceof LivingEntity)
                e.hurt(damageSources().arrow(this, this.getOwner()), CommonMod.config.mauler_bullet_damage);
        });
    }

    @Override
    public @NotNull ItemStack getPickupItem() {
        return new ItemStack(Items.AIR);
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        return true;
    }

    @Override
    public boolean displayFireAnimation() {
        return false;
    }

}