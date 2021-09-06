package mod.azure.mchalo;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import mod.azure.mchalo.blocks.GunBlockEntity;
import mod.azure.mchalo.blocks.GunTableBlock;
import mod.azure.mchalo.client.gui.GunTableScreenHandler;
import mod.azure.mchalo.config.HaloConfig;
import mod.azure.mchalo.item.EnergySwordItem;
import mod.azure.mchalo.item.guns.BattleRifleItem;
import mod.azure.mchalo.item.guns.MagnumItem;
import mod.azure.mchalo.item.guns.RocketLauncherItem;
import mod.azure.mchalo.item.guns.ShotgunItem;
import mod.azure.mchalo.item.guns.SniperItem;
import mod.azure.mchalo.network.C2SMessageSelectCraft;
import mod.azure.mchalo.util.HaloItems;
import mod.azure.mchalo.util.HaloSounds;
import mod.azure.mchalo.util.ProjectilesEntityRegister;
import mod.azure.mchalo.util.recipe.GunTableRecipe;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import software.bernie.geckolib3.GeckoLib;

public class MCHaloMod implements ModInitializer {

	public static HaloItems ITEMS;
	public static HaloConfig config;
	public static HaloSounds SOUNDS;
	public static final String MODID = "mchalo";
	public static ProjectilesEntityRegister PROJECTILES;
	public static BlockEntityType<GunBlockEntity> GUN_TABLE_ENTITY;
	public static final Identifier MAGNUM = new Identifier(MODID, "magnum");
	public static final Identifier MAULER = new Identifier(MODID, "mauler");
	public static final Identifier SNIPER = new Identifier(MODID, "sniper");
	public static final Identifier NEEDLER = new Identifier(MODID, "needler");
	public static final Identifier SHOTGUN = new Identifier(MODID, "shotgun");
	public static final Identifier BRUTESHOT = new Identifier(MODID, "bruteshot");
	public static final Identifier BATTLERIFLE = new Identifier(MODID, "battlerifle");
	public static final Identifier ENERGYSWORD = new Identifier(MODID, "energysword");
	public static final Identifier PLASMARIFLE = new Identifier(MODID, "plasmarifle");
	public static final Identifier PLASMAPISTOL = new Identifier(MODID, "plasmapistol");
	public static final Identifier GUN_TABLE_GUI = new Identifier(MODID, "gun_table_gui");
	public static final Identifier ROCKETLAUNCHER = new Identifier(MODID, "rocketlauncher");
	public static final Identifier lock_slot = new Identifier(MCHaloMod.MODID, "select_craft");
	public static final GunTableBlock GUN_TABLE = new GunTableBlock(
			FabricBlockSettings.of(Material.METAL).strength(4.0f).nonOpaque());
	public static ScreenHandlerType<GunTableScreenHandler> SCREEN_HANDLER_TYPE = ScreenHandlerRegistry
			.registerSimple(GUN_TABLE_GUI, GunTableScreenHandler::new);
	public static final ItemGroup HALOTAB = FabricItemGroupBuilder.create(new Identifier(MODID, "items"))
			.icon(() -> new ItemStack(HaloItems.ENERGYSWORD)).build();
	public static final RecipeSerializer<GunTableRecipe> GUN_TABLE_RECIPE_SERIALIZER = Registry
			.register(Registry.RECIPE_SERIALIZER, new Identifier(MODID, "gun_table"), new GunTableRecipe.Serializer());

	@Override
	public void onInitialize() {
		AutoConfig.register(HaloConfig.class, GsonConfigSerializer::new);
		config = AutoConfig.getConfigHolder(HaloConfig.class).getConfig();
		Registry.register(Registry.BLOCK, new Identifier(MODID, "gun_table"), GUN_TABLE);
		ITEMS = new HaloItems();
		SOUNDS = new HaloSounds();
		PROJECTILES = new ProjectilesEntityRegister();
		GUN_TABLE_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, MODID + ":guntable",
				FabricBlockEntityTypeBuilder.create(GunBlockEntity::new, GUN_TABLE).build(null));
		GeckoLib.initialize();
		ServerPlayNetworking.registerGlobalReceiver(lock_slot, new C2SMessageSelectCraft());
		ServerPlayNetworking.registerGlobalReceiver(MCHaloMod.SNIPER,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandStack().getItem() instanceof SniperItem) {
						((SniperItem) player.getMainHandStack().getItem()).reload(player, Hand.MAIN_HAND);
					}
				});
		ServerPlayNetworking.registerGlobalReceiver(MCHaloMod.SHOTGUN,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandStack().getItem() instanceof ShotgunItem) {
						((ShotgunItem) player.getMainHandStack().getItem()).reload(player, Hand.MAIN_HAND);
					}
				});
		ServerPlayNetworking.registerGlobalReceiver(MCHaloMod.MAGNUM,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandStack().getItem() instanceof MagnumItem) {
						((MagnumItem) player.getMainHandStack().getItem()).reload(player, Hand.MAIN_HAND);
					}
				});
		ServerPlayNetworking.registerGlobalReceiver(MCHaloMod.BATTLERIFLE,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandStack().getItem() instanceof BattleRifleItem) {
						((BattleRifleItem) player.getMainHandStack().getItem()).reload(player, Hand.MAIN_HAND);
					}
				});
		ServerPlayNetworking.registerGlobalReceiver(MCHaloMod.ROCKETLAUNCHER,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandStack().getItem() instanceof RocketLauncherItem) {
						((RocketLauncherItem) player.getMainHandStack().getItem()).reload(player, Hand.MAIN_HAND);
					}
				});
		ServerPlayNetworking.registerGlobalReceiver(MCHaloMod.ENERGYSWORD,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandStack().getItem() instanceof EnergySwordItem) {
						((EnergySwordItem) player.getMainHandStack().getItem()).reload(player, Hand.MAIN_HAND);
					}
				});
//		ServerPlayNetworking.registerGlobalReceiver(MCHaloMod.PLASMAPISTOL,
//				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
//					if (player.getMainHandStack().getItem() instanceof PlasmaPistolItem) {
//						((PlasmaPistolItem) player.getMainHandStack().getItem()).reload(player, Hand.MAIN_HAND);
//					}
//				});;
//		ServerPlayNetworking.registerGlobalReceiver(MCHaloMod.PLASMARIFLE,
//		(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
//			if (player.getMainHandStack().getItem() instanceof PlasmaRifleItem) {
//				((PlasmaRifleItem) player.getMainHandStack().getItem()).reload(player, Hand.MAIN_HAND);
//			}
//		});;
//		ServerPlayNetworking.registerGlobalReceiver(MCHaloMod.NEEDLER,
//		(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
//			if (player.getMainHandStack().getItem() instanceof NeedlerItem) {
//				((NeedlerItem) player.getMainHandStack().getItem()).reload(player, Hand.MAIN_HAND);
//			}
//		});;
//		ServerPlayNetworking.registerGlobalReceiver(MCHaloMod.MAULER,
//		(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
//			if (player.getMainHandStack().getItem() instanceof MaulerItem) {
//				((MaulerItem) player.getMainHandStack().getItem()).reload(player, Hand.MAIN_HAND);
//			}
//		});;
//		ServerPlayNetworking.registerGlobalReceiver(MCHaloMod.BRUTESHOT,
//		(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
//			if (player.getMainHandStack().getItem() instanceof BruteShotItem) {
//				((BruteShotItem) player.getMainHandStack().getItem()).reload(player, Hand.MAIN_HAND);
//			}
//		});
	}
}
