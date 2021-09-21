package mod.azure.mchalo.item;

import java.util.List;

import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.config.HaloConfig.Weapons;
import mod.azure.mchalo.entity.projectiles.BulletEntity;
import mod.azure.mchalo.entity.projectiles.NeedleEntity;
import mod.azure.mchalo.entity.projectiles.RocketEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.network.GeckoLibNetwork;
import software.bernie.geckolib3.network.ISyncable;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class HaloGunBase extends Item implements IAnimatable, ISyncable {

	public AnimationFactory factory = new AnimationFactory(this);
	public static Weapons config = MCHaloMod.config.weapons;
	public String controllerName = "controller";
	public static final int ANIM_OPEN = 0;

	public HaloGunBase(Settings settings) {
		super(settings);
		GeckoLibNetwork.registerSyncable(this);
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
		tooltip.add(new TranslatableText(
				"Ammo: " + (stack.getMaxDamage() - stack.getDamage() - 1) + " / " + (stack.getMaxDamage() - 1))
						.formatted(Formatting.ITALIC));
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		user.setCurrentHand(hand);
		return TypedActionResult.consume(user.getStackInHand(hand));
	}

	public <P extends Item & IAnimatable> PlayState predicate(AnimationEvent<P> event) {
		return PlayState.CONTINUE;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController(this, controllerName, 1, this::predicate));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	@Override
	public void onAnimationSync(int id, int state) {
		if (state == ANIM_OPEN) {
			final AnimationController<?> controller = GeckoLibUtil.getControllerForID(this.factory, id, controllerName);
			if (controller.getAnimationState() == AnimationState.Stopped) {
				controller.markNeedsReload();
				controller.setAnimation(new AnimationBuilder().addAnimation("firing", false));
			}
		}
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

}