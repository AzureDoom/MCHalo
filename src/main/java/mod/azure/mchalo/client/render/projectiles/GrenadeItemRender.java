package mod.azure.mchalo.client.render.projectiles;

import mod.azure.mchalo.client.models.projectiles.GrenadeItemModel;
import mod.azure.mchalo.item.ammo.GrenadeItem;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class GrenadeItemRender extends GeoItemRenderer<GrenadeItem> {

	public GrenadeItemRender() {
		super(new GrenadeItemModel());
	}

}