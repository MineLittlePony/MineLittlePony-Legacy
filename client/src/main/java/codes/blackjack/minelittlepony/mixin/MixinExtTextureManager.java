package codes.blackjack.minelittlepony.mixin;

import net.minecraft.client.render.texture.HttpTexture;
import net.minecraft.client.render.texture.TextureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(TextureManager.class)
public interface MixinExtTextureManager {
	@Accessor("httpTextures")
	Map<String, HttpTexture> getHttpTextures();
}
