package mod.azure.mchalo.client.models;

import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.item.guns.PlasmaRifleItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PlasmaRifleModel extends AnimatedGeoModel<PlasmaRifleItem> {
	@Override
	public Identifier getModelLocation(PlasmaRifleItem object) {
		return new Identifier(MCHaloMod.MODID, "geo/plasma_rifle.geo.json");
	}

	@Override
	public Identifier getTextureLocation(PlasmaRifleItem object) {
		return new Identifier(MCHaloMod.MODID, "textures/items/plasma_rifle.png");
	}

	@Override
	public Identifier getAnimationFileLocation(PlasmaRifleItem animatable) {
		return new Identifier(MCHaloMod.MODID, "animations/plasma_rifle.animation.json");
	}
}
