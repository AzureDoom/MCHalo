package mod.azure.mchalo.client.models;

import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.item.guns.ShotgunItem;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.model.GeoModel;

public class ShotgunModel extends GeoModel<ShotgunItem> {
	@Override
	public ResourceLocation getModelResource(ShotgunItem object) {
		return new ResourceLocation(MCHaloMod.MODID, "geo/shotgun_h2.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(ShotgunItem object) {
		return new ResourceLocation(MCHaloMod.MODID, "textures/item/shotgun_h2.png");
	}

	@Override
	public ResourceLocation getAnimationResource(ShotgunItem animatable) {
		return new ResourceLocation(MCHaloMod.MODID, "animations/shotgun_h2.animation.json");
	}

	@Override
	public RenderType getRenderType(ShotgunItem animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}
