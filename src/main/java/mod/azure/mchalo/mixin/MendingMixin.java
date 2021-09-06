package mod.azure.mchalo.mixin;

import org.spongepowered.asm.mixin.Mixin;

import mod.azure.mchalo.item.HaloGunBase;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.MendingEnchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

@Mixin(value = MendingEnchantment.class)
public class MendingMixin extends Enchantment {

	protected MendingMixin(Rarity weight, EnchantmentTarget type, EquipmentSlot[] slotTypes) {
		super(weight, type, slotTypes);
	}

	@Override
	public boolean isAcceptableItem(ItemStack stack) {
		return (stack.getItem() instanceof HaloGunBase) ? false : super.isAcceptableItem(stack);
	}

}