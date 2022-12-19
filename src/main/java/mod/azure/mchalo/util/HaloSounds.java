package mod.azure.mchalo.util;

import mod.azure.mchalo.MCHaloMod;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class HaloSounds {
	public static SoundEvent SNIPER = of("sniper_fire_h3");
	public static SoundEvent SNIPERRELOAD = of("sniper_reload");
	public static SoundEvent SHOTGUN = of("shotgun");
	public static SoundEvent SHOTGUNRELOAD = of("shotgunreload");
	public static SoundEvent PISTOL = of("pistol");
	public static SoundEvent PISTOLRELOAD = of("pistolreload");
	public static SoundEvent BATTLERIFLE = of("battle_rifle");
	public static SoundEvent BATTLERIFLERELOAD = of("battle_rifle_reload");
	public static SoundEvent ROCKET = of("rocket_h3_1");
	public static SoundEvent ROCKETRELOAD = of("rocket_reload");

	public static SoundEvent PLASMAPISTOL = of("plasmapistol");
	public static SoundEvent PLASMAPISTOLELOAD = of("plasmapistol_reload");
	public static SoundEvent PLASMARIFLE = of("plasmarifle");
	public static SoundEvent PLASMARIFLERELOAD = of("plasmarifle_reload");
	public static SoundEvent NEEDLER = of("needler");
	public static SoundEvent NEEDLERRELOAD = of("needler_reload");
	public static SoundEvent MAULER = of("mauler");
	public static SoundEvent MAULERRELOAD = of("mauler_reload");
	public static SoundEvent BRUTESHOT = of("bruteshot");
	public static SoundEvent BRUTESHOTRELOAD = of("bruteshot_reload");
	public static SoundEvent ENERGYSWORD1A = of("energy_melee1a");
	public static SoundEvent ENERGYSWORD1B = of("energy_melee1b");
	public static SoundEvent ENERGYSWORD1C = of("energy_melee1c");
	public static SoundEvent BRUTESHOT_MELEE1 = of("brute_shot_melee1");
	public static SoundEvent BRUTESHOT_MELEE2 = of("brute_shot_melee2");
	public static SoundEvent BRUTESHOT_MELEE3 = of("brute_shot_melee3");

	static SoundEvent of(String id) {
		SoundEvent sound = SoundEvent.of(new Identifier(MCHaloMod.MODID, id));
		Registry.register(Registries.SOUND_EVENT, new Identifier(MCHaloMod.MODID, id), sound);
		return sound;
	}
}
