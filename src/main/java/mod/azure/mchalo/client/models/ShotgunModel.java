package mod.azure.mchalo.client.models;

import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.item.guns.ShotgunItem;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class ShotgunModel extends GeoModel<ShotgunItem> {
	@Override
	public Identifier getModelResource(ShotgunItem object) {
		return new Identifier(MCHaloMod.MODID, "geo/shotgun_h2.geo.json");
	}

	@Override
	public Identifier getTextureResource(ShotgunItem object) {
		return new Identifier(MCHaloMod.MODID, "textures/item/shotgun_h2.png");
	}

	@Override
	public Identifier getAnimationResource(ShotgunItem animatable) {
		return new Identifier(MCHaloMod.MODID, "animations/shotgun_h2.animation.json");
	}

	@Override
	public RenderLayer getRenderType(ShotgunItem animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}
}
