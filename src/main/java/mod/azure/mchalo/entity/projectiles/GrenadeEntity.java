package mod.azure.mchalo.entity.projectiles;

import mod.azure.mchalo.config.HaloConfig;
import mod.azure.mchalo.network.HaloEntityPacket;
import mod.azure.mchalo.util.HaloItems;
import mod.azure.mchalo.util.ProjectilesEntityRegister;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class GrenadeEntity extends PersistentProjectileEntity implements IAnimatable {

	protected int timeInAir;
	protected boolean inAir;
	protected String type;
	private int ticksInAir;
	private static final TrackedData<Boolean> SPINNING = DataTracker.registerData(GrenadeEntity.class,
			TrackedDataHandlerRegistry.BOOLEAN);

	public GrenadeEntity(EntityType<? extends GrenadeEntity> entityType, World world) {
		super(entityType, world);
		this.pickupType = PersistentProjectileEntity.PickupPermission.DISALLOWED;
	}

	public GrenadeEntity(World world, LivingEntity owner) {
		super(ProjectilesEntityRegister.GRENADE, owner, world);
	}

	protected GrenadeEntity(EntityType<? extends GrenadeEntity> type, double x, double y, double z, World world) {
		this(type, world);
	}

	protected GrenadeEntity(EntityType<? extends GrenadeEntity> type, LivingEntity owner, World world) {
		this(type, owner.getX(), owner.getEyeY() - 0.10000000149011612D, owner.getZ(), world);
		this.setOwner(owner);
		this.pickupType = PersistentProjectileEntity.PickupPermission.DISALLOWED;
	}

	public GrenadeEntity(World world, LivingEntity user, boolean spinning) {
		super(ProjectilesEntityRegister.GRENADE, user, world);
		this.dataTracker.set(SPINNING, spinning);
	}

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(SPINNING, false);
	}

	public boolean isSpinning() {
		return (Boolean) this.dataTracker.get(SPINNING);
	}

	public void setSpinning(boolean spin) {
		this.dataTracker.set(SPINNING, spin);
	}

	private AnimationFactory factory = new AnimationFactory(this);

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		event.getController().setAnimation(new AnimationBuilder().addAnimation("spin", true));
		return PlayState.CONTINUE;
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController<GrenadeEntity>(this, "controller", 0, this::predicate));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	@Override
	public Packet<?> createSpawnPacket() {
		return HaloEntityPacket.createPacket(this);
	}

	@Override
	public void remove(RemovalReason reason) {
		AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(this.world, this.getX(), this.getY(),
				this.getZ());
		areaeffectcloudentity.setParticleType(ParticleTypes.EXPLOSION);
		areaeffectcloudentity.setRadius(HaloConfig.mauler_bullet_damage + 2);
		areaeffectcloudentity.setDuration(1);
		areaeffectcloudentity.updatePosition(this.getX(), this.getEyeY(), this.getZ());
		this.world.spawnEntity(areaeffectcloudentity);
		super.remove(reason);
	}

	@Override
	public void age() {
		++this.ticksInAir;
		if (this.ticksInAir >= 80) {
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
	}

	public void initFromStack(ItemStack stack) {
		if (stack.getItem() == HaloItems.GRENADE) {
		}
	}

	public SoundEvent hitSound = this.getHitSound();

	@Override
	public void setSound(SoundEvent soundIn) {
		this.hitSound = soundIn;
	}

	@Override
	protected SoundEvent getHitSound() {
		return SoundEvents.PARTICLE_SOUL_ESCAPE;
	}

	@Override
	protected void onBlockHit(BlockHitResult blockHitResult) {
		super.onBlockHit(blockHitResult);
		if (!this.world.isClient) {
			if (this.age >= 45) {
				this.explode();
				this.dataTracker.set(SPINNING, false);
				this.remove(Entity.RemovalReason.DISCARDED);
			}
		}
		this.setSound(SoundEvents.ENTITY_GENERIC_EXPLODE);
	}

	@Override
	protected void onEntityHit(EntityHitResult entityHitResult) {
		super.onEntityHit(entityHitResult);
		if (!this.world.isClient) {
			this.explode();
			if (this.age >= 45) {
				this.explode();
				this.dataTracker.set(SPINNING, false);
				this.remove(Entity.RemovalReason.DISCARDED);
			}
		}
	}

	protected void explode() {
		this.world.createExplosion(this, this.getX(), this.getBodyY(0.0625D), this.getZ(),
				HaloConfig.mauler_bullet_damage, (HaloConfig.grenades_cause_fire ? true : false),
				(HaloConfig.grenades_break_blocks ? Explosion.DestructionType.BREAK : Explosion.DestructionType.NONE));
	}

	@Override
	public ItemStack asItemStack() {
		return new ItemStack(Items.AIR);
	}

	@Override
	@Environment(EnvType.CLIENT)
	public boolean shouldRender(double distance) {
		return true;
	}

	@Override
	public boolean doesRenderOnFire() {
		return false;
	}

}