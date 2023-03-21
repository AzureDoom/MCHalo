package mod.azure.mchalo.client.models.projectiles;

import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.entity.projectiles.GrenadeEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.model.GeoModel;

public class GrenadeModel extends GeoModel<GrenadeEntity> {
	@Override
	public ResourceLocation getModelResource(GrenadeEntity object) {
		return new ResourceLocation(MCHaloMod.MODID, "geo/grenade.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(GrenadeEntity object) {
		return new ResourceLocation(MCHaloMod.MODID, "textures/item/grenade.png");
	}

	@Override
	public ResourceLocation getAnimationResource(GrenadeEntity animatable) {
		return new ResourceLocation(MCHaloMod.MODID, "animations/grenade.animation.json");
	}

	@Override
	public RenderType getRenderType(GrenadeEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}
