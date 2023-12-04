package mod.azure.mchalo.registry;

import mod.azure.mchalo.CommonMod;
import mod.azure.mchalo.FabricLibMod;
import mod.azure.mchalo.blocks.blockentity.GunBlockEntity;
import mod.azure.mchalo.entity.projectiles.*;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.block.entity.BlockEntityType;

public record Entities() {
    public static BlockEntityType<GunBlockEntity> GUN_TABLE_ENTITY;
    public static EntityType<BulletEntity> BULLET;
    public static EntityType<NeedleEntity> NEEDLE;
    public static EntityType<PlasmaEntity> PLASMA;
    public static EntityType<PlasmaGEntity> PLASMAG;
    public static EntityType<GrenadeEntity> GRENADE;
    public static EntityType<RocketEntity> ROCKET;

    public static <T extends Entity> EntityType<T> projectile(EntityType.EntityFactory<T> factory, String id) {
        var type = FabricEntityTypeBuilder.<T>create(MobCategory.MISC, factory).dimensions(new EntityDimensions(0.5F, 0.5F, true)).disableSummon().spawnableFarFromPlayer().trackRangeBlocks(90).trackedUpdateRate(1).build();
        Registry.register(BuiltInRegistries.ENTITY_TYPE, CommonMod.modResource(id), type);
        return type;
    }

    public static void initEntities() {
        GUN_TABLE_ENTITY = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, CommonMod.MOD_ID + ":guntable", FabricBlockEntityTypeBuilder.create(GunBlockEntity::new, FabricLibMod.GUN_TABLE).build(null));
        BULLET = projectile(BulletEntity::new, "bullet");
        NEEDLE = projectile(NeedleEntity::new, "needle");
        PLASMA = projectile(PlasmaEntity::new, "plasma");
        PLASMAG = projectile(PlasmaGEntity::new, "plasmag");
        GRENADE = projectile(GrenadeEntity::new, "grenade");
        ROCKET = projectile(RocketEntity::new, "rocket");
    }
}
