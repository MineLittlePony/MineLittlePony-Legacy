package codes.blackjack.minelittlepony;

import java.util.ArrayList;
import java.util.List;

public final class PMAPI {
	public static PlayerModel newPony = (new PlayerModel("newPony", new pm_newPony("/mob/char.png"), 0)).setArmor(new pma_newPony("/armor/")).setURL("http://skins.minecraft.net/MinecraftSkins/%NAME%.png").setScale(0.9375F);
	public static PlayerModel newPonyAdv = (new PlayerModel("newPonyAdv", new pm_newPonyAdv("/mob/char.png"), 0)).setArmor(new pma_newPony("/armor/")).setURL("http://skins.minecraft.net/MinecraftSkins/%NAME%.png").setScale(0.9375F);
	public static PlayerModel human = (new PlayerModel("Human", new pm_Human("/mob/char.png"), 1)).setArmor(new pma_Human("/armor/")).setURL("http://skins.minecraft.net/MinecraftSkins/%NAME%.png").setScale(0.9375F);
	private static List pmlist = new ArrayList();

	public static void addToGUI(PlayerModel pm) {
		System.out.println("[Mine Little Pony] \"" + pm.name + "\" added");
		pmlist.add(pm);
	}
}
