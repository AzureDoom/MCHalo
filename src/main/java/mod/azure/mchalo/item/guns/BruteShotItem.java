package mod.azure.mchalo.item.guns;

import java.util.List;

import io.netty.buffer.Unpooled;
import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.client.ClientInit;
import mod.azure.mchalo.item.HaloGunBase;
import mod.azure.mchalo.util.HaloItems;
import mod.azure.mchalo.util.HaloSounds;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class BruteShotItem extends HaloGunBase {

	public BruteShotItem() {
		super(new Item.Settings().group(MCHaloMod.HALOTAB).maxCount(1).maxDamage(config.bruteshot_max_ammo + 1));
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
			while (user.getStackInHand(hand).getDamage() != 0 && user.getInventory().count(HaloItems.GRENADES) > 0) {
				removeAmmo(HaloItems.GRENADES, user);
				user.getStackInHand(hand).damage(-config.bruteshot_mag_size, user, s -> user.sendToolBreakStatus(hand));
				user.getStackInHand(hand).setCooldown(3);
				user.getEntityWorld().playSound((PlayerEntity) null, user.getX(), user.getY(), user.getZ(),
						HaloSounds.BRUTESHOTRELOAD, SoundCategory.PLAYERS, 1.00F, 1.0F);
			}
		}
	}

	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		float j = EnchantmentHelper.getLevel(Enchantments.POWER, stack);
		super.appendTooltip(stack, world, tooltip, context);
		tooltip.add(new TranslatableText("Damage: "
				+ (j > 0 ? (config.bruteshot_bullet_damage + (j * 1.5F + 0.5F)) : config.bruteshot_bullet_damage))
						.formatted(Formatting.ITALIC));
	}

}
