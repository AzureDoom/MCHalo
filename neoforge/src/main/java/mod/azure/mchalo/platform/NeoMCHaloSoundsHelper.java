package mod.azure.mchalo.platform;

import mod.azure.mchalo.platform.services.MCHaloSoundsHelper;
import mod.azure.mchalo.registry.Sounds;
import net.minecraft.sounds.SoundEvent;

public class NeoMCHaloSoundsHelper implements MCHaloSoundsHelper {
    @Override
    public SoundEvent getRocketSound() {
        return Sounds.ROCKET.get();
    }

    @Override
    public SoundEvent getNeedlerSound() {
        return Sounds.NEEDLER.get();
    }
}
