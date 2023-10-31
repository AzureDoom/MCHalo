package mod.azure.mchalo.item;

import mod.azure.azurelib.entities.TickingLightEntity;
import mod.azure.azurelib.items.BaseGunItem;
import mod.azure.azurelib.platform.Services;
import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.entity.projectiles.*;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public abstract class HaloGunBase extends BaseGunItem {

	private BlockPos lightBlockPos = null;
	public HaloGunBase(Properties settings) {
		super(settings);
	}

	public BulletEntity createBullet(Level worldIn, ItemStack stack, LivingEntity shooter, Float damage) {
		float j = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
		BulletEntity arrowentity = new BulletEntity(worldIn, shooter, j > 0 ? (damage + (j * 1.5F + 0.5F)) : damage);
		return arrowentity;
	}

	public NeedleEntity createNeedle(Level worldIn, ItemStack stack, LivingEntity shooter, Float damage) {
		float j = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
		NeedleEntity arrowentity = new NeedleEntity(worldIn, shooter, j > 0 ? (damage + (j * 1.5F + 0.5F)) : damage);
		return arrowentity;
	}

	public RocketEntity createRocket(Level world, ItemStack stack, LivingEntity shooter) {
		RocketEntity arrowentity = new RocketEntity(world, shooter);
		return arrowentity;
	}

	public PlasmaEntity createPlamsa(Level worldIn, ItemStack stack, LivingEntity shooter, Float damage) {
		float j = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
		PlasmaEntity arrowentity = new PlasmaEntity(worldIn, shooter, j > 0 ? (damage + (j * 1.5F + 0.5F)) : damage);
		return arrowentity;
	}

	public PlasmaGEntity createGPlamsa(Level worldIn, ItemStack stack, LivingEntity shooter, Float damage) {
		float j = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
		PlasmaGEntity arrowentity = new PlasmaGEntity(worldIn, shooter, j > 0 ? (damage + (j * 1.5F + 0.5F)) : damage);
		return arrowentity;
	}

	public GrenadeEntity createGrenade(Level worldIn, ItemStack stack, LivingEntity shooter) {
		GrenadeEntity arrowentity = new GrenadeEntity(worldIn, shooter, false);
		return arrowentity;
	}

	@Override
	protected void spawnLightSource(Entity entity, boolean isInWaterBlock) {
		if (lightBlockPos == null) {
			lightBlockPos = findFreeSpace(entity.level(), entity.blockPosition(), 2);
			if (lightBlockPos == null)
				return;
			entity.level().setBlockAndUpdate(lightBlockPos, Services.PLATFORM.getTickingLightBlock().defaultBlockState());
		} else if (checkDistance(lightBlockPos, entity.blockPosition(), 2)) {
			BlockEntity blockEntity = entity.level().getBlockEntity(lightBlockPos);
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

		int[] offsets = new int[maxDistance * 2 + 1];
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
}