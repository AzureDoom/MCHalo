package mod.azure.mchalo.client.models;

import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.item.guns.BattleRifleItem;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.model.GeoModel;

public class BattleRifleModel extends GeoModel<BattleRifleItem> {
	@Override
	public ResourceLocation getModelResource(BattleRifleItem object) {
		return new ResourceLocation(MCHaloMod.MODID, "geo/battle_rifle.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(BattleRifleItem object) {
		return new ResourceLocation(MCHaloMod.MODID, "textures/item/battle_rifle.png");
	}

	@Override
	public ResourceLocation getAnimationResource(BattleRifleItem animatable) {
		return new ResourceLocation(MCHaloMod.MODID, "animations/battle_rifle.animation.json");
	}
	
	@Override
	public RenderType getRenderType(BattleRifleItem animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}
