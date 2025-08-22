package codes.blackjack.minelittlepony;

import net.minecraft.client.render.entity.PlayerEntityRenderer;

import java.lang.reflect.Field;

public class MineLPReflection {
	public static final Class classRenderPlayer = PlayerEntityRenderer.class;
	public static final Field armorFilenamePrefix;

	public static String[] getArmorFilenamePrefix() {
		try {
			return (String[]) armorFilenamePrefix.get((Object) null);
		} catch (IllegalArgumentException var1) {
		} catch (IllegalAccessException var2) {
		}

		log("Error in obtaining the armorFilenamePrefix");
		return null;
	}

	public static boolean setArmorFilenamePrefix(String[] value) {
		try {
			armorFilenamePrefix.set((Object) null, value);
			return true;
		} catch (Exception var2) {
			log("Error in setting armorFilenamePrefix");
			return false;
		}
	}

	private static Field getHackedArmorFilenamePrefixField() {
		try {
			Field armorField = classRenderPlayer.getDeclaredFields()[3];
			armorField.setAccessible(true);
			Field modField = Field.class.getDeclaredField("modifiers");
			modField.setAccessible(true);
			modField.setInt(armorField, armorField.getModifiers() & -17);
			return armorField;
		} catch (Exception var2) {
			log("Failed to reflect armorFilenamePrefix");
			return null;
		}
	}

	public static void log(String msg) {
		System.out.println("[Mine Little Pony] " + msg);
	}

	static {
		Field reflectedField = getHackedArmorFilenamePrefixField();
		if (reflectedField == null) {
			reflectedField = classRenderPlayer.getDeclaredFields()[3];
			reflectedField.setAccessible(true);
		}

		armorFilenamePrefix = reflectedField;
	}
}
