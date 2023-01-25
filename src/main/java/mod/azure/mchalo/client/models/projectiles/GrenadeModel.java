package mod.azure.mchalo.client.models.projectiles;

import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.entity.projectiles.GrenadeEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import mod.azure.azurelib.model.GeoModel;

public class GrenadeModel extends GeoModel<GrenadeEntity> {
	@Override
	public Identifier getModelResource(GrenadeEntity object) {
		return new Identifier(MCHaloMod.MODID, "geo/grenade.geo.json");
	}

	@Override
	public Identifier getTextureResource(GrenadeEntity object) {
		return new Identifier(MCHaloMod.MODID, "textures/item/grenade.png");
	}

	@Override
	public Identifier getAnimationResource(GrenadeEntity animatable) {
		return new Identifier(MCHaloMod.MODID, "animations/grenade.animation.json");
	}

	@Override
	public RenderLayer getRenderType(GrenadeEntity animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}
}
