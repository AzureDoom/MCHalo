package mod.azure.mchalo.client.models;

import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.item.guns.BruteShotItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BruteShotModel extends AnimatedGeoModel<BruteShotItem> {
	@Override
	public Identifier getModelLocation(BruteShotItem object) {
		return new Identifier(MCHaloMod.MODID, "geo/brute_shot.geo.json");
	}

	@Override
	public Identifier getTextureLocation(BruteShotItem object) {
		return new Identifier(MCHaloMod.MODID, "textures/items/brute_shot.png");
	}

	@Override
	public Identifier getAnimationFileLocation(BruteShotItem animatable) {
		return new Identifier(MCHaloMod.MODID, "animations/brute_shot.animation.json");
	}
}
