package mod.azure.mchalo.client.render.projectiles;

import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.entity.projectiles.PlasmaEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class PlasmaRender extends ProjectileEntityRenderer<PlasmaEntity> {

	private static final Identifier TEXTURE = new Identifier(MCHaloMod.MODID, "textures/item/empty.png");

	public PlasmaRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn);
	}

	@Override
	public Identifier getTexture(PlasmaEntity entity) {
		return TEXTURE;
	}

	@Override
	public void render(PlasmaEntity persistentProjectileEntity, float f, float g, MatrixStack matrixStack,
			VertexConsumerProvider vertexConsumerProvider, int i) {
		super.render(persistentProjectileEntity, f, g, matrixStack, vertexConsumerProvider, i);
		matrixStack.push();
		matrixStack.scale(0, 0, 0);
		matrixStack.pop();
	}

}