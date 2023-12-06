package mod.azure.mchalo.config;

import mod.azure.azurelib.config.Config;
import mod.azure.azurelib.config.Configurable;
import mod.azure.mchalo.CommonMod;

@Config(id = CommonMod.MOD_ID)
public class HaloConfig {

    @Configurable
    public int battlerifle_max_ammo = 144;
    @Configurable
    public int battlerifle_mag_size = 36;
    @Configurable
    public float battlerifle_bullet_damage = 2.5F;

    @Configurable
    public int magnum_max_ammo = 40;
    @Configurable
    public int magnum_mag_size = 8;
    @Configurable
    public float magnum_bullet_damage = 2.5F;

    @Configurable
    public int shotgun_max_ammo = 48;
    @Configurable
    public int shotgun_mag_size = 12;
    @Configurable
    public float shotgun_bullet_damage = 3.5F;

    @Configurable
    public int sniper_max_ammo = 28;
    @Configurable
    public int sniper_mag_size = 4;
    @Configurable
    public float sniper_bullet_damage = 16F;

    @Configurable
    public int rocketlauncher_max_ammo = 8;
    @Configurable
    public int rocketlauncher_mag_size = 2;
    @Configurable
    public int rocketlauncher_damage = 4;

    @Configurable
    public int propshield_max_damage = 500;

    @Configurable
    public int plasmapistol_max_ammo = 100;
    @Configurable
    public int plasmapistol_mag_size = 25;
    @Configurable
    public float plasmapistol_bullet_damage = 6.5F;

    @Configurable
    public int plasmarifle_max_ammo = 100;
    @Configurable
    public int plasmarifle_mag_size = 25;
    @Configurable
    public float plasmarifle_bullet_damage = 15.5F;

    @Configurable
    public int needler_max_ammo = 120;
    @Configurable
    public int needler_mag_size = 20;
    @Configurable
    public float needler_bullet_damage = 6.5F;

    @Configurable
    public int mauler_max_ammo = 25;
    @Configurable
    public int mauler_mag_size = 5;
    @Configurable
    public float mauler_bullet_damage = 3.5F;

    @Configurable
    public boolean grenades_cause_fire = false;
    @Configurable
    public boolean grenades_break_blocks = false;

    @Configurable
    public int bruteshot_max_ammo = 18;
    @Configurable
    public int bruteshot_mag_size = 1;
    @Configurable
    public float bruteshot_bullet_damage = 7.5F;

}
