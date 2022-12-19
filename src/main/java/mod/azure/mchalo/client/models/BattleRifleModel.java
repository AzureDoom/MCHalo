package mod.azure.mchalo.client.models;

import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.item.guns.BattleRifleItem;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class BattleRifleModel extends GeoModel<BattleRifleItem> {
	@Override
	public Identifier getModelResource(BattleRifleItem object) {
		return new Identifier(MCHaloMod.MODID, "geo/battle_rifle.geo.json");
	}

	@Override
	public Identifier getTextureResource(BattleRifleItem object) {
		return new Identifier(MCHaloMod.MODID, "textures/item/battle_rifle.png");
	}

	@Override
	public Identifier getAnimationResource(BattleRifleItem animatable) {
		return new Identifier(MCHaloMod.MODID, "animations/battle_rifle.animation.json");
	}
	
	@Override
	public RenderLayer getRenderType(BattleRifleItem animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}
}
