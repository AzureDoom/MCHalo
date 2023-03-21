package mod.azure.mchalo.item.ammo;

import java.util.function.Consumer;
import java.util.function.Supplier;

import mod.azure.azurelib.animatable.GeoItem;
import mod.azure.azurelib.animatable.client.RenderProvider;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.object.PlayState;
import mod.azure.azurelib.util.AzureLibUtil;
import mod.azure.mchalo.client.render.projectiles.GrenadeItemRender;
import mod.azure.mchalo.entity.projectiles.GrenadeEntity;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class GrenadeItem extends Item implements GeoItem {

	private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);
	private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);

	public GrenadeItem() {
		super(new Item.Properties());
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "shoot_controller", event -> PlayState.CONTINUE));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}

	public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
		var itemStack = user.getItemInHand(hand);
		if (!user.getCooldowns().isOnCooldown(this)) {
			user.getCooldowns().addCooldown(this, 25);
			if (!world.isClientSide) {
				var nadeEntity = new GrenadeEntity(world, user);
				nadeEntity.shootFromRotation(user, user.getXRot(), user.getYRot(), 0.0F, 0.75F, 1.0F);
				nadeEntity.setBaseDamage(0);
				world.addFreshEntity(nadeEntity);
			}
			if (!user.getAbilities().instabuild)
				itemStack.shrink(1);
			return InteractionResultHolder.sidedSuccess(itemStack, world.isClientSide());
		} else
			return InteractionResultHolder.fail(itemStack);
	}

	@Override
	public void createRenderer(Consumer<Object> consumer) {
		consumer.accept(new RenderProvider() {
			private GrenadeItemRender renderer = null;

			@Override
			public BlockEntityWithoutLevelRenderer getCustomRenderer() {
				if (renderer == null)
					return new GrenadeItemRender();
				return renderer;
			}
		});
	}

	@Override
	public Supplier<Object> getRenderProvider() {
		return this.renderProvider;
	}

}
