package mod.azure.mchalo.client.models;

import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.item.guns.PlasmaPistolItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PlasmaPistolModel extends AnimatedGeoModel<PlasmaPistolItem> {
	@Override
	public Identifier getModelResource(PlasmaPistolItem object) {
		return new Identifier(MCHaloMod.MODID, "geo/plasma_pistol.geo.json");
	}

	@Override
	public Identifier getTextureResource(PlasmaPistolItem object) {
		return new Identifier(MCHaloMod.MODID, "textures/items/plasma_pistol.png");
	}

	@Override
	public Identifier getAnimationResource(PlasmaPistolItem animatable) {
		return new Identifier(MCHaloMod.MODID, "animations/plasma_pistol.animation.json");
	}
}
