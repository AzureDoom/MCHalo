package mod.azure.mchalo.client.models;

import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.item.guns.PlasmaPistolItem;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.model.GeoModel;

public class PlasmaPistolModel extends GeoModel<PlasmaPistolItem> {
	@Override
	public ResourceLocation getModelResource(PlasmaPistolItem object) {
		return new ResourceLocation(MCHaloMod.MODID, "geo/plasma_pistol.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(PlasmaPistolItem object) {
		return new ResourceLocation(MCHaloMod.MODID, "textures/item/plasma_pistol.png");
	}

	@Override
	public ResourceLocation getAnimationResource(PlasmaPistolItem animatable) {
		return new ResourceLocation(MCHaloMod.MODID, "animations/plasma_pistol.animation.json");
	}

	@Override
	public RenderType getRenderType(PlasmaPistolItem animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}
