package mod.azure.mchalo;

import mod.azure.azurelib.AzureLibMod;
import mod.azure.azurelib.config.format.ConfigFormats;
import mod.azure.mchalo.blocks.GunTableBlock;
import mod.azure.mchalo.client.gui.GunTableScreenHandler;
import mod.azure.mchalo.config.HaloConfig;
import mod.azure.mchalo.item.HaloGunBase;
import mod.azure.mchalo.network.C2SMessageSelectCraft;
import mod.azure.mchalo.platform.services.MCHaloNetwork;
import mod.azure.mchalo.recipe.GunTableRecipe;
import mod.azure.mchalo.registry.Entities;
import mod.azure.mchalo.registry.Items;
import mod.azure.mchalo.registry.Sounds;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;

public final class FabricLibMod implements ModInitializer {

    public static final GunTableBlock GUN_TABLE = new GunTableBlock();
    public static MenuType<GunTableScreenHandler> SCREEN_HANDLER_TYPE;
    public static RecipeSerializer<GunTableRecipe> GUN_TABLE_RECIPE_SERIALIZER = Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, CommonMod.modResource("gun_table"), new GunTableRecipe.Serializer());
    public static final ResourceKey<CreativeModeTab> ITEM_GROUP = ResourceKey.create(Registries.CREATIVE_MODE_TAB, CommonMod.modResource("items"));

    @Override
    public void onInitialize() {
        CommonMod.config = AzureLibMod.registerConfig(HaloConfig.class, ConfigFormats.json()).getConfigInstance();
        SCREEN_HANDLER_TYPE = new MenuType<>(GunTableScreenHandler::new, FeatureFlags.VANILLA_SET);
        Registry.register(BuiltInRegistries.BLOCK, CommonMod.modResource("gun_table"), GUN_TABLE);
        Sounds.initSounds();
        Items.initItems();
        Entities.initEntities();
        Registry.register(BuiltInRegistries.MENU, CommonMod.modResource("guntable_screen_type"), SCREEN_HANDLER_TYPE);
        ServerPlayNetworking.registerGlobalReceiver(MCHaloNetwork.LOCK_SLOT, new C2SMessageSelectCraft());
        ServerPlayNetworking.registerGlobalReceiver(MCHaloNetwork.RELOAD, (server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
            if (!player.isCreative() && player.getMainHandItem().getItem() instanceof HaloGunBase gunBase)
                gunBase.reload(player, player.getUsedItemHand());
        });
        ServerPlayNetworking.registerGlobalReceiver(MCHaloNetwork.ANIMATE, (server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
            if (player.getMainHandItem().getItem() instanceof HaloGunBase gunBase)
                gunBase.animate(player);
        });
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ITEM_GROUP, FabricItemGroup.builder().icon(() -> new ItemStack(Items.ENERGYSWORD)) // icon
                .title(Component.translatable("itemGroup.mchalo.items")) // title
                .displayItems((context, entries) -> {
                    entries.accept(Items.ENERGYSWORD);
                    entries.accept(Items.MAGNUM);
                    entries.accept(Items.BATTLERIFLE);
                    entries.accept(Items.BULLETCLIP);
                    entries.accept(Items.SHOTGUN);
                    entries.accept(Items.MAULER);
                    entries.accept(Items.SHOTGUN_CLIP);
                    entries.accept(Items.SNIPER);
                    entries.accept(Items.SNIPER_ROUND);
                    entries.accept(Items.BRUTESHOT);
                    entries.accept(Items.GRENADE);
                    entries.accept(Items.NEEDLER);
                    entries.accept(Items.NEEDLES);
                    entries.accept(Items.PLASMAPISTOL);
                    entries.accept(Items.PLASMARIFLE);
                    entries.accept(Items.BATTERIES);
                    entries.accept(Items.ROCKETLAUNCHER);
                    entries.accept(Items.ROCKET);
                    entries.accept(Items.PROPSHIELD);
                    entries.accept(Items.GUN_TABLE);
                }).build()); // build() no longer registers by itself
    }
}
