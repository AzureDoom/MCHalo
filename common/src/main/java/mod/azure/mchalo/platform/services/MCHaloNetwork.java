package mod.azure.mchalo.platform.services;

import mod.azure.mchalo.CommonMod;
import net.minecraft.resources.ResourceLocation;

public interface MCHaloNetwork {
    public static final ResourceLocation LOCK_SLOT = CommonMod.modResource("select_craft");
    public static final ResourceLocation RELOAD = CommonMod.modResource("reload");
    public static final ResourceLocation ANIMATE = CommonMod.modResource("animate");
    void sendCraftingPacket(int selectedIndex);

    void reload(int slot);

    void shoot(int slot);
}
