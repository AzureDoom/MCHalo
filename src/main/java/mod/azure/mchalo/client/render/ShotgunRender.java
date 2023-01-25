package mod.azure.mchalo.client.render;

import mod.azure.mchalo.client.models.ShotgunModel;
import mod.azure.mchalo.item.guns.ShotgunItem;
import net.minecraft.client.render.model.json.ModelTransformation;
import mod.azure.azurelib.renderer.GeoItemRenderer;

public class ShotgunRender extends GeoItemRenderer<ShotgunItem> {

	private ModelTransformation.Mode currentTransform;
	
	public ShotgunRender() {
		super(new ShotgunModel());
	}
	
	@Override
	public long getInstanceId(ShotgunItem animatable) {
		if (currentTransform == ModelTransformation.Mode.GUI) {
			return -1;
		}
		return super.getInstanceId(animatable);
	}
}