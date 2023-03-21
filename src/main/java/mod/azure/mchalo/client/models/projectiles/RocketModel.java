package mod.azure.mchalo.client.models.projectiles;

import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.entity.projectiles.RocketEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.model.GeoModel;

public class RocketModel extends GeoModel<RocketEntity> {
	@Override
	public ResourceLocation getModelResource(RocketEntity object) {
		return new ResourceLocation(MCHaloMod.MODID, "geo/bullet.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(RocketEntity object) {
		return new ResourceLocation(MCHaloMod.MODID, "textures/item/bullet.png");
	}

	@Override
	public ResourceLocation getAnimationResource(RocketEntity animatable) {
		return new ResourceLocation(MCHaloMod.MODID, "animations/bullet.animation.json");
	}

	@Override
	public RenderType getRenderType(RocketEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}
