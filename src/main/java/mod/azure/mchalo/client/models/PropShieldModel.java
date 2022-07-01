package mod.azure.mchalo.client.models;

import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.item.PropShieldItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PropShieldModel extends AnimatedGeoModel<PropShieldItem> {
	@Override
	public Identifier getModelResource(PropShieldItem object) {
		return new Identifier(MCHaloMod.MODID, "geo/prop_shield_h2.geo.json");
	}

	@Override
	public Identifier getTextureResource(PropShieldItem object) {
		return new Identifier(MCHaloMod.MODID, "textures/items/shield_h2.png");
	}

	@Override
	public Identifier getAnimationResource(PropShieldItem animatable) {
		return new Identifier(MCHaloMod.MODID, "animations/shield_h2.animation.json");
	}
}
