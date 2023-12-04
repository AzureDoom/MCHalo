package mod.azure.mchalo.platform;

import mod.azure.mchalo.NeoForgeMod;
import mod.azure.mchalo.platform.services.MCHaloItemsHelper;
import mod.azure.mchalo.registry.Items;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class NeoMCHaloItemsHelper implements MCHaloItemsHelper {
    @Override
    public Block getGunTable() {
        return NeoForgeMod.Blocks.GUN_TABLE.get();
    }

    @Override
    public Item getSniper() {
        return Items.SNIPER.get();
    }

    @Override
    public Item getBattleRifle() {
        return Items.BATTLERIFLE.get();
    }

    @Override
    public Item getSniperAmmo() {
        return Items.SNIPER_ROUND.get();
    }

    @Override
    public Item getShellAmmo() {
        return Items.SHOTGUN_CLIP.get();
    }

    @Override
    public Item getBulletAmmo() {
        return Items.BULLETCLIP.get();
    }

    @Override
    public Item getNeedlesAmmo() {
        return Items.NEEDLES.get();
    }

    @Override
    public Item getBatteriesAmmo() {
        return Items.BATTERIES.get();
    }

    @Override
    public Item getGrenadeAmmo() {
        return Items.GRENADE.get();
    }

    @Override
    public Item getRocketAmmo() {
        return Items.ROCKET.get();
    }
}
