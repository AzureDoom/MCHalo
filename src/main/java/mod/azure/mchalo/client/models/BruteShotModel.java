package mod.azure.mchalo.client.models;

import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.item.guns.BruteShotItem;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.model.GeoModel;

public class BruteShotModel extends GeoModel<BruteShotItem> {
	@Override
	public ResourceLocation getModelResource(BruteShotItem object) {
		return new ResourceLocation(MCHaloMod.MODID, "geo/brute_shot.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(BruteShotItem object) {
		return new ResourceLocation(MCHaloMod.MODID, "textures/item/brute_shot.png");
	}

	@Override
	public ResourceLocation getAnimationResource(BruteShotItem animatable) {
		return new ResourceLocation(MCHaloMod.MODID, "animations/brute_shot.animation.json");
	}
	
	@Override
	public RenderType getRenderType(BruteShotItem animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}
