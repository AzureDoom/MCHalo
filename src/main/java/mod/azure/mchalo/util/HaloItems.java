package mod.azure.mchalo.util;

import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.item.EnergySwordItem;
import mod.azure.mchalo.item.PropShieldItem;
import mod.azure.mchalo.item.ammo.GrenadeItem;
import mod.azure.mchalo.item.ammo.HaloAmmoItem;
import mod.azure.mchalo.item.guns.BattleRifleItem;
import mod.azure.mchalo.item.guns.BruteShotItem;
import mod.azure.mchalo.item.guns.MagnumItem;
import mod.azure.mchalo.item.guns.MaulerItem;
import mod.azure.mchalo.item.guns.NeedlerItem;
import mod.azure.mchalo.item.guns.PlasmaPistolItem;
import mod.azure.mchalo.item.guns.PlasmaRifleItem;
import mod.azure.mchalo.item.guns.RocketLauncherItem;
import mod.azure.mchalo.item.guns.ShotgunItem;
import mod.azure.mchalo.item.guns.SniperItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class HaloItems {

	/* Weapons */
	public static SniperItem SNIPER = item(new SniperItem(), "sniper_rifle");
	public static ShotgunItem SHOTGUN = item(new ShotgunItem(), "shotgun_h2");
	public static MagnumItem MAGNUM = item(new MagnumItem(), "magnum_h3");
	public static BattleRifleItem BATTLERIFLE = item(new BattleRifleItem(), "battle_rifle");
	public static RocketLauncherItem ROCKETLAUNCHER = item(new RocketLauncherItem(), "rocket_launcher_h3");
	public static PlasmaPistolItem PLASMAPISTOL = item(new PlasmaPistolItem(), "plasma_pistol");
	public static PlasmaRifleItem PLASMARIFLE = item(new PlasmaRifleItem(), "plasma_rifle");
	public static NeedlerItem NEEDLER = item(new NeedlerItem(), "needler");
	public static MaulerItem MAULER = item(new MaulerItem(), "mauler");
	public static BruteShotItem BRUTESHOT = item(new BruteShotItem(), "brute_shot");
	public static EnergySwordItem ENERGYSWORD = item(new EnergySwordItem(), "energy_sword");

	/* Ammo */
	public static HaloAmmoItem SNIPER_ROUND = item(new HaloAmmoItem(), "sniper_round");
	public static HaloAmmoItem SHOTGUN_CLIP = item(new HaloAmmoItem(), "shotgun_clip");
	public static HaloAmmoItem BULLETCLIP = item(new HaloAmmoItem(), "bullet_clip");
	public static HaloAmmoItem NEEDLES = item(new HaloAmmoItem(), "needles");
	public static HaloAmmoItem BATTERIES = item(new HaloAmmoItem(), "batteries");
	public static GrenadeItem GRENADE = item(new GrenadeItem(), "grenade");
	public static HaloAmmoItem ROCKET = item(new HaloAmmoItem(), "rocket");

	/* Blocks */
	public static PropShieldItem PROPSHIELD = item(new PropShieldItem(), "prop_shield_h2");
	public static BlockItem GUN_TABLE = item(
			new BlockItem(MCHaloMod.GUN_TABLE, new Item.Settings().group(MCHaloMod.HALOTAB)), "gun_table");

	static <T extends Item> T item(T c, String id) {
		Registry.register(Registry.ITEM, new Identifier(MCHaloMod.MODID, id), c);
		return c;
	}
}
