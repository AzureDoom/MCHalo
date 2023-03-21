package mod.azure.mchalo.item.guns;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import io.netty.buffer.Unpooled;
import mod.azure.azurelib.animatable.GeoItem;
import mod.azure.azurelib.animatable.SingletonGeoAnimatable;
import mod.azure.azurelib.animatable.client.RenderProvider;
import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.client.ClientInit;
import mod.azure.mchalo.client.render.ShotgunRender;
import mod.azure.mchalo.config.HaloConfig;
import mod.azure.mchalo.item.HaloGunBase;
import mod.azure.mchalo.util.HaloItems;
import mod.azure.mchalo.util.HaloSounds;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

public class ShotgunItem extends HaloGunBase {

	private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);

	public ShotgunItem() {
		super(new Item.Properties().stacksTo(1).durability(HaloConfig.shotgun_max_ammo + 1));
		SingletonGeoAnimatable.registerSyncedAnimatable(this);
	}

	@Override
	public void onUseTick(Level worldIn, LivingEntity entityLiving, ItemStack stack, int count) {
		if (entityLiving instanceof Player) {
			var playerentity = (Player) entityLiving;
			if (stack.getDamageValue() < (stack.getMaxDamage() - 1)
					&& !playerentity.getCooldowns().isOnCooldown(this)) {
				playerentity.getCooldowns().addCooldown(this, 18);
				if (!worldIn.isClientSide) {
					for (int y = 0; y < 4; ++y) {
						var shellEntity = createBullet(worldIn, stack, playerentity, HaloConfig.shotgun_bullet_damage);
						shellEntity.shootFromRotation(playerentity,
								playerentity.getXRot() + (y == 3 ? 1 : y == 4 ? -1 : 0),
								playerentity.getYRot() + (y == 3 ? 1 : y == 2 ? -1 : y == 4 ? -1 : 0), 0.5F,
								1.0F * 3.0F, 1.0F);
						worldIn.addFreshEntity(shellEntity);
					}
					stack.hurtAndBreak(1, entityLiving, p -> p.broadcastBreakEvent(entityLiving.getUsedItemHand()));
					worldIn.playSound((Player) null, playerentity.getX(), playerentity.getY(), playerentity.getZ(),
							HaloSounds.SHOTGUN, SoundSource.PLAYERS, 0.5F,
							1.0F / (worldIn.random.nextFloat() * 0.4F + 1.2F) + 1F * 0.5F);
					triggerAnim(playerentity, GeoItem.getOrAssignId(stack, (ServerLevel) worldIn), "shoot_controller",
							"firing");
				}
				var isInsideWaterBlock = playerentity.level.isWaterAt(playerentity.blockPosition());
				spawnLightSource(entityLiving, isInsideWaterBlock);
			}
		}
	}

	@Override
	public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
		if (world.isClientSide)
			if (((Player) entity).getMainHandItem().getItem() instanceof ShotgunItem)
				if (ClientInit.reload.isDown() && selected) {
					var passedData = new FriendlyByteBuf(Unpooled.buffer());
					passedData.writeBoolean(true);
					ClientPlayNetworking.send(MCHaloMod.SHOTGUN, passedData);
				}
	}

	public void reload(Player user, InteractionHand hand) {
		if (user.getItemInHand(hand).getItem() instanceof ShotgunItem) {
			while (!user.isCreative() && user.getItemInHand(hand).getDamageValue() != 0
					&& user.getInventory().countItem(HaloItems.SHOTGUN_CLIP) > 0) {
				removeAmmo(HaloItems.SHOTGUN_CLIP, user);
				user.getItemInHand(hand).hurtAndBreak(-HaloConfig.shotgun_mag_size, user,
						s -> user.broadcastBreakEvent(hand));
				user.getItemInHand(hand).setPopTime(3);
				user.getCommandSenderWorld().playSound((Player) null, user.getX(), user.getY(), user.getZ(),
						HaloSounds.SHOTGUNRELOAD, SoundSource.PLAYERS, 1.00F, 1.0F);
			}
		}
	}

	@Override
	public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag context) {
		var j = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
		super.appendHoverText(stack, world, tooltip, context);
		tooltip.add(Component.translatable("Damage: "
				+ ((j > 0 ? (HaloConfig.shotgun_bullet_damage + (j * 1.5F + 0.5F)) : HaloConfig.shotgun_bullet_damage)
						* 4))
				.withStyle(ChatFormatting.ITALIC));
	}

	@Override
	public Rarity getRarity(ItemStack stack) {
		return Rarity.RARE;
	}

	@Override
	public void createRenderer(Consumer<Object> consumer) {
		consumer.accept(new RenderProvider() {
			private ShotgunRender renderer = null;

			@Override
			public BlockEntityWithoutLevelRenderer getCustomRenderer() {
				if (renderer == null)
					return new ShotgunRender();
				return renderer;
			}
		});
	}

	@Override
	public Supplier<Object> getRenderProvider() {
		return this.renderProvider;
	}
}