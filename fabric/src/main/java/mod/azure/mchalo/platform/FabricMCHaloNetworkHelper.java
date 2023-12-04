package mod.azure.mchalo.platform;

import io.netty.buffer.Unpooled;
import mod.azure.mchalo.network.C2SMessageSelectCraft;
import mod.azure.mchalo.platform.services.MCHaloNetwork;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;

public class FabricMCHaloNetworkHelper implements MCHaloNetwork {
    @Override
    public void sendCraftingPacket(int selectedIndex) {
        C2SMessageSelectCraft.send(selectedIndex);
    }

    @Override
    public void reload(int slot) {
        final var passedData = new FriendlyByteBuf(Unpooled.buffer());
        passedData.writeBoolean(true);
        ClientPlayNetworking.send(MCHaloNetwork.RELOAD, passedData);
    }

    @Override
    public void shoot(int slot) {
        final var passedData = new FriendlyByteBuf(Unpooled.buffer());
        passedData.writeBoolean(true);
        ClientPlayNetworking.send(MCHaloNetwork.ANIMATE, passedData);
    }
}
