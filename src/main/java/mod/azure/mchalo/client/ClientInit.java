package mod.azure.mchalo.client;

import java.util.UUID;

import org.lwjgl.glfw.GLFW;

import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.client.gui.GunTableScreen;
import mod.azure.mchalo.client.render.BattleRifleRender;
import mod.azure.mchalo.client.render.BruteShotRender;
import mod.azure.mchalo.client.render.EnergySwordRender;
import mod.azure.mchalo.client.render.MagnumRender;
import mod.azure.mchalo.client.render.MaulerRender;
import mod.azure.mchalo.client.render.NeedlerRender;
import mod.azure.mchalo.client.render.PlasmaPistolRender;
import mod.azure.mchalo.client.render.PlasmaRifleRender;
import mod.azure.mchalo.client.render.PropShieldRender;
import mod.azure.mchalo.client.render.RocketLauncherRender;
import mod.azure.mchalo.client.render.ShotgunRender;
import mod.azure.mchalo.client.render.SniperRender;
import mod.azure.mchalo.client.render.projectiles.BulletRender;
import mod.azure.mchalo.client.render.projectiles.GrenadeItemRender;
import mod.azure.mchalo.client.render.projectiles.GrenadeRender;
import mod.azure.mchalo.client.render.projectiles.NeedleRender;
import mod.azure.mchalo.client.render.projectiles.PlasmaGRender;
import mod.azure.mchalo.client.render.projectiles.PlasmaRender;
import mod.azure.mchalo.client.render.projectiles.RocketRender;
import mod.azure.mchalo.network.EntityPacket;
import mod.azure.mchalo.util.HaloItems;
import mod.azure.mchalo.util.ProjectilesEntityRegister;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class ClientInit implements ClientModInitializer {

	public static KeyBinding reload = new KeyBinding("key.mchalo.reload", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R,
			"category.mchalo.binds");

	public static KeyBinding scope = new KeyBinding("key.mchalo.scope", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_LEFT_ALT,
			"category.mchalo.binds");

	@Override
	public void onInitializeClient() {
		ScreenRegistry.register(MCHaloMod.SCREEN_HANDLER_TYPE, GunTableScreen::new);
		KeyBindingHelper.registerKeyBinding(reload);
		KeyBindingHelper.registerKeyBinding(scope);
		GeoItemRenderer.registerItemRenderer(HaloItems.SNIPER, new SniperRender());
		GeoItemRenderer.registerItemRenderer(HaloItems.SHOTGUN, new ShotgunRender());
		GeoItemRenderer.registerItemRenderer(HaloItems.MAGNUM, new MagnumRender());
		GeoItemRenderer.registerItemRenderer(HaloItems.BATTLERIFLE, new BattleRifleRender());
		GeoItemRenderer.registerItemRenderer(HaloItems.ROCKETLAUNCHER, new RocketLauncherRender());
		GeoItemRenderer.registerItemRenderer(HaloItems.PROPSHIELD, new PropShieldRender());
		GeoItemRenderer.registerItemRenderer(HaloItems.ENERGYSWORD, new EnergySwordRender());
		GeoItemRenderer.registerItemRenderer(HaloItems.NEEDLER, new NeedlerRender());
		GeoItemRenderer.registerItemRenderer(HaloItems.BRUTESHOT, new BruteShotRender());
		GeoItemRenderer.registerItemRenderer(HaloItems.MAULER, new MaulerRender());
		GeoItemRenderer.registerItemRenderer(HaloItems.PLASMAPISTOL, new PlasmaPistolRender());
		GeoItemRenderer.registerItemRenderer(HaloItems.PLASMARIFLE, new PlasmaRifleRender());
		GeoItemRenderer.registerItemRenderer(HaloItems.GRENADE, new GrenadeItemRender());
		EntityRendererRegistry.register(ProjectilesEntityRegister.BULLET, (ctx) -> new BulletRender(ctx));
		EntityRendererRegistry.register(ProjectilesEntityRegister.NEEDLE, (ctx) -> new NeedleRender(ctx));
		EntityRendererRegistry.register(ProjectilesEntityRegister.ROCKET, (ctx) -> new RocketRender(ctx));
		EntityRendererRegistry.register(ProjectilesEntityRegister.PLASMA, (ctx) -> new PlasmaRender(ctx));
		EntityRendererRegistry.register(ProjectilesEntityRegister.PLASMAG, (ctx) -> new PlasmaGRender(ctx));
		EntityRendererRegistry.register(ProjectilesEntityRegister.GRENADE, (ctx) -> new GrenadeRender(ctx));
		FabricModelPredicateProviderRegistry.register(HaloItems.SNIPER, new Identifier("scoped"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					if (livingEntity != null)
						return isScoped() ? 1.0F : 0.0F;
					return 0.0F;
				});
		FabricModelPredicateProviderRegistry.register(HaloItems.BATTLERIFLE, new Identifier("scoped"),
				(itemStack, clientWorld, livingEntity, seed) -> {
					if (livingEntity != null)
						return isScoped() ? 1.0F : 0.0F;
					return 0.0F;
				});
		ClientPlayNetworking.registerGlobalReceiver(EntityPacket.ID, (client, handler, buf, responseSender) -> {
			onPacket(client, buf);
		});
	}

	private static boolean isScoped() {
		return scope.isPressed();
	}

	@Environment(EnvType.CLIENT)
	public static void onPacket(MinecraftClient context, PacketByteBuf byteBuf) {
		EntityType<?> type = Registry.ENTITY_TYPE.get(byteBuf.readVarInt());
		UUID entityUUID = byteBuf.readUuid();
		int entityID = byteBuf.readVarInt();
		double x = byteBuf.readDouble();
		double y = byteBuf.readDouble();
		double z = byteBuf.readDouble();
		float pitch = (byteBuf.readByte() * 360) / 256.0F;
		float yaw = (byteBuf.readByte() * 360) / 256.0F;
		context.execute(() -> {
			@SuppressWarnings("resource")
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
