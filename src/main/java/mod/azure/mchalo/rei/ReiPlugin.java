package mod.azure.mchalo.rei;

import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.util.recipe.GunTableRecipe;
import net.minecraft.resources.ResourceLocation;

public class ReiPlugin implements REIClientPlugin {

	public static final CategoryIdentifier<HaloDisplay> CRAFTING = CategoryIdentifier
			.of(new ResourceLocation(MCHaloMod.MODID, "crafting"));

	@Override
	public void registerCategories(CategoryRegistry registry) {
		registry.add(new HaloCategory());
		registry.addWorkstations(CRAFTING, HaloCategory.ICON);
	}

	@Override
	public void registerDisplays(DisplayRegistry registry) {
		registry.registerFiller(GunTableRecipe.class, HaloDisplay::new);
	}
}
