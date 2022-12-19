package mod.azure.mchalo.item.guns;

import java.util.function.Consumer;
import java.util.function.Supplier;

import io.netty.buffer.Unpooled;
import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.client.ClientInit;
import mod.azure.mchalo.client.render.RocketLauncherRender;
import mod.azure.mchalo.config.HaloConfig;
import mod.azure.mchalo.entity.projectiles.RocketEntity;
import mod.azure.mchalo.item.HaloGunBase;
import mod.azure.mchalo.util.HaloSounds;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.client.RenderProvider;

public class RocketLauncherItem extends HaloGunBase {

	private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);
	
	public RocketLauncherItem() {
		super(new Item.Settings().maxCount(1).maxDamage(HaloConfig.rocketlauncher_max_ammo + 1));
		SingletonGeoAnimatable.registerSyncedAnimatable(this);
	}

	@Override
	public void onStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int remainingUseTicks) {
		if (entityLiving instanceof PlayerEntity) {
			PlayerEntity playerentity = (PlayerEntity) entityLiving;
			if (stack.getDamage() < (stack.getMaxDamage() - 1)) {
				playerentity.getItemCooldownManager().set(this, 5);
				if (!worldIn.isClient) {
					RocketEntity abstractarrowentity = createRocket(worldIn, stack, playerentity);
					abstractarrowentity.setVelocity(playerentity, playerentity.getPitch(), playerentity.getYaw(),
							0.0F, 0.75F * 3.0F, 1.0F);
					abstractarrowentity.refreshPositionAndAngles(entityLiving.getX(), entityLiving.getBodyY(0.95),
							entityLiving.getZ(), 0, 0);
					stack.damage(1, entityLiving, p -> p.sendToolBreakStatus(entityLiving.getActiveHand()));
					worldIn.spawnEntity(abstractarrowentity);
					worldIn.playSound((PlayerEntity) null, playerentity.getX(), playerentity.getY(),
							playerentity.getZ(), HaloSounds.PISTOL, SoundCategory.PLAYERS, 0.5F,
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
			if (((PlayerEntity) entity).getMainHandStack().getItem() instanceof RocketLauncherItem) {
				if (ClientInit.reload.isPressed() && selected) {
					PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
					passedData.writeBoolean(true);
					ClientPlayNetworking.send(MCHaloMod.ROCKETLAUNCHER, passedData);
				}
			}
		}
	}

	public void reload(PlayerEntity user, Hand hand) {
		if (user.getStackInHand(hand).getItem() instanceof RocketLauncherItem) {
			while (!user.isCreative() && user.getStackInHand(hand).getDamage() != 0 && user.getInventory().count(Items.TNT) > 0) {
				removeAmmo(Items.TNT, user);
				user.getStackInHand(hand).damage(-HaloConfig.rocketlauncher_mag_size, user,
						s -> user.sendToolBreakStatus(hand));
				user.getStackInHand(hand).setBobbingAnimationTime(3);
				user.getEntityWorld().playSound((PlayerEntity) null, user.getX(), user.getY(), user.getZ(),
						HaloSounds.PISTOLRELOAD, SoundCategory.PLAYERS, 1.00F, 1.0F);
			}
		}
	}

	@Override
	public Rarity getRarity(ItemStack stack) {
		return Rarity.RARE;
	}

	@Override
	public void createRenderer(Consumer<Object> consumer) {
		consumer.accept(new RenderProvider() {
			private final RocketLauncherRender renderer = new RocketLauncherRender();

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