package mod.azure.mchalo.client.render;

import mod.azure.mchalo.client.models.BruteShotModel;
import mod.azure.mchalo.item.guns.BruteShotItem;
import net.minecraft.client.render.model.json.ModelTransformation;
import mod.azure.azurelib.renderer.GeoItemRenderer;

public class BruteShotRender extends GeoItemRenderer<BruteShotItem> {

	private ModelTransformation.Mode currentTransform;
	
	public BruteShotRender() {
		super(new BruteShotModel());
	}
	
	@Override
	public long getInstanceId(BruteShotItem animatable) {
		if (currentTransform == ModelTransformation.Mode.GUI) {
			return -1;
		}
		return super.getInstanceId(animatable);
	}
}