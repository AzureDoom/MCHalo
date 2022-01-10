package mod.azure.mchalo.client.models.projectiles;

import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.entity.projectiles.PlasmaEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PlasmaModel extends AnimatedGeoModel<PlasmaEntity> {
	@Override
	public Identifier getModelLocation(PlasmaEntity object) {
		return new Identifier(MCHaloMod.MODID, "geo/plasmabolt.geo.json");
	}

	@Override
	public Identifier getTextureLocation(PlasmaEntity object) {
		return new Identifier(MCHaloMod.MODID, "textures/items/plasma.png");
	}

	@Override
	public Identifier getAnimationFileLocation(PlasmaEntity animatable) {
		return new Identifier(MCHaloMod.MODID, "animations/bullet.animation.json");
	}
}
