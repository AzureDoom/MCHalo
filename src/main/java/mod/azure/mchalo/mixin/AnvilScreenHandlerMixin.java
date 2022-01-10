package mod.azure.mchalo.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mod.azure.mchalo.util.HaloItems;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.ForgingScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;

@Mixin(value = AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin extends ForgingScreenHandler {

	public AnvilScreenHandlerMixin(ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory,
			ScreenHandlerContext context) {
		super(type, syncId, playerInventory, context);
	}

	@Inject(method = "updateResult", at = @At(value = "RETURN"))
	private void blockMending(CallbackInfo ci) {
		ItemStack leftStack = this.input.getStack(0).copy();
		ItemStack rightStack = this.input.getStack(1).copy();
		if ((leftStack.isOf(HaloItems.BATTLERIFLE) || leftStack.isOf(HaloItems.SNIPER)
				|| leftStack.isOf(HaloItems.SHOTGUN) || leftStack.isOf(HaloItems.MAGNUM)
				|| leftStack.isOf(HaloItems.ROCKETLAUNCHER) || leftStack.isOf(HaloItems.PLASMAPISTOL)
				|| leftStack.isOf(HaloItems.PLASMARIFLE) || leftStack.isOf(HaloItems.NEEDLER)
				|| leftStack.isOf(HaloItems.MAULER) || leftStack.isOf(HaloItems.BRUTESHOT)
				|| leftStack.isOf(HaloItems.ENERGYSWORD) || leftStack.isOf(HaloItems.PROPSHIELD))
				&& EnchantmentHelper.getLevel(Enchantments.MENDING, rightStack) > 0
				|| EnchantmentHelper.getLevel(Enchantments.UNBREAKING, rightStack) > 0
				|| EnchantmentHelper.get(rightStack).containsKey(Enchantments.MENDING)
				|| EnchantmentHelper.get(rightStack).containsKey(Enchantments.UNBREAKING)) {
			ItemStack repaired = ItemStack.EMPTY;
			this.output.setStack(0, repaired);
			this.sendContentUpdates();
		}
	}
}