package codes.blackjack.minelittlepony;

public abstract class ModelArmor {
   public final String path;
   public static int slot;
   public ModelPlayer base;
   public ModelPlayer modelArmorChestplate;
   public ModelPlayer modelArmor;

   public ModelArmor(String path) {
      this.path = path;
   }

   public abstract float layer();

   public abstract int subimage();

   public abstract void boxes(boolean var1);
}
