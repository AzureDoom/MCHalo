package mod.azure.mchalo.client.models;

import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.item.EnergySwordItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class EnergySwordModel extends AnimatedGeoModel<EnergySwordItem> {
	@Override
	public Identifier getModelLocation(EnergySwordItem object) {
		return new Identifier(MCHaloMod.MODID, "geo/energy_sword.geo.json");
	}

	@Override
	public Identifier getTextureLocation(EnergySwordItem object) {
		return new Identifier(MCHaloMod.MODID, "textures/items/energy_sword.png");
	}

	@Override
	public Identifier getAnimationFileLocation(EnergySwordItem animatable) {
		return new Identifier(MCHaloMod.MODID, "animations/energy_sword.animation.json");
	}
}
