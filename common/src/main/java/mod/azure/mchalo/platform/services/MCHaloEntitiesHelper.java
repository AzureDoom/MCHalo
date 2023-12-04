package mod.azure.mchalo.platform.services;

import mod.azure.mchalo.blocks.blockentity.GunBlockEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.block.entity.BlockEntityType;

public interface MCHaloEntitiesHelper {
    BlockEntityType<GunBlockEntity> getGunTableEntity();

    EntityType<? extends AbstractArrow> getBulletEntity();

    EntityType<?extends AbstractArrow> getGrenadeEntity();

    EntityType<?extends AbstractArrow> getNeedleEntity();

    EntityType<?extends AbstractArrow> getPlasmaEntity();

    EntityType<?extends AbstractArrow> getPlasmaGEntity();

    EntityType<?extends AbstractArrow> getRocketEntity();
}
