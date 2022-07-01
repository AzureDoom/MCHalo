package mod.azure.mchalo.client.models;

import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.item.guns.NeedlerItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class NeedlerModel extends AnimatedGeoModel<NeedlerItem> {
	@Override
	public Identifier getModelResource(NeedlerItem object) {
		return new Identifier(MCHaloMod.MODID, "geo/needler.geo.json");
	}

	@Override
	public Identifier getTextureResource(NeedlerItem object) {
		return new Identifier(MCHaloMod.MODID, "textures/items/needler.png");
	}

	@Override
	public Identifier getAnimationResource(NeedlerItem animatable) {
		return new Identifier(MCHaloMod.MODID, "animations/needler.animation.json");
	}
}
