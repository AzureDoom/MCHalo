package mod.azure.mchalo.registry;

import mod.azure.mchalo.CommonMod;
import mod.azure.mchalo.NeoForgeMod;
import mod.azure.mchalo.blocks.blockentity.GunBlockEntity;
import mod.azure.mchalo.entity.projectiles.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public record Entities() {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, CommonMod.MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, CommonMod.MOD_ID);
    public static final RegistryObject<BlockEntityType<GunBlockEntity>> GUN_TABLE_ENTITY = BLOCK_ENTITIES.register("guntable", () -> BlockEntityType.Builder.of(GunBlockEntity::new, NeoForgeMod.Blocks.GUN_TABLE.get()).build(null));
    public static final RegistryObject<EntityType<BulletEntity>> BULLET = ENTITIES.register("bullet", () -> EntityType.Builder.<BulletEntity>of(BulletEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(10).build(CommonMod.modResource("bullet").toString()));
    public static final RegistryObject<EntityType<NeedleEntity>> NEEDLE = ENTITIES.register("needle", () -> EntityType.Builder.<NeedleEntity>of(NeedleEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(10).build(CommonMod.modResource("needle").toString()));
    public static final RegistryObject<EntityType<PlasmaEntity>> PLASMA = ENTITIES.register("plasma", () -> EntityType.Builder.<PlasmaEntity>of(PlasmaEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(10).build(CommonMod.modResource("plasma").toString()));
    public static final RegistryObject<EntityType<PlasmaGEntity>> PLASMAG = ENTITIES.register("plasmag", () -> EntityType.Builder.<PlasmaGEntity>of(PlasmaGEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(10).build(CommonMod.modResource("plasmag").toString()));
    public static final RegistryObject<EntityType<GrenadeEntity>> GRENADE = ENTITIES.register("grenade", () -> EntityType.Builder.<GrenadeEntity>of(GrenadeEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(10).build(CommonMod.modResource("grenade").toString()));
    public static final RegistryObject<EntityType<RocketEntity>> ROCKET = ENTITIES.register("rocket", () -> EntityType.Builder.<RocketEntity>of(RocketEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(10).build(CommonMod.modResource("rocket").toString()));
}
