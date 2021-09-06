package mod.azure.mchalo.client.models;

import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.item.guns.SniperItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SniperModel extends AnimatedGeoModel<SniperItem> {
	@Override
	public Identifier getModelLocation(SniperItem object) {
		return new Identifier(MCHaloMod.MODID, "geo/sniper_h3.geo.json");
	}

	@Override
	public Identifier getTextureLocation(SniperItem object) {
		return new Identifier(MCHaloMod.MODID, "textures/items/sniper_h3.png");
	}

	@Override
	public Identifier getAnimationFileLocation(SniperItem animatable) {
		return new Identifier(MCHaloMod.MODID, "animations/sniper_h3.animation.json");
	}
}
