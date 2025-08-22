package codes.blackjack.minelittlepony;

import codes.blackjack.minelittlepony.mixin.MixinExtMinecraft;
import net.minecraft.client.Minecraft;

public final class MinecraftInstanceGetter {
   public static Minecraft getMinecraftInstance() {
	   return MixinExtMinecraft.getMinecraft();
   }
}
