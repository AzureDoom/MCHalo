package mod.azure.mchalo.client.render;

import mod.azure.mchalo.client.models.PropShieldModel;
import mod.azure.mchalo.item.PropShieldItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class PropShieldRender extends GeoItemRenderer<PropShieldItem> {
	public PropShieldRender() {
		super(new PropShieldModel());
	}
}