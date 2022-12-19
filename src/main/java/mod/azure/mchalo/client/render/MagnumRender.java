package mod.azure.mchalo.client.render;

import mod.azure.mchalo.client.models.MagnumModel;
import mod.azure.mchalo.item.guns.MagnumItem;
import net.minecraft.client.render.model.json.ModelTransformation;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class MagnumRender extends GeoItemRenderer<MagnumItem> {

	private ModelTransformation.Mode currentTransform;
	
	public MagnumRender() {
		super(new MagnumModel());
	}
	
	@Override
	public long getInstanceId(MagnumItem animatable) {
		if (currentTransform == ModelTransformation.Mode.GUI) {
			return -1;
		}
		return super.getInstanceId(animatable);
	}
}