package mod.azure.mchalo.client.models.projectiles;

import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.entity.projectiles.BulletEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BulletModel extends AnimatedGeoModel<BulletEntity> {
	@Override
	public Identifier getModelLocation(BulletEntity object) {
		return new Identifier(MCHaloMod.MODID, "geo/bullet.geo.json");
	}

	@Override
	public Identifier getTextureLocation(BulletEntity object) {
		return new Identifier(MCHaloMod.MODID, "textures/items/bullet.png");
	}

	@Override
	public Identifier getAnimationFileLocation(BulletEntity animatable) {
		return new Identifier(MCHaloMod.MODID, "animations/bullet.animation.json");
	}
}
