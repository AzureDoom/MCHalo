package mod.azure.mchalo.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import mod.azure.mchalo.item.HaloGunBase;
import net.minecraft.enchantment.UnbreakingEnchantment;
import net.minecraft.item.ItemStack;

@Mixin(value = UnbreakingEnchantment.class)
public class UnbreakingMixin {

	@Inject(method = "isAcceptableItem(Lnet/minecraft/item/ItemStack;)Z", at = @At("TAIL"))
	private void noUnbreaking(ItemStack stack, CallbackInfoReturnable<?> info) {
		if (stack.getItem() instanceof HaloGunBase)
			info.isCancelled();
	}

}