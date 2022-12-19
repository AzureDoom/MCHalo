package mod.azure.mchalo.client.models.projectiles;

import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.entity.projectiles.NeedleEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class NeedleModel extends GeoModel<NeedleEntity> {
	@Override
	public Identifier getModelResource(NeedleEntity object) {
		return new Identifier(MCHaloMod.MODID, "geo/needle.geo.json");
	}

	@Override
	public Identifier getTextureResource(NeedleEntity object) {
		return new Identifier(MCHaloMod.MODID, "textures/item/needler.png");
	}

	@Override
	public Identifier getAnimationResource(NeedleEntity animatable) {
		return new Identifier(MCHaloMod.MODID, "animations/needle.animation.json");
	}

	@Override
	public RenderLayer getRenderType(NeedleEntity animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}
}
