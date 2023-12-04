package mod.azure.mchalo.client.gui;

import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class GunTableInventory implements Container {
    private final GunTableScreenHandler container;

    private final NonNullList<ItemStack> stacks;


    public GunTableInventory(GunTableScreenHandler container) {
        this.stacks = NonNullList.withSize(6, ItemStack.EMPTY);
        this.container = container;
    }

    public int getContainerSize() {
        return this.stacks.size();
    }

    public boolean isEmpty() {
        for (ItemStack stack : stacks) {
            if (!stack.isEmpty()) return false;
        }
        return true;
    }

    public @NotNull ItemStack getItem(int slot) {
        return this.stacks.get(slot);
    }

    public @NotNull ItemStack removeItem(int slot, int amount) {
        var itemStack = ContainerHelper.removeItem(this.stacks, slot, amount);
        if (!itemStack.isEmpty() && slot != 5) this.container.slotsChanged(this);
        return itemStack;
    }

    public @NotNull ItemStack removeItemNoUpdate(int slot) {
        return ContainerHelper.takeItem(this.stacks, slot);
    }

    public void setItem(int slot, @NotNull ItemStack stack) {
        this.stacks.set(slot, stack);
        if (slot != 5) container.slotsChanged(this);
    }

    public boolean stillValid(@NotNull Player player) {
        return true;
    }

    public void setChanged() {

    }

    public void clearContent() {
        this.stacks.clear();
    }

}
