package mod.azure.mchalo.client.render;

import mod.azure.mchalo.client.models.PlasmaPistolModel;
import mod.azure.mchalo.item.guns.PlasmaPistolItem;
import net.minecraft.client.render.model.json.ModelTransformation;
import mod.azure.azurelib.renderer.GeoItemRenderer;

public class PlasmaPistolRender extends GeoItemRenderer<PlasmaPistolItem> {

	private ModelTransformation.Mode currentTransform;
	
	public PlasmaPistolRender() {
		super(new PlasmaPistolModel());
	}
	
	@Override
	public long getInstanceId(PlasmaPistolItem animatable) {
		if (currentTransform == ModelTransformation.Mode.GUI) {
			return -1;
		}
		return super.getInstanceId(animatable);
	}
}