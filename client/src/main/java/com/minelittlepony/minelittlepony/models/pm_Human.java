package com.minelittlepony.minelittlepony.models;

import com.minelittlepony.minelittlepony.AniParams;
import com.minelittlepony.minelittlepony.render.ModelPlayer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.model.ModelPart;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

@SuppressWarnings("SuspiciousNameCombination")
public class pm_Human extends ModelPlayer {
	public ModelPart head;
	public ModelPart helmet;
	public ModelPart body;
	public ModelPart leftArm;
	public ModelPart rightArm;
	public ModelPart leftLeg;
	public ModelPart rightLeg;
	private ModelPart bipedEars;
	private ModelPart cloak;

	public void init() {
		this.init(0.0F);
	}

	public void init(float yOffset) {
		this.init(yOffset, 0.0F);
	}

	public void init(float yOffset, float stretch) {
		this.cloak = new ModelPart(this, 0, 0);
		this.cloak.addBox(-5.0F, 0.0F, -1.0F, 10, 16, 1, stretch);
		this.bipedEars = new ModelPart(this, 24, 0);
		this.bipedEars.addBox(-3.0F, -6.0F, -1.0F, 6, 6, 1, stretch);
		this.head = new ModelPart(this, 0, 0);
		this.head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, stretch);
		this.head.setPivot(0.0F, 0.0F + yOffset, 0.0F);
		this.helmet = new ModelPart(this, 32, 0);
		this.helmet.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, stretch + 0.5F);
		this.helmet.setPivot(0.0F, 0.0F + yOffset, 0.0F);
		this.body = new ModelPart(this, 16, 16);
		this.body.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, stretch);
		this.body.setPivot(0.0F, 0.0F + yOffset, 0.0F);
		this.rightArm = new ModelPart(this, 40, 16);
		this.rightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, stretch);
		this.rightArm.setPivot(-5.0F, 2.0F + yOffset, 0.0F);
		this.leftArm = new ModelPart(this, 40, 16);
		this.leftArm.flipped = true;
		this.leftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, stretch);
		this.leftArm.setPivot(5.0F, 2.0F + yOffset, 0.0F);
		this.rightLeg = new ModelPart(this, 0, 16);
		this.rightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, stretch);
		this.rightLeg.setPivot(-2.0F, 12.0F + yOffset, 0.0F);
		this.leftLeg = new ModelPart(this, 0, 16);
		this.leftLeg.flipped = true;
		this.leftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, stretch);
		this.leftLeg.setPivot(2.0F, 12.0F + yOffset, 0.0F);
	}

	public void animate(AniParams ani, PlayerEntity player) {
		this.animate(ani);
	}

	public void animate(AniParams ani) {
		this.head.rotationY = ani.horz / 57.29578F;
		this.head.rotationX = ani.vert / 57.29578F;
		this.helmet.rotationY = this.head.rotationY;
		this.helmet.rotationX = this.head.rotationX;
		this.rightArm.rotationX = MathHelper.cos(ani.move * 2.0F / 3.0F + 3.141593F) * 2.0F * ani.swing * 0.5F;
		this.leftArm.rotationX = MathHelper.cos(ani.move * 2.0F / 3.0F) * 2.0F * ani.swing * 0.5F;
		this.rightArm.rotationZ = 0.0F;
		this.leftArm.rotationZ = 0.0F;
		this.rightLeg.rotationX = MathHelper.cos(ani.move * 2.0F / 3.0F) * 1.4F * ani.swing;
		this.leftLeg.rotationX = MathHelper.cos(ani.move * 2.0F / 3.0F + 3.141593F) * 1.4F * ani.swing;
		this.rightLeg.rotationY = 0.0F;
		this.leftLeg.rotationY = 0.0F;
		if (this.hasVehicle) {
			this.rightArm.rotationX -= 0.6283185F;
			this.leftArm.rotationX -= 0.6283185F;
			this.rightLeg.rotationX = -1.256637F;
			this.leftLeg.rotationX = -1.256637F;
			this.rightLeg.rotationY = 0.3141593F;
			this.leftLeg.rotationY = -0.3141593F;
		}

		if (this.heldItemRight != 0) {
			this.rightArm.rotationX = this.rightArm.rotationX * 0.5F - 0.3141593F;
		}

		this.rightArm.rotationY = 0.0F;
		this.leftArm.rotationY = 0.0F;
		if (this.handSwingProgress > -9990.0F) {
			this.body.rotationY = MathHelper.sin(MathHelper.sqrt(this.handSwingProgress) * 3.141593F * 2.0F) * 0.2F;
			this.rightArm.pivotZ = MathHelper.sin(this.body.rotationY) * 5.0F;
			this.rightArm.pivotX = -MathHelper.cos(this.body.rotationY) * 5.0F;
			this.leftArm.pivotZ = -MathHelper.sin(this.body.rotationY) * 5.0F;
			this.leftArm.pivotX = MathHelper.cos(this.body.rotationY) * 5.0F;
			this.rightArm.rotationY += this.body.rotationY;
			this.leftArm.rotationY += this.body.rotationY;
			this.leftArm.rotationY += this.body.rotationY;

			float swingProgress = 1.0F - this.handSwingProgress;
			swingProgress *= swingProgress;
			swingProgress *= swingProgress;
			swingProgress = 1.0F - swingProgress;

			float f7 = MathHelper.sin(swingProgress * 3.141593F);
			float f8 = MathHelper.sin(this.handSwingProgress * 3.141593F) * -(this.head.rotationX - 0.7F) * 0.75F;
			this.rightArm.rotationX = (float) (this.rightArm.rotationX - (f7 * 1.2 + f8));
			this.rightArm.rotationZ = MathHelper.sin(this.handSwingProgress * 3.141593F) * -0.4F;
		}

		if (this.isSneaking) {
			this.body.rotationX = 0.5F;
			this.rightLeg.rotationX -= 0.0F;
			this.leftLeg.rotationX -= 0.0F;
			this.rightArm.rotationX += 0.4F;
			this.leftArm.rotationX += 0.4F;
			this.rightLeg.pivotZ = 4.0F;
			this.leftLeg.pivotZ = 4.0F;
			this.rightLeg.pivotY = 9.0F;
			this.leftLeg.pivotY = 9.0F;
			this.head.pivotY = 1.0F;
		} else {
			this.body.rotationX = 0.0F;
			this.rightLeg.pivotZ = 0.0F;
			this.leftLeg.pivotZ = 0.0F;
			this.rightLeg.pivotY = 12.0F;
			this.leftLeg.pivotY = 12.0F;
			this.head.pivotY = 0.0F;
		}

		this.rightArm.rotationZ += MathHelper.cos(ani.tick * 0.09F) * 0.05F + 0.05F;
		this.leftArm.rotationZ -= MathHelper.cos(ani.tick * 0.09F) * 0.05F + 0.05F;
		this.rightArm.rotationX += MathHelper.sin(ani.tick * 0.067F) * 0.05F;
		this.leftArm.rotationX -= MathHelper.sin(ani.tick * 0.067F) * 0.05F;
		if (this.aimedBow) {
			float f7 = 0.0F;
			float f9 = 0.0F;
			this.rightArm.rotationZ = 0.0F;
			this.leftArm.rotationZ = 0.0F;
			this.rightArm.rotationY = -(0.1F - f7 * 0.6F) + this.head.rotationY;
			this.leftArm.rotationY = 0.1F - f7 * 0.6F + this.head.rotationY + 0.4F;
			this.rightArm.rotationX = -1.570796F + this.head.rotationX;
			this.leftArm.rotationX = -1.570796F + this.head.rotationX;
			this.rightArm.rotationX -= f7 * 1.2F - f9 * 0.4F;
			this.leftArm.rotationX -= f7 * 1.2F - f9 * 0.4F;
			float f2 = ani.tick;
			this.rightArm.rotationZ += MathHelper.cos(f2 * 0.09F) * 0.05F + 0.05F;
			this.leftArm.rotationZ -= MathHelper.cos(f2 * 0.09F) * 0.05F + 0.05F;
			this.rightArm.rotationX += MathHelper.sin(f2 * 0.067F) * 0.05F;
			this.leftArm.rotationX -= MathHelper.sin(f2 * 0.067F) * 0.05F;
		}

	}

	public void render(AniParams ani, boolean thirdPerson) {
		if (thirdPerson) {
			this.head.render(this.scale);
			this.helmet.render(this.scale);
			this.body.render(this.scale);
			this.leftArm.render(this.scale);
			this.leftLeg.render(this.scale);
			this.rightLeg.render(this.scale);
		}

		this.rightArm.render(this.scale);
	}

	public void specials(EntityRenderDispatcher renderman, PlayerEntity player) {
		this.renderDrop(renderman, player, this.rightArm, 1.0F, -0.0625F, 0.4375F, 0.0625F);
		this.renderPumpkin(renderman, player, this.head, 0.625F, 0.0F, -0.25F, 0.0F);
	}

	protected void renderEars(PlayerEntity player, float par2) {
		for (int i = 0; i < 2; ++i) {
			// TODO: These yaws might be wrong!!!
			float f1 = player.prevYaw + (player.yaw - player.prevYaw) * par2 - (player.prevBodyYaw + (player.bodyYaw - player.prevBodyYaw) * par2);
			float f2 = player.prevPitch + (player.pitch - player.prevPitch) * par2;
			GL11.glPushMatrix();
			GL11.glRotatef(f1, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(f2, 1.0F, 0.0F, 0.0F);
			GL11.glTranslatef(0.375F * (i * 2 - 1), 0.0F, 0.0F);
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
		double d = player.lastCapeX + (player.capeX - player.lastCapeX) * par2 - (player.prevX + (player.x - player.prevX) * par2);
		double d1 = player.lastCapeY + (player.capeY - player.lastCapeY) * par2 - (player.prevY + (player.y - player.prevY) * par2);
		double d2 = player.lastCapeZ + (player.capeZ - player.lastCapeZ) * par2 - (player.prevZ + (player.z - player.prevZ) * par2);
		float f10 = player.prevBodyYaw + (player.bodyYaw - player.prevBodyYaw) * par2;
		double d3 = MathHelper.sin(f10 * (float) Math.PI / 180.0F);
		double d4 = (-MathHelper.cos(f10 * (float) Math.PI / 180.0F));
		float f12 = (float) d1 * 10.0F;
		if (f12 < -6.0F) {
			f12 = -6.0F;
		}

		if (f12 > 32.0F) {
			f12 = 32.0F;
		}

		float f13 = (float) (d * d3 + d2 * d4) * 100.0F;
		float f14 = (float) (d * d4 - d2 * d3) * 100.0F;
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
