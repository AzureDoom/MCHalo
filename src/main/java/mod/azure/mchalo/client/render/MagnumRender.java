package mod.azure.mchalo.client.render;

import mod.azure.mchalo.client.models.MagnumModel;
import mod.azure.mchalo.item.guns.MagnumItem;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class MagnumRender extends GeoItemRenderer<MagnumItem> {
	public MagnumRender() {
		super(new MagnumModel());
	}

	@Override
	public RenderLayer getRenderType(MagnumItem animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
	}
}