package mod.azure.mchalo.client.models.projectiles;

import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.item.ammo.GrenadeItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GrenadeItemModel extends AnimatedGeoModel<GrenadeItem> {
	@Override
	public Identifier getModelLocation(GrenadeItem object) {
		return new Identifier(MCHaloMod.MODID, "geo/grenade.geo.json");
	}

	@Override
	public Identifier getTextureLocation(GrenadeItem object) {
		return new Identifier(MCHaloMod.MODID, "textures/items/grenade.png");
	}

	@Override
	public Identifier getAnimationFileLocation(GrenadeItem animatable) {
		return new Identifier(MCHaloMod.MODID, "animations/grenade.animation.json");
	}
}
