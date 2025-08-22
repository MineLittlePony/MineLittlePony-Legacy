package codes.blackjack.minelittlepony;

import codes.blackjack.minelittlepony.mixin.MixinExtEntityRenderDispatcher;
import codes.blackjack.minelittlepony.render.RenderPony;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.entity.living.player.PlayerEntity;

@SuppressWarnings("unused")
public class MineLPEntry implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		System.out.println("Initializing MineLittlePony");
		((MixinExtEntityRenderDispatcher) EntityRenderDispatcher.INSTANCE)
			.getRenderers().put(PlayerEntity.class, new RenderPony());
	}
}
