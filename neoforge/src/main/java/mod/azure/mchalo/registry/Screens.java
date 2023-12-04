package mod.azure.mchalo.registry;

import mod.azure.mchalo.CommonMod;
import mod.azure.mchalo.client.gui.GunTableScreenHandler;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public record Screens() {
    public static final DeferredRegister<MenuType<?>> CONTAIN = DeferredRegister.create(ForgeRegistries.MENU_TYPES, CommonMod.MOD_ID);

    public static final RegistryObject<MenuType<GunTableScreenHandler>> SCREEN_HANDLER_TYPE = CONTAIN.register("gun_table_gui", () -> new MenuType<>(GunTableScreenHandler::new, FeatureFlags.VANILLA_SET));
}
