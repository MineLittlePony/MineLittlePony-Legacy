package codes.blackjack.minelittlepony;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import codes.blackjack.minelittlepony.mixin.MixinExtLivingEntity;
import com.mojang.blaze3d.vertex.BufferBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.TextRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.model.entity.HumanoidModel;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;

public class RenderPony extends PlayerEntityRenderer {
   private HumanoidModel modelBipedMain;
   private HumanoidModel modelArmorChestplate;
   private HumanoidModel modelArmor;
   public static String[] armorFilenamePrefix;
   public static AniParams ani;
   private PlayerModel pm;
   boolean nil0;
   boolean nil1;

   public RenderPony() {
      this.model = PMAPI.newPonyAdv.model;
      this.shadowSize = PMAPI.newPonyAdv.shadowsize;
      this.pm = PMAPI.newPonyAdv;
   }

   @Override
   protected int bindTexture(PlayerEntity player, int armorSlot, float partialTick) {
      boolean ponyArmor = false;
      this.pm = this.getModel(player);
      ItemStack armorInSlot = player.inventory.getArmor(3 - armorSlot);
      if (armorInSlot != null) {
         Item armorItem = armorInSlot.getItem();
         if (armorItem instanceof ArmorItem) {
            ArmorItem armorPiece = (ArmorItem) armorItem;
            ModelArmor.slot = armorSlot;
            String[] path = this.checkPonyVersion(this.pm.armor.path + MineLPReflection.getArmorFilenamePrefix()[armorPiece.materialId] + "_" + this.pm.armor.subimage() + ".png");
            if (MineLPReflection.ForgeAPI.installed) {
               try {
                  if (MineLPReflection.ForgeAPI.isInstance("IArmorTextureProvider", armorPiece)) {
                     String[] forgepath = this.checkPonyVersion((String)MineLPReflection.ForgeAPI.invokeMethod("getArmorTextureFile", armorPiece, armorInSlot));
                     this.bindTexture(forgepath[1]);
                     ponyArmor = Boolean.parseBoolean(forgepath[2]);
                  } else {
                     this.bindTexture(path[1]);
                     ponyArmor = Boolean.parseBoolean(path[2]);
                  }
               } catch (Exception var10) {
                  System.out.println("Error with ForgeAPI Armour Texture Compatibility");
                  this.bindTexture(path[1]);
                  ponyArmor = Boolean.parseBoolean(path[2]);
               }
            } else {
               this.bindTexture(path[1]);
               ponyArmor = Boolean.parseBoolean(path[2]);
            }

            this.pm.armor.boxes(ponyArmor);
            this.setDecorationModel(this.pm.armor.base);
            if (armorInSlot.hasEnchantments()) {
               return 15;
            }

            return 1;
         }
      }

      return -1;
   }

   private String[] checkPonyVersion(String path) {
      String[] returnPath = new String[3];
      returnPath[0] = path;
      returnPath[1] = path;
      returnPath[2] = "false";
      if (Pony.getPonyArmor() == 0) {
         return returnPath;
      } else {
         if (this.pm.name.equals("newPony") || this.pm.name.equals("newPonyAdv")) {
            String ponypath = path.replace(".png", "_pony.png");
            if (Minecraft.class.getResource(ponypath) != null) {
               returnPath[1] = ponypath;
               returnPath[2] = "true";
            }
         }

         return returnPath;
      }
   }

   @Override
   public void render(PlayerEntity par1EntityPlayer, double par2, double par4, double par6, float par8, float par9) {
      ItemStack itemstack = par1EntityPlayer.inventory.getMainHandStack();
      Pony thePony = Pony.getPonyFromRegistry(par1EntityPlayer, this.dispatcher.textureManager);
      par1EntityPlayer.skin = thePony.skinUrl;
	  // .texture perhaps?
	   ((MixinExtLivingEntity) par1EntityPlayer).setTexture(thePony.texture);
     // par1EntityPlayer.bm = thePony.texture;
      this.pm = this.getModel(par1EntityPlayer);
      this.model = this.pm.model;
      this.pm.armor.modelArmorChestplate.heldItemRight = this.pm.armor.modelArmor.heldItemRight = this.pm.model.heldItemRight = itemstack == null ? 0 : 1;
      if (itemstack != null && par1EntityPlayer.getItemUseTimer() > 0) {
         UseAction var11 = itemstack.getUseAction();
         if (var11 == UseAction.BLOCK) {
            this.pm.armor.modelArmorChestplate.heldItemRight = this.pm.armor.modelArmor.heldItemRight = this.pm.model.heldItemRight = 3;
         } else if (var11 == UseAction.BOW) {
            this.pm.armor.modelArmorChestplate.aimedBow = this.pm.armor.modelArmor.aimedBow = this.pm.model.aimedBow = true;
         }
      }

      this.pm.armor.modelArmorChestplate.issneak = this.pm.armor.modelArmor.issneak = this.pm.model.issneak = par1EntityPlayer.isSneaking();
	  boolean isJumping = ((MixinExtLivingEntity) par1EntityPlayer).isJumping();
      this.pm.armor.modelArmorChestplate.isFlying = this.pm.armor.modelArmor.isFlying = this.pm.model.isFlying = thePony.isFlying = thePony.isPegasusFlying(par1EntityPlayer.x, par1EntityPlayer.y, par1EntityPlayer.z, par1EntityPlayer.fallDistance, isJumping, this.dispatcher.world);
      this.pm.armor.modelArmorChestplate.isPegasus = this.pm.armor.modelArmor.isPegasus = this.pm.model.isPegasus = thePony.isPegasus();
      this.pm.armor.modelArmorChestplate.isUnicorn = this.pm.armor.modelArmor.isUnicorn = this.pm.model.isUnicorn = thePony.isUnicorn();
      this.pm.armor.modelArmorChestplate.isMale = this.pm.armor.modelArmor.isMale = this.pm.model.isMale = thePony.isMale();
      this.pm.armor.modelArmorChestplate.size = this.pm.armor.modelArmor.size = this.pm.model.size = thePony.size();
      this.pm.model.glowColor = thePony.glowColor();
      this.pm.armor.modelArmorChestplate.isSleeping = this.pm.armor.modelArmor.isSleeping = this.pm.model.isSleeping = par1EntityPlayer.isSleeping();
      // FIXME: This might be onGround but we don't have it?
	  // this.pm.armor.modelArmorChestplate.h = this.pm.armor.modelArmor.h = this.pm.model.h;
      this.pm.model.wantTail = thePony.wantTail();
      super.render(par1EntityPlayer, par2, par4, par6, par8, par9);
      this.pm.armor.modelArmorChestplate.aimedBow = this.pm.armor.modelArmor.aimedBow = this.pm.model.aimedBow = false;
      this.pm.armor.modelArmorChestplate.issneak = this.pm.armor.modelArmor.issneak = this.pm.model.issneak = false;
      this.pm.armor.modelArmorChestplate.heldItemRight = this.pm.armor.modelArmor.heldItemRight = this.pm.model.heldItemRight = 0;
   }

   protected void renderNameTag(PlayerEntity par1EntityPlayer, double par2, double par4, double par6) {
      if (Minecraft.isDisplayGui() && par1EntityPlayer != this.dispatcher.camera) {
         float var8 = 1.6F;
         float var9 = 0.016666668F * var8;
         float itemstack = par1EntityPlayer.getDistanceTo(this.dispatcher.camera);
         float var11 = par1EntityPlayer.isSneaking() ? 32.0F : 64.0F;
         if (itemstack < var11) {
            if (MineLPReflection.SpoutCraft.installed) {
               String title = (String)MineLPReflection.SpoutCraft.getField("displayName", par1EntityPlayer);
               float alpha = 0.25F;
               if (!title.equals("[hide]")) {
                  String[] lines = title.split("\\n");
                  double y = par4;

                  for(int line = 0; line < lines.length; ++line) {
                     title = lines[line];
                     par4 = y + 0.275 * (double)(lines.length - line - 1);
                     if (!par1EntityPlayer.isSneaking()) {
                        if (par1EntityPlayer.isSleeping()) {
                           this.renderNameTag(par1EntityPlayer, title, par2, par4 - (double)1.5F, par6, 64);
                        } else {
                           this.renderNameTag(par1EntityPlayer, title, par2, par4, par6, 64);
                        }
                     } else {
                        title = (String)MineLPReflection.SpoutCraft.invokeMethod("ChatColor.stripColor", (Object)null, title);
                        TextRenderer fontrenderer = this.getTextRenderer();
                        GL11.glPushMatrix();
                        GL11.glTranslatef((float)par2 + 0.0F, (float)par4 + 2.3F, (float)par6);
                        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
                        GL11.glRotatef(-this.dispatcher.cameraYaw, 0.0F, 1.0F, 0.0F);
                        GL11.glRotatef(this.dispatcher.cameraPitch, 1.0F, 0.0F, 0.0F);
                        GL11.glScalef(-var9, -var9, var9);
                        GL11.glDisable(2896);
                        GL11.glTranslatef(0.0F, 0.25F / var9, 0.0F);
                        GL11.glDepthMask(false);
                        GL11.glEnable(3042);
                        GL11.glBlendFunc(770, 771);
                        BufferBuilder tessellator = BufferBuilder.INSTANCE;
                        GL11.glDisable(3553);
                        tessellator.start();
                        int i = fontrenderer.getWidth(title) / 2;
                        tessellator.color(0.0F, 0.0F, 0.0F, alpha);
                        tessellator.vertex((double)(-i - 1), (double)-1.0F, (double)0.0F);
                        tessellator.vertex((double)(-i - 1), (double)8.0F, (double)0.0F);
                        tessellator.vertex((double)(i + 1), (double)8.0F, (double)0.0F);
                        tessellator.vertex((double)(i + 1), (double)-1.0F, (double)0.0F);
                        tessellator.end();
                        GL11.glEnable(3553);
                        GL11.glDepthMask(true);
                        fontrenderer.draw(title, -fontrenderer.getWidth(title) / 2, 0, 553648127);
                        GL11.glEnable(2896);
                        GL11.glDisable(3042);
                        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                        GL11.glPopMatrix();
                     }
                  }
               }

               return;
            }

            String thePony = par1EntityPlayer.name;
            if (!par1EntityPlayer.isSneaking()) {
               if (par1EntityPlayer.isSleeping()) {
                  this.renderNameTag(par1EntityPlayer, thePony, par2, par4 - (double)1.5F, par6, 64);
               } else {
                  this.renderNameTag(par1EntityPlayer, thePony, par2, par4, par6, 64);
               }
            } else {
               TextRenderer var13 = this.getTextRenderer();
               GL11.glPushMatrix();
               GL11.glTranslatef((float)par2 + 0.0F, (float)par4 + 2.3F, (float)par6);
               GL11.glNormal3f(0.0F, 1.0F, 0.0F);
               GL11.glRotatef(-this.dispatcher.cameraYaw, 0.0F, 1.0F, 0.0F);
               GL11.glRotatef(this.dispatcher.cameraPitch, 1.0F, 0.0F, 0.0F);
               GL11.glScalef(-var9, -var9, var9);
               GL11.glDisable(2896);
               GL11.glTranslatef(0.0F, 0.25F / var9, 0.0F);
               GL11.glDepthMask(false);
               GL11.glEnable(3042);
               GL11.glBlendFunc(770, 771);
               BufferBuilder var14 = BufferBuilder.INSTANCE;
               GL11.glDisable(3553);
               var14.start();
               int var15 = var13.getWidth(thePony) / 2;
               var14.color(0.0F, 0.0F, 0.0F, 0.25F);
               var14.vertex((double)(-var15 - 1), (double)-1.0F, (double)0.0F);
               var14.vertex((double)(-var15 - 1), (double)8.0F, (double)0.0F);
               var14.vertex((double)(var15 + 1), (double)8.0F, (double)0.0F);
               var14.vertex((double)(var15 + 1), (double)-1.0F, (double)0.0F);
               var14.end();
               GL11.glEnable(3553);
               GL11.glDepthMask(true);
               var13.draw(thePony, -var13.getWidth(thePony) / 2, 0, 553648127);
               GL11.glEnable(2896);
               GL11.glDisable(3042);
               GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
               GL11.glPopMatrix();
            }
         }
      }

   }

   @Override
   protected void renderDecoration(PlayerEntity par1EntityPlayer, float par2) {
      this.getModel(par1EntityPlayer).model.specials(this.dispatcher, par1EntityPlayer);
      if (par1EntityPlayer.name.equals("deadmau5") && this.bindHttpTexture(par1EntityPlayer.skin, (String)null)) {
         this.getModel(par1EntityPlayer).model.renderEars(par1EntityPlayer, par2);
      }

      if (this.bindHttpTexture(par1EntityPlayer.cloak, (String)null)) {
         this.getModel(par1EntityPlayer).model.renderCloak(par1EntityPlayer, par2);
      }

   }

   @Override
   protected void scale(PlayerEntity par1EntityPlayer, float par2) {
      float var3 = this.pm.globalscale;
      GL11.glScalef(var3, var3, var3);
   }

   @Override
   public void renderPlayerRightHandModel() {
      Pony.init();
      this.pm = PMAPI.human;
      this.pm.model.handSwingProgress = 0.0F;
      this.pm.model.animate(ani);
      this.pm.model.render(ani, false);
   }

   protected PlayerModel getModel(PlayerEntity entityplayer) {
      Pony pony = Pony.getPonyFromRegistry(entityplayer, this.dispatcher.textureManager);
      return pony.getModel();
   }

   public static int addNewArmourPrefix(String prefix) {
      List armours = new ArrayList(Arrays.asList(armorFilenamePrefix));
      armours.add(prefix);
      armorFilenamePrefix = (String[])armours.toArray(new String[0]);
      return armours.indexOf(prefix);
   }

   static {
      MineLPReflection.preCall();
      armorFilenamePrefix = new String[]{"cloth", "chain", "iron", "diamond", "gold"};
      ani = new AniParams(0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
   }
}
