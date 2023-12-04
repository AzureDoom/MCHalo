package mod.azure.mchalo.registry;

import mod.azure.mchalo.CommonMod;
import mod.azure.mchalo.particle.PlasmaParticle;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public record Particles() {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, CommonMod.MOD_ID);
    public static final RegistryObject<SimpleParticleType> PLASMA = PARTICLES.register("plasma", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> PLASMAG = PARTICLES.register("plasmag", () -> new SimpleParticleType(true));
}
