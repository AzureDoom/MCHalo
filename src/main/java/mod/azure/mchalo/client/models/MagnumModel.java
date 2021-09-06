package mod.azure.mchalo.client.models;

import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.item.guns.MagnumItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MagnumModel extends AnimatedGeoModel<MagnumItem> {
	@Override
	public Identifier getModelLocation(MagnumItem object) {
		return new Identifier(MCHaloMod.MODID, "geo/magnum_h3.geo.json");
	}

	@Override
	public Identifier getTextureLocation(MagnumItem object) {
		return new Identifier(MCHaloMod.MODID, "textures/items/magnum_h3.png");
	}

	@Override
	public Identifier getAnimationFileLocation(MagnumItem animatable) {
		return new Identifier(MCHaloMod.MODID, "animations/pistol.animation.json");
	}
}
