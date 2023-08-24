package mod.azure.mchalo.client;

import java.util.UUID;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.platform.InputConstants;

import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.client.gui.GunTableScreen;
import mod.azure.mchalo.client.render.projectiles.BulletRender;
import mod.azure.mchalo.client.render.projectiles.GrenadeRender;
import mod.azure.mchalo.client.render.projectiles.NeedleRender;
import mod.azure.mchalo.client.render.projectiles.PlasmaGRender;
import mod.azure.mchalo.client.render.projectiles.PlasmaRender;
import mod.azure.mchalo.client.render.projectiles.RocketRender;
import mod.azure.mchalo.particle.PlasmaParticle;
import mod.azure.mchalo.util.HaloItems;
import mod.azure.mchalo.util.HaloParticles;
import mod.azure.mchalo.util.ProjectilesEntityRegister;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

public class ClientInit implements ClientModInitializer {

	public static KeyMapping scope = new KeyMapping("key.mchalo.scope", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_LEFT_ALT, "category.mchalo.binds");

	@Override
	public void onInitializeClient() {
		MenuScreens.register(MCHaloMod.SCREEN_HANDLER_TYPE, GunTableScreen::new);
		KeyBindingHelper.registerKeyBinding(scope);
		EntityRendererRegistry.register(ProjectilesEntityRegister.BULLET, (ctx) -> new BulletRender(ctx));
		EntityRendererRegistry.register(ProjectilesEntityRegister.NEEDLE, (ctx) -> new NeedleRender(ctx));
		EntityRendererRegistry.register(ProjectilesEntityRegister.ROCKET, (ctx) -> new RocketRender(ctx));
		EntityRendererRegistry.register(ProjectilesEntityRegister.PLASMA, (ctx) -> new PlasmaRender(ctx));
		EntityRendererRegistry.register(ProjectilesEntityRegister.PLASMAG, (ctx) -> new PlasmaGRender(ctx));
		EntityRendererRegistry.register(ProjectilesEntityRegister.GRENADE, (ctx) -> new GrenadeRender(ctx));
		ItemProperties.register(HaloItems.SNIPER, new ResourceLocation("scoped"), (itemStack, clientWorld, livingEntity, seed) -> {
			if (livingEntity != null)
				return isScoped() ? 1.0F : 0.0F;
			return 0.0F;
		});
		ItemProperties.register(HaloItems.BATTLERIFLE, new ResourceLocation("scoped"), (itemStack, clientWorld, livingEntity, seed) -> {
			if (livingEntity != null)
				return isScoped() ? 1.0F : 0.0F;
			return 0.0F;
		});
		ParticleFactoryRegistry.getInstance().register(HaloParticles.PLASMA, PlasmaParticle.PurpleFactory::new);
		ParticleFactoryRegistry.getInstance().register(HaloParticles.PLASMAG, PlasmaParticle.GreenFactory::new);
	}

	private static boolean isScoped() {
		return scope.isDown();
	}

	@Environment(EnvType.CLIENT)
	public static void onPacket(Minecraft context, FriendlyByteBuf byteBuf) {
		EntityType<?> type = BuiltInRegistries.ENTITY_TYPE.byId(byteBuf.readVarInt());
		UUID entityUUID = byteBuf.readUUID();
		int entityID = byteBuf.readVarInt();
		double x = byteBuf.readDouble();
		double y = byteBuf.readDouble();
		double z = byteBuf.readDouble();
		float pitch = (byteBuf.readByte() * 360) / 256.0F;
		float yaw = (byteBuf.readByte() * 360) / 256.0F;
		context.execute(() -> {
			ClientLevel world = Minecraft.getInstance().level;
			Entity entity = type.create(world);
			if (entity != null) {
				entity.absMoveTo(x, y, z);
				entity.syncPacketPositionCodec(x, y, z);
				entity.setXRot(pitch);
				entity.setYRot(yaw);
				entity.setId(entityID);
				entity.setUUID(entityUUID);
				world.putNonPlayerEntity(entityID, entity);
			}
		});
	}

}
