package mod.azure.mchalo.mixins;

import mod.azure.mchalo.item.HaloGunBase;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Villager.class)
public abstract class VillagerMixin extends AbstractVillager {

    protected VillagerMixin(EntityType<? extends AbstractVillager> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(at = @At("RETURN"), method = "mobInteract", cancellable = true)
    private void killVillager(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> ci) {
        var itemStack = player.getItemInHand(hand);
        if (itemStack.getItem() instanceof HaloGunBase) ci.setReturnValue(InteractionResult.FAIL);
    }
}
