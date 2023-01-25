package mod.azure.mchalo.client.render.projectiles;

import mod.azure.mchalo.client.models.projectiles.GrenadeItemModel;
import mod.azure.mchalo.item.ammo.GrenadeItem;
import mod.azure.azurelib.renderer.GeoItemRenderer;

public class GrenadeItemRender extends GeoItemRenderer<GrenadeItem> {

	public GrenadeItemRender() {
		super(new GrenadeItemModel());
	}

}