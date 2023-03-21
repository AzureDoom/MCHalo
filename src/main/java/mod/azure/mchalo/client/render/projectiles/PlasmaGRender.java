package mod.azure.mchalo.client.render.projectiles;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.entity.projectiles.PlasmaGEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class PlasmaGRender extends ArrowRenderer<PlasmaGEntity> {

	private static final ResourceLocation TEXTURE = new ResourceLocation(MCHaloMod.MODID, "textures/item/empty.png");

	public PlasmaGRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn);
	}

	@Override
	public ResourceLocation getTextureLocation(PlasmaGEntity entity) {
		return TEXTURE;
	}

	@Override
	public void render(PlasmaGEntity persistentProjectileEntity, float f, float g, PoseStack matrixStack,
			MultiBufferSource vertexConsumerProvider, int i) {
		super.render(persistentProjectileEntity, f, g, matrixStack, vertexConsumerProvider, i);
		matrixStack.pushPose();
		matrixStack.scale(0, 0, 0);
		matrixStack.popPose();
	}

}