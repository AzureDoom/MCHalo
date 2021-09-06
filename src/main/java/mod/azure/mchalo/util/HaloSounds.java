package mod.azure.mchalo.util;

import mod.azure.mchalo.MCHaloMod;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class HaloSounds {
	public static SoundEvent SNIPER = of("mchalo.sniper_fire_h3");
	public static SoundEvent SNIPERRELOAD = of("mchalo.sniper_reload");
	public static SoundEvent SHOTGUN = of("mchalo.shotgun");
	public static SoundEvent SHOTGUNRELOAD = of("mchalo.shotgunreload");
	public static SoundEvent PISTOL = of("mchalo.pistol");
	public static SoundEvent PISTOLRELOAD = of("mchalo.pistolreload");
	public static SoundEvent BATTLERIFLE = of("mchalo.battle_rifle");
	public static SoundEvent BATTLERIFLERELOAD = of("mchalo.battle_rifle_reload");
	public static SoundEvent ROCKET = of("mchalo.rocket_h3_1");
	public static SoundEvent ROCKETRELOAD = of("mchalo.rocket_reload");

	public static SoundEvent PLASMAPISTOL = of("mchalo.plasmapistol");
	public static SoundEvent PLASMAPISTOLELOAD = of("mchalo.plasmapistol_reload");
	public static SoundEvent PLASMARIFLE = of("mchalo.plasmarifle");
	public static SoundEvent PLASMARIFLERELOAD = of("mchalo.plasmarifle_reload");
	public static SoundEvent NEEDLER = of("mchalo.needler");
	public static SoundEvent NEEDLERRELOAD = of("mchalo.needler_reload");
	public static SoundEvent MAULER = of("mchalo.mauler");
	public static SoundEvent MAULERRELOAD = of("mchalo.mauler_reload");
	public static SoundEvent BRUTESHOT = of("mchalo.bruteshot");
	public static SoundEvent BRUTESHOTRELOAD = of("mchalo.bruteshot_reload");

	static SoundEvent of(String id) {
		SoundEvent sound = new SoundEvent(new Identifier(MCHaloMod.MODID, id));
		Registry.register(Registry.SOUND_EVENT, new Identifier(MCHaloMod.MODID, id), sound);
		return sound;
	}
}
