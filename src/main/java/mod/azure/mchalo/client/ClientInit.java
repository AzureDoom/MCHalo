package mod.azure.mchalo.client;

import java.util.UUID;

import org.lwjgl.glfw.GLFW;

import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.client.gui.GunTableScreen;
import mod.azure.mchalo.client.render.projectiles.BulletRender;
import mod.azure.mchalo.client.render.projectiles.GrenadeRender;
import mod.azure.mchalo.client.render.projectiles.NeedleRender;
import mod.azure.mchalo.client.render.projectiles.PlasmaGRender;
import mod.azure.mchalo.client.render.projectiles.PlasmaRender;
import mod.azure.mchalo.client.render.projectiles.RocketRender;
import mod.azure.mchalo.network.HaloEntityPacket;
import mod.azure.mchalo.particle.PlasmaParticle;
import mod.azure.mchalo.util.HaloItems;
import mod.azure.mchalo.util.HaloParticles;
import mod.azure.mchalo.util.ProjectilesEntityRegister;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class ClientInit implements ClientModInitializer {

	public static KeyBinding reload = new KeyBinding("key.mchalo.reload", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R,
			"category.mchalo.binds");

	public static KeyBinding scope = new KeyBinding("key.mchalo.scope", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_LEFT_ALT,
			"category.mchalo.binds");

	@Override
	public void onInitializeClient() {
		HandledScreens.register(MCHaloMod.SCREEN_HANDLER_TYPE, GunTableScreen::new);
		KeyBindingHelper.registerKeyBinding(reload);
		KeyBindingHelper.registerKeyBinding(scope);
		EntityRendererRegistry.register(ProjectilesEntityRegister.BULLET, (ctx) -> new BulletRender(ctx));
		EntityRendererRegistry.register(ProjectilesEntityRegister.NEEDLE, (ctx) -> new NeedleRender(ctx));
		EntityRendererRegistry.register(ProjectilesEntityRegister.ROCKET, (ctx) -> new RocketRender(ctx));
		EntityRendererRegistry.register(ProjectilesEntityRegister.PLASMA, (ctx) -> new PlasmaRender(ctx));
		EntityRendererRegistry.register(ProjectilesEntityRegister.PLASMAG, (ctx) -> new PlasmaGRender(ctx));
		EntityRendererRegistry.register(ProjectilesEntityRegister.GRENADE, (ctx) -> new GrenadeRender(ctx));
		ModelPredicateProviderRegistry.register(HaloItems.SNIPER, new Identifier("scoped"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					if (livingEntity != null)
						return isScoped() ? 1.0F : 0.0F;
					return 0.0F;
				});
		ModelPredicateProviderRegistry.register(HaloItems.BATTLERIFLE, new Identifier("scoped"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					if (livingEntity != null)
						return isScoped() ? 1.0F : 0.0F;
					return 0.0F;
				});
		ClientPlayNetworking.registerGlobalReceiver(HaloEntityPacket.ID, (client, handler, buf, responseSender) -> {
			onPacket(client, buf);
		});
		ParticleFactoryRegistry.getInstance().register(HaloParticles.PLASMA, PlasmaParticle.PurpleFactory::new);
		ParticleFactoryRegistry.getInstance().register(HaloParticles.PLASMAG, PlasmaParticle.GreenFactory::new);
	}

	private static boolean isScoped() {
		return scope.isPressed();
	}

	@Environment(EnvType.CLIENT)
	public static void onPacket(MinecraftClient context, PacketByteBuf byteBuf) {
		EntityType<?> type = Registries.ENTITY_TYPE.get(byteBuf.readVarInt());
		UUID entityUUID = byteBuf.readUuid();
		int entityID = byteBuf.readVarInt();
		double x = byteBuf.readDouble();
		double y = byteBuf.readDouble();
		double z = byteBuf.readDouble();
		float pitch = (byteBuf.readByte() * 360) / 256.0F;
		float yaw = (byteBuf.readByte() * 360) / 256.0F;
		context.execute(() -> {
			ClientWorld world = MinecraftClient.getInstance().world;
			Entity entity = type.create(world);
			if (entity != null) {
				entity.updatePosition(x, y, z);
				entity.updateTrackedPosition(x, y, z);
				entity.setPitch(pitch);
				entity.setYaw(yaw);
				entity.setId(entityID);
				entity.setUuid(entityUUID);
				world.addEntity(entityID, entity);
			}
		});
	}

}
