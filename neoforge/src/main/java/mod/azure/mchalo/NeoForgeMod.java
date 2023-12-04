package mod.azure.mchalo;

import mod.azure.azurelib.AzureLibMod;
import mod.azure.azurelib.config.format.ConfigFormats;
import mod.azure.mchalo.blocks.GunTableBlock;
import mod.azure.mchalo.config.HaloConfig;
import mod.azure.mchalo.platform.NeoMCHaloNetworkHelper;
import mod.azure.mchalo.registry.*;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber
@Mod(CommonMod.MOD_ID)
public final class NeoForgeMod {
    /**
     * TODO: Fix Scoping not showing texture
     */
    public static NeoForgeMod instance;

    public NeoForgeMod() {
        instance = this;
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        CommonMod.config = AzureLibMod.registerConfig(HaloConfig.class, ConfigFormats.json()).getConfigInstance();
        modEventBus.addListener(this::setup);
        Items.ITEMS.register(modEventBus);
        Blocks.BLOCKS.register(modEventBus);
        Sounds.SOUNDS.register(modEventBus);
        Entities.ENTITIES.register(modEventBus);
        Entities.BLOCK_ENTITIES.register(modEventBus);
        Particles.PARTICLES.register(modEventBus);
        Tabs.TABS.register(modEventBus);
        Recipes.SERIAL.register(modEventBus);
        Screens.CONTAIN.register(modEventBus);
    }

    private void setup(final FMLCommonSetupEvent event) {
        NeoMCHaloNetworkHelper.registerClientReceiverPackets();
    }

    public record Blocks() {
        public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, CommonMod.MOD_ID);
        public static final RegistryObject<Block> GUN_TABLE = BLOCKS.register("gun_table", GunTableBlock::new);

    }
}
