package mod.azure.mchalo.client.render;

import mod.azure.mchalo.client.models.EnergySwordModel;
import mod.azure.mchalo.item.EnergySwordItem;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class EnergySwordRender extends GeoItemRenderer<EnergySwordItem> {
	public EnergySwordRender() {
		super(new EnergySwordModel());
	}

	@Override
	public RenderLayer getRenderType(EnergySwordItem animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}
}