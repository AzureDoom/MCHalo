package mod.azure.mchalo.item;

import mod.azure.azurelib.Keybindings;
import mod.azure.azurelib.animatable.GeoItem;
import mod.azure.azurelib.animatable.SingletonGeoAnimatable;
import mod.azure.azurelib.animatable.client.RenderProvider;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.animation.RawAnimation;
import mod.azure.azurelib.core.object.PlayState;
import mod.azure.azurelib.util.AzureLibUtil;
import mod.azure.mchalo.client.render.GunRender;
import mod.azure.mchalo.helper.CommonHelper;
import mod.azure.mchalo.platform.Services;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class EnergySwordItem extends SwordItem implements GeoItem {

    protected static String controller = "controller";
    private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this, true);
    private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);

    public EnergySwordItem() {
        super(Tiers.DIAMOND, 1, -2.0f, new Item.Properties().stacksTo(1).durability(20));
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public void registerControllers(ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, controller, event -> PlayState.CONTINUE).triggerableAnim("open", RawAnimation.begin().thenPlay("opening").thenLoop("open_loop")).triggerableAnim("close", RawAnimation.begin().thenPlayAndHold("closing")).setSoundKeyframeHandler(event -> {
            if (event.getKeyframeData().getSound().matches("energy_open")) {
            }
            if (event.getKeyframeData().getSound().matches("energy_loop")) {
            }
        }));
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull Level world, @NotNull Entity entity, int slot, boolean selected) {
        if (entity instanceof Player player) {
            if (!world.isClientSide) {
                if (player.getMainHandItem().is(this) && selected) {
                    triggerAnim(player, GeoItem.getOrAssignId(stack, (ServerLevel) world), controller, "open");
                } else triggerAnim(player, GeoItem.getOrAssignId(stack, (ServerLevel) world), controller, "close");
            }
            if (world.isClientSide && player.getMainHandItem().getItem() instanceof EnergySwordItem)
                if (Keybindings.RELOAD.isDown() && selected) {
                }
        }
    }

    public void reload(Player user, InteractionHand hand) {
        if (user.getItemInHand(hand).getItem() instanceof EnergySwordItem) {
            while (user.getItemInHand(hand).getDamageValue() != 0 && user.getInventory().countItem(Services.ITEMS_HELPER.getBatteriesAmmo()) > 0) {
                CommonHelper.removeAmmo(Services.ITEMS_HELPER.getBatteriesAmmo(), user);
                user.getItemInHand(hand).hurtAndBreak(-20, user, s -> user.broadcastBreakEvent(hand));
                user.getItemInHand(hand).setPopTime(3);
            }
        }
    }

    @Override
    public boolean isFoil(@NotNull ItemStack stack) {
        return false;
    }

    @Override
    public boolean isEnchantable(@NotNull ItemStack stack) {
        return false;
    }

    @Override
    public boolean isValidRepairItem(@NotNull ItemStack stack, @NotNull ItemStack ingredient) {
        return false;
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack stack, @NotNull LivingEntity target, @NotNull LivingEntity miner) {
        if (miner instanceof Player playerentity && stack.getDamageValue() < (stack.getMaxDamage() - 1) && !playerentity.getCooldowns().isOnCooldown(this) && playerentity.getMainHandItem().getItem() instanceof EnergySwordItem) {
            playerentity.getCooldowns().addCooldown(this, 20);
            var aabb = new AABB(playerentity.blockPosition().above()).inflate(2D, 2D, 2D);
            playerentity.level().getEntities(playerentity, aabb).forEach(e -> doDamage(playerentity, e));
            stack.hurtAndBreak(1, playerentity, p -> p.broadcastBreakEvent(playerentity.getUsedItemHand()));
        }
        return super.hurtEnemy(stack, target, miner);
    }

    private void doDamage(LivingEntity user, Entity target) {
        if (target instanceof LivingEntity) {
            target.invulnerableTime = 0;
            target.hurt(user.damageSources().playerAttack((Player) user), 30F);
        }
    }

    @Override
    public @NotNull Rarity getRarity(@NotNull ItemStack stack) {
        return Rarity.EPIC;
    }

    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private final GunRender renderer = null;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (renderer == null) return new GunRender("energy_sword");
                return renderer;
            }
        });
    }

    @Override
    public Supplier<Object> getRenderProvider() {
        return this.renderProvider;
    }

}
