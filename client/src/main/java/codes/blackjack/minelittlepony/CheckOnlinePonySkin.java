package codes.blackjack.minelittlepony;

import codes.blackjack.minelittlepony.mixin.MixinExtTextureManager;
import net.minecraft.client.render.texture.HttpTexture;
import net.minecraft.client.render.texture.TextureManager;

class CheckOnlinePonySkin extends Thread {
	private final String location;
	private final Pony pony;
	private final TextureManager renderEngine;

	CheckOnlinePonySkin(Pony ponySkinIsFor, String s, TextureManager aRenderEngine) {
		this.pony = ponySkinIsFor;
		this.location = s;
		this.renderEngine = aRenderEngine;
	}

	public void run() {
		boolean skinChecked = false;
		HttpTexture threaddownloadimagedata;

		for (int backoff = 1; backoff <= 5; ++backoff) {
			threaddownloadimagedata = ((MixinExtTextureManager) this.renderEngine).getHttpTextures().get(this.location);

			if (threaddownloadimagedata != null && threaddownloadimagedata.image != null) {
				this.pony.checkSkin(threaddownloadimagedata.image);
				if (!this.pony.isPonySkin) {
					this.pony.isPony = true;
					this.pony.isPegasus = this.pony.backgroundIsPegasus;
					this.pony.isUnicorn = this.pony.backgroundIsUnicorn;
					this.pony.wantTail = this.pony.backgroundWantTail;
					this.pony.isMale = this.pony.backgroundIsMale;
					this.pony.size = this.pony.backgroundSize;
					this.pony.advancedTexturing = this.pony.backgroundAdvancedTexturing;
				} else {
					this.pony.skinUrl = this.location;
				}

				skinChecked = true;
				break;
			}

			try {
				sleep((long) (Math.pow(2.0F, backoff) * 300.0F));
			} catch (InterruptedException var7) {
				Thread.currentThread().interrupt();
				break;
			}
		}

		if (!skinChecked) {
			System.out.println("[Mine Little Pony] Failed to process a player texture in a timely fashion for " + this.location + " (timed out).");
			this.pony.isPonySkin = false;
		}

	}
}
