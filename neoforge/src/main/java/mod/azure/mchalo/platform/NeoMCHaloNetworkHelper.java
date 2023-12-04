package mod.azure.mchalo.platform;

import mod.azure.mchalo.CommonMod;
import mod.azure.mchalo.packets.AnimatePacket;
import mod.azure.mchalo.packets.CraftingPacket;
import mod.azure.mchalo.packets.ReloadPacket;
import mod.azure.mchalo.platform.services.MCHaloNetwork;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class NeoMCHaloNetworkHelper implements MCHaloNetwork {
    private static final String VER = "1";
    private static final SimpleChannel PACKET_CHANNEL = NetworkRegistry.newSimpleChannel(CommonMod.modResource("main"), () -> VER, VER::equals, VER::equals);

    @Override
    public void sendCraftingPacket(int selectedIndex) {
        PACKET_CHANNEL.sendToServer(new CraftingPacket(selectedIndex));
    }

    @Override
    public void reload(int slot) {
        PACKET_CHANNEL.sendToServer(new ReloadPacket(slot));
    }

    @Override
    public void shoot(int slot) {
        PACKET_CHANNEL.sendToServer(new AnimatePacket(slot));
    }

    public static void registerClientReceiverPackets() {
        int id = 0;
        PACKET_CHANNEL.registerMessage(id++, CraftingPacket.class, CraftingPacket::encode, CraftingPacket::new, CraftingPacket::handle);
        PACKET_CHANNEL.registerMessage(id++, ReloadPacket.class, ReloadPacket::encode, ReloadPacket::new, ReloadPacket::handle);
        PACKET_CHANNEL.registerMessage(id++, AnimatePacket.class, AnimatePacket::encode, AnimatePacket::new, AnimatePacket::handle);
    }
}
