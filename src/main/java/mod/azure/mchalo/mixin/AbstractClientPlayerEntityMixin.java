package mod.azure.mchalo.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.mojang.authlib.GameProfile;

import mod.azure.mchalo.client.ClientInit;
import mod.azure.mchalo.util.HaloItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(AbstractClientPlayerEntity.class)
public abstract class AbstractClientPlayerEntityMixin extends PlayerEntity {

	public AbstractClientPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile profile) {
		super(world, pos, yaw, profile);
	}

	@SuppressWarnings("resource")
	@Inject(at = @At("HEAD"), method = "getFovMultiplier", cancellable = true)
	private void render(CallbackInfoReturnable<Float> ci) {
		ItemStack itemStack = this.getMainHandStack();
		if (MinecraftClient.getInstance().options.getPerspective().isFirstPerson()) {
			if (itemStack.isOf(HaloItems.SNIPER)) {
				ci.setReturnValue(ClientInit.scope.isPressed() ? 0.1F : 1.0F);
			}
			if (itemStack.isOf(HaloItems.BATTLERIFLE)) {
				ci.setReturnValue(ClientInit.scope.isPressed() ? 0.5F : 1.0F);
			}
		}
	}
}
