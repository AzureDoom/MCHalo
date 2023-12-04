package mod.azure.mchalo.registry;

import mod.azure.mchalo.CommonMod;
import mod.azure.mchalo.recipe.GunTableRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public record Recipes() {
    public static final DeferredRegister<RecipeSerializer<?>> SERIAL = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, CommonMod.MOD_ID);

    public static final RegistryObject<RecipeSerializer<?>> GUN_TABLE_RECIPE_SERIALIZER = SERIAL.register("gun_table", () -> new GunTableRecipe.Serializer());
}
