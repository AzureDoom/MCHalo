package mod.azure.mchalo.platform;

import mod.azure.mchalo.FabricLibMod;
import mod.azure.mchalo.platform.services.MCHaloItemsHelper;
import mod.azure.mchalo.registry.Items;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class FabricMCHaloItemsHelper implements MCHaloItemsHelper {
    @Override
    public Block getGunTable() {
        return FabricLibMod.GUN_TABLE;
    }

    @Override
    public Item getSniper() {
        return Items.SNIPER;
    }

    @Override
    public Item getBattleRifle() {
        return Items.BATTLERIFLE;
    }

    @Override
    public Item getSniperAmmo() {
        return Items.SNIPER_ROUND;
    }

    @Override
    public Item getShellAmmo() {
        return Items.SHOTGUN_CLIP;
    }

    @Override
    public Item getBulletAmmo() {
        return Items.BULLETCLIP;
    }

    @Override
    public Item getNeedlesAmmo() {
        return Items.NEEDLES;
    }

    @Override
    public Item getBatteriesAmmo() {
        return Items.BATTERIES;
    }

    @Override
    public Item getGrenadeAmmo() {
        return Items.GRENADE;
    }

    @Override
    public Item getRocketAmmo() {
        return Items.ROCKET;
    }
}
