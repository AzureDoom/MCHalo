package mod.azure.mchalo.client.render.projectiles;

import mod.azure.mchalo.client.models.projectiles.GrenadeItemModel;
import mod.azure.mchalo.item.ammo.GrenadeItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class GrenadeItemRender extends GeoItemRenderer<GrenadeItem> {

	public GrenadeItemRender() {
		super(new GrenadeItemModel());
	}

}