package mod.azure.mchalo.item.guns;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import io.netty.buffer.Unpooled;
import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.client.ClientInit;
import mod.azure.mchalo.client.render.PlasmaPistolRender;
import mod.azure.mchalo.config.HaloConfig;
import mod.azure.mchalo.entity.projectiles.PlasmaGEntity;
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

public class PlasmaPistolItem extends HaloGunBase {

	private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);
	
	public PlasmaPistolItem() {
		super(new Item.Settings().maxCount(1).maxDamage(HaloConfig.plasmapistol_max_ammo + 1));
		SingletonGeoAnimatable.registerSyncedAnimatable(this);
	}

	@Override
	public void usageTick(World worldIn, LivingEntity entityLiving, ItemStack stack, int count) {
		if (entityLiving instanceof PlayerEntity) {
			PlayerEntity playerentity = (PlayerEntity) entityLiving;
			if (stack.getDamage() < (stack.getMaxDamage() - 1)
					&& !playerentity.getItemCooldownManager().isCoolingDown(this)) {
				playerentity.getItemCooldownManager().set(this, 5);
				if (!worldIn.isClient) {
					PlasmaGEntity abstractarrowentity = createGPlamsa(worldIn, stack, playerentity,
							HaloConfig.plasmapistol_bullet_damage);
					abstractarrowentity.setVelocity(playerentity, playerentity.getPitch(), playerentity.getYaw(), 0.0F,
							0.5F * 3.0F, 1.0F);
					if (EnchantmentHelper.getLevel(Enchantments.FLAME, stack) > 0) {
						abstractarrowentity.setOnFireFor(100);
					}
					boolean isInsideWaterBlock = playerentity.world.isWater(playerentity.getBlockPos());
					spawnLightSource(entityLiving, isInsideWaterBlock);
					abstractarrowentity.age = 33;
					worldIn.spawnEntity(abstractarrowentity);
					stack.damage(1, entityLiving, p -> p.sendToolBreakStatus(entityLiving.getActiveHand()));
					worldIn.playSound((PlayerEntity) null, playerentity.getX(), playerentity.getY(),
							playerentity.getZ(), HaloSounds.PLASMAPISTOL, SoundCategory.PLAYERS, 0.25F, 1.3F);
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
			if (((PlayerEntity) entity).getMainHandStack().getItem() instanceof PlasmaPistolItem) {
				if (ClientInit.reload.isPressed() && selected) {
					PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
					passedData.writeBoolean(true);
					ClientPlayNetworking.send(MCHaloMod.PLASMAPISTOL, passedData);
				}
			}
		}
	}

	public void reload(PlayerEntity user, Hand hand) {
		if (user.getStackInHand(hand).getItem() instanceof PlasmaPistolItem) {
			while (!user.isCreative() && user.getStackInHand(hand).getDamage() != 0
					&& user.getInventory().count(HaloItems.BATTERIES) > 0) {
				removeAmmo(HaloItems.BATTERIES, user);
				user.getStackInHand(hand).damage(-HaloConfig.plasmapistol_mag_size, user,
						s -> user.sendToolBreakStatus(hand));
				user.getStackInHand(hand).setBobbingAnimationTime(3);
				user.getEntityWorld().playSound((PlayerEntity) null, user.getX(), user.getY(), user.getZ(),
						HaloSounds.PLASMAPISTOLELOAD, SoundCategory.PLAYERS, 1.00F, 1.0F);
			}
		}
	}

	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		float j = EnchantmentHelper.getLevel(Enchantments.POWER, stack);
		super.appendTooltip(stack, world, tooltip, context);
		tooltip.add(Text.translatable("Damage: " + (j > 0 ? (HaloConfig.plasmapistol_bullet_damage + (j * 1.5F + 0.5F))
				: HaloConfig.plasmapistol_bullet_damage)).formatted(Formatting.ITALIC));
	}

	@Override
	public Rarity getRarity(ItemStack stack) {
		return Rarity.RARE;
	}

	@Override
	public void createRenderer(Consumer<Object> consumer) {
		consumer.accept(new RenderProvider() {
			private final PlasmaPistolRender renderer = new PlasmaPistolRender();

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
