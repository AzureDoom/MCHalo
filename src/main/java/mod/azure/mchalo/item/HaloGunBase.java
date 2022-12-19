package mod.azure.mchalo.item;

import java.util.List;

import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.blocks.blockentity.TickingLightEntity;
import mod.azure.mchalo.entity.projectiles.BulletEntity;
import mod.azure.mchalo.entity.projectiles.GrenadeEntity;
import mod.azure.mchalo.entity.projectiles.NeedleEntity;
import mod.azure.mchalo.entity.projectiles.PlasmaEntity;
import mod.azure.mchalo.entity.projectiles.PlasmaGEntity;
import mod.azure.mchalo.entity.projectiles.RocketEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.animation.Animation.LoopType;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public abstract class HaloGunBase extends Item implements GeoItem {

	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private BlockPos lightBlockPos = null;

	public HaloGunBase(Settings settings) {
		super(settings);
	}

	public void removeAmmo(Item ammo, PlayerEntity playerEntity) {
		if (!playerEntity.isCreative()) {
			for (ItemStack item : playerEntity.getInventory().offHand) {
				if (item.getItem() == ammo) {
					item.decrement(1);
					break;
				}
				for (ItemStack item1 : playerEntity.getInventory().main) {
					if (item1.getItem() == ammo) {
						item1.decrement(1);
						break;
					}
				}
			}
		}
	}

	@Override
	public boolean hasGlint(ItemStack stack) {
		return false;
	}

	@Override
	public boolean isEnchantable(ItemStack stack) {
		return false;
	}

	@Override
	public boolean canRepair(ItemStack stack, ItemStack ingredient) {
		return super.canRepair(stack, ingredient);
	}

	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		tooltip.add(Text
				.translatable(
						"Ammo: " + (stack.getMaxDamage() - stack.getDamage() - 1) + " / " + (stack.getMaxDamage() - 1))
				.formatted(Formatting.ITALIC));
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		user.setCurrentHand(hand);
		return TypedActionResult.consume(user.getStackInHand(hand));
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "shoot_controller", event -> PlayState.CONTINUE)
				.triggerableAnim("firing", RawAnimation.begin().then("firing", LoopType.PLAY_ONCE)));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}

	public BulletEntity createBullet(World worldIn, ItemStack stack, LivingEntity shooter, Float damage) {
		float j = EnchantmentHelper.getLevel(Enchantments.POWER, stack);
		BulletEntity arrowentity = new BulletEntity(worldIn, shooter, j > 0 ? (damage + (j * 1.5F + 0.5F)) : damage);
		return arrowentity;
	}

	public NeedleEntity createNeedle(World worldIn, ItemStack stack, LivingEntity shooter, Float damage) {
		float j = EnchantmentHelper.getLevel(Enchantments.POWER, stack);
		NeedleEntity arrowentity = new NeedleEntity(worldIn, shooter, j > 0 ? (damage + (j * 1.5F + 0.5F)) : damage);
		return arrowentity;
	}

	public RocketEntity createRocket(World world, ItemStack stack, LivingEntity shooter) {
		RocketEntity arrowentity = new RocketEntity(world, shooter);
		return arrowentity;
	}

	public PlasmaEntity createPlamsa(World worldIn, ItemStack stack, LivingEntity shooter, Float damage) {
		float j = EnchantmentHelper.getLevel(Enchantments.POWER, stack);
		PlasmaEntity arrowentity = new PlasmaEntity(worldIn, shooter, j > 0 ? (damage + (j * 1.5F + 0.5F)) : damage);
		return arrowentity;
	}

	public PlasmaGEntity createGPlamsa(World worldIn, ItemStack stack, LivingEntity shooter, Float damage) {
		float j = EnchantmentHelper.getLevel(Enchantments.POWER, stack);
		PlasmaGEntity arrowentity = new PlasmaGEntity(worldIn, shooter, j > 0 ? (damage + (j * 1.5F + 0.5F)) : damage);
		return arrowentity;
	}

	public GrenadeEntity createGrenade(World worldIn, ItemStack stack, LivingEntity shooter) {
		GrenadeEntity arrowentity = new GrenadeEntity(worldIn, shooter, false);
		return arrowentity;
	}

	protected void spawnLightSource(Entity entity, boolean isInWaterBlock) {
		if (lightBlockPos == null) {
			lightBlockPos = findFreeSpace(entity.world, entity.getBlockPos(), 2);
			if (lightBlockPos == null)
				return;
			entity.world.setBlockState(lightBlockPos, MCHaloMod.TICKING_LIGHT_BLOCK.getDefaultState());
		} else if (checkDistance(lightBlockPos, entity.getBlockPos(), 2)) {
			BlockEntity blockEntity = entity.world.getBlockEntity(lightBlockPos);
			if (blockEntity instanceof TickingLightEntity) {
				((TickingLightEntity) blockEntity).refresh(isInWaterBlock ? 20 : 0);
			} else
				lightBlockPos = null;
		} else
			lightBlockPos = null;
	}

	private boolean checkDistance(BlockPos blockPosA, BlockPos blockPosB, int distance) {
		return Math.abs(blockPosA.getX() - blockPosB.getX()) <= distance
				&& Math.abs(blockPosA.getY() - blockPosB.getY()) <= distance
				&& Math.abs(blockPosA.getZ() - blockPosB.getZ()) <= distance;
	}

	private BlockPos findFreeSpace(World world, BlockPos blockPos, int maxDistance) {
		if (blockPos == null)
			return null;

		int[] offsets = new int[maxDistance * 2 + 1];
		offsets[0] = 0;
		for (int i = 2; i <= maxDistance * 2; i += 2) {
			offsets[i - 1] = i / 2;
			offsets[i] = -i / 2;
		}
		for (int x : offsets)
			for (int y : offsets)
				for (int z : offsets) {
					BlockPos offsetPos = blockPos.add(x, y, z);
					BlockState state = world.getBlockState(offsetPos);
					if (state.isAir() || state.getBlock().equals(MCHaloMod.TICKING_LIGHT_BLOCK))
						return offsetPos;
				}

		return null;
	}

}