package mod.azure.mchalo.packets;

import mod.azure.mchalo.client.gui.GunTableScreenHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.PacketListener;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CraftingPacket {

    private final int index;

    public CraftingPacket(final FriendlyByteBuf packetBuffer) {
        index = packetBuffer.readInt();
    }

    public CraftingPacket(int index) {
        this.index = index;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(index);
    }

    public void handle(Supplier<NetworkEvent.Context> sup) {
        final NetworkEvent.Context ctx = sup.get();
        ctx.enqueueWork(() -> {
            final NetworkEvent.Context context = sup.get();
            final PacketListener handler = context.getNetworkManager().getPacketListener();
            if (handler instanceof ServerGamePacketListenerImpl serverGamePacketListener) {
                final ServerPlayer playerEntity = serverGamePacketListener.player;
                final AbstractContainerMenu container = playerEntity.containerMenu;
                if (container instanceof GunTableScreenHandler gunTableScreenHandler) {
                    gunTableScreenHandler.setRecipeIndex(index);
                    gunTableScreenHandler.switchTo(index);
                }
            }
        });
        ctx.setPacketHandled(true);
    }

}
