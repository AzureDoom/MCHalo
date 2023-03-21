package mod.azure.mchalo.client.models;

import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.item.EnergySwordItem;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.model.GeoModel;

public class EnergySwordModel extends GeoModel<EnergySwordItem> {
	@Override
	public ResourceLocation getModelResource(EnergySwordItem object) {
		return new ResourceLocation(MCHaloMod.MODID, "geo/energy_sword.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(EnergySwordItem object) {
		return new ResourceLocation(MCHaloMod.MODID, "textures/item/energy_sword.png");
	}

	@Override
	public ResourceLocation getAnimationResource(EnergySwordItem animatable) {
		return new ResourceLocation(MCHaloMod.MODID, "animations/energy_sword.animation.json");
	}
	
	@Override
	public RenderType getRenderType(EnergySwordItem animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}
