package com.minelittlepony.minelittlepony.mixin;

import net.minecraft.client.render.entity.PlayerEntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PlayerEntityRenderer.class)
public interface MixinExtPlayerEntityRenderer {
	@Accessor("ARMOR_VARIANTS")
	static String[] getArmorVariants() {
		throw new AssertionError();
	}
}
