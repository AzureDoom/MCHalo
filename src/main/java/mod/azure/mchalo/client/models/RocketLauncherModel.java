package mod.azure.mchalo.client.models;

import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.item.guns.RocketLauncherItem;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class RocketLauncherModel extends GeoModel<RocketLauncherItem> {
	@Override
	public Identifier getModelResource(RocketLauncherItem object) {
		return new Identifier(MCHaloMod.MODID, "geo/rocket_launcher_h3.geo.json");
	}

	@Override
	public Identifier getTextureResource(RocketLauncherItem object) {
		return new Identifier(MCHaloMod.MODID, "textures/item/rocket_launcher_h3.png");
	}

	@Override
	public Identifier getAnimationResource(RocketLauncherItem animatable) {
		return new Identifier(MCHaloMod.MODID, "animations/rocket_launcher_h3.animation.json");
	}

	@Override
	public RenderLayer getRenderType(RocketLauncherItem animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}
}
