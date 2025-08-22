package codes.blackjack.minelittlepony.render;

import java.text.DecimalFormat;

public class PlayerModel {
	public String name;
	public ModelPlayer model;
	public ModelArmor armor;
	private float width = 0.6F;
	private float height = 1.8F;
	public float shadowsize = 0.5F;
	private float thirdpersondistance = 4.0F;
	private float yoffset = 1.62F;
	public float globalscale = 1.0F;

	public PlayerModel(String name, ModelPlayer model) {
		this.name = name;
		this.model = model;
	}

	public PlayerModel setArmor(ModelArmor armor) {
		this.armor = armor;
		return this;
	}

	public PlayerModel setURL(String url) {
		return this;
	}

	public PlayerModel setShadow(float size) {
		this.shadowsize = size;
		return this;
	}

	public PlayerModel setSize(float width, float height) {
		this.width = width;
		this.height = height;
		return this;
	}

	public PlayerModel setOffset(float offset) {
		this.yoffset = offset;
		return this;
	}

	public PlayerModel setDistance(float distance) {
		this.thirdpersondistance = distance;
		return this;
	}

	public PlayerModel setScale(float scale) {
		this.globalscale = scale;
		return this;
	}

	public boolean hasArmor() {
		return this.armor != null && this.armor.base != null && this.armor.path != null;
	}

	public String getSize(DecimalFormat df) {
		return df.format(this.width) + " * " + df.format(this.height) + " * " + df.format(this.width);
	}
}
