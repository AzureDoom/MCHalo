package mod.azure.mchalo.item;

import mod.azure.azurelib.AzureLibMod;
import mod.azure.azurelib.Keybindings;
import mod.azure.azurelib.animatable.GeoItem;
import mod.azure.azurelib.animatable.SingletonGeoAnimatable;
import mod.azure.azurelib.animatable.client.RenderProvider;
import mod.azure.azurelib.constant.DataTickets;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager;
import mod.azure.azurelib.core.animation.Animation;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.animation.RawAnimation;
import mod.azure.azurelib.core.object.PlayState;
import mod.azure.azurelib.items.BaseGunItem;
import mod.azure.azurelib.util.AzureLibUtil;
import mod.azure.mchalo.client.render.GunRender;
import mod.azure.mchalo.helper.CommonHelper;
import mod.azure.mchalo.helper.ProjectileEnum;
import mod.azure.mchalo.platform.Services;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class HaloGunBase extends Item implements GeoItem {
    protected Item ammoType;
    protected final String id;
    protected final int clipSize;
    protected final float attackDamage;
    protected final SoundEvent firingSound;
    protected final SoundEvent reloadSound;
    private static final String firing = "firing";
    protected final ProjectileEnum projectileType;
    private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);
    private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);

    public HaloGunBase(String id, ProjectileEnum projectileType, int maxClipSize, SoundEvent reloadSound, SoundEvent firingSound, int clipSize, float attackDamage) {
        super(new Item.Properties().stacksTo(1).durability(maxClipSize + 1));
        this.id = id;
        this.projectileType = projectileType;
        this.reloadSound = reloadSound;
        this.firingSound = firingSound;
        this.clipSize = clipSize;
        this.attackDamage = attackDamage;
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    public String getItemID() {
        return this.id;
    }

    public ProjectileEnum getProjectileType() {
        return this.projectileType;
    }

    public Item getAmmoType() {
        switch (this.getProjectileType()) {
            case BULLET, BRBULLET -> this.ammoType = Services.ITEMS_HELPER.getBulletAmmo();
            case NEEDLE -> this.ammoType = Services.ITEMS_HELPER.getNeedlesAmmo();
            case ROCKET -> this.ammoType = Services.ITEMS_HELPER.getRocketAmmo();
            case PLASMA, PLASMAG -> this.ammoType = Services.ITEMS_HELPER.getBatteriesAmmo();
            case GRENADE -> this.ammoType = Services.ITEMS_HELPER.getGrenadeAmmo();
            case SHELL, MAULER -> this.ammoType = Services.ITEMS_HELPER.getShellAmmo();
            case SNIPER -> this.ammoType = Services.ITEMS_HELPER.getSniperAmmo();
        }
        return this.ammoType;
    }

    public SoundEvent getReloadSound() {
        return this.reloadSound;
    }

    public SoundEvent getFiringSound() {
        return this.firingSound;
    }

    public int getClipSize() {
        return this.clipSize;
    }

    public float getAttackDamage() {
        return this.attackDamage;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        var itemStack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(itemStack);
    }

    private void autoFire(@NotNull ItemStack itemStack, @NotNull Level level, @NotNull Player player) {
        switch (this.getProjectileType()) {
            case MAULER -> {
                player.getCooldowns().addCooldown(this, 18);
                for (int y = 0; y < 8; ++y) {
                    var bulletEntity = CommonHelper.createBullet(level, itemStack, player, getAttackDamage());
                    if (EnchantmentHelper.getItemEnchantmentLevel(mod.azure.azurelib.platform.Services.PLATFORM.getIncendairyenchament(), itemStack) > 0)
                        bulletEntity.setSecondsOnFire(100);
                    bulletEntity.shootFromRotation(player, player.getXRot() + (y == 3 ? 3 : y == 4 ? -3 : 0), player.getYRot() + (y == 3 ? 3 : y == 2 ? -3 : y == 4 ? -3 : 0), 0.5F, 3.0F, 1.0F);
                    bulletEntity.tickCount = 37;
                    level.addFreshEntity(bulletEntity);
                }
            }
            case ROCKET -> {
                player.getCooldowns().addCooldown(this, 25);
                var rocketEntity = CommonHelper.createRocket(level, itemStack, player);
                if (EnchantmentHelper.getItemEnchantmentLevel(mod.azure.azurelib.platform.Services.PLATFORM.getIncendairyenchament(), itemStack) > 0)
                    rocketEntity.setSecondsOnFire(100);
                rocketEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.25F, 1.0F);
                rocketEntity.moveTo(player.getX(), player.getY(0.5), player.getZ(), 0, 0);
                level.addFreshEntity(rocketEntity);
            }
            case SNIPER -> {
                player.getCooldowns().addCooldown(this, 35);
                var bulletEntity = CommonHelper.createBullet(level, itemStack, player, getAttackDamage());
                if (EnchantmentHelper.getItemEnchantmentLevel(mod.azure.azurelib.platform.Services.PLATFORM.getIncendairyenchament(), itemStack) > 0)
                    bulletEntity.setSecondsOnFire(100);
                bulletEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 3.0F, 1.0F);
                level.addFreshEntity(bulletEntity);
            }
        }
    }

    private void singleFire(@NotNull ItemStack itemStack, @NotNull Level level, @NotNull Player player) {
        switch (this.getProjectileType()) {
            case BRBULLET -> {
                player.getCooldowns().addCooldown(this, 12);
                for (int y = 0; y < 3; ++y) {
                    var bulletEntity = CommonHelper.createBullet(level, itemStack, player, getAttackDamage());
                    if (EnchantmentHelper.getItemEnchantmentLevel(mod.azure.azurelib.platform.Services.PLATFORM.getIncendairyenchament(), itemStack) > 0)
                        bulletEntity.setSecondsOnFire(100);
                    bulletEntity.shootFromRotation(player, player.getXRot(), player.getYRot() + (y == 1 ? 0.0F : y == 2 ? -0.5F : 0.5F), 0.5F, 3.0F, 1.0F);
                    level.addFreshEntity(bulletEntity);
                }
            }
            case BULLET -> {
                player.getCooldowns().addCooldown(this, 8);
                var bulletEntity = CommonHelper.createBullet(level, itemStack, player, getAttackDamage());
                if (EnchantmentHelper.getItemEnchantmentLevel(mod.azure.azurelib.platform.Services.PLATFORM.getIncendairyenchament(), itemStack) > 0)
                    bulletEntity.setSecondsOnFire(100);
                bulletEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 3.0F, 1.0F);
                level.addFreshEntity(bulletEntity);
            }
            case GRENADE -> {
                player.getCooldowns().addCooldown(this, 12);
                var nadeEntity = CommonHelper.createGrenade(level, itemStack, player);
                if (EnchantmentHelper.getItemEnchantmentLevel(mod.azure.azurelib.platform.Services.PLATFORM.getIncendairyenchament(), itemStack) > 0)
                    nadeEntity.setSecondsOnFire(100);
                nadeEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 0.5F * 3.0F, 1.0F);
                nadeEntity.moveTo(player.getX(), player.getY(0.95), player.getZ(), 0, 0);
                level.addFreshEntity(nadeEntity);
            }
            case NEEDLE -> {
                player.getCooldowns().addCooldown(this, 3);
                var needleEntity = CommonHelper.createNeedle(level, itemStack, player, getAttackDamage());
                if (EnchantmentHelper.getItemEnchantmentLevel(mod.azure.azurelib.platform.Services.PLATFORM.getIncendairyenchament(), itemStack) > 0)
                    needleEntity.setSecondsOnFire(100);
                needleEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
                needleEntity.tickCount = 23;
                level.addFreshEntity(needleEntity);
            }
            case PLASMA -> {
                player.getCooldowns().addCooldown(this, 8);
                var plamsaEntity = CommonHelper.createPlamsa(level, itemStack, player, getAttackDamage());
                if (EnchantmentHelper.getItemEnchantmentLevel(mod.azure.azurelib.platform.Services.PLATFORM.getIncendairyenchament(), itemStack) > 0)
                    plamsaEntity.setSecondsOnFire(100);
                plamsaEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
                level.addFreshEntity(plamsaEntity);
            }
            case PLASMAG -> {
                player.getCooldowns().addCooldown(this, 5);
                var plasmaGEntity = CommonHelper.createGPlamsa(level, itemStack, player, getAttackDamage());
                if (EnchantmentHelper.getItemEnchantmentLevel(mod.azure.azurelib.platform.Services.PLATFORM.getIncendairyenchament(), itemStack) > 0)
                    plasmaGEntity.setSecondsOnFire(100);
                plasmaGEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 0.5F * 3.0F, 1.0F);
                plasmaGEntity.tickCount = 33;
                level.addFreshEntity(plasmaGEntity);
            }
            case SHELL -> {
                player.getCooldowns().addCooldown(this, 18);
                for (int y = 0; y < 5; ++y) {
                    var shellEntity = CommonHelper.createBullet(level, itemStack, player, getAttackDamage());
                    if (EnchantmentHelper.getItemEnchantmentLevel(mod.azure.azurelib.platform.Services.PLATFORM.getIncendairyenchament(), itemStack) > 0)
                        shellEntity.setSecondsOnFire(100);
                    shellEntity.shootFromRotation(player, player.getXRot() + (y == 3 ? 1 : y == 4 ? -1 : 0), player.getYRot() + (y == 3 ? 1 : y == 2 ? -1 : y == 4 ? -1 : 0), 0.5F, 3.0F, 1.0F);
                    level.addFreshEntity(shellEntity);
                }
            }
        }
    }

    public void fireWeapon(@NotNull ItemStack itemStack, @NotNull Level level, @NotNull Player player) {
        CommonHelper.spawnLightSource(player, player.level().isWaterAt(player.blockPosition()));
        itemStack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(player.getUsedItemHand()));
        if (this.getFiringSound() != null)
            level.playSound(null, player.getX(), player.getY(), player.getZ(), getFiringSound(), SoundSource.PLAYERS, 0.25F, 1.3F);
        if (!level.isClientSide) {
            if (itemStack.getDamageValue() < (itemStack.getMaxDamage() - 1)) {
                this.autoFire(itemStack, level, player);
            }
            if (itemStack.getDamageValue() < (itemStack.getMaxDamage() - 1) && !player.getCooldowns().isOnCooldown(this)) {
                this.singleFire(itemStack, level, player);
            }
        }
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, Level world, @NotNull Entity entity, int slot, boolean selected) {
        if (world.isClientSide && entity instanceof Player player && player.getMainHandItem().getItem() instanceof HaloGunBase && selected) {
            if (Keybindings.RELOAD.consumeClick()) {
                Services.NETWORK.reload(slot);
            }
            if (AzureLibMod.config.useVanillaUseKey) {
                if (Minecraft.getInstance().options.keyUse.isDown()) {
                    Services.NETWORK.shoot(slot);
                }
            } else {
                if (Keybindings.FIRE_WEAPON.isDown()) {
                    Services.NETWORK.shoot(slot);
                }
            }
        }
    }

    public static void animate(Player player) {
        if (player.getMainHandItem().getDamageValue() < (player.getMainHandItem().getMaxDamage() - 1) && player.getMainHandItem().getItem() instanceof HaloGunBase gunBase) {
            if (!player.getCooldowns().isOnCooldown(player.getMainHandItem().getItem())) {
                gunBase.fireWeapon(player.getMainHandItem(), player.level(), player);
                gunBase.triggerAnim(player, GeoItem.getOrAssignId(player.getMainHandItem(), (ServerLevel) player.level()), "shoot_controller", HaloGunBase.firing);
            }
        } else {
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.COMPARATOR_CLICK, SoundSource.PLAYERS, 0.25F, 1.3F);
        }
    }

    public static void reload(Player user, InteractionHand hand) {
        if (user.getMainHandItem().getItem() instanceof HaloGunBase gunBase) {
            while (!user.isCreative() && user.getMainHandItem().getDamageValue() != 0 && user.getInventory().countItem(gunBase.getAmmoType()) > 0) {
                CommonHelper.removeAmmo(gunBase.getAmmoType(), user);
                user.getMainHandItem().hurtAndBreak(-gunBase.getClipSize(), user, s -> user.broadcastBreakEvent(hand));
                user.getMainHandItem().setPopTime(3);
                if (gunBase.getReloadSound() != null)
                    user.level().playSound(null, user.getX(), user.getY(), user.getZ(), gunBase.getReloadSound(), SoundSource.PLAYERS, 1.00F, 1.0F);
            }
        }
    }

    /*
     * Turns off the enchanted glint. Useful for Arachnids that uses enchantments for attachments.
     */
    @Override
    public boolean isFoil(ItemStack stack) {
        return stack.isEnchanted();
    }

    @Override
    public int getEnchantmentValue() {
        return 2;
    }

    /*
     * Makes the item not use enchantments in the enchament table.
     */
    @Override
    public boolean isEnchantable(@NotNull ItemStack stack) {
        return true;
    }

    @Override
    public @NotNull Rarity getRarity(@NotNull ItemStack stack) {
        return Rarity.RARE;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack) {
        return 72000;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, @NotNull TooltipFlag context) {
        tooltip.add(Component.translatable("Ammo: " + (stack.getMaxDamage() - stack.getDamageValue() - 1) + " / " + (stack.getMaxDamage() - 1)).withStyle(ChatFormatting.ITALIC));
    }

    @Override
    public Supplier<Object> getRenderProvider() {
        return renderProvider;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "shoot_controller", event -> {
            if (event.getData(DataTickets.ITEM_RENDER_PERSPECTIVE) == ItemDisplayContext.GUI) return PlayState.STOP;
            return PlayState.CONTINUE;
        }).triggerableAnim(HaloGunBase.firing, RawAnimation.begin().then(HaloGunBase.firing, Animation.LoopType.PLAY_ONCE)).triggerableAnim("reload", RawAnimation.begin().then("reload", Animation.LoopType.PLAY_ONCE)));
    }

    @Override
    public boolean isPerspectiveAware() {
        return true;
    }

    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private final GunRender<BaseGunItem> renderer = null;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (renderer == null) return new GunRender<BaseGunItem>(getItemID());
                return this.renderer;
            }
        });
    }
}