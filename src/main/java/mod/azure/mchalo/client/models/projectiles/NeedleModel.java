package mod.azure.mchalo.client.models.projectiles;

import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.entity.projectiles.NeedleEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.model.GeoModel;

public class NeedleModel extends GeoModel<NeedleEntity> {
	@Override
	public ResourceLocation getModelResource(NeedleEntity object) {
		return new ResourceLocation(MCHaloMod.MODID, "geo/needle.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(NeedleEntity object) {
		return new ResourceLocation(MCHaloMod.MODID, "textures/item/needler.png");
	}

	@Override
	public ResourceLocation getAnimationResource(NeedleEntity animatable) {
		return new ResourceLocation(MCHaloMod.MODID, "animations/needle.animation.json");
	}

	@Override
	public RenderType getRenderType(NeedleEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}
