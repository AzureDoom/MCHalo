package mod.azure.mchalo.util;

import mod.azure.mchalo.MCHaloMod;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class HaloParticles {

	public static final DefaultParticleType PLASMA = register(new Identifier(MCHaloMod.MODID, "plasma"), true);
	public static final DefaultParticleType PLASMAG = register(new Identifier(MCHaloMod.MODID, "plasmag"), true);

	private static DefaultParticleType register(Identifier identifier, boolean alwaysSpawn) {
		return Registry.register(Registries.PARTICLE_TYPE, identifier, FabricParticleTypes.simple(alwaysSpawn));
	}
}
