package mod.azure.mchalo.client.models.projectiles;

import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.item.ammo.GrenadeItem;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.model.GeoModel;

public class GrenadeItemModel extends GeoModel<GrenadeItem> {
	@Override
	public ResourceLocation getModelResource(GrenadeItem object) {
		return new ResourceLocation(MCHaloMod.MODID, "geo/grenade.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(GrenadeItem object) {
		return new ResourceLocation(MCHaloMod.MODID, "textures/item/grenade.png");
	}

	@Override
	public ResourceLocation getAnimationResource(GrenadeItem animatable) {
		return new ResourceLocation(MCHaloMod.MODID, "animations/grenade.animation.json");
	}
}
