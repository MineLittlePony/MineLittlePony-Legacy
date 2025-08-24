package com.minelittlepony.minelittlepony.mixin;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(EntityRenderDispatcher.class)
public interface MixinExtEntityRenderDispatcher {
	@Accessor("renderers")
	Map<Class<?>, EntityRenderer> getRenderers();
}
