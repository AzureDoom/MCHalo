package mod.azure.mchalo.client.models;

import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.item.guns.MagnumItem;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.model.GeoModel;

public class MagnumModel extends GeoModel<MagnumItem> {
	@Override
	public ResourceLocation getModelResource(MagnumItem object) {
		return new ResourceLocation(MCHaloMod.MODID, "geo/magnum_h3.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(MagnumItem object) {
		return new ResourceLocation(MCHaloMod.MODID, "textures/item/magnum_h3.png");
	}

	@Override
	public ResourceLocation getAnimationResource(MagnumItem animatable) {
		return new ResourceLocation(MCHaloMod.MODID, "animations/pistol.animation.json");
	}
	
	@Override
	public RenderType getRenderType(MagnumItem animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}
