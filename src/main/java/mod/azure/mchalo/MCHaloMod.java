package mod.azure.mchalo;

import mod.azure.azurelib.AzureLibMod;
import mod.azure.azurelib.config.format.ConfigFormats;
import mod.azure.mchalo.blocks.GunTableBlock;
import mod.azure.mchalo.blocks.blockentity.GunBlockEntity;
import mod.azure.mchalo.client.gui.GunTableScreenHandler;
import mod.azure.mchalo.config.HaloConfig;
import mod.azure.mchalo.item.EnergySwordItem;
import mod.azure.mchalo.item.guns.*;
import mod.azure.mchalo.network.C2SMessageSelectCraft;
import mod.azure.mchalo.util.HaloItems;
import mod.azure.mchalo.util.HaloSounds;
import mod.azure.mchalo.util.ProjectilesEntityRegister;
import mod.azure.mchalo.util.recipe.GunTableRecipe;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class MCHaloMod implements ModInitializer {

    public static HaloItems ITEMS;
    public static HaloConfig config;
    public static HaloSounds SOUNDS;
    public static final String MODID = "mchalo";
    public static ProjectilesEntityRegister PROJECTILES;
    public static BlockEntityType<GunBlockEntity> GUN_TABLE_ENTITY;
    public static final GunTableBlock GUN_TABLE = new GunTableBlock();
    public static final ResourceLocation MAGNUM = new ResourceLocation(MODID, "magnum");
    public static final ResourceLocation MAULER = new ResourceLocation(MODID, "mauler");
    public static final ResourceLocation SNIPER = new ResourceLocation(MODID, "sniper");
    public static final ResourceLocation NEEDLER = new ResourceLocation(MODID, "needler");
    public static final ResourceLocation SHOTGUN = new ResourceLocation(MODID, "shotgun");
    public static final ResourceLocation BRUTESHOT = new ResourceLocation(MODID, "bruteshot");
    public static final ResourceLocation BATTLERIFLE = new ResourceLocation(MODID, "battlerifle");
    public static final ResourceLocation ENERGYSWORD = new ResourceLocation(MODID, "energysword");
    public static final ResourceLocation PLASMARIFLE = new ResourceLocation(MODID, "plasmarifle");
    public static final ResourceLocation PLASMAPISTOL = new ResourceLocation(MODID, "plasmapistol");
    public static final ResourceLocation GUN_TABLE_GUI = new ResourceLocation(MODID, "gun_table_gui");
    public static final ResourceLocation ROCKETLAUNCHER = new ResourceLocation(MODID, "rocketlauncher");
    public static final ResourceLocation lock_slot = new ResourceLocation(MODID, "select_craft");
    public static MenuType<GunTableScreenHandler> SCREEN_HANDLER_TYPE;
    public static final ResourceKey<CreativeModeTab> ITEM_GROUP = ResourceKey.create(Registries.CREATIVE_MODE_TAB, new ResourceLocation(MODID, "items"));
    public static final RecipeSerializer<GunTableRecipe> GUN_TABLE_RECIPE_SERIALIZER = Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, new ResourceLocation(MODID, "gun_table"), new GunTableRecipe.Serializer());

    @Override
    public void onInitialize() {
        config = AzureLibMod.registerConfig(HaloConfig.class, ConfigFormats.json()).getConfigInstance();
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(MODID, "gun_table"), GUN_TABLE);
        ITEMS = new HaloItems();
        SOUNDS = new HaloSounds();
        PROJECTILES = new ProjectilesEntityRegister();
        GUN_TABLE_ENTITY = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, MODID + ":guntable", FabricBlockEntityTypeBuilder.create(GunBlockEntity::new, GUN_TABLE).build(null));
        SCREEN_HANDLER_TYPE = new MenuType<>(GunTableScreenHandler::new, FeatureFlags.VANILLA_SET);
        Registry.register(BuiltInRegistries.MENU, new ResourceLocation(MODID, "guntable_screen_type"), SCREEN_HANDLER_TYPE);
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ITEM_GROUP, FabricItemGroup.builder().icon(() -> new ItemStack(HaloItems.ENERGYSWORD)) // icon
                .title(Component.translatable("itemGroup.mchalo.items")) // title
                .displayItems((context, entries) -> {
                    entries.accept(HaloItems.ENERGYSWORD);
                    entries.accept(HaloItems.MAGNUM);
                    entries.accept(HaloItems.BATTLERIFLE);
                    entries.accept(HaloItems.BULLETCLIP);
                    entries.accept(HaloItems.SHOTGUN);
                    entries.accept(HaloItems.MAULER);
                    entries.accept(HaloItems.SHOTGUN_CLIP);
                    entries.accept(HaloItems.SNIPER);
                    entries.accept(HaloItems.SNIPER_ROUND);
                    entries.accept(HaloItems.BRUTESHOT);
                    entries.accept(HaloItems.GRENADE);
                    entries.accept(HaloItems.NEEDLER);
                    entries.accept(HaloItems.NEEDLES);
                    entries.accept(HaloItems.PLASMAPISTOL);
                    entries.accept(HaloItems.PLASMARIFLE);
                    entries.accept(HaloItems.BATTERIES);
                    entries.accept(HaloItems.ROCKETLAUNCHER);
                    entries.accept(HaloItems.ROCKET);
                    entries.accept(HaloItems.PROPSHIELD);
                    entries.accept(HaloItems.GUN_TABLE);
                }).build()); // build() no longer registers by itself
        ServerPlayNetworking.registerGlobalReceiver(lock_slot, new C2SMessageSelectCraft());
        ServerPlayNetworking.registerGlobalReceiver(MCHaloMod.SNIPER, (server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
            if (player.getMainHandItem().getItem() instanceof SniperItem)
                ((SniperItem) player.getMainHandItem().getItem()).reload(player, InteractionHand.MAIN_HAND);
        });
        ServerPlayNetworking.registerGlobalReceiver(MCHaloMod.SHOTGUN, (server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
            if (player.getMainHandItem().getItem() instanceof ShotgunItem)
                ((ShotgunItem) player.getMainHandItem().getItem()).reload(player, InteractionHand.MAIN_HAND);
        });
        ServerPlayNetworking.registerGlobalReceiver(MCHaloMod.MAGNUM, (server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
            if (player.getMainHandItem().getItem() instanceof MagnumItem)
                ((MagnumItem) player.getMainHandItem().getItem()).reload(player, InteractionHand.MAIN_HAND);
        });
        ServerPlayNetworking.registerGlobalReceiver(MCHaloMod.BATTLERIFLE, (server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
            if (player.getMainHandItem().getItem() instanceof BattleRifleItem)
                ((BattleRifleItem) player.getMainHandItem().getItem()).reload(player, InteractionHand.MAIN_HAND);
        });
        ServerPlayNetworking.registerGlobalReceiver(MCHaloMod.ROCKETLAUNCHER, (server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
            if (player.getMainHandItem().getItem() instanceof RocketLauncherItem)
                ((RocketLauncherItem) player.getMainHandItem().getItem()).reload(player, InteractionHand.MAIN_HAND);
        });
        ServerPlayNetworking.registerGlobalReceiver(MCHaloMod.ENERGYSWORD, (server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
            if (player.getMainHandItem().getItem() instanceof EnergySwordItem)
                ((EnergySwordItem) player.getMainHandItem().getItem()).reload(player, InteractionHand.MAIN_HAND);
        });
        ServerPlayNetworking.registerGlobalReceiver(MCHaloMod.PLASMAPISTOL, (server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
            if (player.getMainHandItem().getItem() instanceof PlasmaPistolItem)
                ((PlasmaPistolItem) player.getMainHandItem().getItem()).reload(player, InteractionHand.MAIN_HAND);
        });
        ;
        ServerPlayNetworking.registerGlobalReceiver(MCHaloMod.PLASMARIFLE, (server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
            if (player.getMainHandItem().getItem() instanceof PlasmaRifleItem)
                ((PlasmaRifleItem) player.getMainHandItem().getItem()).reload(player, InteractionHand.MAIN_HAND);
        });
        ;
        ServerPlayNetworking.registerGlobalReceiver(MCHaloMod.NEEDLER, (server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
            if (player.getMainHandItem().getItem() instanceof NeedlerItem)
                ((NeedlerItem) player.getMainHandItem().getItem()).reload(player, InteractionHand.MAIN_HAND);
        });
        ;
        ServerPlayNetworking.registerGlobalReceiver(MCHaloMod.MAULER, (server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
            if (player.getMainHandItem().getItem() instanceof MaulerItem)
                ((MaulerItem) player.getMainHandItem().getItem()).reload(player, InteractionHand.MAIN_HAND);
        });
        ;
        ServerPlayNetworking.registerGlobalReceiver(MCHaloMod.BRUTESHOT, (server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
            if (player.getMainHandItem().getItem() instanceof BruteShotItem)
                ((BruteShotItem) player.getMainHandItem().getItem()).reload(player, InteractionHand.MAIN_HAND);
        });
    }
}
