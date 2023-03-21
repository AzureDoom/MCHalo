package mod.azure.mchalo.client.models;

import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.item.guns.PlasmaRifleItem;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.model.GeoModel;

public class PlasmaRifleModel extends GeoModel<PlasmaRifleItem> {
	@Override
	public ResourceLocation getModelResource(PlasmaRifleItem object) {
		return new ResourceLocation(MCHaloMod.MODID, "geo/plasma_rifle.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(PlasmaRifleItem object) {
		return new ResourceLocation(MCHaloMod.MODID, "textures/item/plasma_rifle.png");
	}

	@Override
	public ResourceLocation getAnimationResource(PlasmaRifleItem animatable) {
		return new ResourceLocation(MCHaloMod.MODID, "animations/plasma_rifle.animation.json");
	}

	@Override
	public RenderType getRenderType(PlasmaRifleItem animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}
