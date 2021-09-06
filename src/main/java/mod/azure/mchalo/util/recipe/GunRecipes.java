package mod.azure.mchalo.util.recipe;

import mod.azure.mchalo.client.gui.GunTableInventory;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;

public interface GunRecipes extends Recipe<GunTableInventory> {
	default RecipeType<?> getType() {
		return RecipeType.CRAFTING;
	}
}