package mod.azure.mchalo.client.models;

import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.item.guns.SniperItem;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class SniperModel extends GeoModel<SniperItem> {
	@Override
	public Identifier getModelResource(SniperItem object) {
		return new Identifier(MCHaloMod.MODID, "geo/sniper_h3.geo.json");
	}

	@Override
	public Identifier getTextureResource(SniperItem object) {
		return new Identifier(MCHaloMod.MODID, "textures/item/sniper_h3.png");
	}

	@Override
	public Identifier getAnimationResource(SniperItem animatable) {
		return new Identifier(MCHaloMod.MODID, "animations/sniper_h3.animation.json");
	}

	@Override
	public RenderLayer getRenderType(SniperItem animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}
}
