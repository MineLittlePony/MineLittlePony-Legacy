package codes.blackjack.minelittlepony;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.living.player.LocalPlayerEntity;
import net.minecraft.client.render.texture.HttpTexture;
import net.minecraft.client.render.texture.TextureManager;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Pony {
   public static PonyConfig config;
   public static final int numberOfPonies = 127;
   protected static ArrayList backgroundPonies = new ArrayList();
   public static boolean hasInit = false;
   private static Map registry = new HashMap();
   private static final Class classRenderEngine = TextureManager.class;
   private static Map urlToImageDataMap;
   private static boolean renderEngineInit = false;
   private static boolean alreadyReadSettings = false;
   private static int ponyLevel = 2;
   public static int useSizes = 1;
   public static int ponyArmor = 1;
   public static int showSnuzzles = 1;
   public boolean advancedTexturing;
   public String texture;
   public String backgroundTexture;
   public boolean backgroundIsPegasus;
   public boolean backgroundIsUnicorn;
   public int backgroundWantTail;
   public boolean backgroundIsMale;
   public int backgroundSize;
   public boolean backgroundAdvancedTexturing;
   public boolean textureSetup = false;
   public String skinUrl;
   public String realSkinUrl;
   boolean isSpPlayer;
   public boolean isPony;
   public boolean isPonySkin;
   public boolean isPegasus;
   public boolean isUnicorn;
   public boolean isFlying;
   public boolean isGlow;
   public int glowColor;
   public boolean isMale = false;
   public int size = 1;
   public int wantTail = 0;
   public float defaultYOffset;
   private boolean pegasusFlying;
   private final int dangerzone = 2;
   private float previousFallDistance;

   public static Pony getPonyFromRegistry(PlayerEntity player, TextureManager renderengine) {
      HttpTexture threaddownloadimagedata = null;
      String username = player.name;
      String location = "http://skins.minecraft.net/MinecraftSkins/" + username + ".png";
      if (!renderEngineInit) {
         try {
            Field urlToImageDataMapField = classRenderEngine.getDeclaredFields()[7];
            urlToImageDataMapField.setAccessible(true);
            urlToImageDataMap = (Map)urlToImageDataMapField.get(renderengine);
         } catch (Exception exception) {
            System.out.println("[Mine Little Pony] Failed to reflect RenderEngine (exception)." + exception);
         }

         renderEngineInit = true;
      }

      init();
      Pony myLittlePony;
      if (!registry.containsKey(username)) {
         myLittlePony = new Pony(player);
         registry.put(username, myLittlePony);
      } else {
         myLittlePony = (Pony)registry.get(username);
      }

      threaddownloadimagedata = (HttpTexture)urlToImageDataMap.get(location);
      if (ponyLevel != 0 && myLittlePony.getTextureSetup() && (threaddownloadimagedata == null || threaddownloadimagedata.image == null)) {
         registry.remove(username);
         myLittlePony = new Pony(player);
         registry.put(username, myLittlePony);
      }

      if (!myLittlePony.getTextureSetup() && threaddownloadimagedata != null && threaddownloadimagedata.image != null) {
         myLittlePony.checkSkin(threaddownloadimagedata.image);
         if (!myLittlePony.isPonySkin) {
            myLittlePony.isPony = true;
            myLittlePony.isPegasus = myLittlePony.backgroundIsPegasus;
            myLittlePony.isUnicorn = myLittlePony.backgroundIsUnicorn;
            myLittlePony.wantTail = myLittlePony.backgroundWantTail;
            myLittlePony.isMale = myLittlePony.backgroundIsMale;
            myLittlePony.size = myLittlePony.backgroundSize;
            myLittlePony.advancedTexturing = myLittlePony.backgroundAdvancedTexturing;
         } else {
            myLittlePony.skinUrl = location;
         }

         myLittlePony.setTextureSetup(true);
      }

      return myLittlePony;
   }

   public Pony(PlayerEntity player) {
      init();
      this.texture = "/mob/char.png";
      this.skinUrl = null;
      String username = player.name;
      if (username != null) {
         this.skinUrl = "http://s3.amazonaws.com/MinecraftSkins/" + username + ".png";
         this.realSkinUrl = this.skinUrl;
      }

      this.isSpPlayer = player instanceof LocalPlayerEntity;
      this.isPony = false;
      this.isPonySkin = false;
      this.isPegasus = false;
      this.isUnicorn = false;
      this.isMale = false;
      this.wantTail = 0;
      this.advancedTexturing = false;
      this.isFlying = false;
      this.isGlow = false;
      this.pegasusFlying = false;
      this.defaultYOffset = 1.62F;
      if (ponyLevel == 0) {
         this.textureSetup = true;
      } else if (ponyLevel == 1) {
         this.textureSetup = false;
      } else {
         if (ponyLevel == 2) {
            this.textureSetup = false;
            if (this.isSpPlayer) {
               System.out.println("[Mine Little Pony] Temporarily reset skin to the default single player skin charpony.png");
               this.texture = "/mob/charpony.png";
            } else {
               System.out.println("[Mine Little Pony] Temporarily reset skin to a background pony");
               int backgroundNumber = username.hashCode() % backgroundPonies.size();
               if (backgroundNumber < 0) {
                  backgroundNumber += backgroundPonies.size();
               }

               this.texture = (String)backgroundPonies.get(backgroundNumber);
               System.out.println("[Mine Little Pony] " + username + " gets skin " + backgroundNumber);
            }

            this.backgroundIsPegasus = false;
            this.backgroundIsUnicorn = false;
            this.backgroundTexture = this.texture;

            try {
               BufferedImage bufferedimage = ImageIO.read(Minecraft.class.getResource(this.texture));
               this.checkBuiltinTexture(bufferedimage);
            } catch (Exception var9) {
               this.texture = "/mob/charpony.png";
               System.out.println("[Mine Little Pony] Failed to read a background pony texture from a file, resetting to default charpony.png");

               try {
                  BufferedImage var11 = ImageIO.read(Minecraft.class.getResource(this.texture));
                  this.checkBuiltinTexture(var11);
               } catch (Exception var8) {
                  this.texture = "/mob/char.png";
                  System.out.println("[Mine Little Pony] Failed to read charpony.png, resetting to default char.png");

                  try {
                     BufferedImage var10 = ImageIO.read(Minecraft.class.getResource(this.texture));
                     this.checkBuiltinTexture(var10);
                  } catch (Exception var7) {
                     System.out.println("[Mine Little Pony] Failed to read char.png, I just don't know what went wrong.");
                  }
               }
            }

            this.skinUrl = null;
         }

      }
   }

   public void checkSkin(BufferedImage bufferedimage) {
      this.isPony = false;
      this.isPonySkin = false;
      this.isPegasus = false;
      this.isUnicorn = false;
      this.isPonySkin = false;
      this.isMale = false;
      this.wantTail = 0;
      Color flagPix = new Color(bufferedimage.getRGB(0, 0), true);
      int red = flagPix.getRed();
      int green = flagPix.getGreen();
      int blue = flagPix.getBlue();
      int alpha = flagPix.getAlpha();
      Color applejack = new Color(249, 177, 49, 255);
      Color dashie = new Color(136, 202, 240, 255);
      Color twilight = new Color(209, 159, 228, 255);
      Color celestia = new Color(254, 249, 252, 255);
      if (flagPix.equals(applejack)) {
         this.isPony = true;
         this.isPonySkin = true;
      }

      if (flagPix.equals(dashie)) {
         this.isPony = true;
         this.isPonySkin = true;
         this.isPegasus = true;
      }

      if (flagPix.equals(twilight)) {
         this.isPony = true;
         this.isPonySkin = true;
         this.isUnicorn = true;
      }

      if (flagPix.equals(celestia)) {
         this.isPony = true;
         this.isPonySkin = true;
         this.isPegasus = true;
         this.isUnicorn = true;
      }

      Color tailcolor = new Color(bufferedimage.getRGB(1, 0), true);
      Color tailcolor1 = new Color(66, 88, 68, 255);
      Color tailcolor2 = new Color(70, 142, 136, 255);
      Color tailcolor3 = new Color(83, 75, 118, 255);
      Color tailcolor4 = new Color(138, 107, 127, 255);
      if (tailcolor.equals(tailcolor1)) {
         this.wantTail = 4;
      } else if (tailcolor.equals(tailcolor2)) {
         this.wantTail = 3;
      } else if (tailcolor.equals(tailcolor3)) {
         this.wantTail = 2;
      } else if (tailcolor.equals(tailcolor4)) {
         this.wantTail = 1;
      } else {
         this.wantTail = 0;
      }

      Color gendercolor = new Color(bufferedimage.getRGB(2, 0), true);
      Color gendercolor1 = new Color(255, 255, 255, 255);
      if (gendercolor.equals(gendercolor1)) {
         this.isMale = true;
      } else {
         this.isMale = false;
      }

      Color sizecolor = new Color(bufferedimage.getRGB(3, 0), true);
      Color scootaloo = new Color(255, 190, 83);
      Color bigmac = new Color(206, 50, 84);
      Color luna = new Color(42, 60, 120);
      this.size = 1;
      if (useSizes == 1) {
         if (sizecolor.equals(scootaloo)) {
            this.size = 0;
         } else if (sizecolor.equals(bigmac)) {
            this.size = 2;
         } else if (sizecolor.equals(luna)) {
            this.size = 3;
         } else {
            this.size = 1;
         }
      }

      Color black = new Color(0, 0, 0);
      Color advcutiecolor = new Color(bufferedimage.getRGB(4, 0), true);
      if (advcutiecolor.getAlpha() == 0) {
         this.advancedTexturing = false;
      } else {
         this.advancedTexturing = false;

         for(int x = 4; x < 8; ++x) {
            for(int y = 0; y < 8; ++y) {
               Color aColor = new Color(bufferedimage.getRGB(x, y), true);
               if (!aColor.equals(black)) {
                  this.advancedTexturing = true;
               }
            }
         }
      }

      Color tempGlowColor = new Color(bufferedimage.getRGB(0, 1), true);
      if (!tempGlowColor.equals(black) && tempGlowColor.getAlpha() != 0) {
         this.glowColor = tempGlowColor.getRGB();
      } else {
         this.glowColor = -12303190;
      }

   }

   public void checkBuiltinTexture(BufferedImage bufferedimage) {
      this.checkSkin(bufferedimage);
      this.backgroundIsPegasus = this.isPegasus;
      this.backgroundIsUnicorn = this.isUnicorn;
      this.backgroundWantTail = this.wantTail;
      this.backgroundIsMale = this.isMale;
      this.backgroundSize = this.size;
      this.backgroundAdvancedTexturing = this.advancedTexturing;
      this.isPonySkin = false;
   }

   public static int getNumberOfPonies() {
      return 127;
   }

   public static int getPonyArmor() {
      return ponyArmor;
   }

   public static int showSnuzzles() {
      return showSnuzzles;
   }

   public boolean isPony() {
      return this.isPony;
   }

   public boolean isPonySkin() {
      return this.isPonySkin;
   }

   public boolean isUnicorn() {
      return this.isUnicorn;
   }

   public boolean isPegasus() {
      return this.isPegasus;
   }

   public int wantTail() {
      return this.wantTail;
   }

   public boolean isMale() {
      return this.isMale;
   }

   public int size() {
      return this.size;
   }

   public boolean advancedTexturing() {
      return this.advancedTexturing;
   }

   public boolean isFlying() {
      return this.isFlying;
   }

   public boolean isGlow() {
      return this.isGlow;
   }

   public int glowColor() {
      return this.glowColor;
   }

   public boolean getTextureSetup() {
      return this.textureSetup;
   }

   public void setTextureSetup(boolean setup) {
      this.textureSetup = setup;
   }

   public boolean isPegasusFlying(double posX, double posY, double posZ, float fallDistance, boolean isJumping, World equestria) {
      if (!this.isPegasus) {
         this.pegasusFlying = false;
         return false;
      } else if (isJumping) {
         return true;
      } else {
         boolean falling = fallDistance > 0.0F;
         boolean levitating = fallDistance == this.previousFallDistance;
         boolean standingOnAir;
         if (falling && !levitating) {
            standingOnAir = this.standingOnAir(posX, posY, posZ, 1.5F, equestria);
         } else {
            standingOnAir = this.standingOnAir(posX, posY, posZ, 1.0F, equestria);
         }

         if (!standingOnAir) {
            this.pegasusFlying = false;
            return false;
         } else if (this.pegasusFlying) {
            return true;
         } else if (levitating) {
            this.pegasusFlying = true;
            return true;
         } else {
            this.previousFallDistance = fallDistance;
            if (fallDistance < 2.0F) {
               return false;
            } else {
               this.pegasusFlying = true;
               return true;
            }
         }
      }
   }

   public boolean standingOnAir(double posX, double posY, double posZ, float range, World equestria) {
      boolean foundSolidBlock = false;
      int y;
      if (this.isSpPlayer) {
         y = MathHelper.floor(posY - (double)this.defaultYOffset - (double)0.01F);
      } else {
         y = MathHelper.floor(posY - (double)0.01F);
      }

      for(float shiftX = 0.0F - range; shiftX < range * 2.0F; shiftX += range) {
         for(float shiftZ = 0.0F - range; shiftZ < range * 2.0F; shiftZ += range) {
            int x = MathHelper.floor(posX + (double) shiftX);
            int z = MathHelper.floor(posZ + (double)shiftZ);
            if (!equestria.isAir(x, y, z)) {
               foundSolidBlock = true;
            }
         }
      }

      return !foundSolidBlock;
   }

   public PlayerModel getModel() {
      boolean is_a_pony = false;
      switch (getPonyLevel()) {
         case 0:
            is_a_pony = false;
            break;
         case 1:
            if (!this.isPonySkin) {
               is_a_pony = false;
            } else {
               is_a_pony = true;
            }
            break;
         case 2:
            is_a_pony = true;
      }

      if (is_a_pony) {
         return this.advancedTexturing ? PMAPI.newPonyAdv : PMAPI.newPony;
      } else {
         return PMAPI.human;
      }
   }

   public static int getPonyLevel() {
      if (!alreadyReadSettings) {
         try {
            alreadyReadSettings = true;
            int readInt = config.getIntProperty("ponylevel");
            if (readInt < 3 && readInt > -1) {
               ponyLevel = readInt;
               System.out.println("[Mine Little Pony] Read settings and set pony level to " + ponyLevel);
            } else {
               ponyLevel = 2;
               System.out.println("[Mine Little Pony] Invalid settings file detected, falling back to making everyone ponies by default.");
            }

            readInt = config.getIntProperty("sizes");
            if (readInt < 2 && readInt > -1) {
               useSizes = readInt;
               if (useSizes == 0) {
                  System.out.println("[Mine Little Pony] Preventing different sized ponies from being displayed.");
               } else {
                  System.out.println("[Mine Little Pony] Using all sizes of pony.");
               }
            } else {
               useSizes = 1;
               System.out.println("[Mine Little Pony] Invalid settings file detected, falling back to using all sizes of ponies.");
            }

            readInt = config.getIntProperty("ponyarmor");
            if (readInt < 2 && readInt > -1) {
               ponyArmor = readInt;
               if (ponyArmor == 0) {
                  System.out.println("[Mine Little Pony] Disabling pony armor.");
               } else {
                  System.out.println("[Mine Little Pony] Pony armor enabled.");
               }
            } else {
               ponyArmor = 1;
               System.out.println("[Mine Little Pony] Invalid settings file detected, falling back to using pony armor.");
            }

            readInt = config.getIntProperty("snuzzles");
            if (readInt < 2 && readInt > -1) {
               showSnuzzles = readInt;
               if (showSnuzzles == 0) {
                  System.out.println("[Mine Little Pony] Disabling snuzzles. You are a bad pony.");
               } else {
                  System.out.println("[Mine Little Pony] Snuzzles enabled.");
               }
            } else {
               showSnuzzles = 1;
               System.out.println("[Mine Little Pony] Invalid settings file detected, falling back to showing snuzzles.");
            }
         } catch (Exception var1) {
            System.out.println("[Mine Little Pony] Could not read pony settings file, falling back to making everyone ponies by default and allowing all sizes of ponies.");
            alreadyReadSettings = true;
            ponyLevel = 2;
            useSizes = 1;
         }
      }

      return ponyLevel;
   }

   public static void init() {
      if (!hasInit) {
         System.out.println("[Mine Little Pony] Player Model API for Mine Little Pony (beta) Initializing...");
         PMAPI.addToGUI(PMAPI.human);
         PMAPI.addToGUI(PMAPI.newPony);
         PMAPI.addToGUI(PMAPI.newPonyAdv);
         PMAPI.newPony.model.init();
         PMAPI.newPony.armor.modelArmorChestplate.init(0.0F, 1.0F);
         PMAPI.newPony.armor.modelArmor.init(0.0F, 0.5F);
         PMAPI.newPonyAdv.model.init();
         PMAPI.newPonyAdv.armor.modelArmorChestplate.init(0.0F, 1.0F);
         PMAPI.newPonyAdv.armor.modelArmor.init(0.0F, 0.5F);
         PMAPI.human.model.init();
         PMAPI.human.armor.modelArmorChestplate.init(0.0F, 1.0F);
         PMAPI.human.armor.modelArmor.init(0.0F, 0.5F);
         config = new PonyConfig(MinecraftInstanceGetter.getMinecraftInstance());
         int dummy = getPonyLevel();

         for(int check = 0; check < 127; ++check) {
            String checkTexture = "/mob/bpony_" + check + ".png";
            if (Pony.class.getResource(checkTexture) != null) {
               backgroundPonies.add(checkTexture);
            }
         }

         System.out.println("[Mine Little Pony] Dectected " + backgroundPonies.size() + " of " + 127 + " background ponies installed.");
         hasInit = true;
         System.out.println("[Mine Little Pony] Done initializing.");
      }

   }
}
