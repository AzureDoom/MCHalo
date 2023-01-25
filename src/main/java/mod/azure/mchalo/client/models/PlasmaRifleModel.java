package mod.azure.mchalo.client.models;

import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.item.guns.PlasmaRifleItem;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import mod.azure.azurelib.model.GeoModel;

public class PlasmaRifleModel extends GeoModel<PlasmaRifleItem> {
	@Override
	public Identifier getModelResource(PlasmaRifleItem object) {
		return new Identifier(MCHaloMod.MODID, "geo/plasma_rifle.geo.json");
	}

	@Override
	public Identifier getTextureResource(PlasmaRifleItem object) {
		return new Identifier(MCHaloMod.MODID, "textures/item/plasma_rifle.png");
	}

	@Override
	public Identifier getAnimationResource(PlasmaRifleItem animatable) {
		return new Identifier(MCHaloMod.MODID, "animations/plasma_rifle.animation.json");
	}

	@Override
	public RenderLayer getRenderType(PlasmaRifleItem animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}
}
