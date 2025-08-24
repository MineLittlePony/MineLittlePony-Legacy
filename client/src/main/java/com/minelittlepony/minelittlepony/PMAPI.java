package com.minelittlepony.minelittlepony;

import com.minelittlepony.minelittlepony.models.pm_Human;
import com.minelittlepony.minelittlepony.models.pm_newPony;
import com.minelittlepony.minelittlepony.models.pm_newPonyAdv;
import com.minelittlepony.minelittlepony.models.pma_Human;
import com.minelittlepony.minelittlepony.models.pma_newPony;
import com.minelittlepony.minelittlepony.render.PlayerModel;

public final class PMAPI {
	public static final PlayerModel newPony = (new PlayerModel("newPony", new pm_newPony())).setArmor(new pma_newPony("/armor/")).setURL("http://skins.minecraft.net/MinecraftSkins/%NAME%.png").setScale(0.9375F);
	public static final PlayerModel newPonyAdv = (new PlayerModel("newPonyAdv", new pm_newPonyAdv())).setArmor(new pma_newPony("/armor/")).setURL("http://skins.minecraft.net/MinecraftSkins/%NAME%.png").setScale(0.9375F);
	public static final PlayerModel human = (new PlayerModel("Human", new pm_Human())).setArmor(new pma_Human("/armor/")).setURL("http://skins.minecraft.net/MinecraftSkins/%NAME%.png").setScale(0.9375F);

	private PMAPI() {
	}
}
