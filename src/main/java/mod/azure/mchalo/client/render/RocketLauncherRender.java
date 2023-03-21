package mod.azure.mchalo.client.render;

import mod.azure.azurelib.renderer.GeoItemRenderer;
import mod.azure.mchalo.client.models.RocketLauncherModel;
import mod.azure.mchalo.item.guns.RocketLauncherItem;

public class RocketLauncherRender extends GeoItemRenderer<RocketLauncherItem> {
	public RocketLauncherRender() {
		super(new RocketLauncherModel());
	}
}