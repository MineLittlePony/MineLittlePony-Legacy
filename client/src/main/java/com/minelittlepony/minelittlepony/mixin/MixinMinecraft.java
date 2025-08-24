package com.minelittlepony.minelittlepony.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.Session;
import net.minecraft.client.entity.living.player.InputPlayerEntity;
import net.minecraft.client.render.texture.TextureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MixinMinecraft {
	@Shadow
	public Session session;

	@Shadow
	public InputPlayerEntity player;

	@Shadow
	public TextureManager textureManager;

	@Inject(method = "run", at = @At("HEAD"))
	private void setSession(CallbackInfo ci) {
		// TODO: this is just for testing!
		if (System.getProperty("user.name").equals("appledash")) {
			this.session = new Session("Blackjack", "-");
		}
	}
}
