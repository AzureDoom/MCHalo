package mod.azure.mchalo.registry;

import mod.azure.mchalo.CommonMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class Tabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CommonMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> ITEM_GROUP = TABS.register("items", () -> CreativeModeTab.builder().title(Component.translatable("itemGroup." + CommonMod.MOD_ID + ".items")).icon(() -> new ItemStack(Items.ENERGYSWORD.get())).displayItems((enabledFeatures, entries) -> {
        entries.accept(Items.ENERGYSWORD.get());
        entries.accept(Items.MAGNUM.get());
        entries.accept(Items.BATTLERIFLE.get());
        entries.accept(Items.BULLETCLIP.get());
        entries.accept(Items.SHOTGUN.get());
        entries.accept(Items.MAULER.get());
        entries.accept(Items.SHOTGUN_CLIP.get());
        entries.accept(Items.SNIPER.get());
        entries.accept(Items.SNIPER_ROUND.get());
        entries.accept(Items.BRUTESHOT.get());
        entries.accept(Items.GRENADE.get());
        entries.accept(Items.NEEDLER.get());
        entries.accept(Items.NEEDLES.get());
        entries.accept(Items.PLASMAPISTOL.get());
        entries.accept(Items.PLASMARIFLE.get());
        entries.accept(Items.BATTERIES.get());
        entries.accept(Items.ROCKETLAUNCHER.get());
        entries.accept(Items.ROCKET.get());
        entries.accept(Items.PROPSHIELD.get());
        entries.accept(Items.GUN_TABLE.get());
    }).build());
}
