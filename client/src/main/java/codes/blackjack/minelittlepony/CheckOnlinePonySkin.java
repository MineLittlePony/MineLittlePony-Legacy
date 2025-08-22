package codes.blackjack.minelittlepony;

import net.minecraft.client.render.texture.HttpTexture;
import net.minecraft.client.render.texture.TextureManager;

import java.lang.reflect.Field;
import java.util.Map;

class CheckOnlinePonySkin extends Thread {
	final String location;
	final Pony pony;
	protected TextureManager renderEngine;
	public static final Class classRenderEngine = TextureManager.class;

	CheckOnlinePonySkin(Pony ponySkinIsFor, String s, TextureManager aRenderEngine) {
		this.pony = ponySkinIsFor;
		this.location = s;
		this.renderEngine = aRenderEngine;
	}

	public void run() {
		boolean skinChecked = false;
		HttpTexture threaddownloadimagedata = null;

		for (int backoff = 1; backoff <= 5; ++backoff) {
			try {
				Field urlToImageDataMapField = classRenderEngine.getDeclaredFields()[7];
				urlToImageDataMapField.setAccessible(true);
				Map urlToImageDataMap = (Map) urlToImageDataMapField.get(this.renderEngine);
				threaddownloadimagedata = (HttpTexture) urlToImageDataMap.get(this.location);
			} catch (Exception var6) {
				System.out.println("[Mine Little Pony] Failed to reflect RenderEngine (exception).");
			}

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
				sleep((long) (Math.pow((double) 2.0F, (double) backoff) * (double) 300.0F));
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
