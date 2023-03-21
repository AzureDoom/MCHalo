package mod.azure.mchalo.client.models;

import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.item.guns.RocketLauncherItem;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.model.GeoModel;

public class RocketLauncherModel extends GeoModel<RocketLauncherItem> {
	@Override
	public ResourceLocation getModelResource(RocketLauncherItem object) {
		return new ResourceLocation(MCHaloMod.MODID, "geo/rocket_launcher_h3.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(RocketLauncherItem object) {
		return new ResourceLocation(MCHaloMod.MODID, "textures/item/rocket_launcher_h3.png");
	}

	@Override
	public ResourceLocation getAnimationResource(RocketLauncherItem animatable) {
		return new ResourceLocation(MCHaloMod.MODID, "animations/rocket_launcher_h3.animation.json");
	}

	@Override
	public RenderType getRenderType(RocketLauncherItem animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}
