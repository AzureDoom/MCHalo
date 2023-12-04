package mod.azure.mchalo.registry;

import mod.azure.mchalo.CommonMod;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public record Sounds() {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, CommonMod.MOD_ID);
    public static final RegistryObject<SoundEvent> SNIPER = SOUNDS.register("sniper_fire_h3", () -> SoundEvent.createVariableRangeEvent(CommonMod.modResource("sniper_fire_h3")));
    public static final RegistryObject<SoundEvent> SNIPERRELOAD = SOUNDS.register("sniper_reload", () -> SoundEvent.createVariableRangeEvent(CommonMod.modResource("sniper_reload")));
    public static final RegistryObject<SoundEvent> SHOTGUN = SOUNDS.register("shotgun", () -> SoundEvent.createVariableRangeEvent(CommonMod.modResource("shotgun")));
    public static final RegistryObject<SoundEvent> SHOTGUNRELOAD = SOUNDS.register("shotgunreload", () -> SoundEvent.createVariableRangeEvent(CommonMod.modResource("shotgunreload")));
    public static final RegistryObject<SoundEvent> PISTOL = SOUNDS.register("pistol", () -> SoundEvent.createVariableRangeEvent(CommonMod.modResource("pistol")));
    public static final RegistryObject<SoundEvent> PISTOLRELOAD = SOUNDS.register("pistolreload", () -> SoundEvent.createVariableRangeEvent(CommonMod.modResource("pistolreload")));
    public static final RegistryObject<SoundEvent> BATTLERIFLE = SOUNDS.register("battle_rifle", () -> SoundEvent.createVariableRangeEvent(CommonMod.modResource("battle_rifle")));
    public static final RegistryObject<SoundEvent> BATTLERIFLERELOAD = SOUNDS.register("battle_rifle_reload", () -> SoundEvent.createVariableRangeEvent(CommonMod.modResource("battle_rifle_reload")));
    public static final RegistryObject<SoundEvent> ROCKET = SOUNDS.register("rocket_h3_1", () -> SoundEvent.createVariableRangeEvent(CommonMod.modResource("rocket_h3_1")));
    public static final RegistryObject<SoundEvent> ROCKETRELOAD = SOUNDS.register("rocket_reload", () -> SoundEvent.createVariableRangeEvent(CommonMod.modResource("rocket_reload")));
    public static final RegistryObject<SoundEvent> PLASMAPISTOL = SOUNDS.register("plasmapistol", () -> SoundEvent.createVariableRangeEvent(CommonMod.modResource("plasmapistol")));
    public static final RegistryObject<SoundEvent> PLASMAPISTOLELOAD = SOUNDS.register("plasmapistol_reload", () -> SoundEvent.createVariableRangeEvent(CommonMod.modResource("plasmapistol_reload")));
    public static final RegistryObject<SoundEvent> PLASMARIFLE = SOUNDS.register("plasmarifle", () -> SoundEvent.createVariableRangeEvent(CommonMod.modResource("plasmarifle")));
    public static final RegistryObject<SoundEvent> PLASMARIFLERELOAD = SOUNDS.register("plasmarifle_reload", () -> SoundEvent.createVariableRangeEvent(CommonMod.modResource("plasmarifle_reload")));
    public static final RegistryObject<SoundEvent> NEEDLER = SOUNDS.register("needler", () -> SoundEvent.createVariableRangeEvent(CommonMod.modResource("needler")));
    public static final RegistryObject<SoundEvent> NEEDLERRELOAD = SOUNDS.register("needler_reload", () -> SoundEvent.createVariableRangeEvent(CommonMod.modResource("needler_reload")));
    public static final RegistryObject<SoundEvent> MAULER = SOUNDS.register("mauler", () -> SoundEvent.createVariableRangeEvent(CommonMod.modResource("mauler")));
    public static final RegistryObject<SoundEvent> MAULERRELOAD = SOUNDS.register("mauler_reload", () -> SoundEvent.createVariableRangeEvent(CommonMod.modResource("mauler_reload")));
    public static final RegistryObject<SoundEvent> BRUTESHOT = SOUNDS.register("bruteshot", () -> SoundEvent.createVariableRangeEvent(CommonMod.modResource("bruteshot")));
    public static final RegistryObject<SoundEvent> BRUTESHOTRELOAD = SOUNDS.register("bruteshot_reload", () -> SoundEvent.createVariableRangeEvent(CommonMod.modResource("bruteshot_reload")));
    public static final RegistryObject<SoundEvent> ENERGYSWORD1A = SOUNDS.register("energy_melee1a", () -> SoundEvent.createVariableRangeEvent(CommonMod.modResource("energy_melee1a")));
    public static final RegistryObject<SoundEvent> ENERGYSWORD1B = SOUNDS.register("energy_melee1b", () -> SoundEvent.createVariableRangeEvent(CommonMod.modResource("energy_melee1b")));
    public static final RegistryObject<SoundEvent> ENERGYSWORD1C = SOUNDS.register("energy_melee1c", () -> SoundEvent.createVariableRangeEvent(CommonMod.modResource("energy_melee1c")));
    public static final RegistryObject<SoundEvent> ENERGYSWORDLOOP = SOUNDS.register("energy_loop", () -> SoundEvent.createVariableRangeEvent(CommonMod.modResource("energy_loop")));
    public static final RegistryObject<SoundEvent> ENERGYSWORDOPEN = SOUNDS.register("energy_open", () -> SoundEvent.createVariableRangeEvent(CommonMod.modResource("energy_open")));
    public static final RegistryObject<SoundEvent> BRUTESHOT_MELEE1 = SOUNDS.register("brute_shot_melee1", () -> SoundEvent.createVariableRangeEvent(CommonMod.modResource("brute_shot_melee1")));
    public static final RegistryObject<SoundEvent> BRUTESHOT_MELEE2 = SOUNDS.register("brute_shot_melee2", () -> SoundEvent.createVariableRangeEvent(CommonMod.modResource("brute_shot_melee2")));
    public static final RegistryObject<SoundEvent> BRUTESHOT_MELEE3 = SOUNDS.register("brute_shot_melee3", () -> SoundEvent.createVariableRangeEvent(CommonMod.modResource("brute_shot_melee3")));
}
