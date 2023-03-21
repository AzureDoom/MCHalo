package mod.azure.mchalo.util.recipe;

import mod.azure.mchalo.client.gui.GunTableInventory;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

public interface GunRecipes extends Recipe<GunTableInventory> {
	default RecipeType<?> getType() {
		return RecipeType.CRAFTING;
	}
}