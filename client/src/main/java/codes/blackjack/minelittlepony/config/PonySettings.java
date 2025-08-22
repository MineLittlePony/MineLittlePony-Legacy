package codes.blackjack.minelittlepony.config;

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
				System.out.println("[Mine Little Pony] Read settings and set pony level to " + ponyLevel);
			} else {
				ponyLevel = PonyLevel.ALL_PONIES;
				System.out.println("[Mine Little Pony] Invalid settings file detected, falling back to making everyone ponies by default.");
			}

			readInt = config.getIntProperty("sizes");
			if (readInt < 2 && readInt > -1) {
				useSizes = PonySizes.values()[readInt];
				if (useSizes == PonySizes.ONE_SIZE) {
					System.out.println("[Mine Little Pony] Preventing different sized ponies from being displayed.");
				} else {
					System.out.println("[Mine Little Pony] Using all sizes of pony.");
				}
			} else {
				useSizes = PonySizes.ALL_SIZES;
				System.out.println("[Mine Little Pony] Invalid settings file detected, falling back to using all sizes of ponies.");
			}

			readInt = config.getIntProperty("ponyarmor");
			if (readInt < 2 && readInt > -1) {
				ponyArmor = readInt == 1;
				if (!ponyArmor) {
					System.out.println("[Mine Little Pony] Disabling pony armor.");
				} else {
					System.out.println("[Mine Little Pony] Pony armor enabled.");
				}
			} else {
				ponyArmor = true;
				System.out.println("[Mine Little Pony] Invalid settings file detected, falling back to using pony armor.");
			}

			readInt = config.getIntProperty("snuzzles");
			if (readInt < 2 && readInt > -1) {
				showSnuzzles = readInt == 1;
				if (!showSnuzzles) {
					System.out.println("[Mine Little Pony] Disabling snuzzles. You are a bad pony.");
				} else {
					System.out.println("[Mine Little Pony] Snuzzles enabled.");
				}
			} else {
				showSnuzzles = true;
				System.out.println("[Mine Little Pony] Invalid settings file detected, falling back to showing snuzzles.");
			}
		} catch (Exception var1) {
			System.out.println("[Mine Little Pony] Could not read pony settings file, falling back to making everyone ponies by default and allowing all sizes of ponies.");
			ponyLevel = PonyLevel.ALL_PONIES;
			useSizes = PonySizes.ALL_SIZES;
		}
	}
}
