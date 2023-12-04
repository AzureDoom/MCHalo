package mod.azure.mchalo.registry;

import mod.azure.mchalo.CommonMod;
import mod.azure.mchalo.FabricLibMod;
import mod.azure.mchalo.helper.ProjectileEnum;
import mod.azure.mchalo.item.EnergySwordItem;
import mod.azure.mchalo.item.HaloGunBase;
import mod.azure.mchalo.item.PropShieldItem;
import mod.azure.mchalo.item.ammo.GrenadeItem;
import mod.azure.mchalo.item.ammo.HaloAmmoItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

public record Items() {
    /* Weapons */
    public static HaloGunBase SNIPER;
    public static HaloGunBase SHOTGUN;
    public static HaloGunBase MAGNUM;
    public static HaloGunBase BATTLERIFLE;
    public static HaloGunBase ROCKETLAUNCHER;
    public static HaloGunBase PLASMAPISTOL;
    public static HaloGunBase PLASMARIFLE;
    public static HaloGunBase NEEDLER;
    public static HaloGunBase MAULER;
    public static HaloGunBase BRUTESHOT;
    public static EnergySwordItem ENERGYSWORD;
    public static PropShieldItem PROPSHIELD;

    /* Ammo */
    public static HaloAmmoItem SNIPER_ROUND;
    public static HaloAmmoItem SHOTGUN_CLIP;
    public static HaloAmmoItem BULLETCLIP;
    public static HaloAmmoItem NEEDLES;
    public static HaloAmmoItem BATTERIES;
    public static GrenadeItem GRENADE;
    public static HaloAmmoItem ROCKET;

    /* Blocks */
    public static BlockItem GUN_TABLE;

    static <T extends Item> T item(String id, T c) {
        Registry.register(BuiltInRegistries.ITEM, CommonMod.modResource(id), c);
        return c;
    }

    public static void initItems() {
        SNIPER = item("sniper_rifle", new HaloGunBase("sniper_h3", ProjectileEnum.SNIPER, CommonMod.config.sniper_max_ammo, Sounds.SNIPERRELOAD, Sounds.SNIPER, CommonMod.config.sniper_mag_size, CommonMod.config.sniper_bullet_damage) {
        });
        SHOTGUN = item("shotgun_h2", new HaloGunBase("shotgun_h2", ProjectileEnum.SHELL, CommonMod.config.shotgun_max_ammo, Sounds.SHOTGUNRELOAD, Sounds.SHOTGUN, CommonMod.config.shotgun_mag_size, CommonMod.config.shotgun_bullet_damage){
        });
        MAGNUM = item("magnum_h3", new HaloGunBase("magnum_h3", ProjectileEnum.BULLET, CommonMod.config.magnum_max_ammo, Sounds.PISTOLRELOAD, Sounds.PISTOL, CommonMod.config.magnum_mag_size, CommonMod.config.magnum_bullet_damage) {
        });
        BATTLERIFLE = item("battle_rifle", new HaloGunBase("battle_rifle", ProjectileEnum.BRBULLET, CommonMod.config.battlerifle_max_ammo, Sounds.BATTLERIFLERELOAD, Sounds.BATTLERIFLE, CommonMod.config.battlerifle_mag_size, CommonMod.config.battlerifle_bullet_damage) {
        });
        ROCKETLAUNCHER = item("rocket_launcher_h3", new HaloGunBase("rocket_launcher_h3", ProjectileEnum.ROCKET, CommonMod.config.rocketlauncher_max_ammo, Sounds.ROCKETRELOAD, Sounds.ROCKET, CommonMod.config.rocketlauncher_mag_size, CommonMod.config.rocketlauncher_damage) {
        });
        PLASMAPISTOL = item("plasma_pistol", new HaloGunBase("plasma_pistol", ProjectileEnum.PLASMAG, CommonMod.config.plasmapistol_max_ammo, Sounds.PLASMAPISTOLELOAD, Sounds.PLASMAPISTOL, CommonMod.config.plasmapistol_mag_size, CommonMod.config.plasmapistol_bullet_damage) {
        });
        PLASMARIFLE = item("plasma_rifle", new HaloGunBase("plasma_rifle", ProjectileEnum.PLASMA, CommonMod.config.plasmarifle_max_ammo, Sounds.PLASMARIFLERELOAD, Sounds.PLASMARIFLE, CommonMod.config.plasmarifle_mag_size, CommonMod.config.plasmarifle_bullet_damage) {
        });
        NEEDLER = item("needler", new HaloGunBase("needler", ProjectileEnum.NEEDLE, CommonMod.config.needler_max_ammo, Sounds.NEEDLERRELOAD, Sounds.NEEDLER, CommonMod.config.needler_mag_size, CommonMod.config.needler_bullet_damage){
        });
        MAULER = item("mauler", new HaloGunBase("mauler", ProjectileEnum.MAULER, CommonMod.config.mauler_max_ammo, Sounds.MAULERRELOAD, Sounds.MAULER, CommonMod.config.mauler_mag_size, CommonMod.config.mauler_bullet_damage){
        });
        BRUTESHOT = item("brute_shot", new HaloGunBase("brute_shot", ProjectileEnum.GRENADE, CommonMod.config.bruteshot_max_ammo, Sounds.BRUTESHOTRELOAD, Sounds.BRUTESHOT, CommonMod.config.bruteshot_mag_size, CommonMod.config.bruteshot_bullet_damage){
        });
        ENERGYSWORD = item("energy_sword", new EnergySwordItem());
        PROPSHIELD = item("prop_shield_h2", new PropShieldItem());
        SNIPER_ROUND = item("sniper_round", new HaloAmmoItem());
        SHOTGUN_CLIP = item("shotgun_clip", new HaloAmmoItem());
        BULLETCLIP = item("bullet_clip", new HaloAmmoItem());
        NEEDLES = item("needles", new HaloAmmoItem());
        BATTERIES = item("batteries", new HaloAmmoItem());
        GRENADE = item("grenade", new GrenadeItem());
        ROCKET = item("rocket", new HaloAmmoItem());
        GUN_TABLE = item("gun_table", new BlockItem(FabricLibMod.GUN_TABLE, new Item.Properties()));
    }
}
