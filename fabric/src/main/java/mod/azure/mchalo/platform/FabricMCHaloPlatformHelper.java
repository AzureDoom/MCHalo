package mod.azure.mchalo.platform;

import mod.azure.mchalo.FabricLibMod;
import mod.azure.mchalo.platform.services.MCHaloPlatformHelper;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class FabricMCHaloPlatformHelper implements MCHaloPlatformHelper {
    @Override
    public RecipeSerializer<?> getRecipeSeializer() {
        return FabricLibMod.GUN_TABLE_RECIPE_SERIALIZER;
    }

    @Override
    public MenuType<?> getGunScreenHandler() {
        return FabricLibMod.SCREEN_HANDLER_TYPE;
    }
}
