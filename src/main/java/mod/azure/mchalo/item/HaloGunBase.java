package mod.azure.mchalo.item;

import mod.azure.azurelib.items.BaseGunItem;
import mod.azure.mchalo.entity.projectiles.BulletEntity;
import mod.azure.mchalo.entity.projectiles.GrenadeEntity;
import mod.azure.mchalo.entity.projectiles.NeedleEntity;
import mod.azure.mchalo.entity.projectiles.PlasmaEntity;
import mod.azure.mchalo.entity.projectiles.PlasmaGEntity;
import mod.azure.mchalo.entity.projectiles.RocketEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

public abstract class HaloGunBase extends BaseGunItem {

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

}