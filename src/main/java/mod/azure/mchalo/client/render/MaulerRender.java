package mod.azure.mchalo.client.render;

import mod.azure.mchalo.client.models.MaulerModel;
import mod.azure.mchalo.item.guns.MaulerItem;
import net.minecraft.client.render.model.json.ModelTransformation;
import mod.azure.azurelib.renderer.GeoItemRenderer;

public class MaulerRender extends GeoItemRenderer<MaulerItem> {

	private ModelTransformation.Mode currentTransform;
	
	public MaulerRender() {
		super(new MaulerModel());
	}
	
	@Override
	public long getInstanceId(MaulerItem animatable) {
		if (currentTransform == ModelTransformation.Mode.GUI) {
			return -1;
		}
		return super.getInstanceId(animatable);
	}
}