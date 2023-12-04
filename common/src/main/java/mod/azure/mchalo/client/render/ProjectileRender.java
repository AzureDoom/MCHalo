package mod.azure.mchalo.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mod.azure.azurelib.animatable.GeoEntity;
import mod.azure.azurelib.cache.object.BakedGeoModel;
import mod.azure.azurelib.renderer.GeoEntityRenderer;
import mod.azure.azurelib.util.RenderUtils;
import mod.azure.mchalo.CommonMod;
import mod.azure.mchalo.client.models.ProjectileModel;
import mod.azure.mchalo.entity.projectiles.GrenadeEntity;
import mod.azure.mchalo.helper.EntityEnum;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;

public class ProjectileRender<T extends Entity & GeoEntity> extends GeoEntityRenderer<T> {

    public ProjectileRender(EntityRendererProvider.Context renderManager, EntityEnum entityType, String id) {
        super(renderManager, new ProjectileModel(entityType, CommonMod.modResource(id + "/" + id)));
    }

    @Override
    public void preRender(PoseStack poseStack, T animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        RenderUtils.faceRotation(poseStack, animatable, partialTick);
        if (!(animatable instanceof GrenadeEntity))
            poseStack.scale(animatable.tickCount > 2 ? 0.5F : 0.0F, animatable.tickCount > 2 ? 0.5F : 0.0F, animatable.tickCount > 2 ? 0.5F : 0.0F);
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
