package mod.azure.mchalo.registry;

import mod.azure.mchalo.CommonMod;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public record Particles() {

    public static final SimpleParticleType PLASMA = register(CommonMod.modResource("plasma"));
    public static final SimpleParticleType PLASMAG = register(CommonMod.modResource("plasmag"));

    private static SimpleParticleType register(ResourceLocation identifier) {
        return Registry.register(BuiltInRegistries.PARTICLE_TYPE, identifier, FabricParticleTypes.simple(true));
    }
}
