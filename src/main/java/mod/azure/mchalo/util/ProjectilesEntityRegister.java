package mod.azure.mchalo.util;

import java.util.LinkedList;
import java.util.List;

import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.entity.projectiles.BulletEntity;
import mod.azure.mchalo.entity.projectiles.GrenadeEntity;
import mod.azure.mchalo.entity.projectiles.NeedleEntity;
import mod.azure.mchalo.entity.projectiles.PlasmaEntity;
import mod.azure.mchalo.entity.projectiles.PlasmaGEntity;
import mod.azure.mchalo.entity.projectiles.RocketEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ProjectilesEntityRegister {

	public static List<EntityType<? extends Entity>> ENTITY_TYPES = new LinkedList();
	public static List<EntityType<? extends Entity>> ENTITY_THAT_USE_ITEM_RENDERS = new LinkedList();

	public static EntityType<BulletEntity> BULLET = projectile(BulletEntity::new, "bullet");
	public static EntityType<NeedleEntity> NEEDLE = projectile(NeedleEntity::new, "needle");
	public static EntityType<PlasmaEntity> PLASMA = projectile(PlasmaEntity::new, "plasma");
	public static EntityType<PlasmaGEntity> PLASMAG = projectile(PlasmaGEntity::new, "plasmag");
	public static EntityType<GrenadeEntity> GRENADE = projectile(GrenadeEntity::new, "grenade");
	public static EntityType<BulletEntity> BOLT = projectile(BulletEntity::new, "bolt");
	public static EntityType<RocketEntity> ROCKET = projectile(RocketEntity::new, "rocket");

	public static <T extends Entity> EntityType<T> projectile(EntityType.EntityFactory<T> factory, String id) {
		return projectile(factory, id, true);
	}

	public static <T extends Entity> EntityType<T> projectile(EntityType.EntityFactory<T> factory, String id,
			boolean itemRender) {

		var type = FabricEntityTypeBuilder.<T>create(MobCategory.MISC, factory)
				.dimensions(new EntityDimensions(0.5F, 0.5F, true)).disableSummon().spawnableFarFromPlayer()
				.trackRangeBlocks(90).trackedUpdateRate(1).build();

		Registry.register(BuiltInRegistries.ENTITY_TYPE, new ResourceLocation(MCHaloMod.MODID, id), type);

		ENTITY_TYPES.add(type);

		if (itemRender)
			ENTITY_THAT_USE_ITEM_RENDERS.add(type);

		return type;
	}
}
