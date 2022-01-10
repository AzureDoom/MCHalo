package mod.azure.mchalo.client.models.projectiles;

import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.entity.projectiles.PlasmaGEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PlasmaGModel extends AnimatedGeoModel<PlasmaGEntity> {
	@Override
	public Identifier getModelLocation(PlasmaGEntity object) {
		return new Identifier(MCHaloMod.MODID, "geo/plasmabolt.geo.json");
	}

	@Override
	public Identifier getTextureLocation(PlasmaGEntity object) {
		return new Identifier(MCHaloMod.MODID, "textures/items/plasma_green.png");
	}

	@Override
	public Identifier getAnimationFileLocation(PlasmaGEntity animatable) {
		return new Identifier(MCHaloMod.MODID, "animations/bullet.animation.json");
	}
}
