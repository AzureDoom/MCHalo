package mod.azure.mchalo.client.render;

import mod.azure.mchalo.client.models.NeedlerModel;
import mod.azure.mchalo.item.guns.NeedlerItem;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class NeedlerRender extends GeoItemRenderer<NeedlerItem> {
	public NeedlerRender() {
		super(new NeedlerModel());
	}

	@Override
	public RenderLayer getRenderType(NeedlerItem animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
	}
}