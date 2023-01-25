package mod.azure.mchalo.client.models;

import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.item.guns.NeedlerItem;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import mod.azure.azurelib.model.GeoModel;

public class NeedlerModel extends GeoModel<NeedlerItem> {
	@Override
	public Identifier getModelResource(NeedlerItem object) {
		return new Identifier(MCHaloMod.MODID, "geo/needler.geo.json");
	}

	@Override
	public Identifier getTextureResource(NeedlerItem object) {
		return new Identifier(MCHaloMod.MODID, "textures/item/needler.png");
	}

	@Override
	public Identifier getAnimationResource(NeedlerItem animatable) {
		return new Identifier(MCHaloMod.MODID, "animations/needler.animation.json");
	}
	
	@Override
	public RenderLayer getRenderType(NeedlerItem animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}
}
