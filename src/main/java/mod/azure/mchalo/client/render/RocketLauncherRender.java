package mod.azure.mchalo.client.render;

import mod.azure.mchalo.client.models.RocketLauncherModel;
import mod.azure.mchalo.item.guns.RocketLauncherItem;
import net.minecraft.client.render.model.json.ModelTransformation;
import mod.azure.azurelib.renderer.GeoItemRenderer;

public class RocketLauncherRender extends GeoItemRenderer<RocketLauncherItem> {

	private ModelTransformation.Mode currentTransform;
	
	public RocketLauncherRender() {
		super(new RocketLauncherModel());
	}
	
	@Override
	public long getInstanceId(RocketLauncherItem animatable) {
		if (currentTransform == ModelTransformation.Mode.GUI) {
			return -1;
		}
		return super.getInstanceId(animatable);
	}
}