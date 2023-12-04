package mod.azure.mchalo.registry;

import mod.azure.mchalo.CommonMod;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;

public record Sounds() {
    public static SoundEvent SNIPER;
    public static SoundEvent SNIPERRELOAD;
    public static SoundEvent SHOTGUN;
    public static SoundEvent SHOTGUNRELOAD;
    public static SoundEvent PISTOL;
    public static SoundEvent PISTOLRELOAD;
    public static SoundEvent BATTLERIFLE;
    public static SoundEvent BATTLERIFLERELOAD;
    public static SoundEvent ROCKET;
    public static SoundEvent ROCKETRELOAD;
    public static SoundEvent PLASMAPISTOL;
    public static SoundEvent PLASMAPISTOLELOAD;
    public static SoundEvent PLASMARIFLE;
    public static SoundEvent PLASMARIFLERELOAD;
    public static SoundEvent NEEDLER;
    public static SoundEvent NEEDLERRELOAD;
    public static SoundEvent MAULER;
    public static SoundEvent MAULERRELOAD;
    public static SoundEvent BRUTESHOT;
    public static SoundEvent BRUTESHOTRELOAD;
    public static SoundEvent ENERGYSWORD1A;
    public static SoundEvent ENERGYSWORD1B;
    public static SoundEvent ENERGYSWORD1C;
    public static SoundEvent ENERGYSWORDLOOP;
    public static SoundEvent ENERGYSWORDOPEN;
    public static SoundEvent BRUTESHOT_MELEE1;
    public static SoundEvent BRUTESHOT_MELEE2;
    public static SoundEvent BRUTESHOT_MELEE3;

    static SoundEvent of(String id) {
        var sound = SoundEvent.createVariableRangeEvent(CommonMod.modResource(id));
        Registry.register(BuiltInRegistries.SOUND_EVENT, CommonMod.modResource(id), sound);
        return sound;
    }

    public static void initSounds() {
        SNIPER = of("sniper_fire_h3");
        SNIPERRELOAD = of("sniper_reload");
        SHOTGUN = of("shotgun");
        SHOTGUNRELOAD = of("shotgunreload");
        PISTOL = of("pistol");
        PISTOLRELOAD = of("pistolreload");
        BATTLERIFLE = of("battle_rifle");
        BATTLERIFLERELOAD = of("battle_rifle_reload");
        ROCKET = of("rocket_h3_1");
        ROCKETRELOAD = of("rocket_reload");
        PLASMAPISTOL = of("plasmapistol");
        PLASMAPISTOLELOAD = of("plasmapistol_reload");
        PLASMARIFLE = of("plasmarifle");
        PLASMARIFLERELOAD = of("plasmarifle_reload");
        NEEDLER = of("needler");
        NEEDLERRELOAD = of("needler_reload");
        MAULER = of("mauler");
        MAULERRELOAD = of("mauler_reload");
        BRUTESHOT = of("bruteshot");
        BRUTESHOTRELOAD = of("bruteshot_reload");
        ENERGYSWORD1A = of("energy_melee1a");
        ENERGYSWORD1B = of("energy_melee1b");
        ENERGYSWORD1C = of("energy_melee1c");
        ENERGYSWORDLOOP = of("energy_loop");
        ENERGYSWORDOPEN = of("energy_open");
        BRUTESHOT_MELEE1 = of("brute_shot_melee1");
        BRUTESHOT_MELEE2 = of("brute_shot_melee2");
        BRUTESHOT_MELEE3 = of("brute_shot_melee3");
    }
}
