package mod.azure.mchalo.client.render;

import mod.azure.azurelib.renderer.GeoItemRenderer;
import mod.azure.mchalo.client.models.ShotgunModel;
import mod.azure.mchalo.item.guns.ShotgunItem;

public class ShotgunRender extends GeoItemRenderer<ShotgunItem> {
	public ShotgunRender() {
		super(new ShotgunModel());
	}
}