package mod.azure.mchalo.client.models;

import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.item.guns.MaulerItem;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.model.GeoModel;

public class MaulerModel extends GeoModel<MaulerItem> {
	@Override
	public ResourceLocation getModelResource(MaulerItem object) {
		return new ResourceLocation(MCHaloMod.MODID, "geo/mauler.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(MaulerItem object) {
		return new ResourceLocation(MCHaloMod.MODID, "textures/item/mauler.png");
	}

	@Override
	public ResourceLocation getAnimationResource(MaulerItem animatable) {
		return new ResourceLocation(MCHaloMod.MODID, "animations/mauler.animation.json");
	}

	@Override
	public RenderType getRenderType(MaulerItem animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}
