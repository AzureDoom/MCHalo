package mod.azure.mchalo.client.models;

import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.item.guns.MaulerItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MaulerModel extends AnimatedGeoModel<MaulerItem> {
	@Override
	public Identifier getModelResource(MaulerItem object) {
		return new Identifier(MCHaloMod.MODID, "geo/mauler.geo.json");
	}

	@Override
	public Identifier getTextureResource(MaulerItem object) {
		return new Identifier(MCHaloMod.MODID, "textures/items/mauler.png");
	}

	@Override
	public Identifier getAnimationResource(MaulerItem animatable) {
		return new Identifier(MCHaloMod.MODID, "animations/mauler.animation.json");
	}
}
