package mod.azure.mchalo.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import mod.azure.mchalo.MCHaloMod;

@Config(name = MCHaloMod.MODID)
public class HaloConfig implements ConfigData {

	@ConfigEntry.Gui.CollapsibleObject
	public Weapons weapons = new Weapons();

	public static class Weapons {
		public int battlerifle_max_ammo = 144;
		public int battlerifle_mag_size = 36;
		public float battlerifle_bullet_damage = 2.5F;

		public int magnum_max_ammo = 40;
		public int magnum_mag_size = 8;
		public float magnum_bullet_damage = 2.5F;

		public int shotgun_max_ammo = 48;
		public int shotgun_mag_size = 12;
		public float shotgun_bullet_damage = 3.5F;

		public int sniper_max_ammo = 28;
		public int sniper_mag_size = 4;
		public float sniper_bullet_damage = 16F;

		public int rocketlauncher_max_ammo = 8;
		public int rocketlauncher_mag_size = 2;

		public int propshield_max_damage = 500;

		public int plasmapistol_max_ammo = 100;
		public int plasmapistol_mag_size = 25;
		public float plasmapistol_bullet_damage = 6.5F;

		public int plasmarifle_max_ammo = 100;
		public int plasmarifle_mag_size = 25;
		public float plasmarifle_bullet_damage = 15.5F;

		public int needler_max_ammo = 120;
		public int needler_mag_size = 20;
		public float needler_bullet_damage = 6.5F;

		public int mauler_max_ammo = 25;
		public int mauler_mag_size = 5;
		public float mauler_bullet_damage = 3.5F;

		public int bruteshot_max_ammo = 18;
		public int bruteshot_mag_size = 1;
		public float bruteshot_bullet_damage = 7.5F;
	}

}
