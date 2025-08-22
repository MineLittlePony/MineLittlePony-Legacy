package codes.blackjack.minelittlepony.models;

import codes.blackjack.minelittlepony.AniParams;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.model.ModelPart;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;

public class pm_newPonyArmor extends pm_newPonyAdv {
	public ModelPart Bodypiece;
	public ModelPart extBody;
	public ModelPart[] extHead;
	public ModelPart[] extLegs;

	public pm_newPonyArmor() {
		super();
		this.isArmour = true;
	}

	public void animate(AniParams aniparams) {
		this.checkRainboom(aniparams.swing);
		this.rotateHead(aniparams.horz, aniparams.vert);
		float bodySwingRotation = 0.0F;
		if (this.handSwingProgress > -9990.0F && !this.isUnicorn) {
			bodySwingRotation = MathHelper.sin(MathHelper.sqrt(this.handSwingProgress) * 3.141593F * 2.0F) * 0.2F;
		}

		this.Body.rotationY = bodySwingRotation * 0.2F;
		this.Bodypiece.rotationY = bodySwingRotation * 0.2F;
		this.extBody.rotationY = bodySwingRotation * 0.2F;
		this.setLegs(aniparams.move, aniparams.swing);
		this.holdItem();
		this.swingItem(this.handSwingProgress);
		if (this.isSneaking && !this.isFlying) {
			this.adjustBody(BODY_ROTATE_ANGLE_X_SNEAK, BODY_RP_Y_SNEAK, BODY_RP_Z_SNEAK);
			this.sneakLegs();
			this.setHead(0.0F, 6.0F, -2.0F);
		} else {
			this.adjustBody(BODY_ROTATE_ANGLE_X_NOTSNEAK, BODY_RP_Y_NOTSNEAK, BODY_RP_Z_NOTSNEAK);
			this.rightLeg.pivotY = FRONT_LEG_RP_Y_NOTSNEAK;
			this.leftLeg.pivotY = FRONT_LEG_RP_Y_NOTSNEAK;
			this.extLegs[0].pivotY = FRONT_LEG_RP_Y_NOTSNEAK;
			this.extLegs[1].pivotY = FRONT_LEG_RP_Y_NOTSNEAK;
			this.swingArms(aniparams.tick);
			this.setHead(0.0F, 0.0F, 0.0F);
		}

		if (this.isSleeping) {
			this.ponySleep();
		}

		if (this.aimedBow) {
			this.aimBow(aniparams.tick);
		}

	}

	protected void setHead(float posX, float posY, float posZ) {
		this.setRotationPoint(this.head, posX, posY, posZ);
		this.setRotationPoint(this.extHead[0], posX, posY, posZ);
		this.setRotationPoint(this.extHead[1], posX, posY, posZ);
	}

	protected void rotateHead(float horz, float vert) {
		float headRotateAngleY;
		float headRotateAngleX;
		if (this.isSleeping) {
			headRotateAngleY = 1.4F;
			headRotateAngleX = 0.1F;
		} else {
			headRotateAngleY = horz / 57.29578F;
			headRotateAngleX = vert / 57.29578F;
		}

		if (headRotateAngleX > 0.5F) {
			headRotateAngleX = 0.5F;
		}

		if (headRotateAngleX < -0.5F) {
			headRotateAngleX = -0.5F;
		}

		this.head.rotationY = headRotateAngleY;
		this.head.rotationX = headRotateAngleX;
		this.extHead[0].rotationY = headRotateAngleY;
		this.extHead[0].rotationX = headRotateAngleX;
		this.extHead[1].rotationY = headRotateAngleY;
		this.extHead[1].rotationX = headRotateAngleX;
	}

	protected void adjustBody(float rotateAngleX, float rotationPointY, float rotationPointZ) {
		this.Body.rotationX = rotateAngleX;
		this.Body.pivotY = rotationPointY;
		this.Body.pivotZ = rotationPointZ;
		this.Bodypiece.rotationX = rotateAngleX;
		this.Bodypiece.pivotY = rotationPointY;
		this.Bodypiece.pivotZ = rotationPointZ;
		this.extBody.rotationX = rotateAngleX;
		this.extBody.pivotY = rotationPointY;
		this.extBody.pivotZ = rotationPointZ;
	}

	protected void ridingPony() {
		this.setHead(this.head.pivotX + 0.0F, this.head.pivotY + RIDING_SHIFT_Y, this.head.pivotZ + RIDING_SHIFT_Z);
		this.shiftRotationPoint(this.Body, 0.0F, RIDING_SHIFT_Y, RIDING_SHIFT_Z);
		this.shiftRotationPoint(this.Bodypiece, 0.0F, RIDING_SHIFT_Y, RIDING_SHIFT_Z);
		this.shiftRotationPoint(this.extBody, 0.0F, RIDING_SHIFT_Y, RIDING_SHIFT_Z);
		this.shiftRotationPoint(this.leftArm, 0.0F, RIDING_SHIFT_Y, RIDING_SHIFT_Z);
		this.shiftRotationPoint(this.rightarm, 0.0F, RIDING_SHIFT_Y, RIDING_SHIFT_Z);
		this.shiftRotationPoint(this.leftLeg, 0.0F, RIDING_SHIFT_Y, RIDING_SHIFT_Z);
		this.shiftRotationPoint(this.rightLeg, 0.0F, RIDING_SHIFT_Y, RIDING_SHIFT_Z);
		this.shiftRotationPoint(this.extLegs[0], 0.0F, RIDING_SHIFT_Y, RIDING_SHIFT_Z);
		this.shiftRotationPoint(this.extLegs[1], 0.0F, RIDING_SHIFT_Y, RIDING_SHIFT_Z);
	}

	protected void renderHead() {
		this.head.render(this.scale);
		this.extHead[0].render(this.scale);
		this.extHead[1].render(this.scale);
	}

	protected void renderNeck() {
	}

	protected void renderBody() {
		this.Body.render(this.scale);
		this.Bodypiece.render(this.scale);
		this.extBody.render(this.scale);
	}

	protected void renderTail() {
	}

	protected void renderLegs() {
		this.leftArm.render(this.scale);
		this.rightarm.render(this.scale);
		this.leftLeg.render(this.scale);
		this.rightLeg.render(this.scale);
		this.extLegs[0].render(this.scale);
		this.extLegs[1].render(this.scale);
	}

	protected void initTextures() {
		this.extHead = new ModelPart[2];
		this.extLegs = new ModelPart[2];
		this.initHeadTextures();
		this.initBodyTextures();
		this.initLegTextures();
	}

	protected void initHeadTextures() {
		this.head = new ModelPart(this, 0, 0);
		this.extHead[0] = new ModelPart(this, 0, 0);
		this.extHead[1] = new ModelPart(this, 0, 4);
	}

	protected void initBodyTextures() {
		this.Body = new ModelPart(this, 16, 16);
		this.Bodypiece = new ModelPart(this, 0, 0);
		this.extBody = new ModelPart(this, 16, 8);
	}

	protected void initLegTextures() {
		this.rightarm = new ModelPart(this, 0, 16);
		this.leftArm = new ModelPart(this, 0, 16);
		this.leftArm.flipped = true;
		this.rightLeg = new ModelPart(this, 0, 16);
		this.leftLeg = new ModelPart(this, 0, 16);
		this.leftLeg.flipped = true;
		this.steveArm = new ModelPart(this, 0, 16);
		this.unicornArm = new ModelPart(this, 0, 16);
		this.extLegs[0] = new ModelPart(this, 48, 8);
		this.extLegs[1] = new ModelPart(this, 48, 8);
		this.extLegs[1].flipped = true;
	}

	protected void initPositions(float yOffset, float stretch) {
		this.initHeadPositions(yOffset, stretch);
		this.initBodyPositions(yOffset, stretch);
		this.initLegPositions(yOffset, stretch);
	}

	protected void initHeadPositions(float yOffset, float stretch) {
		this.head.addBox(-4.0F + HEAD_CENTRE_X, -4.0F + HEAD_CENTRE_Y, -4.0F + HEAD_CENTRE_Z, 8, 8, 8, stretch * 1.1F);
		this.head.setPivot(HEAD_RP_X, HEAD_RP_Y + yOffset, HEAD_RP_Z);
		this.extHead[0].addBox(-4.0F + HEAD_CENTRE_X, -6.0F + HEAD_CENTRE_Y, 1.0F + HEAD_CENTRE_Z, 2, 2, 2, stretch * 0.5F);
		this.extHead[0].setPivot(HEAD_RP_X, HEAD_RP_Y + yOffset, HEAD_RP_Z);
		this.extHead[1].addBox(2.0F + HEAD_CENTRE_X, -6.0F + HEAD_CENTRE_Y, 1.0F + HEAD_CENTRE_Z, 2, 2, 2, stretch * 0.5F);
		this.extHead[1].setPivot(HEAD_RP_X, HEAD_RP_Y + yOffset, HEAD_RP_Z);
	}

	protected void initBodyPositions(float yOffset, float stretch) {
		this.Body.addBox(-4.0F, 4.0F, -2.0F, 8, 8, 4, stretch);
		this.Body.setPivot(HEAD_RP_X, HEAD_RP_Y + yOffset, HEAD_RP_Z);
		this.Bodypiece.addBox(-4.0F, 4.0F, 6.0F, 8, 8, 8, stretch);
		this.Bodypiece.setPivot(HEAD_RP_X, HEAD_RP_Y + yOffset, HEAD_RP_Z);
		this.extBody.addBox(-4.0F, 4.0F, -2.0F, 8, 8, 16, stretch);
		this.extBody.setPivot(HEAD_RP_X, HEAD_RP_Y + yOffset, HEAD_RP_Z);
	}

	protected void initLegPositions(float yOffset, float stretch) {
		super.initLegPositions(yOffset, stretch);
		this.extLegs[0].addBox(-2.0F + THIRDP_ARM_CENTRE_X, -6.0F + THIRDP_ARM_CENTRE_Y, -2.0F + THIRDP_ARM_CENTRE_Z, 4, 12, 4, stretch);
		this.extLegs[0].setPivot(-3.0F, 0.0F + yOffset, 0.0F);
		this.extLegs[1].addBox(-2.0F + THIRDP_ARM_CENTRE_X, -6.0F + THIRDP_ARM_CENTRE_Y, -2.0F + THIRDP_ARM_CENTRE_Z, 4, 12, 4, stretch);
		this.extLegs[1].setPivot(3.0F, 0.0F + yOffset, 0.0F);
	}

	private void syncLegs() {
		this.extLegs[0].rotationX = this.rightLeg.rotationX;
		this.extLegs[0].rotationY = this.rightLeg.rotationY;
		this.extLegs[0].rotationZ = this.rightLeg.rotationZ;
		this.extLegs[0].pivotX = this.rightLeg.pivotX;
		this.extLegs[0].pivotY = this.rightLeg.pivotY;
		this.extLegs[0].pivotZ = this.rightLeg.pivotZ;
		this.extLegs[1].rotationX = this.leftLeg.rotationX;
		this.extLegs[1].rotationY = this.leftLeg.rotationY;
		this.extLegs[1].rotationZ = this.leftLeg.rotationZ;
		this.extLegs[1].pivotX = this.leftLeg.pivotX;
		this.extLegs[1].pivotY = this.leftLeg.pivotY;
		this.extLegs[1].pivotZ = this.leftLeg.pivotZ;
	}

	protected void rotateLegs(float move, float swing) {
		super.rotateLegs(move, swing);
		this.syncLegs();
	}

	protected void adjustLegs() {
		super.adjustLegs();
		this.syncLegs();
	}

	protected void sneakLegs() {
		super.sneakLegs();
		this.syncLegs();
	}

	protected void ponySleep() {
		super.ponySleep();
		this.syncLegs();
	}

	public void specials(EntityRenderDispatcher dispatcher, PlayerEntity player) {
		if (!this.isSleeping) {
			this.renderDrop(dispatcher, player, this.rightarm, 1.0F, -0.0625F, 0.8375F, 0.0625F);
		}

		this.renderPumpkin(dispatcher, player, this.head, 0.625F, 0.0F, -0.25F, 0.0F);
	}
}
