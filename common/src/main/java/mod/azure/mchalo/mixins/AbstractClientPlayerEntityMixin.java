package mod.azure.mchalo.mixins;

import com.mojang.authlib.GameProfile;
import mod.azure.azurelib.Keybindings;
import mod.azure.mchalo.platform.Services;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayer.class)
public abstract class AbstractClientPlayerEntityMixin extends Player {

    protected AbstractClientPlayerEntityMixin(Level world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

    @Inject(at = @At("HEAD"), method = "getFieldOfViewModifier", cancellable = true)
    private void render(CallbackInfoReturnable<Float> ci) {
        var itemStack = this.getMainHandItem();
        if (Minecraft.getInstance().options.getCameraType().isFirstPerson()) {
            if (itemStack.is(Services.ITEMS_HELPER.getSniper()))
                ci.setReturnValue(Keybindings.SCOPE.isDown() ? 0.1F : 1.0F);
            if (itemStack.is(Services.ITEMS_HELPER.getBattleRifle()))
                ci.setReturnValue(Keybindings.SCOPE.isDown() ? 0.5F : 1.0F);
        }
    }
}
