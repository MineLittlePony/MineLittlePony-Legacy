package codes.blackjack.minelittlepony;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.entity.living.LivingEntity;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class MineLPReflection {
   public static MineLPRData Modloader;
   public static MineLPRData ForgeAPI;
   public static MineLPRData SpoutCraft;
   public static final Class classRenderPlayer = PlayerEntityRenderer.class;
   public static final Field armorFilenamePrefix;

   public static String[] getArmorFilenamePrefix() {
      try {
         return (String[])armorFilenamePrefix.get((Object)null);
      } catch (IllegalArgumentException var1) {
      } catch (IllegalAccessException var2) {
      }

      log("Error in obtaining the armorFilenamePrefix");
      return null;
   }

   public static boolean setArmorFilenamePrefix(String[] value) {
      try {
         armorFilenamePrefix.set((Object)null, value);
         return true;
      } catch (Exception var2) {
         log("Error in setting armorFilenamePrefix");
         return false;
      }
   }

   private static Field getHackedArmorFilenamePrefixField() {
      try {
         Field armorField = classRenderPlayer.getDeclaredFields()[3];
         armorField.setAccessible(true);
         Field modField = Field.class.getDeclaredField("modifiers");
         modField.setAccessible(true);
         modField.setInt(armorField, armorField.getModifiers() & -17);
         return armorField;
      } catch (Exception var2) {
         log("Failed to reflect armorFilenamePrefix");
         return null;
      }
   }

   private static boolean reflectModloader(MineLPRData data) {
      log("Checking Modloader...");
      Class mL = getClass("ModLoader");
      data.installed = mL != null;
      if (mL == null) {
         return false;
      } else {
         data.putClass("Modloader", mL);
         if (Modloader.removeNullData()) {
            log("Warning: Modloader reflection returned some nulls");
         }

         return true;
      }
   }

   private static boolean reflectForgeAPI(MineLPRData data) {
      log("Checking ForgeAPI...");
      Class forgeAPIIItemRendererItemRenderType;
      Class forgeAPIIItemRendererItemRendererHelper;
      Class forgeAPIIArmorTextureProvider;
      Class forgeAPIIItemRenderer;
      Class forgeAPIMinecraftForgeClient;
      Class[] reflectedForgeAPIClasses = new Class[]{forgeAPIIItemRendererItemRenderType = getClass("net.minecraft.src.forge.IItemRenderer$ItemRenderType"), forgeAPIIItemRendererItemRendererHelper = getClass("net.minecraft.src.forge.IItemRenderer$ItemRendererHelper"), forgeAPIIArmorTextureProvider = getClass("net.minecraft.src.forge.IArmorTextureProvider"), forgeAPIIItemRenderer = getClass("net.minecraft.src.forge.IItemRenderer"), forgeAPIMinecraftForgeClient = getClass("net.minecraft.src.forge.MinecraftForgeClient")};
      data.installed = false;
      int len$ = reflectedForgeAPIClasses.length;
      int i$ = 0;
      if (i$ < len$) {
         Class c = reflectedForgeAPIClasses[i$];
         if (c == null) {
            return false;
         }

         data.installed = true;
      }

      data.putClass("IArmorTextureProvider", forgeAPIIArmorTextureProvider);
      data.putClass("IItemRenderer", forgeAPIIItemRenderer);
      data.putClass("MinecraftForgeClient", forgeAPIMinecraftForgeClient);
      Method m;
      data.putMethod("getArmorTextureFile", m = getMethod(0, forgeAPIIArmorTextureProvider, true));
      if (m == null) {
         return false;
      } else {
         log("ForgeAPI Method " + stringMethod(m));
         data.putMethod("getItemRenderer", m = getMethod(forgeAPIMinecraftForgeClient, "getItemRenderer", ItemStack.class, forgeAPIIItemRendererItemRenderType));
         if (m == null) {
            return false;
         } else {
            log("ForgeAPI Method " + stringMethod(m));
            data.putMethod("shouldUseRenderHelper", m = getMethod(forgeAPIMinecraftForgeClient, "shouldUseRenderHelper", forgeAPIIItemRendererItemRenderType, ItemStack.class, forgeAPIIItemRendererItemRendererHelper));
            if (m == null) {
               return false;
            } else {
               log("ForgeAPI Method " + stringMethod(m));
               data.putMethod("Item.getRenderPasses", m = getMethod(Item.class, "getRenderPasses", Integer.TYPE));
               if (m == null) {
                  return false;
               } else {
                  log("ForgeAPI Method " + stringMethod(m));
                  Field f;
                  data.putField("IItemRenderer.vip", f = getField(PlayerEntity.class, "vip"));
                  if (f == null) {
                     return false;
                  } else {
                     log("ForgeAPI Field " + stringField(f));
                     Object o;
                     data.putObject("IItemRenderer$ItemRenderType.EQUIPPED", o = getField((Object)null, (Field)getField(forgeAPIIItemRendererItemRenderType, "EQUIPPED")));
                     if (o == null) {
                        return false;
                     } else {
                        log("ForgeAPI Object " + o.toString());
                        data.putObject("IItemRenderer$ItemRendererHelper.BLOCK_3D", o = getField((Object)null, (Field)getField(forgeAPIIItemRendererItemRendererHelper, "BLOCK_3D")));
                        if (o == null) {
                           return false;
                        } else {
                           log("ForgeAPI Object " + o.toString());
                           if (ForgeAPI.removeNullData()) {
                              log("Warning: ForgeAPI reflection returned some nulls");
                           }

                           return true;
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private static boolean reflectSpoutCraft(MineLPRData data) {
      log("Checking SpoutCraft...");
      Class spoutCraftChatColor;
      Class spoutCraftHDImageBufferDownload;
      Class spoutCraftVIP;
      Class spoutMaterialData;
      Class spoutCustomItem;
      Class spoutTool;
      Class[] reflectedSpoutCraftClasses = new Class[]{spoutCraftChatColor = getClass("org.bukkit.ChatColor"), spoutCraftHDImageBufferDownload = getClass("org.spoutcraft.client.HDImageBufferDownload"), spoutCraftVIP = getClass("org.spoutcraft.client.special.VIP"), spoutMaterialData = getClass("org.spoutcraft.spoutcraftapi.material.MaterialData"), spoutCustomItem = getClass("org.spoutcraft.spoutcraftapi.material.CustomItem"), spoutTool = getClass("org.spoutcraft.spoutcraftapi.material.Tool")};
      data.installed = false;
      int len$ = reflectedSpoutCraftClasses.length;
      int i$ = 0;
      if (i$ < len$) {
         Class c = reflectedSpoutCraftClasses[i$];
         if (c == null) {
            return false;
         }

         data.installed = true;
      }

      data.putClass("ChatColor", spoutCraftChatColor);
      data.putClass("HDImageBufferDownload", spoutCraftHDImageBufferDownload);
      data.putClass("VIP", spoutCraftVIP);
      data.putClass("MaterialData", spoutMaterialData);
      data.putClass("CustomItem", spoutCustomItem);
      data.putClass("Tool", spoutTool);
      Constructor c;
      data.putConstructor("HDImageBufferDownload", c = getConstructor(spoutCraftHDImageBufferDownload));
      if (c == null) {
         return false;
      } else {
         log("SpoutCraft Method " + stringConstructor(c));
         Method m;
         data.putMethod("ChatColor.stripColor", m = getMethod(1, spoutCraftChatColor, false));
         if (m == null) {
            return false;
         } else {
            log("SpoutCraft Method " + stringMethod(m));
            data.putMethod("MaterialData.getCustomItem", m = getMethod(4, spoutMaterialData, false));
            if (m == null) {
               return false;
            } else {
               log("SpoutCraft Method " + stringMethod(m));
               data.putMethod("VIP.getScale", m = getMethod(spoutCraftVIP, "getScale"));
               if (m == null) {
                  return false;
               } else {
                  log("SpoutCraft Method " + stringMethod(m));
                  data.putMethod("VIP.getArmor", m = getMethod(spoutCraftVIP, "getArmor", Integer.TYPE));
                  if (m == null) {
                     return false;
                  } else {
                     log("SpoutCraft Method " + stringMethod(m));
                     Field f;
                     data.putField("EntityLiving.displayName", f = getField(LivingEntity.class, "displayName"));
                     if (f == null) {
                        return false;
                     } else {
                        log("SpoutCraft Field " + stringField(f));
                        data.putField("EntityPlayer.vip", f = getField(PlayerEntity.class, "vip"));
                        if (f == null) {
                           return false;
                        } else {
                           log("SpoutCraft Field " + stringField(f));
                           if (SpoutCraft.removeNullData()) {
                              log("Warning: SpoutCraft reflection returned some nulls");
                           }

                           return true;
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public static boolean doesClassExist(String classpath) {
      try {
         return Class.forName(classpath) != null;
      } catch (ClassNotFoundException var2) {
      } catch (Exception var3) {
      }

      return false;
   }

   public static Class getClass(String classpath) {
      try {
         return Class.forName(classpath);
      } catch (ClassNotFoundException var2) {
      } catch (Exception var3) {
      }

      return null;
   }

   public static Constructor getConstructor(Class c, Class... args) {
      Exception ex = null;

      try {
         Constructor con = null;

         try {
            if (args != null && args.length != 0) {
               con = c.getConstructor(args);
            } else {
               con = c.getConstructor();
            }
         } catch (Exception var5) {
            con = null;
         }

         return con;
      } catch (SecurityException e) {
         ex = e;
      } catch (Exception e) {
         ex = e;
      }

      if (ex != null) {
         log("Failed to match Constructor for class \"" + c.getName() + "\"");
         ex.printStackTrace();
      }

      return null;
   }

   public static Constructor getConstructor(int i, Class c) {
      Exception ex = null;

      try {
         return c.getConstructors()[i];
      } catch (SecurityException e) {
         ex = e;
      } catch (Exception e) {
         ex = e;
      }

      if (ex != null) {
         log("Failed to match Constructor for class \"" + c.getName() + "\"");
         ex.printStackTrace();
      }

      return null;
   }

   public static Field getField(Class c, String fieldName) {
      Exception ex = null;

      try {
         Field f = null;

         try {
            f = c.getField(fieldName);
         } catch (Exception var5) {
            f = null;
         }

         if (f == null) {
            f = c.getDeclaredField(fieldName);
         }

         f.setAccessible(true);
         return f;
      } catch (SecurityException e) {
         ex = e;
      } catch (NoSuchFieldException e) {
         ex = e;
      } catch (Exception e) {
         ex = e;
      }

      if (ex != null) {
         log("Failed to match Field \"" + fieldName + "\" in " + c.getName());
         ex.printStackTrace();
      }

      return null;
   }

   public static Field getField(int i, Class c, boolean declared) {
      Exception ex = null;

      try {
         Field f = (declared ? c.getDeclaredFields() : c.getFields())[i];
         f.setAccessible(true);
         return f;
      } catch (SecurityException e) {
         ex = e;
      } catch (Exception e) {
         ex = e;
      }

      if (ex != null) {
         log("Failed to match Field #" + String.valueOf(i) + " in " + c.getName());
         ex.printStackTrace();
      }

      return null;
   }

   public static Object getField(Object object, Field field) {
      try {
         return field.get(object);
      } catch (IllegalArgumentException e) {
         e.printStackTrace();
      } catch (IllegalAccessException e) {
         e.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      }

      return null;
   }

   public static boolean setField(Object object, Field field, Object value) {
      try {
         field.setAccessible(true);
         field.set(object, value);
         return true;
      } catch (IllegalArgumentException e) {
         e.printStackTrace();
      } catch (IllegalAccessException e) {
         e.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      }

      return false;
   }

   public static Method getMethod(Class c, String methodName, Class... types) {
      Exception ex = null;

      try {
         Method m = null;

         try {
            m = c.getMethod(methodName, types);
         } catch (Exception var6) {
            m = null;
         }

         if (m == null) {
            if (types != null && types.length != 0) {
               m = c.getDeclaredMethod(methodName, types);
            } else {
               m = c.getDeclaredMethod(methodName);
            }
         }

         m.setAccessible(true);
         return m;
      } catch (SecurityException e) {
         ex = e;
      } catch (NoSuchMethodException e) {
         ex = e;
      } catch (Exception e) {
         ex = e;
      }

      if (ex != null) {
         log("Failed to match method \"" + methodName + "\" in " + c.getName());
         log("Types: " + getStringFromTypes(types));
         ex.printStackTrace();
      }

      return null;
   }

   public static Method getMethod(int i, Class c, boolean declared) {
      Exception ex = null;

      try {
         Method m = (declared ? c.getDeclaredMethods() : c.getMethods())[i];
         m.setAccessible(true);
         return m;
      } catch (SecurityException e) {
         ex = e;
      } catch (Exception e) {
         ex = e;
      }

      if (ex != null) {
         log("Failed to match method #" + String.valueOf(i) + " in " + c.getName());
         ex.printStackTrace();
      }

      return null;
   }

   public static Object invokeMethod(Object object, Method method, Object... params) {
      try {
         if (params == null) {
            return method.invoke(object);
         }

         return method.invoke(object, params);
      } catch (IllegalArgumentException e) {
         e.printStackTrace();
      } catch (IllegalAccessException e) {
         e.printStackTrace();
      } catch (InvocationTargetException e) {
         e.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      }

      return null;
   }

   public static Object getArray(Class c, int size) {
      return Array.newInstance(c, size);
   }

   public static Object getArray(Class type, Object... cont) {
      Object a = Array.newInstance(type, cont.length);

      for(int i = 0; i < cont.length; ++i) {
         Array.set(a, i, cont[i]);
      }

      return a;
   }

   public static void preCall() {
   }

   public static void log(String msg) {
      System.out.println("[Mine Little Pony] " + msg);
   }

   public static String getStringFromTypes(Class... types) {
      String temp = "";
      temp = temp + "(";
      boolean first = true;

      for(Class c : types) {
         if (!first) {
            temp = temp + ",";
         } else {
            first = false;
         }

         temp = temp + c.getName();
      }

      temp = temp + ")";
      return temp;
   }

   public static Class[] getTypesFromObjects(Object... objects) {
      Class[] types = new Class[objects.length];

      for(int i = 0; i < objects.length; ++i) {
         types[i] = objects[i].getClass();
      }

      return types;
   }

   public static Constructor printConstructor(Constructor c) {
      System.out.println(stringConstructor(c));
      return c;
   }

   public static String stringConstructor(Constructor c) {
      return Modifier.toString(c.getModifiers()) + " " + c.getName() + getStringFromTypes(c.getParameterTypes()) + (c.getExceptionTypes().length > 0 ? " throws " + c.getExceptionTypes() : "");
   }

   public static Method printMethod(Method m) {
      System.out.println(stringMethod(m));
      return m;
   }

   public static String stringMethod(Method m) {
      return Modifier.toString(m.getModifiers()) + " " + (m.getReturnType() != null ? m.getReturnType().getName() : "void") + " " + m.getName() + getStringFromTypes(m.getParameterTypes()) + (m.getExceptionTypes().length > 0 ? " throws " + getStringFromTypes(m.getExceptionTypes()) : "");
   }

   public static Field printField(Field f) {
      System.out.println(stringField(f));
      return f;
   }

   public static String stringField(Field f) {
      return Modifier.toString(f.getModifiers()) + " " + f.getType().getName() + " " + f.getName();
   }

   static {
      Field reflectedField = getHackedArmorFilenamePrefixField();
      if (reflectedField == null) {
         reflectedField = classRenderPlayer.getDeclaredFields()[3];
         reflectedField.setAccessible(true);
      }

      armorFilenamePrefix = reflectedField;
      log("Checking compatibilities...");
      Modloader = new MineLPRData();
      Modloader.compatible = false; //reflectModloader(Modloader);
      ForgeAPI = new MineLPRData();
      ForgeAPI.compatible = false; //reflectForgeAPI(ForgeAPI);
      SpoutCraft = new MineLPRData();
      SpoutCraft.compatible = false;// reflectSpoutCraft(SpoutCraft);
      log("Compatibility Check Done!");
      if (Modloader.installed) {
         log("Modloader " + (Modloader.compatible ? "Installed and Compatible" : "Installed but Incompatible"));
      }

      if (ForgeAPI.installed) {
         log("ForgeAPI " + (ForgeAPI.compatible ? "Installed and Compatible" : "Installed but Incompatible"));
      }

      if (SpoutCraft.installed) {
         log("SpoutCraft " + (SpoutCraft.compatible ? "Installed and Compatible" : "Installed but Incompatible"));
      }

   }
}
