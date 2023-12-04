package mod.azure.mchalo.client.render.projectiles;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.azure.mchalo.CommonMod;
import mod.azure.mchalo.entity.projectiles.BulletEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.AbstractArrow;

public class EmptyRender<T extends AbstractArrow> extends ArrowRenderer<T> {

    private static final ResourceLocation TEXTURE = CommonMod.modResource("textures/item/empty.png");

    public EmptyRender(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return TEXTURE;
    }

    @Override
    public void render(T persistentProjectileEntity, float f, float g, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i) {
        super.render(persistentProjectileEntity, f, g, matrixStack, vertexConsumerProvider, i);
        matrixStack.pushPose();
        matrixStack.scale(0, 0, 0);
        matrixStack.popPose();
    }

}