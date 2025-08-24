package com.minelittlepony.minelittlepony.render;

public abstract class ModelArmor {
	public final String path;
	public static int slot;
	public ModelPlayer base;
	public ModelPlayer modelArmorChestplate;
	public ModelPlayer modelArmor;

	protected ModelArmor(String path) {
		this.path = path;
	}

	public abstract int subimage();

	public abstract void boxes(boolean var1);
}
