package mod.azure.mchalo.platform;

import mod.azure.mchalo.platform.services.MCHaloSoundsHelper;
import mod.azure.mchalo.registry.Sounds;
import net.minecraft.sounds.SoundEvent;

public class FabricMCHaloSoundsHelper implements MCHaloSoundsHelper {
    @Override
    public SoundEvent getRocketSound() {
        return Sounds.ROCKET;
    }

    @Override
    public SoundEvent getNeedlerSound() {
        return Sounds.NEEDLER;
    }
}
