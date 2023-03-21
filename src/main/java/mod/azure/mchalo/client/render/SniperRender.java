package mod.azure.mchalo.client.render;

import mod.azure.azurelib.renderer.GeoItemRenderer;
import mod.azure.mchalo.client.models.SniperModel;
import mod.azure.mchalo.item.guns.SniperItem;

public class SniperRender extends GeoItemRenderer<SniperItem> {
	public SniperRender() {
		super(new SniperModel());
	}
}