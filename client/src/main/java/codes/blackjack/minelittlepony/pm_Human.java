package codes.blackjack.minelittlepony;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.model.ModelPart;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

public class pm_Human extends ModelPlayer {
   public ModelPart head;
   public ModelPart helmet;
   public ModelPart body;
   public ModelPart leftarm;
   public ModelPart rightarm;
   public ModelPart leftleg;
   public ModelPart rightleg;
   public ModelPart bipedEars;
   public ModelPart cloak;

   public pm_Human(String texture) {
      super(texture);
   }

   public void init() {
      this.init(0.0F);
   }

   public void init(float yoffset) {
      this.init(yoffset, 0.0F);
   }

   public void init(float yoffset, float stretch) {
      this.cloak = new ModelPart(this, 0, 0);
      this.cloak.addBox(-5.0F, 0.0F, -1.0F, 10, 16, 1, stretch);
      this.bipedEars = new ModelPart(this, 24, 0);
      this.bipedEars.addBox(-3.0F, -6.0F, -1.0F, 6, 6, 1, stretch);
      this.head = new ModelPart(this, 0, 0);
      this.head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, stretch);
      this.head.setPivot(0.0F, 0.0F + yoffset, 0.0F);
      this.helmet = new ModelPart(this, 32, 0);
      this.helmet.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, stretch + 0.5F);
      this.helmet.setPivot(0.0F, 0.0F + yoffset, 0.0F);
      this.body = new ModelPart(this, 16, 16);
      this.body.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, stretch);
      this.body.setPivot(0.0F, 0.0F + yoffset, 0.0F);
      this.rightarm = new ModelPart(this, 40, 16);
      this.rightarm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, stretch);
      this.rightarm.setPivot(-5.0F, 2.0F + yoffset, 0.0F);
      this.leftarm = new ModelPart(this, 40, 16);
      this.leftarm.flipped = true;
      this.leftarm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, stretch);
      this.leftarm.setPivot(5.0F, 2.0F + yoffset, 0.0F);
      this.rightleg = new ModelPart(this, 0, 16);
      this.rightleg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, stretch);
      this.rightleg.setPivot(-2.0F, 12.0F + yoffset, 0.0F);
      this.leftleg = new ModelPart(this, 0, 16);
      this.leftleg.flipped = true;
      this.leftleg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, stretch);
      this.leftleg.setPivot(2.0F, 12.0F + yoffset, 0.0F);
   }

   public void animate(AniParams ani, PlayerEntity player) {
      this.animate(ani);
   }

   public void animate(AniParams ani) {
      this.head.rotationY = ani.horz / 57.29578F;
      this.head.rotationX = ani.vert / 57.29578F;
      this.helmet.rotationY = this.head.rotationY;
      this.helmet.rotationX = this.head.rotationX;
      this.rightarm.rotationX = MathHelper.cos(ani.move * 2.0F / 3.0F + 3.141593F) * 2.0F * ani.swing * 0.5F;
      this.leftarm.rotationX = MathHelper.cos(ani.move * 2.0F / 3.0F) * 2.0F * ani.swing * 0.5F;
      this.rightarm.rotationZ = 0.0F;
      this.leftarm.rotationZ = 0.0F;
      this.rightleg.rotationX = MathHelper.cos(ani.move * 2.0F / 3.0F) * 1.4F * ani.swing;
      this.leftleg.rotationX = MathHelper.cos(ani.move * 2.0F / 3.0F + 3.141593F) * 1.4F * ani.swing;
      this.rightleg.rotationY = 0.0F;
      this.leftleg.rotationY = 0.0F;
      if (this.hasVehicle) {
	      ModelPart var10000 = this.rightarm;
         var10000.rotationX += -0.6283185F;
         var10000 = this.leftarm;
         var10000.rotationX += -0.6283185F;
         this.rightleg.rotationX = -1.256637F;
         this.leftleg.rotationX = -1.256637F;
         this.rightleg.rotationY = 0.3141593F;
         this.leftleg.rotationY = -0.3141593F;
      }

      if (this.heldItemRight != 0) {
         this.rightarm.rotationX = this.rightarm.rotationX * 0.5F - 0.3141593F;
      }

      this.rightarm.rotationY = 0.0F;
      this.leftarm.rotationY = 0.0F;
      if (this.handSwingProgress > -9990.0F) {
         float swingprogress = this.handSwingProgress;
         this.body.rotationY = MathHelper.sin(MathHelper.sqrt(swingprogress) * 3.141593F * 2.0F) * 0.2F;
         this.rightarm.pivotZ = MathHelper.sin(this.body.rotationY) * 5.0F;
         this.rightarm.pivotX = -MathHelper.cos(this.body.rotationY) * 5.0F;
         this.leftarm.pivotZ = -MathHelper.sin(this.body.rotationY) * 5.0F;
         this.leftarm.pivotX = MathHelper.cos(this.body.rotationY) * 5.0F;
	      ModelPart var13 = this.rightarm;
         var13.rotationY += this.body.rotationY;
         var13 = this.leftarm;
         var13.rotationY += this.body.rotationY;
         var13 = this.leftarm;
         var13.rotationY += this.body.rotationY;
         swingprogress = 1.0F - this.handSwingProgress;
         swingprogress *= swingprogress;
         swingprogress *= swingprogress;
         swingprogress = 1.0F - swingprogress;
         float f7 = MathHelper.sin(swingprogress * 3.141593F);
         float f8 = MathHelper.sin(this.handSwingProgress * 3.141593F) * -(this.head.rotationX - 0.7F) * 0.75F;
         var13 = this.rightarm;
         var13.rotationX = (float)((double)var13.rotationX - ((double)f7 * 1.2 + (double)f8));
         var13 = this.rightarm;
         var13.rotationY += this.body.rotationY * 2.0F;
         this.rightarm.rotationZ = MathHelper.sin(this.handSwingProgress * 3.141593F) * -0.4F;
      }

      if (this.issneak) {
         this.body.rotationX = 0.5F;
		 ModelPart var18 = this.rightleg;
         var18.rotationX -= 0.0F;
         var18 = this.leftleg;
         var18.rotationX -= 0.0F;
         var18 = this.rightarm;
         var18.rotationX += 0.4F;
         var18 = this.leftarm;
         var18.rotationX += 0.4F;
         this.rightleg.pivotZ = 4.0F;
         this.leftleg.pivotZ = 4.0F;
         this.rightleg.pivotY = 9.0F;
         this.leftleg.pivotY = 9.0F;
         this.head.pivotY = 1.0F;
      } else {
         this.body.rotationX = 0.0F;
         this.rightleg.pivotZ = 0.0F;
         this.leftleg.pivotZ= 0.0F;
         this.rightleg.pivotY = 12.0F;
         this.leftleg.pivotY = 12.0F;
         this.head.pivotY = 0.0F;
      }

	   ModelPart var22 = this.rightarm;
      var22.rotationZ += MathHelper.cos(ani.tick * 0.09F) * 0.05F + 0.05F;
      var22 = this.leftarm;
      var22.rotationZ -= MathHelper.cos(ani.tick * 0.09F) * 0.05F + 0.05F;
      var22 = this.rightarm;
      var22.rotationX += MathHelper.sin(ani.tick * 0.067F) * 0.05F;
      var22 = this.leftarm;
      var22.rotationX -= MathHelper.sin(ani.tick * 0.067F) * 0.05F;
      if (this.aimedBow) {
         float f7 = 0.0F;
         float f9 = 0.0F;
         this.rightarm.rotationZ = 0.0F;
         this.leftarm.rotationZ = 0.0F;
         this.rightarm.rotationY = -(0.1F - f7 * 0.6F) + this.head.rotationY;
         this.leftarm.rotationY = 0.1F - f7 * 0.6F + this.head.rotationY + 0.4F;
         this.rightarm.rotationX = -1.570796F + this.head.rotationX;
         this.leftarm.rotationX = -1.570796F + this.head.rotationX;
         var22 = this.rightarm;
         var22.rotationX -= f7 * 1.2F - f9 * 0.4F;
         var22 = this.leftarm;
         var22.rotationX -= f7 * 1.2F - f9 * 0.4F;
         float f2 = ani.tick;
         var22 = this.rightarm;
         var22.rotationZ += MathHelper.cos(f2 * 0.09F) * 0.05F + 0.05F;
         var22 = this.leftarm;
         var22.rotationZ -= MathHelper.cos(f2 * 0.09F) * 0.05F + 0.05F;
         var22 = this.rightarm;
         var22.rotationX += MathHelper.sin(f2 * 0.067F) * 0.05F;
         var22 = this.leftarm;
         var22.rotationX -= MathHelper.sin(f2 * 0.067F) * 0.05F;
      }

   }

   public void render(AniParams ani, boolean thirdperson) {
      if (thirdperson) {
         this.head.render(this.scale);
         this.helmet.render(this.scale);
         this.body.render(this.scale);
         this.leftarm.render(this.scale);
         this.leftleg.render(this.scale);
         this.rightleg.render(this.scale);
      }

      this.rightarm.render(this.scale);
   }

   public void specials(EntityRenderDispatcher renderman, PlayerEntity player) {
      this.renderDrop(renderman, player, this.rightarm, 1.0F, -0.0625F, 0.4375F, 0.0625F);
      this.renderPumpkin(renderman, player, this.head, 0.625F, 0.0F, -0.25F, 0.0F);
   }

   protected void renderEars(PlayerEntity player, float par2) {
      for(int i = 0; i < 2; ++i) {
		  // The unmapped ones are the head bob offset and prev
         float f1 = player.prevYaw + (player.yaw - player.prevYaw) * par2 - (player.be + (player.bd - player.be) * par2);
         float f2 = player.prevPitch + (player.pitch - player.prevPitch) * par2;
         GL11.glPushMatrix();
         GL11.glRotatef(f1, 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(f2, 1.0F, 0.0F, 0.0F);
         GL11.glTranslatef(0.375F * (float)(i * 2 - 1), 0.0F, 0.0F);
         GL11.glTranslatef(0.0F, -0.375F, 0.0F);
         GL11.glRotatef(-f2, 1.0F, 0.0F, 0.0F);
         GL11.glRotatef(-f1, 0.0F, 1.0F, 0.0F);
         float f7 = 1.333333F;
         GL11.glScalef(f7, f7, f7);
         this.bipedEars.rotationY = this.head.rotationY;
         this.bipedEars.rotationX = this.head.rotationX;
         this.bipedEars.pivotX = 0.0F;
         this.bipedEars.pivotY = 0.0F;
         this.bipedEars.render(0.0625F);
         GL11.glPopMatrix();
      }

   }

   protected void renderCloak(PlayerEntity player, float par2) {
      GL11.glPushMatrix();
      GL11.glTranslatef(0.0F, 0.0F, 0.125F);
      /*double d = player.aE + (player.aH - player.aE) * (double)par2 - (player.l + (player.o - player.l) * (double)par2);
      double d1 = player.aF + (player.aI - player.aF) * (double)par2 - (player.m + (player.p - player.m) * (double)par2);
      double d2 = player.aG + (player.aJ - player.aG) * (double)par2 - (player.n + (player.q - player.n) * (double)par2);*/
	   double d = player.lastCapeX + (player.capeX - player.lastCapeX) * (double)par2 - (player.prevX + (player.x - player.prevX) * (double)par2);
	   double d1 = player.lastCapeY + (player.capeY - player.lastCapeY) * (double)par2 - (player.prevY + (player.y - player.prevY) * (double)par2);
	   double d2 = player.lastCapeZ + (player.capeZ - player.lastCapeZ) * (double)par2 - (player.prevZ + (player.z - player.prevZ) * (double)par2);
      float f10 = player.be + (player.bd - player.be) * par2;
      double d3 = (double)MathHelper.sin(f10 * (float)Math.PI / 180.0F);
      double d4 = (double)(-MathHelper.cos(f10 * (float)Math.PI / 180.0F));
      float f12 = (float)d1 * 10.0F;
      if (f12 < -6.0F) {
         f12 = -6.0F;
      }

      if (f12 > 32.0F) {
         f12 = 32.0F;
      }

      float f13 = (float)(d * d3 + d2 * d4) * 100.0F;
      float f14 = (float)(d * d4 - d2 * d3) * 100.0F;
      if (f13 < 0.0F) {
         f13 = 0.0F;
      }

	   float f15 = player.prevHeadYaw + (player.headYaw - player.prevHeadYaw) * par2;
	   f12 += MathHelper.sin((player.prevStrideDistance + (player.strideDistance - player.prevStrideDistance) * par2) * 6.0F) * 32.0F * f15;
      if (player.isSneaking()) {
         f12 += 25.0F;
      }

      GL11.glRotatef(6.0F + f13 / 2.0F + f12, 1.0F, 0.0F, 0.0F);
      GL11.glRotatef(f14 / 2.0F, 0.0F, 0.0F, 1.0F);
      GL11.glRotatef(-f14 / 2.0F, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
      this.cloak.render(0.0625F);
      GL11.glPopMatrix();
   }
}
