package codes.blackjack.minelittlepony;

import net.minecraft.entity.living.player.PlayerEntity;

import java.util.Map;

public class mod_MineLittlePony {
   public void load() {
   }

   public void addRenderer(Map map) {
      map.put(PlayerEntity.class, new RenderPony());
   }

   public String getVersion() {
      return "1.2.7";
   }
}
