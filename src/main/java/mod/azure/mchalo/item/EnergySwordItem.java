package mod.azure.mchalo.item;

import io.netty.buffer.Unpooled;
import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.client.ClientInit;
import mod.azure.mchalo.config.HaloConfig.Weapons;
import mod.azure.mchalo.item.guns.BattleRifleItem;
import mod.azure.mchalo.util.HaloSounds;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
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

public class EnergySwordItem extends SwordItem implements IAnimatable, ISyncable {

	public AnimationFactory factory = new AnimationFactory(this);
	public static Weapons config = MCHaloMod.config.weapons;
	public String controllerName = "controller";
	public static final int ANIM_OPENING = 0;
	public static final int ANIM_OPENED = 1;
	public static final int ANIM_CLOSING = 2;
	public static final int ANIM_CLOSED = 3;

	public EnergySwordItem() {
		super(ToolMaterials.DIAMOND, 64, -2.4F, new Item.Settings().group(MCHaloMod.HALOTAB).maxCount(1).maxDamage(20));
		GeckoLibNetwork.registerSyncable(this);
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
		if (state == ANIM_OPENING) {
			final AnimationController<?> controller = GeckoLibUtil.getControllerForID(this.factory, id, controllerName);
			if (controller.getAnimationState() == AnimationState.Stopped) {
				controller.markNeedsReload();
				controller.setAnimation(
						new AnimationBuilder().addAnimation("opening", false).addAnimation("open_loop", false));
			}
		}
		if (state == ANIM_OPENED) {
			final AnimationController<?> controller = GeckoLibUtil.getControllerForID(this.factory, id, controllerName);
			if (controller.getAnimationState() == AnimationState.Stopped) {
				controller.markNeedsReload();
				controller.setAnimation(new AnimationBuilder().addAnimation("open_loop"));
			}
		}
		if (state == ANIM_CLOSING) {
			final AnimationController<?> controller = GeckoLibUtil.getControllerForID(this.factory, id, controllerName);
			if (controller.getAnimationState() == AnimationState.Stopped) {
				controller.markNeedsReload();
				controller.setAnimation(
						new AnimationBuilder().addAnimation("closing", false).addAnimation("closed_loop", false));
			}
		}
		if (state == ANIM_CLOSED) {
			final AnimationController<?> controller = GeckoLibUtil.getControllerForID(this.factory, id, controllerName);
			if (controller.getAnimationState() == AnimationState.Stopped) {
				controller.markNeedsReload();
				controller.setAnimation(new AnimationBuilder().addAnimation("closed_loop"));
			}
		}
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
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		if (world.isClient) {
			if (((PlayerEntity) entity).getMainHandStack().getItem() instanceof EnergySwordItem) {
				if (ClientInit.reload.isPressed() && selected) {
					PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
					passedData.writeBoolean(true);
					ClientPlayNetworking.send(MCHaloMod.ENERGYSWORD, passedData);
				}
			}
		}
		if (!world.isClient) {
			final int id = GeckoLibUtil.guaranteeIDForStack(stack, (ServerWorld) world);
			if (((PlayerEntity) entity).getMainHandStack().getItem() instanceof EnergySwordItem) {
				GeckoLibNetwork.syncAnimation((PlayerEntity) entity, this, id, selected ? ANIM_OPENED : ANIM_CLOSED);
				for (PlayerEntity otherPlayer : PlayerLookup.tracking((PlayerEntity) entity)) {
					GeckoLibNetwork.syncAnimation(otherPlayer, this, id, selected ? ANIM_OPENED : ANIM_CLOSED);
				}
			}
		}
	}

	public void reload(PlayerEntity user, Hand hand) {
		if (user.getStackInHand(hand).getItem() instanceof BattleRifleItem) {
			while (user.getStackInHand(hand).getDamage() != 0 && user.getInventory().count(Items.DIAMOND) > 0) {
				removeAmmo(Items.DIAMOND, user);
				user.getStackInHand(hand).damage(-config.battlerifle_mag_size, user,
						s -> user.sendToolBreakStatus(hand));
				user.getStackInHand(hand).setBobbingAnimationTime(3);
				user.getEntityWorld().playSound((PlayerEntity) null, user.getX(), user.getY(), user.getZ(),
						HaloSounds.BATTLERIFLERELOAD, SoundCategory.PLAYERS, 1.00F, 1.0F);
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
		return false;
	}

}
