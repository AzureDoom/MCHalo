package mod.azure.mchalo.entity.projectiles;

import java.util.List;

import mod.azure.azurelib.AzureLibMod;
import mod.azure.azurelib.animatable.GeoEntity;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.object.PlayState;
import mod.azure.azurelib.entities.TickingLightEntity;
import mod.azure.azurelib.network.packet.EntityPacket;
import mod.azure.azurelib.util.AzureLibUtil;
import mod.azure.mchalo.util.HaloItems;
import mod.azure.mchalo.util.HaloSounds;
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
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class NeedleEntity extends AbstractArrow implements GeoEntity {

	protected int timeInAir;
	protected boolean inAir;
	private int ticksInAir;
	private static float bulletdamage;
	private BlockPos lightBlockPos = null;
	private int idleTicks = 0;
	private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);

	public NeedleEntity(EntityType<? extends NeedleEntity> entityType, Level world) {
		super(entityType, world);
		this.pickup = AbstractArrow.Pickup.DISALLOWED;
	}

	public NeedleEntity(Level world, LivingEntity owner, Float damage) {
		super(ProjectilesEntityRegister.NEEDLE, owner, world);
		bulletdamage = damage;
	}

	protected NeedleEntity(EntityType<? extends NeedleEntity> type, double x, double y, double z, Level world) {
		this(type, world);
	}

	protected NeedleEntity(EntityType<? extends NeedleEntity> type, LivingEntity owner, Level world) {
		this(type, owner.getX(), owner.getEyeY() - 0.10000000149011612D, owner.getZ(), world);
		this.setOwner(owner);
		if (owner instanceof Player)
			this.pickup = AbstractArrow.Pickup.DISALLOWED;
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

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return EntityPacket.createPacket(this);
	}

	@Override
	protected void doPostHurtEffects(LivingEntity living) {
		super.doPostHurtEffects(living);
		this.remove(Entity.RemovalReason.DISCARDED);
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
		if (this.ticksInAir >= 40)
			this.remove(Entity.RemovalReason.DISCARDED);
		var isInsideWaterBlock = this.getCommandSenderWorld().isWaterAt(blockPosition());
		spawnLightSource(isInsideWaterBlock);
		var world = this.getCommandSenderWorld();
		var livingEntities = (List<Monster>) world.getEntitiesOfClass(Monster.class, new AABB(this.getX() - 6.0, this.getY() - 6.0, this.getZ() - 6.0, this.getX() + 6.0, this.getY() + 6.0, this.getZ() + 6.0), entity1 -> entity1 != ((AbstractArrow) this).getOwner());
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

	private void spawnLightSource(boolean isInWaterBlock) {
		if (lightBlockPos == null) {
			lightBlockPos = findFreeSpace(this.getCommandSenderWorld(), blockPosition(), 2);
			if (lightBlockPos == null)
				return;
			this.getCommandSenderWorld().setBlockAndUpdate(lightBlockPos, AzureLibMod.TICKING_LIGHT_BLOCK.defaultBlockState());
		} else if (checkDistance(lightBlockPos, blockPosition(), 2)) {
			var blockEntity = this.getCommandSenderWorld().getBlockEntity(lightBlockPos);
			if (blockEntity instanceof TickingLightEntity) {
				((TickingLightEntity) blockEntity).refresh(isInWaterBlock ? 20 : 0);
			} else
				lightBlockPos = null;
		} else
			lightBlockPos = null;
	}

	private boolean checkDistance(BlockPos blockPosA, BlockPos blockPosB, int distance) {
		return Math.abs(blockPosA.getX() - blockPosB.getX()) <= distance && Math.abs(blockPosA.getY() - blockPosB.getY()) <= distance && Math.abs(blockPosA.getZ() - blockPosB.getZ()) <= distance;
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
					if (state.isAir() || state.getBlock().equals(AzureLibMod.TICKING_LIGHT_BLOCK))
						return offsetPos;
				}

		return null;
	}

	@Override
	protected boolean tryPickup(Player player) {
		return false;
	}

	public void initFromStack(ItemStack stack) {
		if (stack.getItem() == HaloItems.NEEDLES) {
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
		return HaloSounds.NEEDLER;
	}

	@Override
	protected void onHitBlock(BlockHitResult blockHitResult) {
		super.onHitBlock(blockHitResult);
		if (!this.getCommandSenderWorld().isClientSide) {
			this.remove(Entity.RemovalReason.DISCARDED);
		}
		this.setSoundEvent(HaloSounds.NEEDLER);
	}

	@Override
	protected void onHitEntity(EntityHitResult entityHitResult) {
		var entity = entityHitResult.getEntity();
		if (entityHitResult.getType() != HitResult.Type.ENTITY || !((EntityHitResult) entityHitResult).getEntity().is(entity))
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
				if (!this.getCommandSenderWorld().isClientSide) {
					livingEntity.setArrowCount(livingEntity.getArrowCount() + 1);
				}
				if (!this.getCommandSenderWorld().isClientSide && entity2 instanceof LivingEntity) {
					EnchantmentHelper.doPostHurtEffects(livingEntity, entity2);
					EnchantmentHelper.doPostDamageEffects((LivingEntity) entity2, livingEntity);
				}

				this.doPostHurtEffects(livingEntity);
				if (entity2 != null && livingEntity != entity2 && livingEntity instanceof Player && entity2 instanceof ServerPlayer && !this.isSilent())
					((ServerPlayer) entity2).connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.ARROW_HIT_PLAYER, ClientboundGameEventPacket.DEMO_PARAM_INTRO));
				this.remove(RemovalReason.KILLED);
			}
		} else {
			if (!this.getCommandSenderWorld().isClientSide)
				this.remove(RemovalReason.KILLED);
		}
	}

	@Override
	public ItemStack getPickupItem() {
		return new ItemStack(HaloItems.NEEDLES);
	}

	@Override
	@Environment(EnvType.CLIENT)
	public boolean shouldRenderAtSqrDistance(double distance) {
		return true;
	}

}