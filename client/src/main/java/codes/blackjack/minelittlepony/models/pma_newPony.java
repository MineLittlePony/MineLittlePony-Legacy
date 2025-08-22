package codes.blackjack.minelittlepony.models;

import codes.blackjack.minelittlepony.render.ModelArmor;
import net.minecraft.client.render.model.ModelPart;

public class pma_newPony extends ModelArmor {
	public pma_newPony(String path) {
		super(path);
		this.modelArmorChestplate = new pm_newPonyArmor(path);
		this.modelArmor = new pm_newPonyArmor(path);
	}

	public int subimage() {
		return slot == 2 ? 2 : 1;
	}

	public void boxes(boolean ponyArmor) {
		this.base = slot != 2 ? this.modelArmorChestplate : this.modelArmor;
		((pm_newPonyAdv) this.base).head.visible = slot == 0;
		((pm_newPonyAdv) this.base).rightarm.visible = ((pm_newPonyAdv) this.base).LeftArm.visible = slot == 2 || slot == 3;
		if (!ponyArmor) {
			((pm_newPonyAdv) this.base).Body.visible = slot == 1 || slot == 2;
			((pm_newPonyArmor) this.base).Bodypiece.visible = slot == 1 || slot == 2;
			((pm_newPonyAdv) this.base).RightLeg.visible = ((pm_newPonyAdv) this.base).LeftLeg.visible = slot == 2 || slot == 3;

			for (ModelPart part : ((pm_newPonyArmor) this.base).extHead) {
				part.visible = false;
			}

			((pm_newPonyArmor) this.base).extBody.visible = false;

			for (ModelPart part : ((pm_newPonyArmor) this.base).extLegs) {
				part.visible = false;
			}
		} else {
			((pm_newPonyAdv) this.base).Body.visible = false;
			((pm_newPonyArmor) this.base).Bodypiece.visible = false;
			((pm_newPonyAdv) this.base).RightLeg.visible = ((pm_newPonyAdv) this.base).LeftLeg.visible = false;

			for (ModelPart part : ((pm_newPonyArmor) this.base).extHead) {
				part.visible = slot == 0;
			}

			((pm_newPonyArmor) this.base).extBody.visible = slot == 1 || slot == 2;

			for (ModelPart part : ((pm_newPonyArmor) this.base).extLegs) {
				part.visible = slot == 2 || slot == 3;
			}
		}

	}
}
