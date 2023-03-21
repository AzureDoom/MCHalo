package mod.azure.mchalo.client.render;

import mod.azure.azurelib.renderer.GeoItemRenderer;
import mod.azure.mchalo.client.models.MagnumModel;
import mod.azure.mchalo.item.guns.MagnumItem;

public class MagnumRender extends GeoItemRenderer<MagnumItem> {

	public MagnumRender() {
		super(new MagnumModel());
	}
}