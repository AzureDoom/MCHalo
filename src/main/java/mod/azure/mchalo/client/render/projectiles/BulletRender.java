package mod.azure.mchalo.client.render.projectiles;

import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.entity.projectiles.BulletEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class BulletRender extends ProjectileEntityRenderer<BulletEntity> {

	private static final Identifier TEXTURE = new Identifier(MCHaloMod.MODID, "textures/items/empty.png");

	public BulletRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn);
	}

	@Override
	public Identifier getTexture(BulletEntity entity) {
		return TEXTURE;
	}

	@Override
	public void render(BulletEntity persistentProjectileEntity, float f, float g, MatrixStack matrixStack,
			VertexConsumerProvider vertexConsumerProvider, int i) {
		super.render(persistentProjectileEntity, f, g, matrixStack, vertexConsumerProvider, i);
		matrixStack.push();
		matrixStack.scale(0, 0, 0);
		matrixStack.pop();
	}

}