package mod.azure.mchalo;

import mod.azure.mchalo.config.HaloConfig;
import net.minecraft.resources.ResourceLocation;

public record CommonMod() {
    public static HaloConfig config;
    public static final String MOD_ID = "mchalo";

    public static final ResourceLocation modResource(String name) {
        return new ResourceLocation(MOD_ID, name);
    }
}
