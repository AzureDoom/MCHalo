package mod.azure.mchalo.client.models;

import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.item.guns.BattleRifleItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BattleRifleModel extends AnimatedGeoModel<BattleRifleItem> {
	@Override
	public Identifier getModelResource(BattleRifleItem object) {
		return new Identifier(MCHaloMod.MODID, "geo/battle_rifle.geo.json");
	}

	@Override
	public Identifier getTextureResource(BattleRifleItem object) {
		return new Identifier(MCHaloMod.MODID, "textures/items/battle_rifle.png");
	}

	@Override
	public Identifier getAnimationResource(BattleRifleItem animatable) {
		return new Identifier(MCHaloMod.MODID, "animations/battle_rifle.animation.json");
	}
}
