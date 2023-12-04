package mod.azure.mchalo.client.models;

import mod.azure.azurelib.animatable.GeoEntity;
import mod.azure.azurelib.model.DefaultedEntityGeoModel;
import mod.azure.mchalo.CommonMod;
import mod.azure.mchalo.helper.EntityEnum;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class ProjectileModel<T extends GeoEntity> extends DefaultedEntityGeoModel<T> {
    private final EntityEnum entityType;

    public ProjectileModel(EntityEnum entityType, ResourceLocation assetSubpath) {
        super(assetSubpath);
        this.entityType = entityType;
    }

    @Override
    public ResourceLocation getTextureResource(T animatable) {
        String texture = null;
        switch (entityType) {
            case ROCKET -> texture = "bullet";
            case NEEEDLE -> texture = "needler/needler";
            case GRENADE -> texture = "grenade/grenade";
        }
        return CommonMod.modResource("textures/item/" + texture + ".png");
    }

    @Override
    public RenderType getRenderType(T animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureResource(animatable));
    }
}
