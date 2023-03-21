package mod.azure.mchalo.client.render;

import mod.azure.azurelib.renderer.GeoItemRenderer;
import mod.azure.mchalo.client.models.MaulerModel;
import mod.azure.mchalo.item.guns.MaulerItem;

public class MaulerRender extends GeoItemRenderer<MaulerItem> {

	public MaulerRender() {
		super(new MaulerModel());
	}
}