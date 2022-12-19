package mod.azure.mchalo.client.models;

import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.item.guns.BruteShotItem;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class BruteShotModel extends GeoModel<BruteShotItem> {
	@Override
	public Identifier getModelResource(BruteShotItem object) {
		return new Identifier(MCHaloMod.MODID, "geo/brute_shot.geo.json");
	}

	@Override
	public Identifier getTextureResource(BruteShotItem object) {
		return new Identifier(MCHaloMod.MODID, "textures/item/brute_shot.png");
	}

	@Override
	public Identifier getAnimationResource(BruteShotItem animatable) {
		return new Identifier(MCHaloMod.MODID, "animations/brute_shot.animation.json");
	}
	
	@Override
	public RenderLayer getRenderType(BruteShotItem animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}
}
