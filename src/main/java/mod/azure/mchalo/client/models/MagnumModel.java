package mod.azure.mchalo.client.models;

import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.item.guns.MagnumItem;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class MagnumModel extends GeoModel<MagnumItem> {
	@Override
	public Identifier getModelResource(MagnumItem object) {
		return new Identifier(MCHaloMod.MODID, "geo/magnum_h3.geo.json");
	}

	@Override
	public Identifier getTextureResource(MagnumItem object) {
		return new Identifier(MCHaloMod.MODID, "textures/item/magnum_h3.png");
	}

	@Override
	public Identifier getAnimationResource(MagnumItem animatable) {
		return new Identifier(MCHaloMod.MODID, "animations/pistol.animation.json");
	}
	
	@Override
	public RenderLayer getRenderType(MagnumItem animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}
}
