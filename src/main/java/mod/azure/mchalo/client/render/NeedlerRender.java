package mod.azure.mchalo.client.render;

import mod.azure.azurelib.renderer.GeoItemRenderer;
import mod.azure.mchalo.client.models.NeedlerModel;
import mod.azure.mchalo.item.guns.NeedlerItem;

public class NeedlerRender extends GeoItemRenderer<NeedlerItem> {
	public NeedlerRender() {
		super(new NeedlerModel());
	}
}