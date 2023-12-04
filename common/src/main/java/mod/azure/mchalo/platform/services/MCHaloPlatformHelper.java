package mod.azure.mchalo.platform.services;

import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.crafting.RecipeSerializer;

public interface MCHaloPlatformHelper {

    RecipeSerializer<?> getRecipeSeializer();

    MenuType<?> getGunScreenHandler();
}
