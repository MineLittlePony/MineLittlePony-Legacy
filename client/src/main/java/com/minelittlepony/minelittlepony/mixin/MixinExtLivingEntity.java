package com.minelittlepony.minelittlepony.mixin;

import net.minecraft.entity.living.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LivingEntity.class)
public interface MixinExtLivingEntity {
	@Accessor("jumping")
	boolean isJumping();

	@Accessor("texture")
	void setTexture(String texture);
}
