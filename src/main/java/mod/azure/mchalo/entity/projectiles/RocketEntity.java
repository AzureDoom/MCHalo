package mod.azure.mchalo.entity.projectiles;

import mod.azure.azurelib.animatable.GeoEntity;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.object.PlayState;
import mod.azure.azurelib.entities.TickingLightEntity;
import mod.azure.azurelib.network.packet.EntityPacket;
import mod.azure.azurelib.platform.Services;
import mod.azure.azurelib.util.AzureLibUtil;
import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.util.HaloItems;
import mod.azure.mchalo.util.HaloSounds;
import mod.azure.mchalo.util.ProjectilesEntityRegister;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class RocketEntity extends AbstractArrow implements GeoEntity {

    protected int timeInAir;
    protected boolean inAir;
    private int ticksInAir;
    private BlockPos lightBlockPos = null;
    private int idleTicks = 0;
    private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);

    public RocketEntity(EntityType<? extends RocketEntity> entityType, Level world) {
        super(entityType, world);
        this.pickup = AbstractArrow.Pickup.DISALLOWED;
    }

    public RocketEntity(Level world, LivingEntity owner) {
        super(ProjectilesEntityRegister.ROCKET, owner, world);
    }

    @Override
    public void registerControllers(ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, event -> {
            return PlayState.CONTINUE;
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    protected RocketEntity(EntityType<? extends RocketEntity> type, double x, double y, double z, Level world) {
        this(type, world);
    }

    protected RocketEntity(EntityType<? extends RocketEntity> type, LivingEntity owner, Level world) {
        this(type, owner.getX(), owner.getEyeY() - 0.10000000149011612D, owner.getZ(), world);
        this.setOwner(owner);
        if (owner instanceof Player)
            this.pickup = AbstractArrow.Pickup.DISALLOWED;
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return EntityPacket.createPacket(this);
    }

    @Override
    public void remove(RemovalReason reason) {
        var areaeffectcloudentity = new AreaEffectCloud(this.getCommandSenderWorld(), this.getX(), this.getY(), this.getZ());
        areaeffectcloudentity.setParticle(ParticleTypes.EXPLOSION);
        areaeffectcloudentity.setRadius(6);
        areaeffectcloudentity.setDuration(1);
        areaeffectcloudentity.absMoveTo(this.getX(), this.getEyeY(), this.getZ());
        this.getCommandSenderWorld().addFreshEntity(areaeffectcloudentity);
        this.doDamage();
        super.remove(reason);
    }

    @Override
    protected void tickDespawn() {
        ++this.ticksInAir;
        if (this.ticksInAir >= 40) {
            this.remove(Entity.RemovalReason.DISCARDED);
            this.doDamage();
        }
    }

    @Override
    protected void doPostHurtEffects(LivingEntity living) {
        super.doPostHurtEffects(living);
        if (!(living instanceof Player)) {
            living.setDeltaMovement(0, 0, 0);
            living.invulnerableTime = 0;
        }
    }

    @Override
    public void shoot(double x, double y, double z, float speed, float divergence) {
        super.shoot(x, y, z, speed, divergence);
        this.ticksInAir = 0;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putShort("life", (short) this.ticksInAir);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.ticksInAir = tag.getShort("life");
    }

    @Override
    public void tick() {
        var idleOpt = 100;
        if (getDeltaMovement().lengthSqr() < 0.01)
            idleTicks++;
        else
            idleTicks = 0;
        if (idleOpt <= 0 || idleTicks < idleOpt)
            super.tick();
        ++this.ticksInAir;
        if (this.ticksInAir >= 100) {
            this.remove(Entity.RemovalReason.DISCARDED);
            this.doDamage();
        }
        var isInsideWaterBlock = this.getCommandSenderWorld().isWaterAt(blockPosition());
        spawnLightSource(isInsideWaterBlock);
    }

    private void spawnLightSource(boolean isInWaterBlock) {
        if (lightBlockPos == null) {
            lightBlockPos = findFreeSpace(this.getCommandSenderWorld(), blockPosition(), 2);
            if (lightBlockPos == null)
                return;
            this.getCommandSenderWorld().setBlockAndUpdate(lightBlockPos, Services.PLATFORM.getTickingLightBlock().defaultBlockState());
        } else if (checkDistance(lightBlockPos, blockPosition(), 2)) {
            var blockEntity = this.getCommandSenderWorld().getBlockEntity(lightBlockPos);
            if (blockEntity instanceof TickingLightEntity)
                ((TickingLightEntity) blockEntity).refresh(isInWaterBlock ? 20 : 0);
            else
                lightBlockPos = null;
        } else
            lightBlockPos = null;
    }

    private boolean checkDistance(BlockPos blockPosA, BlockPos blockPosB, int distance) {
        return Math.abs(blockPosA.getX() - blockPosB.getX()) <= distance
                && Math.abs(blockPosA.getY() - blockPosB.getY()) <= distance
                && Math.abs(blockPosA.getZ() - blockPosB.getZ()) <= distance;
    }

    private BlockPos findFreeSpace(Level world, BlockPos blockPos, int maxDistance) {
        if (blockPos == null)
            return null;

        var offsets = new int[maxDistance * 2 + 1];
        offsets[0] = 0;
        for (int i = 2; i <= maxDistance * 2; i += 2) {
            offsets[i - 1] = i / 2;
            offsets[i] = -i / 2;
        }
        for (int x : offsets)
            for (int y : offsets)
                for (int z : offsets) {
                    var offsetPos = blockPos.offset(x, y, z);
                    var state = world.getBlockState(offsetPos);
                    if (state.isAir() || state.getBlock().equals(Services.PLATFORM.getTickingLightBlock()))
                        return offsetPos;
                }

        return null;
    }

    public void initFromStack(ItemStack stack) {
        if (stack.getItem() == HaloItems.ROCKET) {
        }
    }

    @Override
    public boolean isNoGravity() {
        return this.isUnderWater() ? false : true;
    }

    public SoundEvent hitSound = this.getDefaultHitGroundSoundEvent();

    @Override
    public void setSoundEvent(SoundEvent soundIn) {
        this.hitSound = soundIn;
    }

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return HaloSounds.ROCKET;
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        if (!this.getCommandSenderWorld().isClientSide) {
            this.doDamage();
            this.remove(Entity.RemovalReason.DISCARDED);
        }
        this.setSoundEvent(HaloSounds.ROCKET);
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        if (!this.getCommandSenderWorld().isClientSide) {
            this.doDamage();
            this.remove(Entity.RemovalReason.DISCARDED);
        }
    }

    @Override
    public ItemStack getPickupItem() {
        return new ItemStack(HaloItems.ROCKET);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public boolean shouldRenderAtSqrDistance(double distance) {
        return true;
    }

    public void doDamage() {
        var aabb = new AABB(this.blockPosition().above()).inflate(6D, 6D, 6D);
        this.getCommandSenderWorld().getEntities(this, aabb).forEach(e -> {
            if (e.isAlive() && e instanceof LivingEntity)
                e.hurt(damageSources().playerAttack((Player) this.getOwner()), MCHaloMod.config.rocketlauncher_damage);
        });
    }

    @Override
    public boolean displayFireAnimation() {
        return false;
    }
}