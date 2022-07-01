package mod.azure.mchalo.client.models;

import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.item.guns.RocketLauncherItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class RocketLauncherModel extends AnimatedGeoModel<RocketLauncherItem> {
	@Override
	public Identifier getModelResource(RocketLauncherItem object) {
		return new Identifier(MCHaloMod.MODID, "geo/rocket_launcher_h3.geo.json");
	}

	@Override
	public Identifier getTextureResource(RocketLauncherItem object) {
		return new Identifier(MCHaloMod.MODID, "textures/items/rocket_launcher_h3.png");
	}

	@Override
	public Identifier getAnimationResource(RocketLauncherItem animatable) {
		return new Identifier(MCHaloMod.MODID, "animations/rocket_launcher_h3.animation.json");
	}
}
