package mod.azure.mchalo.mixins;

import mod.azure.mchalo.item.EnergySwordItem;
import mod.azure.mchalo.item.HaloGunBase;
import mod.azure.mchalo.item.PropShieldItem;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = AnvilMenu.class)
public abstract class AnvilScreenHandlerMixin extends ItemCombinerMenu {

    protected AnvilScreenHandlerMixin(MenuType<?> type, int syncId, Inventory playerInventory, ContainerLevelAccess context) {
        super(type, syncId, playerInventory, context);
    }

    @Inject(method = "createResult", at = @At(value = "RETURN"))
    private void blockMending(CallbackInfo ci) {
        var leftStack = this.inputSlots.getItem(0).copy();
        var rightStack = this.inputSlots.getItem(1).copy();
        if ((leftStack.getItem() instanceof HaloGunBase || leftStack.getItem() instanceof EnergySwordItem || leftStack.getItem() instanceof PropShieldItem) && (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.MENDING, rightStack) > 0 || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.UNBREAKING, rightStack) > 0 || EnchantmentHelper.getEnchantments(rightStack).containsKey(Enchantments.MENDING) || EnchantmentHelper.getEnchantments(rightStack).containsKey(Enchantments.UNBREAKING))) {
            var repaired = ItemStack.EMPTY;
            this.resultSlots.setItem(0, repaired);
            this.broadcastChanges();
        }
    }
}