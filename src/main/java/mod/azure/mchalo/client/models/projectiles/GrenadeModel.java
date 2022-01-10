package mod.azure.mchalo.client.models.projectiles;

import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.entity.projectiles.GrenadeEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GrenadeModel extends AnimatedGeoModel<GrenadeEntity> {
	@Override
	public Identifier getModelLocation(GrenadeEntity object) {
		return new Identifier(MCHaloMod.MODID, "geo/grenade.geo.json");
	}

	@Override
	public Identifier getTextureLocation(GrenadeEntity object) {
		return new Identifier(MCHaloMod.MODID, "textures/items/grenade.png");
	}

	@Override
	public Identifier getAnimationFileLocation(GrenadeEntity animatable) {
		return new Identifier(MCHaloMod.MODID, "animations/grenade.animation.json");
	}
}
