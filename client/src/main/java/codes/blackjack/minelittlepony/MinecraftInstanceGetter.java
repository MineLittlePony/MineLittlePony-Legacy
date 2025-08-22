package codes.blackjack.minelittlepony;

import java.lang.reflect.Field;
import net.minecraft.client.Minecraft;

public final class MinecraftInstanceGetter {
   private static Minecraft instance = null;

   public static Minecraft getMinecraftInstance() {
      if (instance == null) {
         try {
            ThreadGroup threadgroup = Thread.currentThread().getThreadGroup();
            int i = threadgroup.activeCount();
            Thread[] athread = new Thread[i];
            threadgroup.enumerate(athread);

            for(int j = 0; j < athread.length; ++j) {
               System.out.println(athread[j].getName());
            }

            for(int k = 0; k < athread.length; ++k) {
               if (athread[k].getName().equals("Minecraft main thread")) {
                  instance = (Minecraft)getPrivateValue(Thread.class, athread[k], "target");
                  break;
               }
            }
         } catch (SecurityException securityexception) {
            throw new RuntimeException(securityexception);
         } catch (NoSuchFieldException nosuchfieldexception) {
            throw new RuntimeException(nosuchfieldexception);
         }
      }

      return instance;
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
