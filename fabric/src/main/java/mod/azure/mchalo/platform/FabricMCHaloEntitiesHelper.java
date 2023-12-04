package mod.azure.mchalo.platform;

import mod.azure.mchalo.blocks.blockentity.GunBlockEntity;
import mod.azure.mchalo.platform.services.MCHaloEntitiesHelper;
import mod.azure.mchalo.registry.Entities;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class FabricMCHaloEntitiesHelper implements MCHaloEntitiesHelper {
    @Override
    public BlockEntityType<GunBlockEntity> getGunTableEntity() {
        return Entities.GUN_TABLE_ENTITY;
    }

    @Override
    public EntityType<? extends AbstractArrow> getBulletEntity() {
        return Entities.BULLET;
    }

    @Override
    public EntityType<? extends AbstractArrow> getGrenadeEntity() {
        return Entities.GRENADE;
    }

    @Override
    public EntityType<? extends AbstractArrow> getNeedleEntity() {
        return Entities.NEEDLE;
    }

    @Override
    public EntityType<? extends AbstractArrow> getPlasmaEntity() {
        return Entities.PLASMA;
    }

    @Override
    public EntityType<? extends AbstractArrow> getPlasmaGEntity() {
        return Entities.PLASMAG;
    }

    @Override
    public EntityType<? extends AbstractArrow> getRocketEntity() {
        return Entities.ROCKET;
    }
}
