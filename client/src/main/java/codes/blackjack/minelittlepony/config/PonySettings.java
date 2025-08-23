package codes.blackjack.minelittlepony.config;

import codes.blackjack.minelittlepony.MineLPEntry;

public final class PonySettings {
	private static PonyLevel ponyLevel = PonyLevel.ALL_PONIES;
	private static PonySizes useSizes = PonySizes.ALL_SIZES;
	private static boolean ponyArmor = true;
	private static boolean showSnuzzles = true;

	private PonySettings() {
	}

	public static PonyLevel getPonyLevel() {
		return ponyLevel;
	}

	public static PonySizes getUseSizes() {
		return useSizes;
	}

	public static boolean isPonyArmor() {
		return ponyArmor;
	}

	public static boolean isShowSnuzzles() {
		return showSnuzzles;
	}

	public static void load(PonyConfig config) {
		try {
			int readInt = config.getIntProperty("ponylevel");
			if (readInt < 3 && readInt >= 0) {
				ponyLevel = PonyLevel.values()[readInt];
				MineLPEntry.LOGGER.info("Read settings and set pony level to {}", ponyLevel);
			} else {
				ponyLevel = PonyLevel.ALL_PONIES;
				MineLPEntry.LOGGER.info("Invalid settings file detected, falling back to making everyone ponies by default.");
			}

			readInt = config.getIntProperty("sizes");
			if (readInt < 2 && readInt > -1) {
				useSizes = PonySizes.values()[readInt];
				if (useSizes == PonySizes.ONE_SIZE) {
					MineLPEntry.LOGGER.info("Preventing different sized ponies from being displayed.");
				} else {
					MineLPEntry.LOGGER.info("Using all sizes of pony.");
				}
			} else {
				useSizes = PonySizes.ALL_SIZES;
				MineLPEntry.LOGGER.info("Invalid settings file detected, falling back to using all sizes of ponies.");
			}

			readInt = config.getIntProperty("ponyarmor");
			if (readInt < 2 && readInt > -1) {
				ponyArmor = readInt == 1;
				if (!ponyArmor) {
					MineLPEntry.LOGGER.info("Disabling pony armor.");
				} else {
					MineLPEntry.LOGGER.info("Pony armor enabled.");
				}
			} else {
				ponyArmor = true;
				MineLPEntry.LOGGER.info("Invalid settings file detected, falling back to using pony armor.");
			}

			readInt = config.getIntProperty("snuzzles");
			if (readInt < 2 && readInt > -1) {
				showSnuzzles = readInt == 1;
				if (!showSnuzzles) {
					MineLPEntry.LOGGER.info("Disabling snuzzles. You are a bad pony.");
				} else {
					MineLPEntry.LOGGER.info("Snuzzles enabled.");
				}
			} else {
				showSnuzzles = true;
				MineLPEntry.LOGGER.info("Invalid settings file detected, falling back to showing snuzzles.");
			}
		} catch (Exception var1) {
			MineLPEntry.LOGGER.info("Could not read pony settings file, falling back to making everyone ponies by default and allowing all sizes of ponies.");
			ponyLevel = PonyLevel.ALL_PONIES;
			useSizes = PonySizes.ALL_SIZES;
		}
	}
}
