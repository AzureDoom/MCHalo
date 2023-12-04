package mod.azure.mchalo.registry;

import mod.azure.mchalo.CommonMod;
import mod.azure.mchalo.NeoForgeMod;
import mod.azure.mchalo.helper.ProjectileEnum;
import mod.azure.mchalo.item.EnergySwordItem;
import mod.azure.mchalo.item.HaloGunBase;
import mod.azure.mchalo.item.PropShieldItem;
import mod.azure.mchalo.item.ammo.GrenadeItem;
import mod.azure.mchalo.item.ammo.HaloAmmoItem;
import mod.azure.mchalo.platform.Services;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public record Items() {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CommonMod.MOD_ID);
    public static final RegistryObject<Item> ENERGYSWORD = ITEMS.register("energy_sword", EnergySwordItem::new);
    public static final RegistryObject<Item> PROPSHIELD = ITEMS.register("prop_shield_h2", PropShieldItem::new);
    /* Ammo */
    public static final RegistryObject<Item> SNIPER_ROUND = ITEMS.register("sniper_round", HaloAmmoItem::new);
    /* Weapons */
    public static final RegistryObject<Item> SNIPER = ITEMS.register("sniper_rifle", () -> new HaloGunBase("sniper_h3", ProjectileEnum.SNIPER, CommonMod.config.sniper_max_ammo, Sounds.SNIPERRELOAD.get(), Sounds.SNIPER.get(), CommonMod.config.sniper_mag_size, CommonMod.config.sniper_bullet_damage) {
        });
    public static final RegistryObject<Item> SHOTGUN_CLIP = ITEMS.register("shotgun_clip", HaloAmmoItem::new);
    public static final RegistryObject<Item> SHOTGUN = ITEMS.register("shotgun_h2", () -> new HaloGunBase("shotgun_h2", ProjectileEnum.SHELL,  CommonMod.config.shotgun_max_ammo, Sounds.SHOTGUNRELOAD.get(), Sounds.SHOTGUN.get(), CommonMod.config.shotgun_mag_size, CommonMod.config.shotgun_bullet_damage) {
        });
    public static final RegistryObject<Item> MAULER = ITEMS.register("mauler", () -> new HaloGunBase("mauler", ProjectileEnum.MAULER, CommonMod.config.mauler_max_ammo, Sounds.MAULERRELOAD.get(), Sounds.MAULER.get(), CommonMod.config.mauler_mag_size, CommonMod.config.mauler_bullet_damage) {
        });
    public static final RegistryObject<Item> BULLETCLIP = ITEMS.register("bullet_clip", HaloAmmoItem::new);
    public static final RegistryObject<Item> MAGNUM = ITEMS.register("magnum_h3", () -> new HaloGunBase("magnum_h3", ProjectileEnum.BULLET, CommonMod.config.magnum_max_ammo, Sounds.PISTOLRELOAD.get(), Sounds.PISTOL.get(), CommonMod.config.magnum_mag_size, CommonMod.config.magnum_bullet_damage) {
        });
    public static final RegistryObject<Item> BATTLERIFLE = ITEMS.register("battle_rifle", () -> new HaloGunBase("battle_rifle", ProjectileEnum.BRBULLET, CommonMod.config.battlerifle_max_ammo, Sounds.BATTLERIFLERELOAD.get(), Sounds.BATTLERIFLE.get(), CommonMod.config.battlerifle_mag_size, CommonMod.config.battlerifle_bullet_damage) {
        });
    public static final RegistryObject<Item> NEEDLES = ITEMS.register("needles", HaloAmmoItem::new);
    public static final RegistryObject<Item> NEEDLER = ITEMS.register("needler", () -> new HaloGunBase("needler", ProjectileEnum.NEEDLE, CommonMod.config.needler_max_ammo, Sounds.NEEDLERRELOAD.get(), Sounds.NEEDLER.get(), CommonMod.config.needler_mag_size, CommonMod.config.needler_bullet_damage) {
        });
    public static final RegistryObject<Item> BATTERIES = ITEMS.register("batteries", HaloAmmoItem::new);
    public static final RegistryObject<Item> PLASMAPISTOL = ITEMS.register("plasma_pistol", () -> new HaloGunBase("plasma_pistol", ProjectileEnum.PLASMAG, CommonMod.config.plasmapistol_max_ammo, Sounds.PLASMAPISTOLELOAD.get(), Sounds.PLASMAPISTOL.get(), CommonMod.config.plasmapistol_mag_size, CommonMod.config.plasmapistol_bullet_damage) {
        });
    public static final RegistryObject<Item> PLASMARIFLE = ITEMS.register("plasma_rifle", () -> new HaloGunBase("plasma_rifle", ProjectileEnum.PLASMA, CommonMod.config.plasmarifle_max_ammo, Sounds.PLASMARIFLERELOAD.get(), Sounds.PLASMARIFLE.get(), CommonMod.config.plasmarifle_mag_size, CommonMod.config.plasmarifle_bullet_damage) {
        });
    public static final RegistryObject<Item> GRENADE = ITEMS.register("grenade", GrenadeItem::new);
    public static final RegistryObject<Item> BRUTESHOT = ITEMS.register("brute_shot", () -> new HaloGunBase("brute_shot", ProjectileEnum.GRENADE, CommonMod.config.bruteshot_max_ammo, Sounds.BRUTESHOTRELOAD.get(), Sounds.BRUTESHOT.get(), CommonMod.config.bruteshot_mag_size, CommonMod.config.bruteshot_bullet_damage) {
        });
    public static final RegistryObject<Item> ROCKET = ITEMS.register("rocket", HaloAmmoItem::new);
    public static final RegistryObject<Item> ROCKETLAUNCHER = ITEMS.register("rocket_launcher_h3", () -> new HaloGunBase("rocket_launcher_h3", ProjectileEnum.ROCKET, CommonMod.config.rocketlauncher_max_ammo, Sounds.ROCKETRELOAD.get(), Sounds.ROCKET.get(), CommonMod.config.rocketlauncher_mag_size, CommonMod.config.rocketlauncher_damage) {
        });
    /* Blocks */
    public static final RegistryObject<Item> GUN_TABLE = ITEMS.register("gun_table", () -> new BlockItem(NeoForgeMod.Blocks.GUN_TABLE.get(), new Item.Properties()));

}
