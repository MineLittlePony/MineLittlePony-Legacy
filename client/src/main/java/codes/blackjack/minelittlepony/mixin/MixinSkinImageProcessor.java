package codes.blackjack.minelittlepony.mixin;

import net.minecraft.client.render.texture.SkinImageProcessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.awt.image.BufferedImage;

@Mixin(SkinImageProcessor.class)
public class MixinSkinImageProcessor {
	@Inject(
		method = "process",
		at = @At("HEAD"),
		cancellable = true
	)
	private void reprocessImage(BufferedImage image, CallbackInfoReturnable<BufferedImage> cir) {
		// Square HD MineLittlePony skin. It has extra data in the lower half, so we need to
		// crop it down to 2:1
		if (image.getWidth() == image.getHeight()) {
			cir.setReturnValue(
				image.getSubimage(0, 0, image.getWidth(), image.getHeight() / 2)
			);
		}
	}
}
