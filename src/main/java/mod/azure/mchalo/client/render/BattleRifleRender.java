package mod.azure.mchalo.client.render;

import mod.azure.mchalo.client.models.BattleRifleModel;
import mod.azure.mchalo.item.guns.BattleRifleItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class BattleRifleRender extends GeoItemRenderer<BattleRifleItem> {
	
	public BattleRifleRender() {
		super(new BattleRifleModel());
	}
}