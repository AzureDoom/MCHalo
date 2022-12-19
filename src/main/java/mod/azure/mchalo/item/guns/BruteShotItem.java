package mod.azure.mchalo.item.guns;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import io.netty.buffer.Unpooled;
import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.client.ClientInit;
import mod.azure.mchalo.client.render.BruteShotRender;
import mod.azure.mchalo.config.HaloConfig;
import mod.azure.mchalo.entity.projectiles.GrenadeEntity;
import mod.azure.mchalo.item.HaloGunBase;
import mod.azure.mchalo.util.HaloItems;
import mod.azure.mchalo.util.HaloSounds;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.client.RenderProvider;

public class BruteShotItem extends HaloGunBase {

	private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);
	
	public BruteShotItem() {
		super(new Item.Settings().maxCount(1).maxDamage(HaloConfig.bruteshot_max_ammo + 1));
		SingletonGeoAnimatable.registerSyncedAnimatable(this);
	}

	@Override
	public void usageTick(World worldIn, LivingEntity entityLiving, ItemStack stack, int count) {
		if (entityLiving instanceof PlayerEntity) {
			PlayerEntity playerentity = (PlayerEntity) entityLiving;
			if (stack.getDamage() < (stack.getMaxDamage() - 1)
					&& !playerentity.getItemCooldownManager().isCoolingDown(this)) {
				playerentity.getItemCooldownManager().set(this, 12);
				if (!worldIn.isClient) {
					GrenadeEntity abstractarrowentity = createGrenade(worldIn, stack, playerentity);
					abstractarrowentity.setVelocity(playerentity, playerentity.getPitch(), playerentity.getYaw(), 0.0F,
							0.5F * 3.0F, 1.0F);
					abstractarrowentity.refreshPositionAndAngles(entityLiving.getX(), entityLiving.getBodyY(0.95),
							entityLiving.getZ(), 0, 0);
					stack.damage(1, entityLiving, p -> p.sendToolBreakStatus(entityLiving.getActiveHand()));
					worldIn.spawnEntity(abstractarrowentity);
					worldIn.playSound((PlayerEntity) null, playerentity.getX(), playerentity.getY(),
							playerentity.getZ(), HaloSounds.BRUTESHOT, SoundCategory.PLAYERS, 0.5F,
							1.0F / (worldIn.random.nextFloat() * 0.4F + 1.2F) + 1F * 0.5F);
					triggerAnim(playerentity, GeoItem.getOrAssignId(stack, (ServerWorld) worldIn), "shoot_controller",
							"firing");
				}
				boolean isInsideWaterBlock = playerentity.world.isWater(playerentity.getBlockPos());
				spawnLightSource(entityLiving, isInsideWaterBlock);
			}
		}
	}

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		if (world.isClient) {
			if (((PlayerEntity) entity).getMainHandStack().getItem() instanceof BruteShotItem) {
				if (ClientInit.reload.isPressed() && selected) {
					PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
					passedData.writeBoolean(true);
					ClientPlayNetworking.send(MCHaloMod.BRUTESHOT, passedData);
				}
			}
		}
	}

	public void reload(PlayerEntity user, Hand hand) {
		if (user.getStackInHand(hand).getItem() instanceof BruteShotItem) {
			while (!user.isCreative() && user.getStackInHand(hand).getDamage() != 0
					&& user.getInventory().count(HaloItems.GRENADE) > 0) {
				removeAmmo(HaloItems.GRENADE, user);
				user.getStackInHand(hand).damage(-HaloConfig.bruteshot_mag_size, user,
						s -> user.sendToolBreakStatus(hand));
				user.getStackInHand(hand).setBobbingAnimationTime(3);
				user.getEntityWorld().playSound((PlayerEntity) null, user.getX(), user.getY(), user.getZ(),
						HaloSounds.BRUTESHOTRELOAD, SoundCategory.PLAYERS, 1.00F, 1.0F);
			}
		}
	}

	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		float j = EnchantmentHelper.getLevel(Enchantments.POWER, stack);
		super.appendTooltip(stack, world, tooltip, context);
		tooltip.add(Text.translatable("Damage: " + (j > 0 ? (HaloConfig.bruteshot_bullet_damage + (j * 1.5F + 0.5F))
				: HaloConfig.bruteshot_bullet_damage)).formatted(Formatting.ITALIC));
	}

	@Override
	public Rarity getRarity(ItemStack stack) {
		return Rarity.RARE;
	}

	@Override
	public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity miner) {
		if (miner instanceof PlayerEntity) {
			PlayerEntity playerentity = (PlayerEntity) miner;
			if (stack.getDamage() < (stack.getMaxDamage() - 1)) {
				if (!playerentity.getItemCooldownManager().isCoolingDown(this)
						&& playerentity.getMainHandStack().getItem() instanceof BruteShotItem) {
					playerentity.getItemCooldownManager().set(this, 20);
					final Box aabb = new Box(playerentity.getBlockPos().up()).expand(2D, 1D, 2D);
					playerentity.getEntityWorld().getOtherEntities(playerentity, aabb)
							.forEach(e -> doDamage(playerentity, e));
					stack.damage(1, playerentity, p -> p.sendToolBreakStatus(playerentity.getActiveHand()));
				}
			}
		}
		return super.postHit(stack, target, miner);
	}

	private void doDamage(LivingEntity user, Entity target) {
		if (target instanceof LivingEntity) {
			target.timeUntilRegen = 0;
			target.damage(DamageSource.player((PlayerEntity) user), 9F);
		}
	}

	@Override
	public void createRenderer(Consumer<Object> consumer) {
		consumer.accept(new RenderProvider() {
			private final BruteShotRender renderer = new BruteShotRender();

			@Override
			public BuiltinModelItemRenderer getCustomRenderer() {
				return this.renderer;
			}
		});
	}

	@Override
	public Supplier<Object> getRenderProvider() {
		return this.renderProvider;
	}

}
