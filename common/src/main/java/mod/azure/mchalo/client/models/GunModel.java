package mod.azure.mchalo.client.models;

import mod.azure.azurelib.animatable.GeoItem;
import mod.azure.azurelib.model.DefaultedItemGeoModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class GunModel<T extends Item & GeoItem> extends DefaultedItemGeoModel<T> {
    public GunModel(ResourceLocation assetSubpath) {
        super(assetSubpath);
    }
}
