package mod.azure.mchalo.client.render;

import mod.azure.mchalo.client.models.SniperModel;
import mod.azure.mchalo.item.guns.SniperItem;
import net.minecraft.client.render.model.json.ModelTransformation;
import mod.azure.azurelib.renderer.GeoItemRenderer;

public class SniperRender extends GeoItemRenderer<SniperItem> {

	private ModelTransformation.Mode currentTransform;
	
	public SniperRender() {
		super(new SniperModel());
	}
	
	@Override
	public long getInstanceId(SniperItem animatable) {
		if (currentTransform == ModelTransformation.Mode.GUI) {
			return -1;
		}
		return super.getInstanceId(animatable);
	}
}