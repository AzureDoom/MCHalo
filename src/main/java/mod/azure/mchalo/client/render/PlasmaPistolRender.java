package mod.azure.mchalo.client.render;

import mod.azure.azurelib.renderer.GeoItemRenderer;
import mod.azure.mchalo.client.models.PlasmaPistolModel;
import mod.azure.mchalo.item.guns.PlasmaPistolItem;

public class PlasmaPistolRender extends GeoItemRenderer<PlasmaPistolItem> {

	public PlasmaPistolRender() {
		super(new PlasmaPistolModel());
	}
}