package mod.azure.mchalo.client.models;

import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.item.guns.SniperItem;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.model.GeoModel;

public class SniperModel extends GeoModel<SniperItem> {
	@Override
	public ResourceLocation getModelResource(SniperItem object) {
		return new ResourceLocation(MCHaloMod.MODID, "geo/sniper_h3.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(SniperItem object) {
		return new ResourceLocation(MCHaloMod.MODID, "textures/item/sniper_h3.png");
	}

	@Override
	public ResourceLocation getAnimationResource(SniperItem animatable) {
		return new ResourceLocation(MCHaloMod.MODID, "animations/sniper_h3.animation.json");
	}

	@Override
	public RenderType getRenderType(SniperItem animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}
