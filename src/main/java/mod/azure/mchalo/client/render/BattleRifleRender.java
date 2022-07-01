package mod.azure.mchalo.client.render;

import mod.azure.mchalo.client.models.BattleRifleModel;
import mod.azure.mchalo.item.guns.BattleRifleItem;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class BattleRifleRender extends GeoItemRenderer<BattleRifleItem> {
	public BattleRifleRender() {
		super(new BattleRifleModel());
	}

	@Override
	public RenderLayer getRenderType(BattleRifleItem animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}
}