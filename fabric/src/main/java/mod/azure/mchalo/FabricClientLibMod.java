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
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;

public class FabricClientLibMod implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        MenuScreens.register(FabricLibMod.SCREEN_HANDLER_TYPE, GunTableScreen::new);
        EntityRendererRegistry.register(Entities.BULLET, EmptyRender::new);
        EntityRendererRegistry.register(Entities.NEEDLE, (ctx) -> new ProjectileRender(ctx, EntityEnum.NEEEDLE, "needle"));
        EntityRendererRegistry.register(Entities.ROCKET, EmptyRender::new);
        EntityRendererRegistry.register(Entities.PLASMA, EmptyRender::new);
        EntityRendererRegistry.register(Entities.PLASMAG, EmptyRender::new);
        EntityRendererRegistry.register(Entities.GRENADE, (ctx) -> new ProjectileRender(ctx, EntityEnum.GRENADE, "rocket"));
        ItemProperties.register(Items.SNIPER, new ResourceLocation("scoped"), (itemStack, clientWorld, livingEntity, seed) -> {
            if (livingEntity != null) return Keybindings.SCOPE.isDown() ? 1.0F : 0.0F;
            return 0.0F;
        });
        ItemProperties.register(Items.BATTLERIFLE, new ResourceLocation("scoped"), (itemStack, clientWorld, livingEntity, seed) -> {
            if (livingEntity != null) return Keybindings.SCOPE.isDown() ? 1.0F : 0.0F;
            return 0.0F;
        });
        ParticleFactoryRegistry.getInstance().register(Particles.PLASMA, PlasmaParticle.PurpleFactory::new);
        ParticleFactoryRegistry.getInstance().register(Particles.PLASMAG, PlasmaParticle.GreenFactory::new);
    }
}
