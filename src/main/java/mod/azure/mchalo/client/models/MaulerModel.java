package mod.azure.mchalo.client.models;

import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.item.guns.MaulerItem;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class MaulerModel extends GeoModel<MaulerItem> {
	@Override
	public Identifier getModelResource(MaulerItem object) {
		return new Identifier(MCHaloMod.MODID, "geo/mauler.geo.json");
	}

	@Override
	public Identifier getTextureResource(MaulerItem object) {
		return new Identifier(MCHaloMod.MODID, "textures/item/mauler.png");
	}

	@Override
	public Identifier getAnimationResource(MaulerItem animatable) {
		return new Identifier(MCHaloMod.MODID, "animations/mauler.animation.json");
	}

	@Override
	public RenderLayer getRenderType(MaulerItem animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}
}
