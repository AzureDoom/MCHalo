package mod.azure.mchalo.config;

import eu.midnightdust.lib.config.MidnightConfig;

public class HaloConfig extends MidnightConfig {

	@Entry
	public static int battlerifle_max_ammo = 144;
	@Entry
	public static int battlerifle_mag_size = 36;
	@Entry
	public static float battlerifle_bullet_damage = 2.5F;

	@Entry
	public static int magnum_max_ammo = 40;
	@Entry
	public static int magnum_mag_size = 8;
	@Entry
	public static float magnum_bullet_damage = 2.5F;

	@Entry
	public static int shotgun_max_ammo = 48;
	@Entry
	public static int shotgun_mag_size = 12;
	@Entry
	public static float shotgun_bullet_damage = 3.5F;

	@Entry
	public static int sniper_max_ammo = 28;
	@Entry
	public static int sniper_mag_size = 4;
	@Entry
	public static float sniper_bullet_damage = 16F;

	@Entry
	public static int rocketlauncher_max_ammo = 8;
	@Entry
	public static int rocketlauncher_mag_size = 2;
	@Entry
	public static int rocketlauncher_damage = 4;

	@Entry
	public static int propshield_max_damage = 500;

	@Entry
	public static int plasmapistol_max_ammo = 100;
	@Entry
	public static int plasmapistol_mag_size = 25;
	@Entry
	public static float plasmapistol_bullet_damage = 6.5F;

	@Entry
	public static int plasmarifle_max_ammo = 100;
	@Entry
	public static int plasmarifle_mag_size = 25;
	@Entry
	public static float plasmarifle_bullet_damage = 15.5F;

	@Entry
	public static int needler_max_ammo = 120;
	@Entry
	public static int needler_mag_size = 20;
	@Entry
	public static float needler_bullet_damage = 6.5F;

	@Entry
	public static int mauler_max_ammo = 25;
	@Entry
	public static int mauler_mag_size = 5;
	@Entry
	public static float mauler_bullet_damage = 3.5F;

	@Entry
	public static boolean grenades_cause_fire = false;
	@Entry
	public static boolean grenades_break_blocks = false;

	@Entry
	public static int bruteshot_max_ammo = 18;
	@Entry
	public static int bruteshot_mag_size = 1;
	@Entry
	public static float bruteshot_bullet_damage = 7.5F;

}
