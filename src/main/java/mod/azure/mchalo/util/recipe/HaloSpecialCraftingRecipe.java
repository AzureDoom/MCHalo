package mod.azure.mchalo.util.recipe;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public abstract class HaloSpecialCraftingRecipe implements GunRecipes {
	private final ResourceLocation id;

	public HaloSpecialCraftingRecipe(ResourceLocation id) {
		this.id = id;
	}

	public ResourceLocation getId() {
		return this.id;
	}

	public boolean isSpecial() {
		return true;
	}

	public ItemStack getOutput() {
		return ItemStack.EMPTY;
	}
}