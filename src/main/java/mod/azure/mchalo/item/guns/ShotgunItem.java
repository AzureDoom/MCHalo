package mod.azure.mchalo.item.guns;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import io.netty.buffer.Unpooled;
import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.client.ClientInit;
import mod.azure.mchalo.client.render.ShotgunRender;
import mod.azure.mchalo.config.HaloConfig;
import mod.azure.mchalo.entity.projectiles.BulletEntity;
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
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.client.RenderProvider;

public class ShotgunItem extends HaloGunBase {

	private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);
	
	public ShotgunItem() {
		super(new Item.Settings().maxCount(1).maxDamage(HaloConfig.shotgun_max_ammo + 1));
		SingletonGeoAnimatable.registerSyncedAnimatable(this);
	}

	@Override
	public void usageTick(World worldIn, LivingEntity entityLiving, ItemStack stack, int count) {
		if (entityLiving instanceof PlayerEntity) {
			PlayerEntity playerentity = (PlayerEntity) entityLiving;
			if (stack.getDamage() < (stack.getMaxDamage() - 1)
					&& !playerentity.getItemCooldownManager().isCoolingDown(this)) {
				playerentity.getItemCooldownManager().set(this, 18);
				if (!worldIn.isClient) {
					for (int y = 0; y < 4; ++y) {
						BulletEntity abstractarrowentity = createBullet(worldIn, stack, playerentity,
								HaloConfig.shotgun_bullet_damage);
						abstractarrowentity.setVelocity(playerentity,
								playerentity.getPitch() + (y == 3 ? 1 : y == 4 ? -1 : 0),
								playerentity.getYaw() + (y == 3 ? 1 : y == 2 ? -1 : y == 4 ? -1 : 0), 0.5F, 1.0F * 3.0F,
								1.0F);
						if (EnchantmentHelper.getLevel(Enchantments.FLAME, stack) > 0) {
							abstractarrowentity.setOnFireFor(100);
						}
						worldIn.spawnEntity(abstractarrowentity);
					}
					stack.damage(1, entityLiving, p -> p.sendToolBreakStatus(entityLiving.getActiveHand()));
					worldIn.playSound((PlayerEntity) null, playerentity.getX(), playerentity.getY(),
							playerentity.getZ(), HaloSounds.SHOTGUN, SoundCategory.PLAYERS, 0.5F,
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
			if (((PlayerEntity) entity).getMainHandStack().getItem() instanceof ShotgunItem) {
				if (ClientInit.reload.isPressed() && selected) {
					PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
					passedData.writeBoolean(true);
					ClientPlayNetworking.send(MCHaloMod.SHOTGUN, passedData);
				}
			}
		}
	}

	public void reload(PlayerEntity user, Hand hand) {
		if (user.getStackInHand(hand).getItem() instanceof ShotgunItem) {
			while (!user.isCreative() && user.getStackInHand(hand).getDamage() != 0
					&& user.getInventory().count(HaloItems.SHOTGUN_CLIP) > 0) {
				removeAmmo(HaloItems.SHOTGUN_CLIP, user);
				user.getStackInHand(hand).damage(-HaloConfig.shotgun_mag_size, user,
						s -> user.sendToolBreakStatus(hand));
				user.getStackInHand(hand).setBobbingAnimationTime(3);
				user.getEntityWorld().playSound((PlayerEntity) null, user.getX(), user.getY(), user.getZ(),
						HaloSounds.SHOTGUNRELOAD, SoundCategory.PLAYERS, 1.00F, 1.0F);
			}
		}
	}

	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		float j = EnchantmentHelper.getLevel(Enchantments.POWER, stack);
		super.appendTooltip(stack, world, tooltip, context);
		tooltip.add(Text.translatable("Damage: "
				+ ((j > 0 ? (HaloConfig.shotgun_bullet_damage + (j * 1.5F + 0.5F)) : HaloConfig.shotgun_bullet_damage)
						* 4))
				.formatted(Formatting.ITALIC));
	}

	@Override
	public Rarity getRarity(ItemStack stack) {
		return Rarity.RARE;
	}

	@Override
	public void createRenderer(Consumer<Object> consumer) {
		consumer.accept(new RenderProvider() {
			private final ShotgunRender renderer = new ShotgunRender();

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