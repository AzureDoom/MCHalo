package mod.azure.mchalo.client.render;

import mod.azure.azurelib.renderer.GeoItemRenderer;
import mod.azure.mchalo.client.models.PlasmaRifleModel;
import mod.azure.mchalo.item.guns.PlasmaRifleItem;

public class PlasmaRifleRender extends GeoItemRenderer<PlasmaRifleItem> {
	public PlasmaRifleRender() {
		super(new PlasmaRifleModel());
	}
}