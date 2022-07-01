package mod.azure.mchalo.client.models;

import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.item.EnergySwordItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class EnergySwordModel extends AnimatedGeoModel<EnergySwordItem> {
	@Override
	public Identifier getModelResource(EnergySwordItem object) {
		return new Identifier(MCHaloMod.MODID, "geo/energy_sword.geo.json");
	}

	@Override
	public Identifier getTextureResource(EnergySwordItem object) {
		return new Identifier(MCHaloMod.MODID, "textures/items/energy_sword.png");
	}

	@Override
	public Identifier getAnimationResource(EnergySwordItem animatable) {
		return new Identifier(MCHaloMod.MODID, "animations/energy_sword.animation.json");
	}
}
