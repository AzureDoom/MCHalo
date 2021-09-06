package mod.azure.mchalo.client.render;

import mod.azure.mchalo.client.models.SniperModel;
import mod.azure.mchalo.item.guns.SniperItem;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class SniperRender extends GeoItemRenderer<SniperItem> {
	public SniperRender() {
		super(new SniperModel());
	}

	@Override
	public RenderLayer getRenderType(SniperItem animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
	}
}