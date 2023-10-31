package mod.azure.mchalo.entity.projectiles;

import mod.azure.azurelib.entities.TickingLightEntity;
import mod.azure.azurelib.network.packet.EntityPacket;
import mod.azure.azurelib.platform.Services;
import mod.azure.mchalo.util.HaloItems;
import mod.azure.mchalo.util.HaloParticles;
import mod.azure.mchalo.util.ProjectilesEntityRegister;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class PlasmaEntity extends AbstractArrow {

    protected static float bulletdamage;
    public SoundEvent hitSound = this.getDefaultHitGroundSoundEvent();
    protected int timeInAir;
    protected boolean inAir;
    private int ticksInAir;
    private BlockPos lightBlockPos = null;
    private int idleTicks = 0;

    public PlasmaEntity(EntityType<? extends PlasmaEntity> entityType, Level world) {
        super(entityType, world);
        this.pickup = AbstractArrow.Pickup.DISALLOWED;
    }

    public PlasmaEntity(Level world, LivingEntity owner, Float damage) {
        super(ProjectilesEntityRegister.PLASMA, owner, world);
        bulletdamage = damage;
    }

    protected PlasmaEntity(EntityType<? extends PlasmaEntity> type, double x, double y, double z, Level world) {
        this(type, world);
    }

    protected PlasmaEntity(EntityType<? extends PlasmaEntity> type, LivingEntity owner, Level world) {
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
    protected void doPostHurtEffects(LivingEntity living) {
        super.doPostHurtEffects(living);
        if (!(living instanceof Player)) {
            living.invulnerableTime = 0;
            living.setDeltaMovement(0, 0, 0);
        }
    }

    @Override
    public void tickDespawn() {
        ++this.ticksInAir;
        if (this.ticksInAir >= 40)
            this.remove(Entity.RemovalReason.DISCARDED);
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
        if (this.ticksInAir >= 80)
            this.remove(Entity.RemovalReason.DISCARDED);
        var isInsideWaterBlock = this.getCommandSenderWorld().isWaterAt(blockPosition());
        spawnLightSource(isInsideWaterBlock);
        if (this.getCommandSenderWorld().isClientSide)
            this.getCommandSenderWorld().addParticle(HaloParticles.PLASMA, true, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
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
                    BlockPos offsetPos = blockPos.offset(x, y, z);
                    BlockState state = world.getBlockState(offsetPos);
                    if (state.isAir() || state.getBlock().equals(Services.PLATFORM.getTickingLightBlock()))
                        return offsetPos;
                }

        return null;
    }

    public void initFromStack(ItemStack stack) {
        if (stack.getItem() == HaloItems.SNIPER_ROUND) {
        }
    }

    @Override
    public boolean isNoGravity() {
        return this.isUnderWater() ? false : true;
    }

    @Override
    public void setSoundEvent(SoundEvent soundIn) {
        this.hitSound = soundIn;
    }

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.ARMOR_EQUIP_IRON;
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        if (!this.getCommandSenderWorld().isClientSide)
            this.remove(Entity.RemovalReason.DISCARDED);
        this.setSoundEvent(SoundEvents.ARMOR_EQUIP_IRON);
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        var entity = entityHitResult.getEntity();
        if (entityHitResult.getType() != HitResult.Type.ENTITY
                || !((EntityHitResult) entityHitResult).getEntity().is(entity))
            if (!this.getCommandSenderWorld().isClientSide)
                this.remove(Entity.RemovalReason.DISCARDED);
        var entity2 = this.getOwner();
        DamageSource damageSource2;
        if (entity2 == null)
            damageSource2 = damageSources().arrow(this, this);
        else {
            damageSource2 = damageSources().arrow(this, entity2);
            if (entity2 instanceof LivingEntity)
                ((LivingEntity) entity2).setLastHurtMob(entity);
        }
        if (entity.hurt(damageSource2, bulletdamage)) {
            if (entity instanceof LivingEntity) {
                var livingEntity = (LivingEntity) entity;
                if (!this.getCommandSenderWorld().isClientSide && entity2 instanceof LivingEntity) {
                    EnchantmentHelper.doPostHurtEffects(livingEntity, entity2);
                    EnchantmentHelper.doPostDamageEffects((LivingEntity) entity2, livingEntity);
                }

                this.doPostHurtEffects(livingEntity);
                if (entity2 != null && livingEntity != entity2 && livingEntity instanceof Player
                        && entity2 instanceof ServerPlayer && !this.isSilent())
                    ((ServerPlayer) entity2).connection
                            .send(new ClientboundGameEventPacket(ClientboundGameEventPacket.ARROW_HIT_PLAYER, 0.0F));
            }
        } else {
            if (!this.getCommandSenderWorld().isClientSide)
                this.remove(Entity.RemovalReason.DISCARDED);
        }
    }

    @Override
    public ItemStack getPickupItem() {
        return new ItemStack(HaloItems.SNIPER_ROUND);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public boolean shouldRenderAtSqrDistance(double distance) {
        return true;
    }

}