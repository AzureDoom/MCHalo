package mod.azure.mchalo.entity.projectiles;

import mod.azure.mchalo.network.EntityPacket;
import mod.azure.mchalo.util.HaloItems;
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
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class PlasmaEntity extends PersistentProjectileEntity implements IAnimatable {

	protected int timeInAir;
	protected boolean inAir;
	private int ticksInAir;
	private static float bulletdamage;

	public PlasmaEntity(EntityType<? extends PlasmaEntity> entityType, World world) {
		super(entityType, world);
		this.pickupType = PersistentProjectileEntity.PickupPermission.DISALLOWED;
	}

	public PlasmaEntity(World world, LivingEntity owner, Float damage) {
		super(ProjectilesEntityRegister.PLASMA, owner, world);
		bulletdamage = damage;
	}

	protected PlasmaEntity(EntityType<? extends PlasmaEntity> type, double x, double y, double z, World world) {
		this(type, world);
	}

	protected PlasmaEntity(EntityType<? extends PlasmaEntity> type, LivingEntity owner, World world) {
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
		data.addAnimationController(new AnimationController<PlasmaEntity>(this, "controller", 0, this::predicate));
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
		boolean bl = this.isNoClip();
		Vec3d vec3d = this.getVelocity();
		if (this.prevPitch == 0.0F && this.prevYaw == 0.0F) {
			double f = vec3d.horizontalLength();
			this.setYaw((float) (MathHelper.atan2(vec3d.x, vec3d.z) * 57.2957763671875D));
			this.setPitch((float) (MathHelper.atan2(vec3d.y, f) * 57.2957763671875D));
			this.prevYaw = this.getYaw();
			this.prevPitch = this.getPitch();
		}
		if (this.age >= 40) {
			this.remove(Entity.RemovalReason.DISCARDED);
		}
		if (this.inAir && !bl) {
			this.age();
			++this.timeInAir;
		} else {
			this.timeInAir = 0;
			Vec3d vec3d3 = this.getPos();
			Vec3d vector3d3 = vec3d3.add(vec3d);
			HitResult hitResult = this.world.raycast(new RaycastContext(vec3d3, vector3d3,
					RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this));
			if (((HitResult) hitResult).getType() != HitResult.Type.MISS) {
				vector3d3 = ((HitResult) hitResult).getPos();
			}
			while (!this.isRemoved()) {
				EntityHitResult entityHitResult = this.getEntityCollision(vec3d3, vector3d3);
				if (entityHitResult != null) {
					hitResult = entityHitResult;
				}
				if (hitResult != null && ((HitResult) hitResult).getType() == HitResult.Type.ENTITY) {
					Entity entity = ((EntityHitResult) hitResult).getEntity();
					Entity entity2 = this.getOwner();
					if (entity instanceof PlayerEntity && entity2 instanceof PlayerEntity
							&& !((PlayerEntity) entity2).shouldDamagePlayer((PlayerEntity) entity)) {
						hitResult = null;
						entityHitResult = null;
					}
				}
				if (hitResult != null && !bl) {
					this.onCollision((HitResult) hitResult);
					this.velocityDirty = true;
				}
				if (entityHitResult == null || this.getPierceLevel() <= 0) {
					break;
				}
				hitResult = null;
			}
			vec3d = this.getVelocity();
			double d = vec3d.x;
			double e = vec3d.y;
			double g = vec3d.z;
			double h = this.getX() + d;
			double j = this.getY() + e;
			double k = this.getZ() + g;
			double l = vec3d.horizontalLength();
			if (bl) {
				this.setYaw((float) (MathHelper.atan2(-e, -g) * 57.2957763671875D));
			} else {
				this.setYaw((float) (MathHelper.atan2(e, g) * 57.2957763671875D));
			}
			this.setPitch((float) (MathHelper.atan2(e, l) * 57.2957763671875D));
			this.setPitch(updateRotation(this.prevPitch, this.getPitch()));
			this.setYaw(updateRotation(this.prevYaw, this.getYaw()));
			float m = 0.99F;

			this.setVelocity(vec3d.multiply((double) m));
			if (!this.hasNoGravity() && !bl) {
				Vec3d vec3d5 = this.getVelocity();
				this.setVelocity(vec3d5.x, vec3d5.y - 0.05000000074505806D, vec3d5.z);
			}
			this.updatePosition(h, j, k);
			this.checkBlockCollision();
		}
	}

	public void initFromStack(ItemStack stack) {
		if (stack.getItem() == HaloItems.SNIPER_ROUND) {
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
		return SoundEvents.ITEM_ARMOR_EQUIP_IRON;
	}

	@Override
	protected void onBlockHit(BlockHitResult blockHitResult) {
		super.onBlockHit(blockHitResult);
		if (!this.world.isClient) {
			this.remove(Entity.RemovalReason.DISCARDED);
		}
		this.setSound(SoundEvents.ITEM_ARMOR_EQUIP_IRON);
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
				if (!this.world.isClient && entity2 instanceof LivingEntity) {
					EnchantmentHelper.onUserDamaged(livingEntity, entity2);
					EnchantmentHelper.onTargetDamaged((LivingEntity) entity2, livingEntity);
				}

				this.onHit(livingEntity);
				if (entity2 != null && livingEntity != entity2 && livingEntity instanceof PlayerEntity
						&& entity2 instanceof ServerPlayerEntity && !this.isSilent()) {
					((ServerPlayerEntity) entity2).networkHandler.sendPacket(
							new GameStateChangeS2CPacket(GameStateChangeS2CPacket.PROJECTILE_HIT_PLAYER, 0.0F));
				}
			}
		} else {
			if (!this.world.isClient) {
				this.remove(Entity.RemovalReason.DISCARDED);
			}
		}
	}

	@Override
	public ItemStack asItemStack() {
		return new ItemStack(HaloItems.SNIPER_ROUND);
	}

	@Override
	@Environment(EnvType.CLIENT)
	public boolean shouldRender(double distance) {
		return true;
	}

}