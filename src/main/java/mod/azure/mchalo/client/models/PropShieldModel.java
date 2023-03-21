package mod.azure.mchalo.client.models;

import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.item.PropShieldItem;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.model.GeoModel;

public class PropShieldModel extends GeoModel<PropShieldItem> {
	@Override
	public ResourceLocation getModelResource(PropShieldItem object) {
		return new ResourceLocation(MCHaloMod.MODID, "geo/prop_shield_h2.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(PropShieldItem object) {
		return new ResourceLocation(MCHaloMod.MODID, "textures/item/shield_h2.png");
	}

	@Override
	public ResourceLocation getAnimationResource(PropShieldItem animatable) {
		return new ResourceLocation(MCHaloMod.MODID, "animations/shield_h2.animation.json");
	}

	@Override
	public RenderType getRenderType(PropShieldItem animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}
