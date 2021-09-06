package mod.azure.mchalo.client.models;

import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.item.guns.RocketLauncherItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class RocketLauncherModel extends AnimatedGeoModel<RocketLauncherItem> {
	@Override
	public Identifier getModelLocation(RocketLauncherItem object) {
		return new Identifier(MCHaloMod.MODID, "geo/rocket_launcher_h3.geo.json");
	}

	@Override
	public Identifier getTextureLocation(RocketLauncherItem object) {
		return new Identifier(MCHaloMod.MODID, "textures/items/rocket_launcher_h3.png");
	}

	@Override
	public Identifier getAnimationFileLocation(RocketLauncherItem animatable) {
		return new Identifier(MCHaloMod.MODID, "animations/rocket_launcher_h3.animation.json");
	}
}
