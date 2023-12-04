package mod.azure.mchalo.platform;

import mod.azure.mchalo.platform.services.MCHaloPlatformHelper;
import mod.azure.mchalo.registry.Recipes;
import mod.azure.mchalo.registry.Screens;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class NeoMCHaloPlatformHelper implements MCHaloPlatformHelper {

    @Override
    public RecipeSerializer<?> getRecipeSeializer() {
        return Recipes.GUN_TABLE_RECIPE_SERIALIZER.get();
    }

    @Override
    public MenuType<?> getGunScreenHandler() {
        return Screens.SCREEN_HANDLER_TYPE.get();
    }
}
