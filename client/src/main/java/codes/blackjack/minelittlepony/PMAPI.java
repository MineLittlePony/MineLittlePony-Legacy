package codes.blackjack.minelittlepony;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.living.player.PlayerEntity;

public final class PMAPI {
   public static PlayerModel newPony = (new PlayerModel("newPony", new pm_newPony("/mob/char.png"), 0)).setArmor(new pma_newPony("/armor/")).setURL("http://skins.minecraft.net/MinecraftSkins/%NAME%.png").setScale(0.9375F);
   public static PlayerModel newPonyAdv = (new PlayerModel("newPonyAdv", new pm_newPonyAdv("/mob/char.png"), 0)).setArmor(new pma_newPony("/armor/")).setURL("http://skins.minecraft.net/MinecraftSkins/%NAME%.png").setScale(0.9375F);
   public static PlayerModel human = (new PlayerModel("Human", new pm_Human("/mob/char.png"), 1)).setArmor(new pma_Human("/armor/")).setURL("http://skins.minecraft.net/MinecraftSkins/%NAME%.png").setScale(0.9375F);
   public static final String version = "(beta)";
   public static final String title = "Player Model API for Mine Little Pony (beta)";
   public static int id = 0;
   private static List pmlist = new ArrayList();
   public static final float pixel = 0.0625F;

   public static int count() {
      return pmlist.size();
   }

   public static PlayerModel current() {
      return (PlayerModel)pmlist.get(id);
   }

   public static PlayerModel next() {
      return (PlayerModel)pmlist.get(id != count() - 1 ? id + 1 : 0);
   }

   public static PlayerModel previous() {
      return (PlayerModel)pmlist.get(id != 0 ? id - 1 : count() - 1);
   }

   public static void addToGUI(PlayerModel pm) {
      System.out.println("[Mine Little Pony] \"" + pm.name + "\" added");
      pmlist.add(pm);
   }

   public static void change(PlayerModel pm, PlayerEntity player) {
      if (id != pm.id) {
         id = pm.id;
      }
   }
}
