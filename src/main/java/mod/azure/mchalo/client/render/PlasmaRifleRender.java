package mod.azure.mchalo.client.render;

import mod.azure.mchalo.client.models.PlasmaRifleModel;
import mod.azure.mchalo.item.guns.PlasmaRifleItem;
import net.minecraft.client.render.model.json.ModelTransformation;
import mod.azure.azurelib.renderer.GeoItemRenderer;

public class PlasmaRifleRender extends GeoItemRenderer<PlasmaRifleItem> {

	private ModelTransformation.Mode currentTransform;
	
	public PlasmaRifleRender() {
		super(new PlasmaRifleModel());
	}
	
	@Override
	public long getInstanceId(PlasmaRifleItem animatable) {
		if (currentTransform == ModelTransformation.Mode.GUI) {
			return -1;
		}
		return super.getInstanceId(animatable);
	}
}