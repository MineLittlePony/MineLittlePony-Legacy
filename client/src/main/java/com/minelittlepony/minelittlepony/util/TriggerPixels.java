package com.minelittlepony.minelittlepony.util;

import com.minelittlepony.minelittlepony.Pony;
import com.minelittlepony.minelittlepony.config.PonySettings;
import com.minelittlepony.minelittlepony.config.PonySizes;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class TriggerPixels {
	public final boolean isPonySkin;
	public final boolean isPegasus;
	public final boolean isUnicorn;
	public final boolean isMale;
	public final Pony.Size size;
	public final int wantTail;
	public final boolean advancedTexturing;
	public final int glowColor;

	private TriggerPixels(
		boolean isPonySkin,
		boolean isPegasus,
		boolean isUnicorn,
		boolean isMale,
		Pony.Size size,
		int wantTail,
		boolean advancedTexturing,
		int glowColor
	) {
		this.isPonySkin = isPonySkin;
		this.isPegasus = isPegasus;
		this.isUnicorn = isUnicorn;
		this.isMale = isMale;
		this.size = size;
		this.wantTail = wantTail;
		this.advancedTexturing = advancedTexturing;
		this.glowColor = glowColor;
	}

	public static TriggerPixels fromImage(BufferedImage image) {
		boolean isPonySkin = false;
		boolean isPegasus = false;
		boolean isUnicorn = false;
		Pony.Size size = Pony.Size.MARE;
		boolean isMale;
		int wantTail;
		boolean advancedTexturing;
		int glowColor;

		Color speciesFlagColor = new Color(image.getRGB(0, 0), true);
		Color applejack = new Color(249, 177, 49, 255);
		Color dashie = new Color(136, 202, 240, 255);
		Color twilight = new Color(209, 159, 228, 255);
		Color celestia = new Color(254, 249, 252, 255);

		if (speciesFlagColor.equals(applejack)) {
			isPonySkin = true;
		}

		if (speciesFlagColor.equals(dashie)) {
			isPonySkin = true;
			isPegasus = true;
		}

		if (speciesFlagColor.equals(twilight)) {
			isPonySkin = true;
			isUnicorn = true;
		}

		if (speciesFlagColor.equals(celestia)) {
			isPonySkin = true;
			isPegasus = true;
			isUnicorn = true;
		}

		Color tailFlagColor = new Color(image.getRGB(1, 0), true);
		Color tailColor4 = new Color(66, 88, 68, 255);
		Color tailColor3 = new Color(70, 142, 136, 255);
		Color tailColor2 = new Color(83, 75, 118, 255);
		Color tailColor1 = new Color(138, 107, 127, 255);

		if (tailFlagColor.equals(tailColor4)) {
			wantTail = 4;
		} else if (tailFlagColor.equals(tailColor3)) {
			wantTail = 3;
		} else if (tailFlagColor.equals(tailColor2)) {
			wantTail = 2;
		} else if (tailFlagColor.equals(tailColor1)) {
			wantTail = 1;
		} else {
			wantTail = 0;
		}

		Color genderFlagColor = new Color(image.getRGB(2, 0), true);
		Color maleColor = new Color(255, 255, 255, 255);
		isMale = genderFlagColor.equals(maleColor);

		Color sizeFlagColor = new Color(image.getRGB(3, 0), true);
		Color scootaloo = new Color(255, 190, 83);
		Color bigmac = new Color(206, 50, 84);
		Color luna = new Color(42, 60, 120);

		if (PonySettings.getUseSizes() == PonySizes.ALL_SIZES) {
			if (sizeFlagColor.equals(scootaloo)) {
				size = Pony.Size.FILLY;
			} else if (sizeFlagColor.equals(bigmac)) {
				size = Pony.Size.STALLION;
			} else if (sizeFlagColor.equals(luna)) {
				size = Pony.Size.ALICORN;
			}
		}

		Color black = new Color(0, 0, 0);
		Color advcutiecolor = new Color(image.getRGB(4, 0), true);

		if (advcutiecolor.getAlpha() == 0) {
			advancedTexturing = false;
		} else {
			advancedTexturing = false;

			for (int x = 4; x < 8; ++x) {
				for (int y = 0; y < 8; ++y) {
					Color aColor = new Color(image.getRGB(x, y), true);
					if (!aColor.equals(black)) {
						advancedTexturing = true;
					}
				}
			}
		}

		Color glowFlagColor = new Color(image.getRGB(0, 1), true);
		if (!glowFlagColor.equals(black) && glowFlagColor.getAlpha() != 0) {
			glowColor = glowFlagColor.getRGB();
		} else {
			glowColor = 0xff4444aa;
		}

		return new TriggerPixels(
			isPonySkin, isPegasus, isUnicorn, isMale,
			size, wantTail, advancedTexturing, glowColor
		);
	}
}
