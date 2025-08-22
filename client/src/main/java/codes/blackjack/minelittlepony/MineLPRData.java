package codes.blackjack.minelittlepony;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class MineLPRData {
   public boolean installed = false;
   public boolean compatible = false;
   public HashMap<String, Object> classes = new HashMap<>();
   public HashMap<String, Object> constructors = new HashMap();
   public HashMap<String, Object> methods = new HashMap();
   public HashMap<String, Object> fields = new HashMap();
   public HashMap objects;
   private MineLPRData me;

   public Object getInstance(String key, Object... params) {
      Exception ex = null;

      try {
         if (this.constructors.keySet().contains(key)) {
            Constructor c = (Constructor)this.constructors.get(key);
            if (params != null && params.length != 0) {
               return c.newInstance(params);
            }

            return c.newInstance();
         }

         MineLPReflection.log("Unknown requested constructor \"" + key + "\"");
      } catch (IllegalArgumentException e) {
         ex = e;
      } catch (IllegalAccessException e) {
         ex = e;
      } catch (InvocationTargetException e) {
         ex = e;
      } catch (Exception e) {
         ex = e;
      }

      if (ex != null) {
         MineLPReflection.log("Error when trying to get constructor \"" + key + "\"");
         ex.printStackTrace();
      }

      return null;
   }

   public boolean isInstance(String key, Object object) {
      return this.getClass(key).isInstance(object);
   }

   public Object getField(String key, Object object) {
      Exception ex = null;

      try {
         if (this.fields.keySet().contains(key)) {
            Field f = (Field)this.fields.get(key);
            return f.get(object);
         }
      } catch (IllegalArgumentException e) {
         ex = e;
      } catch (IllegalAccessException e) {
         ex = e;
      } catch (Exception e) {
         ex = e;
      }

      if (ex != null) {
         MineLPReflection.log("Error when trying to get field \"" + key + "\"");
         ex.printStackTrace();
      }

      return null;
   }

   public boolean setField(String key, Object object, Object value) {
      Exception ex = null;

      try {
         if (this.fields.keySet().contains(key)) {
            Field f = (Field)this.fields.get(key);
            f.set(object, value);
            return true;
         }

         MineLPReflection.log("Unknown requested field \"" + key + "\"");
         return false;
      } catch (IllegalArgumentException e) {
         ex = e;
      } catch (IllegalAccessException e) {
         ex = e;
      } catch (Exception e) {
         ex = e;
      }

      if (ex != null) {
         MineLPReflection.log("Error when trying to set field \"" + key + "\"");
         ex.printStackTrace();
      }

      return false;
   }

   public Object invokeMethod(String key, Object object, Object... params) {
      Exception ex = null;

      try {
         if (this.methods.containsKey(key)) {
            Method m = (Method)this.methods.get(key);
            if (params != null) {
               return m.invoke(object, params);
            }

            return m.invoke(object);
         }

         MineLPReflection.log("Unknown requested method \"" + key + "\"");
         return null;
      } catch (IllegalArgumentException e) {
         ex = e;
      } catch (IllegalAccessException e) {
         ex = e;
      } catch (InvocationTargetException e) {
         ex = e;
      } catch (Exception e) {
         ex = e;
      }

      if (ex != null) {
         MineLPReflection.log("Method \"" + key + "\" failed to be invoked in " + object.getClass().getName());
         MineLPReflection.log("Types:  " + MineLPReflection.getStringFromTypes(MineLPReflection.getTypesFromObjects(params)));
         MineLPReflection.log("Values:  " + params.toString());
         ex.printStackTrace();
      }

      return null;
   }

   public Class getClass(String key) {
      return this.classes.containsKey(key) ? (Class)this.classes.get(key) : null;
   }

   public Constructor getConstructor(String key) {
      return this.constructors.containsKey(key) ? (Constructor)this.constructors.get(key) : null;
   }

   public Method getMethod(String key) {
      return this.methods.containsKey(key) ? (Method)this.methods.get(key) : null;
   }

   public Field getField(String key) {
      return this.fields.containsKey(key) ? (Field)this.fields.get(key) : null;
   }

   public Object getObject(String key) {
      return this.objects.containsKey(key) ? this.objects.get(key) : (Object)null;
   }

   public void putClass(String key, Class c) {
      this.classes.put(key, c);
   }

   public void putConstructor(String key, Constructor constructor) {
      this.constructors.put(key, constructor);
   }

   public void putMethod(String key, Method method) {
      this.methods.put(key, method);
   }

   public void putField(String key, Field field) {
      this.fields.put(key, field);
   }

   public void putObject(String key, Object object) {
      this.objects.put(key, object);
   }

   public void removeClass(String key) {
      this.classes.remove(key);
   }

   public void removeConstructor(String key) {
      this.constructors.remove(key);
   }

   public void removeMethod(String key) {
      this.methods.remove(key);
   }

   public void removeField(String key) {
      this.fields.remove(key);
   }

   public void removeObject(String key) {
      this.objects.remove(key);
   }

   public boolean hasClass(String key) {
      return this.classes.containsKey(key);
   }

   public boolean hasConstructor(String key) {
      return this.constructors.containsKey(key);
   }

   public boolean hasMethod(String key) {
      return this.methods.containsKey(key);
   }

   public boolean hasField(String key) {
      return this.fields.containsKey(key);
   }

   public boolean hasObject(String key) {
      return this.objects.containsKey(key);
   }

   public boolean removeNullData() {
      boolean nullDataPresent = false;

      for(String key : this.classes.keySet()) {
         if (this.classes.get(key) == null) {
            nullDataPresent = true;
            if (true) {
               this.classes.remove(key);
            }
         }
      }

      for(String key : this.constructors.keySet()) {
         if (this.constructors.get(key) == null) {
            nullDataPresent = true;
            if (true) {
               this.constructors.remove(key);
            }
         }
      }

      for(String key : this.methods.keySet()) {
         if (this.methods.get(key) == null) {
            nullDataPresent = true;
            if (true) {
               this.methods.remove(key);
            }
         }
      }

      for(String key : this.fields.keySet()) {
         if (this.fields.get(key) == null) {
            nullDataPresent = true;
            if (true) {
               this.fields.remove(key);
            }
         }
      }

      return nullDataPresent;
   }
}
