package mod.azure.mchalo.client.models;

import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.item.EnergySwordItem;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import mod.azure.azurelib.model.GeoModel;

public class EnergySwordModel extends GeoModel<EnergySwordItem> {
	@Override
	public Identifier getModelResource(EnergySwordItem object) {
		return new Identifier(MCHaloMod.MODID, "geo/energy_sword.geo.json");
	}

	@Override
	public Identifier getTextureResource(EnergySwordItem object) {
		return new Identifier(MCHaloMod.MODID, "textures/item/energy_sword.png");
	}

	@Override
	public Identifier getAnimationResource(EnergySwordItem animatable) {
		return new Identifier(MCHaloMod.MODID, "animations/energy_sword.animation.json");
	}
	
	@Override
	public RenderLayer getRenderType(EnergySwordItem animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}
}
