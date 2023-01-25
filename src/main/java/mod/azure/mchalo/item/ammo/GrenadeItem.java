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
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class GrenadeItem extends Item implements GeoItem {

	private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);
	private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);

	public GrenadeItem() {
		super(new Item.Settings());
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "shoot_controller", event -> PlayState.CONTINUE));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}

	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
		if (!user.getItemCooldownManager().isCoolingDown(this)) {
			user.getItemCooldownManager().set(this, 25);
			if (!world.isClient) {
				GrenadeEntity snowballEntity = new GrenadeEntity(world, user);
				snowballEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 0.75F, 1.0F);
				snowballEntity.setDamage(0);
				world.spawnEntity(snowballEntity);
			}
			if (!user.getAbilities().creativeMode) {
				itemStack.decrement(1);
			}
			return TypedActionResult.success(itemStack, world.isClient());
		} else {
			return TypedActionResult.fail(itemStack);
		}
	}

	@Override
	public void createRenderer(Consumer<Object> consumer) {
		consumer.accept(new RenderProvider() {
			private final GrenadeItemRender renderer = new GrenadeItemRender();

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
