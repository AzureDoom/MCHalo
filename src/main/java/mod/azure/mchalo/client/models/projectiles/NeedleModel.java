package mod.azure.mchalo.client.models.projectiles;

import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.entity.projectiles.NeedleEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class NeedleModel extends AnimatedGeoModel<NeedleEntity> {
	@Override
	public Identifier getModelLocation(NeedleEntity object) {
		return new Identifier(MCHaloMod.MODID, "geo/needle.geo.json");
	}

	@Override
	public Identifier getTextureLocation(NeedleEntity object) {
		return new Identifier(MCHaloMod.MODID, "textures/items/needler.png");
	}

	@Override
	public Identifier getAnimationFileLocation(NeedleEntity animatable) {
		return new Identifier(MCHaloMod.MODID, "animations/needle.animation.json");
	}
}
