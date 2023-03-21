package mod.azure.mchalo.entity.projectiles;

import mod.azure.azurelib.network.packet.EntityPacket;
import mod.azure.mchalo.util.HaloItems;
import mod.azure.mchalo.util.ProjectilesEntityRegister;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.particles.ParticleTypes;
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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class BulletEntity extends AbstractArrow {

	protected int timeInAir;
	protected boolean inAir;
	private int ticksInAir;
	private static float bulletdamage;

	public BulletEntity(EntityType<? extends BulletEntity> entityType, Level world) {
		super(entityType, world);
		this.pickup = AbstractArrow.Pickup.DISALLOWED;
	}

	public BulletEntity(Level world, LivingEntity owner, Float damage) {
		super(ProjectilesEntityRegister.BULLET, owner, world);
		bulletdamage = damage;
	}

	protected BulletEntity(EntityType<? extends BulletEntity> type, double x, double y, double z, Level world) {
		this(type, world);
	}

	protected BulletEntity(EntityType<? extends BulletEntity> type, LivingEntity owner, Level world) {
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
		if (this.ticksInAir >= 40) {
			this.remove(Entity.RemovalReason.DISCARDED);
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
		super.tick();
		++this.ticksInAir;
		if (this.ticksInAir >= 80) 
			this.remove(Entity.RemovalReason.DISCARDED);
		if (this.level.isClientSide) {
			var x = this.getX() + (this.random.nextDouble()) * (double) this.getBbWidth() * 0.5D;
			var y = this.getZ() + (this.random.nextDouble()) * (double) this.getBbWidth() * 0.5D;
			this.level.addParticle(ParticleTypes.SMOKE, true, x, this.getY(), y, 0, 0, 0);
		}
	}

	public void initFromStack(ItemStack stack) {
		if (stack.getItem() == HaloItems.SNIPER_ROUND) {
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
		return SoundEvents.ARMOR_EQUIP_IRON;
	}

	@Override
	protected void onHitBlock(BlockHitResult blockHitResult) {
		super.onHitBlock(blockHitResult);
		if (!this.level.isClientSide) 
			this.remove(Entity.RemovalReason.DISCARDED);
		this.setSoundEvent(SoundEvents.ARMOR_EQUIP_IRON);
	}

	@Override
	protected void onHitEntity(EntityHitResult entityHitResult) {
		var entity = entityHitResult.getEntity();
		if (entityHitResult.getType() != HitResult.Type.ENTITY
				|| !((EntityHitResult) entityHitResult).getEntity().is(entity)) 
			if (!this.level.isClientSide) 
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
				if (!this.level.isClientSide && entity2 instanceof LivingEntity) {
					EnchantmentHelper.doPostHurtEffects(livingEntity, entity2);
					EnchantmentHelper.doPostDamageEffects((LivingEntity) entity2, livingEntity);
				}
				this.doPostHurtEffects(livingEntity);
				if (entity2 != null && livingEntity != entity2 && livingEntity instanceof Player
						&& entity2 instanceof ServerPlayer && !this.isSilent()) 
					((ServerPlayer) entity2).connection.send(
							new ClientboundGameEventPacket(ClientboundGameEventPacket.ARROW_HIT_PLAYER, 0.0F));
			}
		} else {
			if (!this.level.isClientSide) 
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