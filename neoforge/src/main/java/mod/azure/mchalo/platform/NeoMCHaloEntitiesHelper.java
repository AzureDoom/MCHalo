package mod.azure.mchalo.platform;

import mod.azure.mchalo.blocks.blockentity.GunBlockEntity;
import mod.azure.mchalo.platform.services.MCHaloEntitiesHelper;
import mod.azure.mchalo.registry.Entities;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class NeoMCHaloEntitiesHelper implements MCHaloEntitiesHelper {
    @Override
    public BlockEntityType<GunBlockEntity> getGunTableEntity() {
        return Entities.GUN_TABLE_ENTITY.get();
    }

    @Override
    public EntityType<? extends AbstractArrow> getBulletEntity() {
        return Entities.BULLET.get();
    }

    @Override
    public EntityType<? extends AbstractArrow> getGrenadeEntity() {
        return Entities.GRENADE.get();
    }

    @Override
    public EntityType<? extends AbstractArrow> getNeedleEntity() {
        return Entities.NEEDLE.get();
    }

    @Override
    public EntityType<? extends AbstractArrow> getPlasmaEntity() {
        return Entities.PLASMA.get();
    }

    @Override
    public EntityType<? extends AbstractArrow> getPlasmaGEntity() {
        return Entities.PLASMAG.get();
    }

    @Override
    public EntityType<? extends AbstractArrow> getRocketEntity() {
        return Entities.ROCKET.get();
    }
}
