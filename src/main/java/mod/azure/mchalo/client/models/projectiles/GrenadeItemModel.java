package mod.azure.mchalo.client.models.projectiles;

import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.item.ammo.GrenadeItem;
import net.minecraft.util.Identifier;
import mod.azure.azurelib.model.GeoModel;

public class GrenadeItemModel extends GeoModel<GrenadeItem> {
	@Override
	public Identifier getModelResource(GrenadeItem object) {
		return new Identifier(MCHaloMod.MODID, "geo/grenade.geo.json");
	}

	@Override
	public Identifier getTextureResource(GrenadeItem object) {
		return new Identifier(MCHaloMod.MODID, "textures/item/grenade.png");
	}

	@Override
	public Identifier getAnimationResource(GrenadeItem animatable) {
		return new Identifier(MCHaloMod.MODID, "animations/grenade.animation.json");
	}
}
