package mod.azure.mchalo.item;

import mod.azure.azurelib.items.BaseGunItem;
import mod.azure.mchalo.entity.projectiles.BulletEntity;
import mod.azure.mchalo.entity.projectiles.GrenadeEntity;
import mod.azure.mchalo.entity.projectiles.NeedleEntity;
import mod.azure.mchalo.entity.projectiles.PlasmaEntity;
import mod.azure.mchalo.entity.projectiles.PlasmaGEntity;
import mod.azure.mchalo.entity.projectiles.RocketEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class HaloGunBase extends BaseGunItem {

	public HaloGunBase(Settings settings) {
		super(settings);
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

}