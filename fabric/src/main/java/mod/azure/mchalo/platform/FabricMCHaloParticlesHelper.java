package mod.azure.mchalo.platform;

import mod.azure.mchalo.platform.services.MCHaloParticlesHelper;
import mod.azure.mchalo.registry.Particles;
import net.minecraft.core.particles.SimpleParticleType;

public class FabricMCHaloParticlesHelper implements MCHaloParticlesHelper {
    @Override
    public SimpleParticleType getPlasmaParticle() {
        return Particles.PLASMA;
    }

    @Override
    public SimpleParticleType getPlasmaGParticle() {
        return Particles.PLASMAG;
    }
}
