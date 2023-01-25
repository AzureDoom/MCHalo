package mod.azure.mchalo.client.models;

import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.item.guns.PlasmaPistolItem;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import mod.azure.azurelib.model.GeoModel;

public class PlasmaPistolModel extends GeoModel<PlasmaPistolItem> {
	@Override
	public Identifier getModelResource(PlasmaPistolItem object) {
		return new Identifier(MCHaloMod.MODID, "geo/plasma_pistol.geo.json");
	}

	@Override
	public Identifier getTextureResource(PlasmaPistolItem object) {
		return new Identifier(MCHaloMod.MODID, "textures/item/plasma_pistol.png");
	}

	@Override
	public Identifier getAnimationResource(PlasmaPistolItem animatable) {
		return new Identifier(MCHaloMod.MODID, "animations/plasma_pistol.animation.json");
	}

	@Override
	public RenderLayer getRenderType(PlasmaPistolItem animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}
}
