package mod.azure.mchalo.client.models.projectiles;

import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.entity.projectiles.RocketEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class RocketModel extends AnimatedGeoModel<RocketEntity> {
	@Override
	public Identifier getModelLocation(RocketEntity object) {
		return new Identifier(MCHaloMod.MODID, "geo/bullet.geo.json");
	}

	@Override
	public Identifier getTextureLocation(RocketEntity object) {
		return new Identifier(MCHaloMod.MODID, "textures/items/bullet.png");
	}

	@Override
	public Identifier getAnimationFileLocation(RocketEntity animatable) {
		return new Identifier(MCHaloMod.MODID, "animations/bullet.animation.json");
	}
}
