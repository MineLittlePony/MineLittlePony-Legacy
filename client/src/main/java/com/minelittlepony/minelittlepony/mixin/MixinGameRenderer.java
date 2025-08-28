package com.minelittlepony.minelittlepony.mixin;

import com.minelittlepony.minelittlepony.render.GlowBuffer;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class MixinGameRenderer {
	@Inject(
		method = "render",
		at = @At("HEAD")
	)
	private void onRenderGame$head(float tickDelta, CallbackInfo ci) {
		GlowBuffer.INSTANCE.reset();
	}

	@Inject(
		method = "render",
		at = @At("TAIL")
	)
	private void onRenderGame$tail(float tickDelta, CallbackInfo ci) {
		GlowBuffer.INSTANCE.flush();
	}
}
