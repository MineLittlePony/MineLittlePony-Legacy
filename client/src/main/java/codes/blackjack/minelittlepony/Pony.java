package codes.blackjack.minelittlepony;

import codes.blackjack.minelittlepony.config.PonyConfig;
import codes.blackjack.minelittlepony.config.PonyLevel;
import codes.blackjack.minelittlepony.config.PonySettings;
import codes.blackjack.minelittlepony.config.PonySizes;
import codes.blackjack.minelittlepony.mixin.MixinExtTextureManager;
import codes.blackjack.minelittlepony.render.PlayerModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.living.player.LocalPlayerEntity;
import net.minecraft.client.render.texture.HttpTexture;
import net.minecraft.client.render.texture.TextureManager;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Pony {
	private static final List<String> backgroundPonies = new ArrayList<>();
	private static boolean hasInit;
	private static final Map<String, Pony> registry = new HashMap<>();
	private static Map<String, HttpTexture> urlToImageDataMap;
	private static boolean renderEngineInit;

	private final boolean isSpPlayer;
	public boolean advancedTexturing;
	public String texture;
	public boolean backgroundIsPegasus;
	public boolean backgroundIsUnicorn;
	public int backgroundWantTail;
	public boolean backgroundIsMale;
	public Size backgroundSize;
	public boolean backgroundAdvancedTexturing;
	private boolean textureSetup;
	public String skinUrl;
	public boolean isPony;
	public boolean isPonySkin;
	public boolean isPegasus;
	public boolean isUnicorn;
	public boolean isFlying;
	private boolean isGlow;
	private int glowColor;
	public boolean isMale;
	public Size size = Size.MARE;
	public int wantTail;
	private float defaultYOffset;
	private boolean pegasusFlying;
	private float previousFallDistance;

	public static Pony getPonyFromRegistry(PlayerEntity player, TextureManager renderengine) {
		HttpTexture httpTexture;
		String username = player.name;
		String location = "http://skins.minecraft.net/MinecraftSkins/" + username + ".png";
		if (!renderEngineInit) {
			urlToImageDataMap = ((MixinExtTextureManager) renderengine).getHttpTextures();
			renderEngineInit = true;
		}

		init();
		Pony myLittlePony;
		if (!registry.containsKey(username)) {
			myLittlePony = new Pony(player);
			registry.put(username, myLittlePony);
		} else {
			myLittlePony = registry.get(username);
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
				myLittlePony.isPony = true;
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

	private Pony(PlayerEntity player) {
		init();
		this.texture = "/mob/char.png";
		this.skinUrl = null;
		String username = player.name;
		if (username != null) {
			this.skinUrl = "http://s3.amazonaws.com/MinecraftSkins/" + username + ".png";
		}

		this.isSpPlayer = player instanceof LocalPlayerEntity;
		this.isPony = false;
		this.isPonySkin = false;
		this.isPegasus = false;
		this.isUnicorn = false;
		this.isMale = false;
		this.wantTail = 0;
		this.advancedTexturing = false;
		this.isFlying = false;
		this.isGlow = false;
		this.pegasusFlying = false;
		this.defaultYOffset = 1.62F;
		if (PonySettings.getPonyLevel() == PonyLevel.NO_PONIES) {
			this.textureSetup = true;
		} else if (PonySettings.getPonyLevel() == PonyLevel.SOME_PONIES) {
			this.textureSetup = false;
		} else {
			if (PonySettings.getPonyLevel() == PonyLevel.ALL_PONIES) {
				this.textureSetup = false;
				if (this.isSpPlayer) {
					System.out.println("[Mine Little Pony] Temporarily reset skin to the default single player skin charpony.png");
					this.texture = "/mob/charpony.png";
				} else {
					System.out.println("[Mine Little Pony] Temporarily reset skin to a background pony");
					int backgroundNumber = username.hashCode() % backgroundPonies.size();
					if (backgroundNumber < 0) {
						backgroundNumber += backgroundPonies.size();
					}

					this.texture = backgroundPonies.get(backgroundNumber);
					System.out.println("[Mine Little Pony] " + username + " gets skin " + backgroundNumber);
				}

				this.backgroundIsPegasus = false;
				this.backgroundIsUnicorn = false;

				try {
					BufferedImage bufferedimage = ImageIO.read(Minecraft.class.getResource(this.texture));
					this.checkBuiltinTexture(bufferedimage);
				} catch (Exception var9) {
					this.texture = "/mob/charpony.png";
					System.out.println("[Mine Little Pony] Failed to read a background pony texture from a file, resetting to default charpony.png");

					try {
						BufferedImage var11 = ImageIO.read(Minecraft.class.getResource(this.texture));
						this.checkBuiltinTexture(var11);
					} catch (Exception var8) {
						this.texture = "/mob/char.png";
						System.out.println("[Mine Little Pony] Failed to read charpony.png, resetting to default char.png");

						try {
							BufferedImage var10 = ImageIO.read(Minecraft.class.getResource(this.texture));
							this.checkBuiltinTexture(var10);
						} catch (Exception var7) {
							System.out.println("[Mine Little Pony] Failed to read char.png, I just don't know what went wrong.");
						}
					}
				}

				this.skinUrl = null;
			}

		}
	}


	/**
	 * checkSkin looks at the given skin BufferedImage, and check the special hidden pixel
	 * "flags" to set various attributes of the player's pony model.
	 *
	 * @param image The image to check.
	 */
	public void checkSkin(BufferedImage image) {
		this.isPony = false;
		this.isPonySkin = false;
		this.isPegasus = false;
		this.isUnicorn = false;
		this.isMale = false;
		this.wantTail = 0;

		Color speciesFlagColor = new Color(image.getRGB(0, 0), true);
		Color applejack = new Color(249, 177, 49, 255);
		Color dashie = new Color(136, 202, 240, 255);
		Color twilight = new Color(209, 159, 228, 255);
		Color celestia = new Color(254, 249, 252, 255);

		if (speciesFlagColor.equals(applejack)) {
			this.isPony = true;
			this.isPonySkin = true;
		}

		if (speciesFlagColor.equals(dashie)) {
			this.isPony = true;
			this.isPonySkin = true;
			this.isPegasus = true;
		}

		if (speciesFlagColor.equals(twilight)) {
			this.isPony = true;
			this.isPonySkin = true;
			this.isUnicorn = true;
		}

		if (speciesFlagColor.equals(celestia)) {
			this.isPony = true;
			this.isPonySkin = true;
			this.isPegasus = true;
			this.isUnicorn = true;
		}

		Color tailFlagColor = new Color(image.getRGB(1, 0), true);
		Color tailColor4 = new Color(66, 88, 68, 255);
		Color tailColor3 = new Color(70, 142, 136, 255);
		Color tailColor2 = new Color(83, 75, 118, 255);
		Color tailColor1 = new Color(138, 107, 127, 255);

		if (tailFlagColor.equals(tailColor4)) {
			this.wantTail = 4;
		} else if (tailFlagColor.equals(tailColor3)) {
			this.wantTail = 3;
		} else if (tailFlagColor.equals(tailColor2)) {
			this.wantTail = 2;
		} else if (tailFlagColor.equals(tailColor1)) {
			this.wantTail = 1;
		} else {
			this.wantTail = 0;
		}

		Color genderFlagColor = new Color(image.getRGB(2, 0), true);
		Color maleColor = new Color(255, 255, 255, 255);
		this.isMale = genderFlagColor.equals(maleColor);

		Color sizeFlagColor = new Color(image.getRGB(3, 0), true);
		Color scootaloo = new Color(255, 190, 83);
		Color bigmac = new Color(206, 50, 84);
		Color luna = new Color(42, 60, 120);
		this.size = Size.MARE;
		if (PonySettings.getUseSizes() == PonySizes.ALL_SIZES) {
			if (sizeFlagColor.equals(scootaloo)) {
				this.size = Size.FILLY;
			} else if (sizeFlagColor.equals(bigmac)) {
				this.size = Size.STALLION;
			} else if (sizeFlagColor.equals(luna)) {
				this.size = Size.ALICORN;
			}
		}

		Color black = new Color(0, 0, 0);
		Color advcutiecolor = new Color(image.getRGB(4, 0), true);
		if (advcutiecolor.getAlpha() == 0) {
			this.advancedTexturing = false;
		} else {
			this.advancedTexturing = false;

			for (int x = 4; x < 8; ++x) {
				for (int y = 0; y < 8; ++y) {
					Color aColor = new Color(image.getRGB(x, y), true);
					if (!aColor.equals(black)) {
						this.advancedTexturing = true;
					}
				}
			}
		}

		Color glowFlagColor = new Color(image.getRGB(0, 1), true);
		if (!glowFlagColor.equals(black) && glowFlagColor.getAlpha() != 0) {
			this.glowColor = glowFlagColor.getRGB();
		} else {
			this.glowColor = -12303190;
		}

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

	public static void init() {
		if (!hasInit) {
			System.out.println("[Mine Little Pony] Player Model API for Mine Little Pony (beta) Initializing...");
			PMAPI.addToGUI(PMAPI.human);
			PMAPI.addToGUI(PMAPI.newPony);
			PMAPI.addToGUI(PMAPI.newPonyAdv);
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

			System.out.println("[Mine Little Pony] Detected " + backgroundPonies.size() + " of " + 127 + " background ponies installed.");
			hasInit = true;
			System.out.println("[Mine Little Pony] Done initializing.");
		}

	}

	public enum Size {
		FILLY,
		MARE,
		STALLION,
		ALICORN
	}
}
