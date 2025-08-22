package codes.blackjack.minelittlepony.render;

public class PlayerModel {
	public final String name;
	public final ModelPlayer model;
	public ModelArmor armor;
	public float shadowSize = 0.5F;
	public float globalScale = 1.0F;

	public PlayerModel(String name, ModelPlayer model) {
		this.name = name;
		this.model = model;
	}

	public PlayerModel setArmor(ModelArmor armor) {
		this.armor = armor;
		return this;
	}

	public PlayerModel setURL(String url) {
		// TODO: We might want to actually use this somewhere?
		return this;
	}

	public PlayerModel setScale(float scale) {
		this.globalScale = scale;
		return this;
	}

	public boolean hasArmor() {
		return this.armor != null && this.armor.base != null && this.armor.path != null;
	}
}
