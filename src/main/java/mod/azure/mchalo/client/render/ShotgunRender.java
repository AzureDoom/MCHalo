package mod.azure.mchalo.client.render;

import mod.azure.mchalo.client.models.ShotgunModel;
import mod.azure.mchalo.item.guns.ShotgunItem;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class ShotgunRender extends GeoItemRenderer<ShotgunItem> {
	public ShotgunRender() {
		super(new ShotgunModel());
	}

	@Override
	public RenderLayer getRenderType(ShotgunItem animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}
}