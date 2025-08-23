package codes.blackjack.minelittlepony.mixin;

import codes.blackjack.minelittlepony.util.SkinFetcher;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

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
			System.out.println("Overriding skin URL: " + rawUrl);
			rawUrl = rawUrl.substring(oldSkinServer.length());
			String username = rawUrl.substring(0, rawUrl.length() - 4);

			try {
				String skinUrl = SkinFetcher.getSkinUrl(username);
				System.out.println("Skin URL: " + skinUrl);
				args.set(0, skinUrl);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
