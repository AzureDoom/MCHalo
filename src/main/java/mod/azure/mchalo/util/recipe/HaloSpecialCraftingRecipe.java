package mod.azure.mchalo.util.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public abstract class HaloSpecialCraftingRecipe implements GunRecipes {
	private final Identifier id;

	public HaloSpecialCraftingRecipe(Identifier id) {
		this.id = id;
	}

	public Identifier getId() {
		return this.id;
	}

	public boolean isIgnoredInRecipeBook() {
		return true;
	}

	public ItemStack getOutput() {
		return ItemStack.EMPTY;
	}
}