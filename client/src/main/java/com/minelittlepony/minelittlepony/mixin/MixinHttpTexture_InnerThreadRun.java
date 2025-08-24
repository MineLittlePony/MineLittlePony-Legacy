package com.minelittlepony.minelittlepony.mixin;

import com.minelittlepony.minelittlepony.MineLPEntry;
import com.minelittlepony.minelittlepony.util.SkinFetcher;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.Optional;

@Mixin(
	targets = "net.minecraft.client.render.texture.HttpTexture$1"
)
public class MixinHttpTexture_InnerThreadRun {
	@ModifyArgs(
		method = "run",
		at = @At(
			value = "INVOKE",
			target = "Ljava/net/URL;<init>(Ljava/lang/String;)V"
		)
	)
	private void fixTextureUrl(Args args) {
		String oldSkinServer = "http://skins.minecraft.net/MinecraftSkins/";
		String rawUrl = args.get(0);

		if (rawUrl.startsWith(oldSkinServer) && rawUrl.endsWith(".png")) {
			MineLPEntry.LOGGER.info("Overriding skin URL: {}", rawUrl);
			rawUrl = rawUrl.substring(oldSkinServer.length());
			String username = rawUrl.substring(0, rawUrl.length() - 4);

			try {
				Optional<String> skinUrlOptional = SkinFetcher.getSkinUrl(username);

				if (skinUrlOptional.isPresent()) {
					args.set(0, skinUrlOptional.get());
					MineLPEntry.LOGGER.debug("Skin URL: {}", skinUrlOptional.get());
				}
			} catch (Exception e) {
				MineLPEntry.LOGGER.error("Failed to fetch skin URL for {}", username, e);
			}
		}
	}
}
