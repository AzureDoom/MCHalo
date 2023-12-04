package mod.azure.mchalo.platform;

import mod.azure.azurelib.AzureLib;
import mod.azure.mchalo.platform.services.*;

import java.util.ServiceLoader;

public class Services {
    public static final MCHaloPlatformHelper PLATFORM = load(MCHaloPlatformHelper.class);
    public static final MCHaloNetwork NETWORK = load(MCHaloNetwork.class);
    public static final MCHaloItemsHelper ITEMS_HELPER = load(MCHaloItemsHelper.class);
    public static final MCHaloEntitiesHelper ENTITIES_HELPER = load(MCHaloEntitiesHelper.class);
    public static final MCHaloSoundsHelper SOUNDS_HELPER = load(MCHaloSoundsHelper.class);
    public static final MCHaloParticlesHelper PARTICLES_HELPER = load(MCHaloParticlesHelper.class);

    private Services() {
    }

    public static <T> T load(Class<T> clazz) {

        final T loadedService = ServiceLoader.load(clazz).findFirst().orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        AzureLib.LOGGER.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}
