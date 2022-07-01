package mod.azure.mchalo.client.render;

import mod.azure.mchalo.client.models.PropShieldModel;
import mod.azure.mchalo.item.PropShieldItem;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class PropShieldRender extends GeoItemRenderer<PropShieldItem> {
	public PropShieldRender() {
		super(new PropShieldModel());
	}

	@Override
	public RenderLayer getRenderType(PropShieldItem animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}
}