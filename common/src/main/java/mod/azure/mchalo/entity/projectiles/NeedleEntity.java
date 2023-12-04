package mod.azure.mchalo.entity.projectiles;

import mod.azure.azurelib.animatable.GeoEntity;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.object.PlayState;
import mod.azure.azurelib.network.packet.EntityPacket;
import mod.azure.azurelib.util.AzureLibUtil;
import mod.azure.mchalo.helper.CommonHelper;
import mod.azure.mchalo.platform.Services;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import org.jetbrains.annotations.NotNull;

public class NeedleEntity extends AbstractArrow implements GeoEntity {

    private static float bulletdamage;
    private int idleTicks = 0;
    private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);

    public NeedleEntity(EntityType<? extends NeedleEntity> entityType, Level world) {
        super(entityType, world);
        this.pickup = AbstractArrow.Pickup.DISALLOWED;
    }

    public NeedleEntity(Level world, LivingEntity owner, Float damage) {
        super(Services.ENTITIES_HELPER.getNeedleEntity(), owner, world);
        bulletdamage = damage;
    }

    protected NeedleEntity(EntityType<? extends NeedleEntity> type, double x, double y, double z, Level world) {
        this(type, world);
    }

    protected NeedleEntity(EntityType<? extends NeedleEntity> type, LivingEntity owner, Level world) {
        this(type, owner.getX(), owner.getEyeY() - 0.10000000149011612D, owner.getZ(), world);
        this.setOwner(owner);
        if (owner instanceof Player) this.pickup = AbstractArrow.Pickup.DISALLOWED;
    }

    @Override
    public void registerControllers(ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, event -> PlayState.CONTINUE));
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
    protected void doPostHurtEffects(@NotNull LivingEntity living) {
        super.doPostHurtEffects(living);
        this.remove(Entity.RemovalReason.DISCARDED);
        if (!(living instanceof Player)) {
            living.invulnerableTime = 0;
            living.setDeltaMovement(0, 0, 0);
        }
    }

    @Override
    public void tickDespawn() {
        if (this.tickCount >= 40) this.remove(Entity.RemovalReason.DISCARDED);
    }

    @Override
    public void tick() {
        var idleOpt = 100;
        if (getDeltaMovement().lengthSqr() < 0.01) idleTicks++;
        else idleTicks = 0;
        if (idleOpt <= 0 || idleTicks < idleOpt) super.tick();
        if (this.tickCount >= 40) this.remove(Entity.RemovalReason.DISCARDED);
        CommonHelper.spawnLightSource(this, this.level().isWaterAt(blockPosition()));
        var world = this.level();
        var livingEntities = world.getEntitiesOfClass(Monster.class, new AABB(this.getX() - 6.0, this.getY() - 6.0, this.getZ() - 6.0, this.getX() + 6.0, this.getY() + 6.0, this.getZ() + 6.0), entity1 -> entity1 != this.getOwner());
        if (!livingEntities.isEmpty()) {
            var first = livingEntities.get(0);
            var entityPos = new Vec3(first.getX(), first.getY() + first.getEyeHeight(), first.getZ());
            var distance = entityPos.subtract(this.getX(), this.getY() + this.getEyeHeight(), this.getZ());
            var entityDirect = distance.normalize();
            var arrowDirect = this.getDeltaMovement().normalize();
            var newPath = entityDirect.add(arrowDirect.multiply(4.0, 4.0, 4.0)).normalize();
            var speed = this.getDeltaMovement().length();
            this.setDeltaMovement(newPath.multiply(speed, speed, speed));
        }
    }

    @Override
    protected boolean tryPickup(@NotNull Player player) {
        return false;
    }

    @Override
    public boolean isNoGravity() {
        return !this.isUnderWater();
    }

    public SoundEvent hitSound = this.getDefaultHitGroundSoundEvent();

    @Override
    public void setSoundEvent(@NotNull SoundEvent soundIn) {
        this.hitSound = soundIn;
    }

    @Override
    protected @NotNull SoundEvent getDefaultHitGroundSoundEvent() {
        return Services.SOUNDS_HELPER.getNeedlerSound();
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        if (!this.level().isClientSide) {
            this.remove(Entity.RemovalReason.DISCARDED);
        }
        this.setSoundEvent(Services.SOUNDS_HELPER.getNeedlerSound());
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        var entity = entityHitResult.getEntity();
        if (entityHitResult.getType() != HitResult.Type.ENTITY || !entityHitResult.getEntity().is(entity) && !this.level().isClientSide)
            this.remove(Entity.RemovalReason.DISCARDED);
        var entity2 = this.getOwner();
        DamageSource damageSource2;
        if (entity2 == null) damageSource2 = damageSources().arrow(this, this);
        else {
            damageSource2 = damageSources().arrow(this, entity2);
            if (entity2 instanceof LivingEntity livingEntity) livingEntity.setLastHurtMob(entity);
        }
        if (entity.hurt(damageSource2, bulletdamage)) {
            if (entity instanceof LivingEntity livingEntity) {
                if (!this.level().isClientSide) {
                    livingEntity.setArrowCount(livingEntity.getArrowCount() + 1);
                }
                if (!this.level().isClientSide && entity2 instanceof LivingEntity livingEntity1) {
                    EnchantmentHelper.doPostHurtEffects(livingEntity, entity2);
                    EnchantmentHelper.doPostDamageEffects(livingEntity1, livingEntity);
                }

                this.doPostHurtEffects(livingEntity);
                if (entity2 != null && livingEntity != entity2 && livingEntity instanceof Player && entity2 instanceof ServerPlayer && !this.isSilent())
                    ((ServerPlayer) entity2).connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.ARROW_HIT_PLAYER, ClientboundGameEventPacket.DEMO_PARAM_INTRO));
                this.remove(RemovalReason.KILLED);
            }
        } else {
            if (!this.level().isClientSide) this.remove(RemovalReason.KILLED);
        }
    }

    @Override
    public @NotNull ItemStack getPickupItem() {
        return new ItemStack(Items.AIR);
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        return true;
    }

}