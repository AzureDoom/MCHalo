package mod.azure.mchalo.entity.projectiles;

import mod.azure.azurelib.network.packet.EntityPacket;
import mod.azure.mchalo.CommonMod;
import mod.azure.mchalo.helper.CommonHelper;
import mod.azure.mchalo.platform.Services;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;

public class RocketEntity extends AbstractArrow {
    private int idleTicks = 0;

    public RocketEntity(EntityType<? extends RocketEntity> entityType, Level world) {
        super(entityType, world);
        this.pickup = AbstractArrow.Pickup.DISALLOWED;
    }

    public RocketEntity(Level world, LivingEntity owner) {
        super(Services.ENTITIES_HELPER.getRocketEntity(), owner, world);
    }

    protected RocketEntity(EntityType<? extends RocketEntity> type, double x, double y, double z, Level world) {
        this(type, world);
    }

    protected RocketEntity(EntityType<? extends RocketEntity> type, LivingEntity owner, Level world) {
        this(type, owner.getX(), owner.getEyeY() - 0.10000000149011612D, owner.getZ(), world);
        this.setOwner(owner);
        if (owner instanceof Player) this.pickup = AbstractArrow.Pickup.DISALLOWED;
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return EntityPacket.createPacket(this);
    }

    @Override
    public void remove(@NotNull RemovalReason reason) {
        var areaeffectcloudentity = new AreaEffectCloud(this.level(), this.getX(), this.getY(), this.getZ());
        areaeffectcloudentity.setParticle(ParticleTypes.EXPLOSION);
        areaeffectcloudentity.setRadius(6);
        areaeffectcloudentity.setDuration(1);
        areaeffectcloudentity.absMoveTo(this.getX(), this.getEyeY(), this.getZ());
        this.level().addFreshEntity(areaeffectcloudentity);
        this.doDamage();
        super.remove(reason);
    }

    @Override
    protected void tickDespawn() {
        if (this.tickCount >= 40) {
            this.remove(Entity.RemovalReason.DISCARDED);
            this.doDamage();
        }
    }

    @Override
    protected void doPostHurtEffects(@NotNull LivingEntity living) {
        super.doPostHurtEffects(living);
        if (!(living instanceof Player)) {
            living.setDeltaMovement(0, 0, 0);
            living.invulnerableTime = 0;
        }
    }

    @Override
    public void tick() {
        var idleOpt = 100;
        if (getDeltaMovement().lengthSqr() < 0.01) idleTicks++;
        else idleTicks = 0;
        if (idleOpt <= 0 || idleTicks < idleOpt) super.tick();
        if (this.tickCount >= 100) {
            this.remove(Entity.RemovalReason.DISCARDED);
            this.doDamage();
        }
        if (this.level().isClientSide) {
            var x = this.getX() + (this.random.nextDouble() * 2.0D - 1.0D) * this.getBbWidth() * 0.5D;
            var y = this.getY() + 0.05D + this.random.nextDouble();
            var z = this.getZ() + (this.random.nextDouble() * 2.0D - 1.0D) * this.getBbWidth() * 0.5D;
            this.level().addParticle(ParticleTypes.FLAME, true, x, y, z, 0, 0, 0);
            this.level().addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, true, x, y, z, 0, 0, 0);
        }
        CommonHelper.spawnLightSource(this, this.level().isWaterAt(this.blockPosition()));
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
        return Services.SOUNDS_HELPER.getRocketSound();
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        if (!this.level().isClientSide) {
            this.doDamage();
            this.remove(Entity.RemovalReason.DISCARDED);
        }
        this.setSoundEvent(Services.SOUNDS_HELPER.getRocketSound());
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult entityHitResult) {
        if (!this.level().isClientSide) {
            this.doDamage();
            this.remove(Entity.RemovalReason.DISCARDED);
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

    public void doDamage() {
        var aabb = new AABB(this.blockPosition().above()).inflate(6D, 6D, 6D);
        this.level().getEntities(this, aabb).forEach(e -> {
            if (e.isAlive() && e instanceof LivingEntity)
                e.hurt(damageSources().playerAttack((Player) this.getOwner()), CommonMod.config.rocketlauncher_damage);
        });
    }

    @Override
    public boolean displayFireAnimation() {
        return false;
    }
}