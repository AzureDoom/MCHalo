package mod.azure.mchalo.client.render;

import mod.azure.mchalo.client.models.MaulerModel;
import mod.azure.mchalo.item.guns.MaulerItem;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class MaulerRender extends GeoItemRenderer<MaulerItem> {
	public MaulerRender() {
		super(new MaulerModel());
	}

	@Override
	public RenderLayer getRenderType(MaulerItem animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}
}