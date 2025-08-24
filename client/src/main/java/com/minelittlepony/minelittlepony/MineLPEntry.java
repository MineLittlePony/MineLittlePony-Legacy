package com.minelittlepony.minelittlepony;

import com.minelittlepony.minelittlepony.mixin.MixinExtEntityRenderDispatcher;
import com.minelittlepony.minelittlepony.render.RenderPony;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.entity.living.player.PlayerEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("unused")
public class MineLPEntry implements ClientModInitializer {
	public static final Logger LOGGER = LogManager.getLogger("MineLittlePony");

	@Override
	public void onInitializeClient() {
		LOGGER.info("Initializing MineLittlePony");
		((MixinExtEntityRenderDispatcher) EntityRenderDispatcher.INSTANCE)
			.getRenderers().put(PlayerEntity.class, new RenderPony());
	}
}
