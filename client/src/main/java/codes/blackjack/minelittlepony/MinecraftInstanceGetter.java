package codes.blackjack.minelittlepony;

import codes.blackjack.minelittlepony.mixin.MixinExtMinecraft;
import net.minecraft.client.Minecraft;

import java.lang.reflect.Field;

public final class MinecraftInstanceGetter {
   private static Minecraft instance = null;

   public static Minecraft getMinecraftInstance() {
	   return MixinExtMinecraft.getMinecraft();
   }

   public static Object getPrivateValue(Class class1, Object obj, String s) throws IllegalArgumentException, SecurityException, NoSuchFieldException {
      try {
         Field field = class1.getDeclaredField(s);
         field.setAccessible(true);
         return field.get(obj);
      } catch (IllegalAccessException illegalaccessexception) {
         throw new RuntimeException(illegalaccessexception);
      }
   }
}
