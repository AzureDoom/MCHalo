package mod.azure.mchalo.entity.projectiles;

import java.util.List;

import mod.azure.mchalo.network.EntityPacket;
import mod.azure.mchalo.util.HaloItems;
import mod.azure.mchalo.util.HaloSounds;
import mod.azure.mchalo.util.ProjectilesEntityRegister;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class NeedleEntity extends PersistentProjectileEntity implements IAnimatable {

	protected int timeInAir;
	protected boolean inAir;
	private int ticksInAir;
	private static float bulletdamage;

	public NeedleEntity(EntityType<? extends NeedleEntity> entityType, World world) {
		super(entityType, world);
		this.pickupType = PersistentProjectileEntity.PickupPermission.DISALLOWED;
	}

	public NeedleEntity(World world, LivingEntity owner, Float damage) {
		super(ProjectilesEntityRegister.NEEDLE, owner, world);
		bulletdamage = damage;
	}

	protected NeedleEntity(EntityType<? extends NeedleEntity> type, double x, double y, double z, World world) {
		this(type, world);
	}

	protected NeedleEntity(EntityType<? extends NeedleEntity> type, LivingEntity owner, World world) {
		this(type, owner.getX(), owner.getEyeY() - 0.10000000149011612D, owner.getZ(), world);
		this.setOwner(owner);
		if (owner instanceof PlayerEntity) {
			this.pickupType = PersistentProjectileEntity.PickupPermission.ALLOWED;
		}

	}

	private AnimationFactory factory = new AnimationFactory(this);

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		return PlayState.CONTINUE;
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController<NeedleEntity>(this, "controller", 0, this::predicate));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	@Override
	public Packet<?> createSpawnPacket() {
		return EntityPacket.createPacket(this);
	}

	@Override
	protected void onHit(LivingEntity living) {
		super.onHit(living);
		this.remove(Entity.RemovalReason.DISCARDED);
		if (!(living instanceof PlayerEntity)) {
			living.timeUntilRegen = 0;
			living.setVelocity(0, 0, 0);
		}
	}

	@Override
	public void age() {
		++this.ticksInAir;
		if (this.ticksInAir >= 40) {
			this.remove(Entity.RemovalReason.DISCARDED);
		}
	}

	@Override
	public void setVelocity(double x, double y, double z, float speed, float divergence) {
		super.setVelocity(x, y, z, speed, divergence);
		this.ticksInAir = 0;
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		tag.putShort("life", (short) this.ticksInAir);
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		this.ticksInAir = tag.getShort("life");
	}

	@Override
	public void tick() {
		super.tick();
		if (this.age >= 80) {
			this.remove(Entity.RemovalReason.DISCARDED);
		}
		final World world = this.world;
		final List<LivingEntity> livingEntities = (List<LivingEntity>) world.getEntitiesByClass(
				LivingEntity.class, new Box(this.getX() - 6.0, this.getY() - 6.0, this.getZ() - 6.0, this.getX() + 6.0,
						this.getY() + 6.0, this.getZ() + 6.0),
				entity1 -> entity1 != ((PersistentProjectileEntity) this).getOwner());
		if (!livingEntities.isEmpty()) {
			final Entity first = livingEntities.get(0);
			final Vec3d entityPos = new Vec3d(first.getX(), first.getY() + first.getStandingEyeHeight(), first.getZ());
			final Vec3d distance = entityPos.subtract(this.getX(), this.getY() + this.getStandingEyeHeight(),
					this.getZ());
			final Vec3d entityDirect = distance.normalize();
			final Vec3d arrowDirect = this.getVelocity().normalize();
			final Vec3d newPath = entityDirect.add(arrowDirect.multiply(4.0, 4.0, 4.0)).normalize();
			final double speed = this.getVelocity().length();
			this.setVelocity(newPath.multiply(speed, speed, speed));
		}
	}

	@Override
	protected boolean tryPickup(PlayerEntity player) {
		return false;
	}

	public void initFromStack(ItemStack stack) {
		if (stack.getItem() == HaloItems.NEEDLES) {
		}
	}

	@Override
	public boolean hasNoGravity() {
		return this.isSubmergedInWater() ? false : true;
	}

	public SoundEvent hitSound = this.getHitSound();

	@Override
	public void setSound(SoundEvent soundIn) {
		this.hitSound = soundIn;
	}

	@Override
	protected SoundEvent getHitSound() {
		return HaloSounds.NEEDLER;
	}

	@Override
	protected void onBlockHit(BlockHitResult blockHitResult) {
		super.onBlockHit(blockHitResult);
		if (!this.world.isClient) {
			this.remove(Entity.RemovalReason.DISCARDED);
		}
		this.setSound(HaloSounds.NEEDLER);
	}

	@Override
	protected void onEntityHit(EntityHitResult entityHitResult) {
		Entity entity = entityHitResult.getEntity();
		if (entityHitResult.getType() != HitResult.Type.ENTITY
				|| !((EntityHitResult) entityHitResult).getEntity().isPartOf(entity)) {
			if (!this.world.isClient) {
				this.remove(Entity.RemovalReason.DISCARDED);
			}
		}
		Entity entity2 = this.getOwner();
		DamageSource damageSource2;
		if (entity2 == null) {
			damageSource2 = DamageSource.arrow(this, this);
		} else {
			damageSource2 = DamageSource.arrow(this, entity2);
			if (entity2 instanceof LivingEntity) {
				((LivingEntity) entity2).onAttacking(entity);
			}
		}
		if (entity.damage(damageSource2, bulletdamage)) {
			if (entity instanceof LivingEntity) {
				LivingEntity livingEntity = (LivingEntity) entity;
	            if (!this.world.isClient) {
	                livingEntity.setStuckArrowCount(livingEntity.getStuckArrowCount() + 1);
	             }
				if (!this.world.isClient && entity2 instanceof LivingEntity) {
					EnchantmentHelper.onUserDamaged(livingEntity, entity2);
					EnchantmentHelper.onTargetDamaged((LivingEntity) entity2, livingEntity);
				}

				this.onHit(livingEntity);
				if (entity2 != null && livingEntity != entity2 && livingEntity instanceof PlayerEntity
						&& entity2 instanceof ServerPlayerEntity && !this.isSilent()) {
					((ServerPlayerEntity) entity2).networkHandler.sendPacket(new GameStateChangeS2CPacket(
							GameStateChangeS2CPacket.PROJECTILE_HIT_PLAYER, GameStateChangeS2CPacket.DEMO_OPEN_SCREEN));
				}
				this.discard();
			}
		} else {
			if (!this.world.isClient) {
				this.discard();
			}
		}
	}

	@Override
	public ItemStack asItemStack() {
		return new ItemStack(HaloItems.NEEDLES);
	}

	@Override
	@Environment(EnvType.CLIENT)
	public boolean shouldRender(double distance) {
		return true;
	}

}