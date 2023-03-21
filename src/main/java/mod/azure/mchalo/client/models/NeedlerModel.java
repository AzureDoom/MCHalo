package mod.azure.mchalo.client.models;

import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.item.guns.NeedlerItem;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.model.GeoModel;

public class NeedlerModel extends GeoModel<NeedlerItem> {
	@Override
	public ResourceLocation getModelResource(NeedlerItem object) {
		return new ResourceLocation(MCHaloMod.MODID, "geo/needler.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(NeedlerItem object) {
		return new ResourceLocation(MCHaloMod.MODID, "textures/item/needler.png");
	}

	@Override
	public ResourceLocation getAnimationResource(NeedlerItem animatable) {
		return new ResourceLocation(MCHaloMod.MODID, "animations/needler.animation.json");
	}
	
	@Override
	public RenderType getRenderType(NeedlerItem animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}
