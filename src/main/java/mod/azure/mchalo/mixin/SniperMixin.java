package mod.azure.mchalo.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;

import mod.azure.mchalo.MCHaloMod;
import mod.azure.mchalo.client.ClientInit;
import mod.azure.mchalo.item.guns.BattleRifleItem;
import mod.azure.mchalo.item.guns.SniperItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;

@Mixin(Gui.class)
public abstract class SniperMixin extends GuiComponent {

	private static final ResourceLocation SNIPER = new ResourceLocation(MCHaloMod.MODID,
			"textures/gui/sniper_scope.png");
	private static final ResourceLocation BATTLERIFLE = new ResourceLocation(MCHaloMod.MODID,
			"textures/gui/sniper_scope_2x.png");
	@Shadow
	private final Minecraft minecraft;
	@Shadow
	private int screenWidth;
	@Shadow
	private int screenHeight;
	private boolean scoped = true;

	public SniperMixin(Minecraft minecraft) {
		this.minecraft = minecraft;
	}

	@Inject(at = @At("TAIL"), method = "render")
	private void render(CallbackInfo info) {
		var itemStack = this.minecraft.player.getInventory().getSelected();
		if (this.minecraft.options.getCameraType().isFirstPerson() && itemStack.getItem() instanceof SniperItem) {
			if (ClientInit.scope.isDown()) {
				if (this.scoped == true)
					this.scoped = false;
				this.renderSniperOverlay(SNIPER);
			} else if (!this.scoped)
				this.scoped = true;
		}
		if (this.minecraft.options.getCameraType().isFirstPerson() && itemStack.getItem() instanceof BattleRifleItem) {
			if (ClientInit.scope.isDown()) {
				if (this.scoped == true)
					this.scoped = false;
				this.renderSniperOverlay(BATTLERIFLE);
			} else if (!this.scoped)
				this.scoped = true;
		}
	}

	private void renderSniperOverlay(ResourceLocation identifier) {
		RenderSystem.disableDepthTest();
		RenderSystem.depthMask(false);
		RenderSystem.defaultBlendFunc();
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.setShaderTexture(0, identifier);
		var tessellator = Tesselator.getInstance();
		var bufferBuilder = tessellator.getBuilder();
		bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
		bufferBuilder.vertex(0.0D, (double) this.screenHeight, -90.0D).uv(0.0F, 1.0F).endVertex();
		bufferBuilder.vertex((double) this.screenWidth, (double) this.screenHeight, -90.0D).uv(1.0F, 1.0F).endVertex();
		bufferBuilder.vertex((double) this.screenWidth, 0.0D, -90.0D).uv(1.0F, 0.0F).endVertex();
		bufferBuilder.vertex(0.0D, 0.0D, -90.0D).uv(0.0F, 0.0F).endVertex();
		tessellator.end();
		RenderSystem.depthMask(true);
		RenderSystem.enableDepthTest();
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
	}
}
