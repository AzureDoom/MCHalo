package mod.azure.mchalo;

import mod.azure.azurelib.Keybindings;
import mod.azure.mchalo.client.gui.GunTableScreen;
import mod.azure.mchalo.client.render.ProjectileRender;
import mod.azure.mchalo.client.render.projectiles.EmptyRender;
import mod.azure.mchalo.helper.EntityEnum;
import mod.azure.mchalo.particle.PlasmaParticle;
import mod.azure.mchalo.registry.Entities;
import mod.azure.mchalo.registry.Items;
import mod.azure.mchalo.registry.Particles;
import mod.azure.mchalo.registry.Screens;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = CommonMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public record NeoForgeClientMod() {

    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(Entities.BULLET.get(), EmptyRender::new);
        event.registerEntityRenderer(Entities.NEEDLE.get(), ctx -> new ProjectileRender<>(ctx, EntityEnum.NEEEDLE, "needle"));
        event.registerEntityRenderer(Entities.ROCKET.get(), EmptyRender::new);
        event.registerEntityRenderer(Entities.PLASMA.get(), EmptyRender::new);
        event.registerEntityRenderer(Entities.PLASMAG.get(), EmptyRender::new);
        event.registerEntityRenderer(Entities.GRENADE.get(), ctx -> new ProjectileRender<>(ctx, EntityEnum.GRENADE, "rocket"));
    }

    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent event) {
        MenuScreens.register(Screens.SCREEN_HANDLER_TYPE.get(), GunTableScreen::new);
        ItemProperties.register(Items.SNIPER.get(), new ResourceLocation("scoped"), (itemStack, clientWorld, livingEntity, seed) -> NeoForgeClientMod.isScoped(livingEntity));
        ItemProperties.register(Items.BATTLERIFLE.get(), new ResourceLocation("scoped"), (itemStack, clientWorld, livingEntity, seed) -> NeoForgeClientMod.isScoped(livingEntity));
    }

    @SubscribeEvent
    public static void registry(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(Particles.PLASMA.get(), PlasmaParticle.PurpleFactory::new);
        event.registerSpriteSet(Particles.PLASMAG.get(), PlasmaParticle.GreenFactory::new);
    }

    public static float isScoped(LivingEntity livingEntity) {
        if (livingEntity != null) return Keybindings.SCOPE.isDown() ? 1.0F : 0.0F;
        return 0.0F;
    }
}
