package com.minelittlepony.minelittlepony;

import com.minelittlepony.minelittlepony.config.PonyConfig;
import com.minelittlepony.minelittlepony.config.PonyLevel;
import com.minelittlepony.minelittlepony.config.PonySettings;
import com.minelittlepony.minelittlepony.mixin.MixinExtTextureManager;
import com.minelittlepony.minelittlepony.render.PlayerModel;
import com.minelittlepony.minelittlepony.util.ResourceUtil;
import com.minelittlepony.minelittlepony.util.TriggerPixels;
import net.minecraft.client.entity.living.player.InputPlayerEntity;
import net.minecraft.client.render.texture.HttpTexture;
import net.minecraft.client.render.texture.SkinImageProcessor;
import net.minecraft.client.render.texture.TextureManager;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Pony {
	private static final List<String> backgroundPonies = new ArrayList<>();
	private static final Map<String, Pony> registry = new HashMap<>();
	private static boolean hasInit;

	private final boolean isSpPlayer;
	private final float defaultYOffset;

	public String texture;
	public String skinUrl;
	public boolean isFlying;
	public Size size = Size.MARE;
	private boolean advancedTexturing;
	private boolean backgroundIsPegasus;
	private boolean backgroundIsUnicorn;
	private int backgroundWantTail;
	private boolean backgroundIsMale;
	private Size backgroundSize;
	private boolean backgroundAdvancedTexturing;
	private boolean isPonySkin;
	private boolean isPegasus;
	private boolean isUnicorn;
	private boolean isMale;
	private int wantTail;
	private boolean textureSetup;
	private int glowColor;
	private boolean pegasusFlying;
	private float previousFallDistance;

	private Pony(PlayerEntity player) {
		init();
		this.texture = "/mob/char.png";
		this.skinUrl = null;
		String username = player.name;
		if (username != null) {
			this.skinUrl = "http://s3.amazonaws.com/MinecraftSkins/" + username + ".png";
		}

		this.isSpPlayer = player instanceof InputPlayerEntity;
		this.isPonySkin = false;
		this.isPegasus = false;
		this.isUnicorn = false;
		this.isMale = false;
		this.wantTail = 0;
		this.advancedTexturing = false;
		this.isFlying = false;
		this.pegasusFlying = false;
		this.defaultYOffset = 1.62F;

		if (PonySettings.getPonyLevel() == PonyLevel.NO_PONIES) {
			this.textureSetup = true;
		} else if (PonySettings.getPonyLevel() == PonyLevel.SOME_PONIES) {
			this.textureSetup = false;
		} else if (PonySettings.getPonyLevel() == PonyLevel.ALL_PONIES) {
			this.textureSetup = false;
			if (this.isSpPlayer) {
				MineLPEntry.LOGGER.info("Temporarily reset your skin to the default single-player pony skin");
				this.texture = "/mob/charpony.png";
			} else {
				MineLPEntry.LOGGER.info("Temporarily reset your skin to a background pony");
				int backgroundNumber = username.hashCode() % backgroundPonies.size();
				if (backgroundNumber < 0) {
					backgroundNumber += backgroundPonies.size();
				}

				this.texture = backgroundPonies.get(backgroundNumber);
				MineLPEntry.LOGGER.info("{} gets skin {}", username, backgroundNumber);
			}

			this.backgroundIsPegasus = false;
			this.backgroundIsUnicorn = false;

			String[] texturesToTry = {
				this.texture,
				"/mob/charpony.png",
				"/mob/char.png"
			};

			for (String resourceName : texturesToTry) {
				try {
					this.texture = resourceName;
					this.checkBuiltinTexture(
						ResourceUtil.readImage(this.texture)
					);
				} catch (IOException e) {
					MineLPEntry.LOGGER.warn("Failed to read skin texture {}, trying another one...", resourceName);
				}
			}

			this.skinUrl = null;
		}
	}

	public static Pony getPonyFromRegistry(PlayerEntity player, TextureManager textureManager) {
		Map<String, HttpTexture> urlToImageDataMap = ((MixinExtTextureManager) textureManager).getHttpTextures();

		HttpTexture httpTexture;
		String username = player.name;
		String location = "http://skins.minecraft.net/MinecraftSkins/" + username + ".png";

		init();
		Pony myLittlePony;
		if (!registry.containsKey(username)) {
			myLittlePony = new Pony(player);
			registry.put(username, myLittlePony);
		} else {
			myLittlePony = registry.get(username);
		}

		// This will override the player's skin image in single player.
		if (player.skin == null) {
			player.skin = location;
			textureManager.getHttpTexture(player.skin, new SkinImageProcessor());
		}

		httpTexture = urlToImageDataMap.get(location);
		if (PonySettings.getPonyLevel() != PonyLevel.NO_PONIES && myLittlePony.textureSetup && (httpTexture == null || httpTexture.image == null)) {
			registry.remove(username);
			myLittlePony = new Pony(player);
			registry.put(username, myLittlePony);
		}

		if (!myLittlePony.textureSetup && httpTexture != null && httpTexture.image != null) {
			myLittlePony.checkSkin(httpTexture.image);
			if (!myLittlePony.isPonySkin) {
				myLittlePony.isPegasus = myLittlePony.backgroundIsPegasus;
				myLittlePony.isUnicorn = myLittlePony.backgroundIsUnicorn;
				myLittlePony.wantTail = myLittlePony.backgroundWantTail;
				myLittlePony.isMale = myLittlePony.backgroundIsMale;
				myLittlePony.size = myLittlePony.backgroundSize;
				myLittlePony.advancedTexturing = myLittlePony.backgroundAdvancedTexturing;
			} else {
				myLittlePony.skinUrl = location;
			}

			myLittlePony.textureSetup = true;
		}

		return myLittlePony;
	}

	public static void init() {
		if (!hasInit) {
			MineLPEntry.LOGGER.info("Player Model API for Mine Little Pony (beta) Initializing...");
			PMAPI.newPony.model.init();
			PMAPI.newPony.armor.modelArmorChestplate.init(0.0F, 1.0F);
			PMAPI.newPony.armor.modelArmor.init(0.0F, 0.5F);
			PMAPI.newPonyAdv.model.init();
			PMAPI.newPonyAdv.armor.modelArmorChestplate.init(0.0F, 1.0F);
			PMAPI.newPonyAdv.armor.modelArmor.init(0.0F, 0.5F);
			PMAPI.human.model.init();
			PMAPI.human.armor.modelArmorChestplate.init(0.0F, 1.0F);
			PMAPI.human.armor.modelArmor.init(0.0F, 0.5F);
			PonySettings.load(new PonyConfig());

			for (int check = 0; check < 127; ++check) {
				String checkTexture = "/mob/bpony_" + check + ".png";
				if (Pony.class.getResource(checkTexture) != null) {
					backgroundPonies.add(checkTexture);
				}
			}

			MineLPEntry.LOGGER.info("Detected {} of {} background ponies installed.", backgroundPonies.size(), 127);
			hasInit = true;
			MineLPEntry.LOGGER.info("Done initializing.");
		}

	}

	/**
	 * checkSkin looks at the given skin BufferedImage, and check the special hidden pixel
	 * "flags" to set various attributes of the player's pony model.
	 *
	 * @param image The image to check.
	 */
	private void checkSkin(BufferedImage image) {
		TriggerPixels triggerPixels = TriggerPixels.fromImage(image);

		this.isPonySkin = triggerPixels.isPonySkin;
		this.isPegasus = triggerPixels.isPegasus;
		this.isUnicorn = triggerPixels.isUnicorn;
		this.isMale = triggerPixels.isMale;
		this.size = triggerPixels.size;
		this.wantTail = triggerPixels.wantTail;
		this.advancedTexturing = triggerPixels.advancedTexturing;
		this.glowColor = triggerPixels.glowColor;
	}

	private void checkBuiltinTexture(BufferedImage bufferedimage) {
		this.checkSkin(bufferedimage);
		this.backgroundIsPegasus = this.isPegasus;
		this.backgroundIsUnicorn = this.isUnicorn;
		this.backgroundWantTail = this.wantTail;
		this.backgroundIsMale = this.isMale;
		this.backgroundSize = this.size;
		this.backgroundAdvancedTexturing = this.advancedTexturing;
		this.isPonySkin = false;
	}

	public boolean isUnicorn() {
		return this.isUnicorn;
	}

	public boolean isPegasus() {
		return this.isPegasus;
	}

	public int wantTail() {
		return this.wantTail;
	}

	public boolean isMale() {
		return this.isMale;
	}

	public Size size() {
		return this.size;
	}

	public int glowColor() {
		return this.glowColor;
	}

	public boolean isPegasusFlying(double posX, double posY, double posZ, float fallDistance, boolean isJumping, World equestria) {
		if (!this.isPegasus) {
			this.pegasusFlying = false;
			return false;
		}
		if (isJumping) {
			return true;
		}
		boolean falling = fallDistance > 0.0F;
		boolean levitating = fallDistance == this.previousFallDistance;
		boolean standingOnAir;
		if (falling && !levitating) {
			standingOnAir = this.standingOnAir(posX, posY, posZ, 1.5F, equestria);
		} else {
			standingOnAir = this.standingOnAir(posX, posY, posZ, 1.0F, equestria);
		}

		if (!standingOnAir) {
			this.pegasusFlying = false;
			return false;
		}
		if (this.pegasusFlying) {
			return true;
		}
		if (levitating) {
			this.pegasusFlying = true;
			return true;
		}
		this.previousFallDistance = fallDistance;
		if (fallDistance < 2.0F) {
			return false;
		}
		this.pegasusFlying = true;
		return true;
	}

	private boolean standingOnAir(double posX, double posY, double posZ, float range, World equestria) {
		boolean foundSolidBlock = false;
		int y;
		if (this.isSpPlayer) {
			y = MathHelper.floor(posY - this.defaultYOffset - 0.01F);
		} else {
			y = MathHelper.floor(posY - 0.01F);
		}

		for (float shiftX = 0.0F - range; shiftX < range * 2.0F; shiftX += range) {
			for (float shiftZ = 0.0F - range; shiftZ < range * 2.0F; shiftZ += range) {
				int x = MathHelper.floor(posX + shiftX);
				int z = MathHelper.floor(posZ + shiftZ);
				if (!equestria.isAir(x, y, z)) {
					foundSolidBlock = true;
				}
			}
		}

		return !foundSolidBlock;
	}

	public PlayerModel getModel() {
		boolean isPony = false;

		switch (PonySettings.getPonyLevel()) {
			case NO_PONIES:
				break;
			case SOME_PONIES:
				isPony = this.isPonySkin;
				break;
			case ALL_PONIES:
				isPony = true;
		}

		if (isPony) {
			return this.advancedTexturing ? PMAPI.newPonyAdv : PMAPI.newPony;
		}

		return PMAPI.human;
	}

	public enum Size {
		FILLY,
		MARE,
		STALLION,
		ALICORN
	}
}
