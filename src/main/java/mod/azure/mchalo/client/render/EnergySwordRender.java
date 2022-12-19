package mod.azure.mchalo.client.render;

import mod.azure.mchalo.client.models.EnergySwordModel;
import mod.azure.mchalo.item.EnergySwordItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class EnergySwordRender extends GeoItemRenderer<EnergySwordItem> {
	public EnergySwordRender() {
		super(new EnergySwordModel());
	}
}