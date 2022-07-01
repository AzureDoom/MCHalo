package mod.azure.mchalo.client.models;

import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.item.guns.ShotgunItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ShotgunModel extends AnimatedGeoModel<ShotgunItem> {
	@Override
	public Identifier getModelResource(ShotgunItem object) {
		return new Identifier(MCHaloMod.MODID, "geo/shotgun_h2.geo.json");
	}

	@Override
	public Identifier getTextureResource(ShotgunItem object) {
		return new Identifier(MCHaloMod.MODID, "textures/items/shotgun_h2.png");
	}

	@Override
	public Identifier getAnimationResource(ShotgunItem animatable) {
		return new Identifier(MCHaloMod.MODID, "animations/shotgun_h2.animation.json");
	}
}
