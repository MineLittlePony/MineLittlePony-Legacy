package com.minelittlepony.minelittlepony.mixin;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Minecraft.class)
public interface MixinExtMinecraft {
	@Accessor("INSTANCE")
	static Minecraft getMinecraft() {
		throw new AssertionError();
	}
}
