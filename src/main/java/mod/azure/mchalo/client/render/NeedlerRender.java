package mod.azure.mchalo.client.render;

import mod.azure.mchalo.client.models.NeedlerModel;
import mod.azure.mchalo.item.guns.NeedlerItem;
import net.minecraft.client.render.model.json.ModelTransformation;
import mod.azure.azurelib.renderer.GeoItemRenderer;

public class NeedlerRender extends GeoItemRenderer<NeedlerItem> {

	private ModelTransformation.Mode currentTransform;
	
	public NeedlerRender() {
		super(new NeedlerModel());
	}
	
	@Override
	public long getInstanceId(NeedlerItem animatable) {
		if (currentTransform == ModelTransformation.Mode.GUI) {
			return -1;
		}
		return super.getInstanceId(animatable);
	}
}