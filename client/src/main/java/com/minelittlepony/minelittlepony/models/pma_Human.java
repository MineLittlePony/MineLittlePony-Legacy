package com.minelittlepony.minelittlepony.models;

import com.minelittlepony.minelittlepony.render.ModelArmor;

public class pma_Human extends ModelArmor {
	public pma_Human(String path) {
		super(path);
		this.modelArmorChestplate = new pm_Human();
		this.modelArmor = new pm_Human();
	}

	public int subimage() {
		return slot == 2 ? 2 : 1;
	}

	public void boxes(boolean ignoreMe) {
		this.base = slot != 2 ? this.modelArmorChestplate : this.modelArmor;
		((pm_Human) this.base).head.visible = slot == 0;
		((pm_Human) this.base).helmet.visible = slot == 0;
		((pm_Human) this.base).body.visible = slot == 1 || slot == 2;
		((pm_Human) this.base).rightArm.visible = ((pm_Human) this.base).leftArm.visible = slot == 1;
		((pm_Human) this.base).rightLeg.visible = ((pm_Human) this.base).leftLeg.visible = slot == 2 || slot == 3;
	}
}
