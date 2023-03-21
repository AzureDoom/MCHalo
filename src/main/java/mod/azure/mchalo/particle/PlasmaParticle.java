package mod.azure.mchalo.particle;

import java.util.Random;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;

@Environment(value = EnvType.CLIENT)
public class PlasmaParticle extends TextureSheetParticle {
	static final Random RANDOM = new Random();
	private final SpriteSet spriteProvider;

	PlasmaParticle(ClientLevel world, double x, double y, double z, double velocityX, double velocityY,
			double velocityZ, SpriteSet spriteProvider) {
		super(world, x, y, z, velocityX, velocityY, velocityZ);
		this.friction = 0.96f;
		this.speedUpWhenYMotionIsBlocked = true;
		this.spriteProvider = spriteProvider;
		this.quadSize *= 2f;
		this.hasPhysics = false;
		this.setSpriteFromAge(spriteProvider);
	}

	@Override
	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
	}

	@Override
	public int getLightColor(float tint) {
		var f = ((float) this.age + tint) / (float) this.lifetime;
		f = Mth.clamp(f, 0.0f, 1.0f);
		var lightColor = super.getLightColor(tint);
		var j = lightColor & 0xFF;
		var k = lightColor >> 16 & 0xFF;
		if ((j += (int) (f * 15.0f * 16.0f)) > 240) 
			j = 240;
		return j | k << 16;
	}

	@Override
	public void tick() {
		super.tick();
		this.setSpriteFromAge(this.spriteProvider);
	}

	@Environment(value = EnvType.CLIENT)
	public static class GreenFactory implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet spriteProvider;

		public GreenFactory(SpriteSet spriteProvider) {
			this.spriteProvider = spriteProvider;
		}

		@Override
		public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d,
				double e, double f, double g, double h, double i) {
			double rotate = Mth.clamp(f, 0.0f, 360.0f);
			var glowParticle = new PlasmaParticle(clientWorld, d, e, f, 0.0, 0.0, 0.0, this.spriteProvider);
			glowParticle.setColor(199F, 78F, 189F);
			glowParticle.setParticleSpeed(g * 0.25, h * 0.25, i * 0.25);
			glowParticle.roll = (float) rotate;
			glowParticle.setLifetime(clientWorld.random.nextInt(2) + 2);
			return glowParticle;
		}
	}

	@Environment(value = EnvType.CLIENT)
	public static class PurpleFactory implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet spriteProvider;

		public PurpleFactory(SpriteSet spriteProvider) {
			this.spriteProvider = spriteProvider;
		}

		@Override
		public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d,
				double e, double f, double g, double h, double i) {
			var rotate = Mth.clamp(f, 0.0f, 360.0f);
			var glowParticle = new PlasmaParticle(clientWorld, d, e, f, 0.0, 0.0, 0.0, this.spriteProvider);
			glowParticle.setColor(128F, 199F, 31F);
			glowParticle.setParticleSpeed(g * 0.25, h * 0.25, i * 0.25);
			glowParticle.roll = (float) rotate;
			glowParticle.setLifetime(clientWorld.random.nextInt(2) + 2);
			return glowParticle;
		}
	}
}