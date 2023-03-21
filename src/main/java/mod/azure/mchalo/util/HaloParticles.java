package mod.azure.mchalo.util;

import mod.azure.mchalo.MCHaloMod;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public class HaloParticles {

	public static final SimpleParticleType PLASMA = register(new ResourceLocation(MCHaloMod.MODID, "plasma"), true);
	public static final SimpleParticleType PLASMAG = register(new ResourceLocation(MCHaloMod.MODID, "plasmag"), true);

	private static SimpleParticleType register(ResourceLocation identifier, boolean alwaysSpawn) {
		return Registry.register(BuiltInRegistries.PARTICLE_TYPE, identifier, FabricParticleTypes.simple(alwaysSpawn));
	}
}
