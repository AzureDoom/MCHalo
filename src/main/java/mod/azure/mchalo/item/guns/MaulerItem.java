package mod.azure.mchalo.item.guns;

import java.util.List;

import io.netty.buffer.Unpooled;
import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.client.ClientInit;
import mod.azure.mchalo.config.HaloConfig;
import mod.azure.mchalo.entity.projectiles.BulletEntity;
import mod.azure.mchalo.item.HaloGunBase;
import mod.azure.mchalo.util.HaloItems;
import mod.azure.mchalo.util.HaloSounds;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.client.item.TooltipContext;
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
import software.bernie.geckolib3.network.GeckoLibNetwork;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class MaulerItem extends HaloGunBase {

	public MaulerItem() {
		super(new Item.Settings().group(MCHaloMod.HALOTAB).maxCount(1).maxDamage(HaloConfig.mauler_max_ammo + 1));
	}

	@Override
	public void onStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int remainingUseTicks) {
		if (entityLiving instanceof PlayerEntity) {
			PlayerEntity playerentity = (PlayerEntity) entityLiving;
			if (stack.getDamage() < (stack.getMaxDamage() - 1)) {
				playerentity.getItemCooldownManager().set(this, 18);
				if (!worldIn.isClient) {
					for (int y = 0; y < 8; ++y) {
						BulletEntity abstractarrowentity = createBullet(worldIn, stack, playerentity,
								HaloConfig.mauler_bullet_damage);
						abstractarrowentity.setVelocity(playerentity,
								playerentity.getPitch() + (y == 3 ? 3 : y == 4 ? -3 : 0),
								playerentity.getYaw() + (y == 3 ? 3 : y == 2 ? -3 : y == 4 ? -3 : 0), 0.5F, 1.0F * 3.0F,
								1.0F);
						if (EnchantmentHelper.getLevel(Enchantments.FLAME, stack) > 0) {
							abstractarrowentity.setOnFireFor(100);
						}
						abstractarrowentity.age = 37;
						worldIn.spawnEntity(abstractarrowentity);
					}
					boolean isInsideWaterBlock = playerentity.world.isWater(playerentity.getBlockPos());
					spawnLightSource(entityLiving, isInsideWaterBlock);
					stack.damage(1, entityLiving, p -> p.sendToolBreakStatus(entityLiving.getActiveHand()));
					worldIn.playSound((PlayerEntity) null, playerentity.getX(), playerentity.getY(),
							playerentity.getZ(), HaloSounds.MAULER, SoundCategory.PLAYERS, 0.25F, 1.3F);
					final int id = GeckoLibUtil.guaranteeIDForStack(stack, (ServerWorld) worldIn);
					GeckoLibNetwork.syncAnimation(playerentity, this, id, ANIM_OPEN);
					for (PlayerEntity otherPlayer : PlayerLookup.tracking(playerentity)) {
						GeckoLibNetwork.syncAnimation(otherPlayer, this, id, ANIM_OPEN);
					}
				}
			}
		}
	}

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		if (world.isClient) {
			if (((PlayerEntity) entity).getMainHandStack().getItem() instanceof MaulerItem) {
				if (ClientInit.reload.isPressed() && selected) {
					PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
					passedData.writeBoolean(true);
					ClientPlayNetworking.send(MCHaloMod.MAULER, passedData);
				}
			}
		}
	}

	public void reload(PlayerEntity user, Hand hand) {
		if (user.getStackInHand(hand).getItem() instanceof MaulerItem) {
			while (!user.isCreative() && user.getStackInHand(hand).getDamage() != 0
					&& user.getInventory().count(HaloItems.SHOTGUN_CLIP) > 0) {
				removeAmmo(HaloItems.SHOTGUN_CLIP, user);
				user.getStackInHand(hand).damage(-HaloConfig.mauler_mag_size, user,
						s -> user.sendToolBreakStatus(hand));
				user.getStackInHand(hand).setBobbingAnimationTime(3);
				user.getEntityWorld().playSound((PlayerEntity) null, user.getX(), user.getY(), user.getZ(),
						HaloSounds.MAULERRELOAD, SoundCategory.PLAYERS, 1.00F, 1.0F);
			}
		}
	}

	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		float j = EnchantmentHelper.getLevel(Enchantments.POWER, stack);
		super.appendTooltip(stack, world, tooltip, context);
		tooltip.add(Text.translatable("Damage: "
				+ (j > 0 ? (HaloConfig.mauler_bullet_damage + (j * 1.5F + 0.5F)) : HaloConfig.mauler_bullet_damage) * 8)
				.formatted(Formatting.ITALIC));
	}

	@Override
	public Rarity getRarity(ItemStack stack) {
		return Rarity.RARE;
	}

}
