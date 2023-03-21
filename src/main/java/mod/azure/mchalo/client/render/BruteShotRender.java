package mod.azure.mchalo.client.render;

import mod.azure.azurelib.renderer.GeoItemRenderer;
import mod.azure.mchalo.client.models.BruteShotModel;
import mod.azure.mchalo.item.guns.BruteShotItem;

public class BruteShotRender extends GeoItemRenderer<BruteShotItem> {

	public BruteShotRender() {
		super(new BruteShotModel());
	}
}