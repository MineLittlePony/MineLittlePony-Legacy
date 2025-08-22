package codes.blackjack.minelittlepony;

import codes.blackjack.minelittlepony.mixin.MixinExtEntityRenderDispatcher;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.entity.living.player.PlayerEntity;

public class MineLPEntry implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		System.out.println("Initializing MinelittlePony");
		((MixinExtEntityRenderDispatcher) EntityRenderDispatcher.INSTANCE)
			.getRenderers().put(PlayerEntity.class, new RenderPony());
	}
}
