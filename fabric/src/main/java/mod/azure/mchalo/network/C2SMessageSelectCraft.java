package mod.azure.mchalo.network;

import io.netty.buffer.Unpooled;
import mod.azure.mchalo.client.gui.GunTableScreenHandler;
import mod.azure.mchalo.platform.services.MCHaloNetwork;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;

public class C2SMessageSelectCraft implements ServerPlayNetworking.PlayChannelHandler {

    public static void send(int index) {
        final var buf = new FriendlyByteBuf(Unpooled.buffer());
        buf.writeInt(index);
        ClientPlayNetworking.send(MCHaloNetwork.LOCK_SLOT, buf);
    }

    public void handle(ServerPlayer player, int index) {
        final var container = player.containerMenu;
        if (container instanceof GunTableScreenHandler gunTableScreenHandler) {
            gunTableScreenHandler.setRecipeIndex(index);
            gunTableScreenHandler.switchTo(index);
        }
    }

    @Override
    public void receive(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler, FriendlyByteBuf buf, PacketSender responseSender) {
        final var index = buf.readInt();
        server.execute(() -> handle(player, index));
    }
}
