package codes.blackjack.minelittlepony.models;

import codes.blackjack.minelittlepony.AniParams;
import codes.blackjack.minelittlepony.Pony;
import codes.blackjack.minelittlepony.config.PonySettings;
import codes.blackjack.minelittlepony.render.MineLPGlow;
import codes.blackjack.minelittlepony.render.ModelPlayer;
import codes.blackjack.minelittlepony.render.PlaneRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.model.ModelPart;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

public class pm_newPonyAdv extends ModelPlayer {
	protected static final float HEAD_CENTRE_X = 0.0F;
	protected static final float HEAD_CENTRE_Y = -1.0F;
	protected static final float HEAD_CENTRE_Z = -2.0F;
	protected static final float BODY_CENTRE_X = 0.0F;
	protected static final float BODY_CENTRE_Y = 8.0F;
	protected static final float BODY_CENTRE_Z = 6.0F;
	protected static final  float THIRDP_ARM_CENTRE_X = 0.0F;
	protected static final float THIRDP_ARM_CENTRE_Y = 10.0F;
	protected static final float THIRDP_ARM_CENTRE_Z = 0.0F;
	protected static final float FIRSTP_ARM_CENTRE_X = -1.0F;
	protected static final float FIRSTP_ARM_CENTRE_Y = 4.0F;
	protected static final float FIRSTP_ARM_CENTRE_Z = 0.0F;
	protected static final float HEAD_RP_X = 0.0F;
	protected static final float HEAD_RP_Y = 0.0F;
	protected static final float HEAD_RP_Z = 0.0F;
	protected static final float BODY_RP_Y_SNEAK = 7.0F;
	protected static final float BODY_RP_Y_NOTSNEAK = 0.0F;
	protected static final float BODY_RP_Z_SNEAK = -4.0F;
	protected static final float BODY_RP_Z_NOTSNEAK = 0.0F;
	protected static final float FRONT_LEG_RP_Y_NOTSNEAK = 8.0F;
	protected static final float BODY_ROTATE_ANGLE_X_SNEAK = 0.4F;
	protected static final float BODY_ROTATE_ANGLE_X_NOTSNEAK = 0.0F;
	protected static final float RIDING_SHIFT_Y = -10.0F;
	protected static final float RIDING_SHIFT_Z = -10.0F;
	private static final float FRONT_LEG_RP_Y_SNEAK = 7.0F;
	private static final float WING_FOLDED_RP_Y = 13.0F;
	private static final float WING_FOLDED_RP_Z = -3.0F;
	private static final float LEFT_WING_RP_Y_SNEAK = 10.5F;
	private static final float LEFT_WING_RP_Y_NOTSNEAK = 5.5F;
	private static final float LEFT_WING_RP_Z_SNEAK = 2.0F;
	private static final float LEFT_WING_RP_Z_NOTSNEAK = 3.0F;
	private static final float RIGHT_WING_RP_Y_SNEAK = 11.5F;
	private static final float RIGHT_WING_RP_Y_NOTSNEAK = 6.5F;
	private static final float RIGHT_WING_RP_Z_SNEAK = 2.0F;
	private static final float RIGHT_WING_RP_Z_NOTSNEAK = 3.0F;
	private static final float TAIL_RP_X = 0.0F;
	private static final float TAIL_RP_Y = 0.8F;
	private static final float TAIL_RP_Z = 0.0F;
	private static final float TAIL_RP_Z_SNEAK = 10.0F;
	private static final float TAIL_RP_Z_NOTSNEAK = 14.0F;
	private static final float LEFT_WING_EXT_RP_X = 4.5F;
	private static final float LEFT_WING_EXT_RP_Y = 5.0F;
	private static final float LEFT_WING_EXT_RP_Z = 6.0F;
	private static final float RIGHT_WING_EXT_RP_X = -4.5F;
	private static final float RIGHT_WING_EXT_RP_Y = 5.0F;
	private static final float RIGHT_WING_EXT_RP_Z = 6.0F;
	private static final float EXT_WING_ROTATE_ANGLE_X = 2.5F;
	private static final float LEFT_WING_ROTATE_ANGLE_Z_SNEAK = -6.0F;
	private static final float RIGHT_WING_ROTATE_ANGLE_Z_SNEAK = 6.0F;
	private static final float SNEAK_LEG_X_ROTATION_ADJUSTMENT = 0.4F;
	private static final float ROTATE_270 = 4.712F;
	private static final float ROTATE_90 = 1.571F;

	public ModelPart head;
	public ModelPart Body;
	public ModelPart rightarm;
	public ModelPart leftArm;
	public ModelPart rightLeg;
	public ModelPart leftLeg;
	protected final float NeckRotX = 0.166F;
	protected ModelPart cloak;
	protected ModelPart[] headpiece;
	protected ModelPart helmet;
	protected PlaneRenderer[] bodyPiece;
	protected PlaneRenderer[] bodyPieceNeck;
	protected ModelPart steveArm;
	protected ModelPart unicornArm;
	private boolean rainboom;
	private int tailstop;
	private PlaneRenderer[] muzzleFemale;
	private PlaneRenderer[] muzzleMale;
	private PlaneRenderer[] tail;
	private ModelPart[] leftWing;
	private ModelPart[] rightWing;
	private ModelPart[] leftWingExt;
	private ModelPart[] rightWingExt;

	@Override
	public void init() {
		this.init(0.0F);
	}

	@Override
	public void init(float yOffset) {
		this.init(yOffset, 0.0F);
	}

	@Override
	public void init(float yOffset, float stretch) {
		this.initTextures();
		this.initPositions(yOffset, stretch);
	}

	@Override
	public void animate(AniParams aniparams, PlayerEntity player) {
		this.animate(aniparams);
	}

	@Override
	public void animate(AniParams aniparams) {
		this.checkRainboom(aniparams.swing);
		this.rotateHead(aniparams.horz, aniparams.vert);
		this.swingTailZ(aniparams.move, aniparams.swing);
		float bodySwingRotation = 0.0F;

		if (this.handSwingProgress > -9990.0F && !this.isUnicorn) {
			bodySwingRotation = MathHelper.sin(MathHelper.sqrt(this.handSwingProgress) /* might be floor??? */ * 3.141593F * 2.0F) * 0.2F;
		}

		this.Body.rotationY = bodySwingRotation * 0.2F;

		for (PlaneRenderer renderer : this.bodyPiece) {
			renderer.rotateAngleY = bodySwingRotation * 0.2F;
		}

		for (PlaneRenderer planeRenderer : this.bodyPieceNeck) {
			planeRenderer.rotateAngleY = bodySwingRotation * 0.2F;
		}

		for (ModelPart part : this.leftWing) {
			part.rotationY = bodySwingRotation * 0.2F;
		}

		for (ModelPart modelPart : this.rightWing) {
			modelPart.rotationY = bodySwingRotation * 0.2F;
		}

		this.tailstop = this.tail.length - this.wantTail * 5;
		if (this.tailstop <= 1) {
			this.tailstop = 0;
		}

		for (int j1 = 0; j1 < this.tailstop; ++j1) {
			this.tail[j1].rotateAngleY = bodySwingRotation;
		}

		this.setLegs(aniparams.move, aniparams.swing);
		this.holdItem();
		this.swingItem(this.handSwingProgress);
		if (this.isSneaking && !this.isFlying) {
			this.adjustBody(BODY_ROTATE_ANGLE_X_SNEAK, BODY_RP_Y_SNEAK, BODY_RP_Z_SNEAK);
			this.animatePegasusWingsSneaking();
			this.sneakLegs();
			this.setHead(0.0F, 6.0F, -2.0F);
			this.sneakTail();
		} else {
			this.adjustBody(BODY_ROTATE_ANGLE_X_NOTSNEAK, BODY_RP_Y_NOTSNEAK, BODY_RP_Z_NOTSNEAK);
			if (this.isPegasus) {
				this.animatePegasusWingsNotSneaking(aniparams.tick);
			}

			this.rightLeg.pivotY = FRONT_LEG_RP_Y_NOTSNEAK;
			this.leftLeg.pivotY = FRONT_LEG_RP_Y_NOTSNEAK;
			this.swingArms(aniparams.tick);
			this.setHead(0.0F, 0.0F, 0.0F);
			this.tailstop = this.tail.length - this.wantTail * 5;
			if (this.tailstop <= 1) {
				this.tailstop = 0;
			}

			for (int k6 = 0; k6 < this.tailstop; ++k6) {
				this.setRotationPoint(this.tail[k6], TAIL_RP_X, TAIL_RP_Y, TAIL_RP_Z_NOTSNEAK);
				if (this.rainboom) {
					this.tail[k6].rotateAngleX = ROTATE_90 + 0.1F * MathHelper.sin(aniparams.move);
				} else {
					this.tail[k6].rotateAngleX = 0.5F * aniparams.swing;
				}
			}

			if (!this.rainboom) {
				this.swingTailX(aniparams.tick);
			}
		}

		if (this.rainboom) {
			this.tailstop = this.tail.length - this.wantTail * 5;
			if (this.tailstop <= 1) {
				this.tailstop = 0;
			}

			for (int k1 = 0; k1 < this.tailstop; ++k1) {
				this.tail[k1].rotationPointY += 6.0F;
				++this.tail[k1].rotationPointZ;
			}
		}

		if (this.isSleeping) {
			this.ponySleep();
		}

		if (this.aimedBow) {
			this.aimBow(aniparams.tick);
		}

		this.fixSpecialRotations();
	}

	protected void checkRainboom(float swing) {
		this.rainboom = this.isPegasus && this.isFlying && swing >= 0.9999F;

	}

	protected void setHead(float posX, float posY, float posZ) {
		this.setRotationPoint(this.head, posX, posY, posZ);
		this.setRotationPoint(this.helmet, posX, posY, posZ);

		for (ModelPart modelPart : this.headpiece) {
			this.setRotationPoint(modelPart, posX, posY, posZ);
		}

		if (this.isMale) {
			for (PlaneRenderer planeRenderer : this.muzzleMale) {
				this.setRotationPoint(planeRenderer, posX, posY, posZ);
			}
		} else {
			for (PlaneRenderer planeRenderer : this.muzzleFemale) {
				this.setRotationPoint(planeRenderer, posX, posY, posZ);
			}
		}

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
		if (this.isMale) {
			for (PlaneRenderer planeRenderer : this.muzzleMale) {
				planeRenderer.rotateAngleY = headRotateAngleY;
				planeRenderer.rotateAngleX = headRotateAngleX;
			}
		} else {
			for (PlaneRenderer planeRenderer : this.muzzleFemale) {
				planeRenderer.rotateAngleY = headRotateAngleY;
				planeRenderer.rotateAngleX = headRotateAngleX;
			}
		}

		this.headpiece[0].rotationY = headRotateAngleY;
		this.headpiece[0].rotationX = headRotateAngleX;
		this.headpiece[1].rotationY = headRotateAngleY;
		this.headpiece[1].rotationX = headRotateAngleX;
		this.headpiece[2].rotationY = headRotateAngleY;
		this.headpiece[2].rotationX = headRotateAngleX;
		this.helmet.rotationY = headRotateAngleY;
		this.helmet.rotationX = headRotateAngleX;
		this.headpiece[2].rotationX = headRotateAngleX + 0.5F;
	}

	protected void setLegs(float move, float swing) {
		this.rotateLegs(move, swing);
		this.adjustLegs();
	}

	protected void rotateLegs(float move, float swing) {
		float rightArmRotateAngleX;
		float leftArmRotateAngleX;
		float rightLegRotateAngleX;
		float leftLegRotateAngleX;
		if (this.isFlying && this.isPegasus) {
			if (this.rainboom) {
				rightArmRotateAngleX = ROTATE_270;
				leftArmRotateAngleX = ROTATE_270;
				rightLegRotateAngleX = ROTATE_90;
				leftLegRotateAngleX = ROTATE_90;
			} else {
				rightArmRotateAngleX = MathHelper.sin(0.0F - swing * 0.5F);
				leftArmRotateAngleX = MathHelper.sin(0.0F - swing * 0.5F);
				rightLegRotateAngleX = MathHelper.sin(swing * 0.5F);
				leftLegRotateAngleX = MathHelper.sin(swing * 0.5F);
			}

			this.rightarm.rotationY = 0.2F;
			this.steveArm.rotationY = 0.2F;
			this.leftArm.rotationY = -0.2F;
			this.rightLeg.rotationY = -0.2F;
			this.leftLeg.rotationY = 0.2F;
		} else {
			rightArmRotateAngleX = MathHelper.cos(move * 0.6662F + 3.141593F) * 0.45F * swing;
			leftArmRotateAngleX = MathHelper.cos(move * 0.6662F) * 0.45F * swing;
			rightLegRotateAngleX = MathHelper.cos(move * 0.6662F) * 0.45F * swing;
			leftLegRotateAngleX = MathHelper.cos(move * 0.6662F + 3.141593F) * 0.45F * swing;
			this.rightarm.rotationY = 0.0F;
			this.steveArm.rotationY = 0.0F;
			this.unicornArm.rotationY = 0.0F;
			this.leftArm.rotationY = 0.0F;
			this.rightLeg.rotationY = 0.0F;
			this.leftLeg.rotationY = 0.0F;
		}

		this.rightarm.rotationX = rightArmRotateAngleX;
		this.steveArm.rotationX = rightArmRotateAngleX;
		this.unicornArm.rotationX = 0.0F;
		this.leftArm.rotationX = leftArmRotateAngleX;
		this.rightLeg.rotationX = rightLegRotateAngleX;
		this.leftLeg.rotationX = leftLegRotateAngleX;
		this.rightarm.rotationZ = 0.0F;
		this.steveArm.rotationZ = 0.0F;
		this.unicornArm.rotationZ = 0.0F;
		this.leftArm.rotationZ = 0.0F;
	}

	protected void adjustLegs() {
		float sinBodyRotateAngleYFactor = MathHelper.sin(this.Body.rotationY) * 5.0F;
		float cosBodyRotateAngleYFactor = MathHelper.cos(this.Body.rotationY) * 5.0F;
		float legOutset = 4.0F;
		if (this.isSneaking && !this.isFlying) {
			legOutset = 0.0F;
		}

		if (this.isSleeping) {
			legOutset = 2.6F;
		}

		if (this.rainboom) {
			this.rightarm.pivotZ = sinBodyRotateAngleYFactor + 2.0F;
			this.steveArm.pivotZ = sinBodyRotateAngleYFactor + 2.0F;
			this.leftArm.pivotZ = 0.0F - sinBodyRotateAngleYFactor + 2.0F;
		} else {
			this.rightarm.pivotZ = sinBodyRotateAngleYFactor + 1.0F;
			this.steveArm.pivotZ = sinBodyRotateAngleYFactor + 1.0F;
			this.leftArm.pivotZ = 0.0F - sinBodyRotateAngleYFactor + 1.0F;
		}

		this.rightarm.pivotX = 0.0F - cosBodyRotateAngleYFactor - 1.0F + legOutset;
		this.steveArm.pivotX = 0.0F - cosBodyRotateAngleYFactor;
		this.leftArm.pivotX = cosBodyRotateAngleYFactor + 1.0F - legOutset;
		this.rightLeg.pivotX = 0.0F - cosBodyRotateAngleYFactor - 1.0F + legOutset;
		this.leftLeg.pivotX = cosBodyRotateAngleYFactor + 1.0F - legOutset;
		this.rightarm.rotationY += this.Body.rotationY;
		this.leftArm.rotationY += this.Body.rotationY;
		// TODO: Is this right?!
		this.leftArm.rotationX += this.Body.rotationY;
		this.rightarm.pivotY = 8.0F;
		this.leftArm.pivotY = 8.0F;
		this.rightLeg.pivotZ = 10.0F;
		this.leftLeg.pivotZ = 10.0F;
	}

	private void swingTailZ(float move, float swing) {
		this.tailstop = this.tail.length - this.wantTail * 5;
		if (this.tailstop <= 1) {
			this.tailstop = 0;
		}

		for (int j = 0; j < this.tailstop; ++j) {
			if (this.rainboom) {
				this.tail[j].rotateAngleZ = 0.0F;
			} else {
				this.tail[j].rotateAngleZ = MathHelper.cos(move * 0.8F) * 0.2F * swing;
			}
		}

	}

	private void swingTailX(float tick) {
		float sinTickFactor = MathHelper.sin(tick * 0.067F) * 0.05F;
		this.tailstop = this.tail.length - this.wantTail * 5;
		if (this.tailstop <= 1) {
			this.tailstop = 0;
		}

		for (int l6 = 0; l6 < this.tailstop; ++l6) {
			PlaneRenderer var10000 = this.tail[l6];
			var10000.rotateAngleX += sinTickFactor;
		}

	}

	protected void holdItem() {
		if (this.heldItemRight != 0 && !this.rainboom && !this.isUnicorn) {
			this.rightarm.rotationX = this.rightarm.rotationX * 0.5F - 0.3141593F;
			this.steveArm.rotationX = this.steveArm.rotationX * 0.5F - 0.3141593F;
		}

	}

	protected void swingItem(float swingProgress) {
		if (swingProgress > -9990.0F && !this.isSleeping) {
			float f16 = 1.0F - swingProgress;
			f16 *= f16 * f16;
			f16 = 1.0F - f16;
			float f22 = MathHelper.sin(f16 * 3.141593F);
			float f28 = MathHelper.sin(swingProgress * 3.141593F);
			float f33 = f28 * -(this.head.rotationX - 0.7F) * 0.75F;
			if (this.isUnicorn) {
				this.unicornArm.rotationX = (float) (this.unicornArm.rotationX - (f22 * 1.2 + f33));
				this.unicornArm.rotationY += this.Body.rotationY * 2.0F;
				this.unicornArm.rotationZ = f28 * -0.4F;
			} else {
				this.rightarm.rotationX = (float) (this.rightarm.rotationX - (f22 * 1.2 + f33));
				this.rightarm.rotationY += this.Body.rotationY * 2.0F;
				this.rightarm.rotationZ = f28 * -0.4F;
				this.steveArm.rotationX = (float) (this.steveArm.rotationX - (f22 * 1.2 + f33));
				this.steveArm.rotationY += this.Body.rotationY * 2.0F;
				this.steveArm.rotationZ = f28 * -0.4F;
			}
		}

	}

	protected void swingArms(float tick) {
		if (this.heldItemRight != 0 && !this.isSleeping) {
			float cosTickFactor = MathHelper.cos(tick * 0.09F) * 0.05F + 0.05F;
			float sinTickFactor = MathHelper.sin(tick * 0.067F) * 0.05F;
			if (!this.isUnicorn) {
				this.rightarm.rotationZ += cosTickFactor;
				this.rightarm.rotationX += sinTickFactor;
				this.steveArm.rotationZ += cosTickFactor;
				this.steveArm.rotationX += sinTickFactor;
			} else {
				ModelPart var7 = this.unicornArm;
				var7.rotationZ += cosTickFactor;
				var7.rotationX += sinTickFactor;
			}
		}

	}

	protected void adjustBody(float rotateAngleX, float rotationPointY, float rotationPointZ) {
		this.adjustBodyComponents(rotateAngleX, rotationPointY, rotationPointZ);
		this.adjustNeck(rotateAngleX, rotationPointY, rotationPointZ);
	}

	private void adjustBodyComponents(float rotateAngleX, float rotationPointY, float rotationPointZ) {
		this.Body.rotationX = rotateAngleX;
		this.Body.pivotY = rotationPointY;
		this.Body.pivotZ = rotationPointZ;

		for (PlaneRenderer planeRenderer : this.bodyPiece) {
			planeRenderer.rotateAngleX = rotateAngleX;
			planeRenderer.rotationPointY = rotationPointY;
			planeRenderer.rotationPointZ = rotationPointZ;
		}

	}

	private void adjustNeck(float rotateAngleX, float rotationPointY, float rotationPointZ) {
		for (PlaneRenderer planeRenderer : this.bodyPieceNeck) {
			planeRenderer.rotateAngleX = this.NeckRotX + rotateAngleX;
			planeRenderer.rotationPointY = rotationPointY;
			planeRenderer.rotationPointZ = rotationPointZ;
		}

	}

	protected void sneakLegs() {
		this.rightarm.rotationX -= SNEAK_LEG_X_ROTATION_ADJUSTMENT;
		this.steveArm.rotationX += SNEAK_LEG_X_ROTATION_ADJUSTMENT;
		this.unicornArm.rotationX += SNEAK_LEG_X_ROTATION_ADJUSTMENT;
		this.leftArm.rotationX -= SNEAK_LEG_X_ROTATION_ADJUSTMENT;
		this.rightLeg.pivotY = FRONT_LEG_RP_Y_SNEAK;
		this.leftLeg.pivotY = FRONT_LEG_RP_Y_SNEAK;
	}

	private void sneakTail() {
		this.tailstop = this.tail.length - this.wantTail * 5;
		if (this.tailstop <= 1) {
			this.tailstop = 0;
		}

		for (int i7 = 0; i7 < this.tailstop; ++i7) {
			this.setRotationPoint(this.tail[i7], TAIL_RP_X, TAIL_RP_Y, TAIL_RP_Z_SNEAK);
			this.tail[i7].rotateAngleX = 0.0F;
		}

	}

	protected void ponySleep() {
		this.rightarm.rotationX = ROTATE_270;
		this.leftArm.rotationX = ROTATE_270;
		this.rightLeg.rotationX = ROTATE_90;
		this.leftLeg.rotationX = ROTATE_90;
		float headPosX;
		float headPosY;
		float headPosZ;
		headPosY = 2.0F;
		if (this.isSneaking) {
			headPosZ = -1.0F;
		} else {
			headPosZ = 1.0F;
		}
		headPosX = 1.0F;

		this.setHead(headPosX, headPosY, headPosZ);
		this.shiftRotationPoint(this.rightarm, 0.0F, 2.0F, 6.0F);
		this.shiftRotationPoint(this.leftArm, 0.0F, 2.0F, 6.0F);
		this.shiftRotationPoint(this.rightLeg, 0.0F, 2.0F, -8.0F);
		this.shiftRotationPoint(this.leftLeg, 0.0F, 2.0F, -8.0F);
	}

	protected void aimBow(float tick) {
		if (this.isUnicorn) {
			this.aimBowUnicorn(tick);
		} else {
			this.aimBowPony(tick);
		}

	}

	private void aimBowPony(float tick) {
		this.rightarm.rotationZ = 0.0F;
		this.rightarm.rotationY = -0.06F + this.head.rotationY;
		this.rightarm.rotationX = ROTATE_270 + this.head.rotationX;
		this.rightarm.rotationZ += MathHelper.cos(tick * 0.09F) * 0.05F + 0.05F;
		this.rightarm.rotationX += MathHelper.sin(tick * 0.067F) * 0.05F;
		this.shiftRotationPoint(this.rightarm, 0.0F, 0.0F, 1.0F);
	}

	private void aimBowUnicorn(float tick) {
		this.unicornArm.rotationZ = 0.0F;
		this.unicornArm.rotationY = -0.06F + this.head.rotationY;
		this.unicornArm.rotationX = ROTATE_270 + this.head.rotationX;
		this.unicornArm.rotationZ += MathHelper.cos(tick * 0.09F) * 0.05F + 0.05F;
		this.unicornArm.rotationX += MathHelper.sin(tick * 0.067F) * 0.05F;
	}

	private void animatePegasusWingsSneaking() {
		for (ModelPart modelPart : this.leftWingExt) {
			modelPart.pivotY = LEFT_WING_RP_Y_SNEAK;
			modelPart.pivotZ = LEFT_WING_RP_Z_SNEAK;
			modelPart.rotationX = EXT_WING_ROTATE_ANGLE_X;
			modelPart.rotationZ = LEFT_WING_ROTATE_ANGLE_Z_SNEAK;
		}

		for (int k5 = 0; k5 < this.leftWingExt.length; ++k5) {
			this.rightWingExt[k5].pivotY = RIGHT_WING_RP_Y_SNEAK;
			this.rightWingExt[k5].pivotZ = RIGHT_WING_RP_Z_SNEAK;
			this.rightWingExt[k5].rotationX = EXT_WING_ROTATE_ANGLE_X;
			this.rightWingExt[k5].rotationZ = RIGHT_WING_ROTATE_ANGLE_Z_SNEAK;
		}

	}

	private void animatePegasusWingsNotSneaking(float tick) {
		if (!this.isFlying) {
			for (ModelPart part : this.leftWing) {
				part.pivotY = WING_FOLDED_RP_Y;
				part.pivotZ = WING_FOLDED_RP_Z;
			}

			for (ModelPart modelPart : this.rightWing) {
				modelPart.pivotY = WING_FOLDED_RP_Y;
				modelPart.pivotZ = WING_FOLDED_RP_Z;
			}
		} else {
			float wingRotateAngleZ = MathHelper.sin(tick * 0.536F);

			for (ModelPart part : this.leftWingExt) {
				part.rotationX = EXT_WING_ROTATE_ANGLE_X;
				part.rotationZ = -wingRotateAngleZ - ROTATE_270 - 0.4F;
				part.pivotY = LEFT_WING_RP_Y_NOTSNEAK;
				part.pivotZ = LEFT_WING_RP_Z_NOTSNEAK;
			}

			for (ModelPart modelPart : this.rightWingExt) {
				modelPart.rotationX = EXT_WING_ROTATE_ANGLE_X;
				modelPart.rotationZ = wingRotateAngleZ + ROTATE_270 + 0.4F;
				modelPart.pivotY = RIGHT_WING_RP_Y_NOTSNEAK;
				modelPart.pivotZ = RIGHT_WING_RP_Z_NOTSNEAK;
			}
		}

	}

	private void fixSpecialRotations() {
		this.leftWingExt[2].rotationX -= 0.85F;
		this.leftWingExt[3].rotationX -= 0.75F;
		this.leftWingExt[4].rotationX -= 0.5F;
		this.leftWingExt[6].rotationX -= 0.85F;
		this.rightWingExt[2].rotationX -= 0.85F;
		this.rightWingExt[3].rotationX -= 0.75F;
		this.rightWingExt[4].rotationX -= 0.5F;
		this.rightWingExt[6].rotationX -= 0.85F;
		this.bodyPiece[9].rotateAngleX += 0.5F;
		this.bodyPiece[10].rotateAngleX += 0.5F;
		this.bodyPiece[11].rotateAngleX += 0.5F;
		this.bodyPiece[12].rotateAngleX += 0.5F;
		this.bodyPiece[13].rotateAngleX += 0.5F;
	}

	protected void shiftRotationPoint(@NotNull ModelPart modelPart, float shiftX, float shiftY, float shiftZ) {
		modelPart.pivotX += shiftX;
		modelPart.pivotY += shiftY;
		modelPart.pivotZ += shiftZ;
	}

	private void setRotationPoint(PlaneRenderer planeRenderer, float setX, float setY, float setZ) {
		planeRenderer.rotationPointX = setX;
		planeRenderer.rotationPointY = setY;
		planeRenderer.rotationPointZ = setZ;
	}

	protected void setRotationPoint(ModelPart modelPart, float setX, float setY, float setZ) {
		modelPart.pivotX = setX;
		modelPart.pivotY = setY;
		modelPart.pivotZ = setZ;
	}

	public void render(AniParams aniparams, boolean flag) {
		if (flag) {
			if (this.hasVehicle && !this.isArmour) {
				GL11.glTranslatef(0.0F, -0.56F, -0.46F);
			}

			if (this.isSleeping && !this.isArmour) {
				GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(270.0F, 0.0F, 0.0F, 1.0F);
				GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
				GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
			}

			if (this.size == Pony.Size.FILLY) {
				if (this.isSneaking && !this.isFlying && !this.isArmour) {
					GL11.glTranslatef(0.0F, -0.12F, 0.0F);
				}

				if (this.isSleeping && !this.isArmour) {
					GL11.glTranslatef(0.0F, -1.0F, 0.25F);
				}

				GL11.glPushMatrix();
				GL11.glTranslatef(0.0F, 0.76F, 0.0F);
				GL11.glScalef(0.9F, 0.9F, 0.9F);
				this.renderHead();
				if (this.isSneaking && !this.isFlying) {
					GL11.glTranslatef(0.0F, -0.01F, 0.15F);
				}

				this.renderNeck();
				GL11.glPopMatrix();
				GL11.glPushMatrix();
				GL11.glTranslatef(0.0F, 0.76F, -0.04F);
				GL11.glScalef(0.6F, 0.6F, 0.6F);
				this.renderBody();
				this.renderTail();
				GL11.glPopMatrix();
				GL11.glPushMatrix();
				GL11.glTranslatef(0.0F, 0.89F, 0.0F);
				GL11.glScalef(0.6F, 0.41F, 0.6F);
				if (this.isSneaking && !this.isFlying) {
					GL11.glTranslatef(0.0F, 0.12F, 0.0F);
				}

				if (this.rainboom) {
					GL11.glTranslatef(0.0F, -0.08F, 0.0F);
				}

				this.renderLegs();
				GL11.glPopMatrix();
			} else if (this.size == Pony.Size.STALLION) {
				if (this.isSleeping && !this.isArmour) {
					GL11.glTranslatef(0.0F, -0.47F, 0.2F);
				}

				GL11.glPushMatrix();
				GL11.glTranslatef(0.0F, -0.17F, -0.04F);
				if (this.isSleeping && !this.isArmour) {
					GL11.glTranslatef(0.0F, 0.0F, -0.1F);
				}

				if (this.isSneaking && !this.isFlying) {
					GL11.glTranslatef(0.0F, 0.15F, 0.0F);
				}

				this.renderHead();
				GL11.glPopMatrix();
				GL11.glPushMatrix();
				GL11.glTranslatef(0.0F, -0.15F, -0.07F);
				if (this.isSneaking && !this.isFlying) {
					GL11.glTranslatef(0.0F, 0.0F, -0.05F);
				}

				this.renderNeck();
				GL11.glPopMatrix();
				GL11.glPushMatrix();
				GL11.glTranslatef(0.0F, -0.2F, -0.04F);
				GL11.glScalef(1.15F, 1.2F, 1.2F);
				this.renderBody();
				GL11.glPopMatrix();
				GL11.glPushMatrix();
				GL11.glTranslatef(0.0F, -0.2F, 0.08F);
				this.renderTail();
				GL11.glPopMatrix();
				GL11.glPushMatrix();
				GL11.glTranslatef(0.0F, -0.14F, 0.0F);
				GL11.glScalef(1.15F, 1.12F, 1.15F);
				this.renderLegs();
				GL11.glPopMatrix();
			} else if (this.size == Pony.Size.ALICORN) {
				if (this.isSleeping && !this.isArmour) {
					GL11.glTranslatef(0.0F, -0.43F, 0.25F);
				}

				GL11.glPushMatrix();
				GL11.glTranslatef(0.0F, -0.15F, 0.01F);
				if (this.isSneaking && !this.isFlying) {
					GL11.glTranslatef(0.0F, 0.05F, 0.0F);
				}

				this.renderHead();
				GL11.glPopMatrix();
				GL11.glPushMatrix();
				GL11.glTranslatef(0.0F, -0.19F, -0.01F);
				GL11.glScalef(1.0F, 1.1F, 1.0F);
				if (this.isSneaking && !this.isFlying) {
					GL11.glTranslatef(0.0F, -0.06F, -0.04F);
				}

				this.renderNeck();
				GL11.glPopMatrix();
				GL11.glPushMatrix();
				GL11.glTranslatef(0.0F, -0.1F, 0.0F);
				GL11.glScalef(1.0F, 1.0F, 1.0F);
				this.renderBody();
				this.renderTail();
				GL11.glPopMatrix();
				GL11.glPushMatrix();
				GL11.glTranslatef(0.0F, -0.25F, 0.03F);
				GL11.glScalef(1.0F, 1.18F, 1.0F);
				if (this.rainboom) {
					GL11.glTranslatef(0.0F, 0.05F, 0.0F);
				}

				this.renderLegs();
				GL11.glPopMatrix();
			} else {
				if (this.isSleeping && !this.isArmour) {
					GL11.glTranslatef(0.0F, -0.535F, 0.25F);
				}

				this.renderHead();
				this.renderNeck();
				this.renderBody();
				this.renderTail();
				this.renderLegs();
			}
		} else {
			this.steveArm.render(this.scale);
		}

	}

	protected void renderHead() {
		this.head.render(this.scale);
		this.headpiece[0].render(this.scale);
		this.headpiece[1].render(this.scale);
		if (this.isUnicorn) {
			this.headpiece[2].render(this.scale);
		}

		if (PonySettings.isShowSnuzzles()) {
			if (this.isMale) {
				for (PlaneRenderer planeRenderer : this.muzzleMale) {
					planeRenderer.render(this.scale);
				}
			} else {
				for (PlaneRenderer planeRenderer : this.muzzleFemale) {
					planeRenderer.render(this.scale);
				}
			}
		}

		this.helmet.render(this.scale);
	}

	protected void renderNeck() {
		for (PlaneRenderer planeRenderer : this.bodyPieceNeck) {
			planeRenderer.render(this.scale);
		}

	}

	protected void renderBody() {
		this.Body.render(this.scale);

		for (PlaneRenderer planeRenderer : this.bodyPiece) {
			planeRenderer.render(this.scale);
		}

		if (this.isPegasus) {
			if (!this.isFlying && !this.isSneaking) {
				for (ModelPart part : this.leftWing) {
					part.render(this.scale);
				}

				for (ModelPart modelPart : this.rightWing) {
					modelPart.render(this.scale);
				}
			} else {
				for (ModelPart part : this.leftWingExt) {
					part.render(this.scale);
				}

				for (ModelPart modelPart : this.rightWingExt) {
					modelPart.render(this.scale);
				}
			}
		}

	}

	protected void renderTail() {
		int tailstop;
		tailstop = this.tail.length - this.wantTail * 5;
		if (tailstop <= 1) {
			tailstop = 0;
		}

		for (int k = 0; k < tailstop; ++k) {
			this.tail[k].render(this.scale);
		}

	}

	protected void renderLegs() {
		this.leftArm.render(this.scale);
		this.rightarm.render(this.scale);
		this.leftLeg.render(this.scale);
		this.rightLeg.render(this.scale);
	}

	protected void initTextures() {
		this.tail = new PlaneRenderer[21];
		this.headpiece = new ModelPart[3];
		this.muzzleFemale = new PlaneRenderer[10];
		this.muzzleMale = new PlaneRenderer[5];
		this.bodyPiece = new PlaneRenderer[14];
		this.bodyPieceNeck = new PlaneRenderer[4];
		this.leftWing = new ModelPart[3];
		this.rightWing = new ModelPart[3];
		this.leftWingExt = new ModelPart[7];
		this.rightWingExt = new ModelPart[7];
		this.initHeadTextures();
		this.initMuzzleTextures();
		this.initBodyTextures();
		this.initLegTextures();
		this.initTailTextures();
		this.initWingTextures();
	}

	protected void initHeadTextures() {
		this.cloak = new ModelPart(this, 0, 0);
		this.head = new ModelPart(this, 0, 0);
		this.headpiece[0] = new ModelPart(this, 12, 16);
		this.headpiece[1] = new ModelPart(this, 12, 16);
		this.headpiece[1].flipped = true;
		this.headpiece[2] = new ModelPart(this, 0, 3);
		this.helmet = new ModelPart(this, 32, 0);
	}

	private void initMuzzleTextures() {
		this.muzzleFemale[0] = new PlaneRenderer(this, 10, 14);
		this.muzzleFemale[1] = new PlaneRenderer(this, 11, 13);
		this.muzzleFemale[2] = new PlaneRenderer(this, 9, 14);
		this.muzzleFemale[3] = new PlaneRenderer(this, 14, 14);
		this.muzzleFemale[4] = new PlaneRenderer(this, 11, 12);
		this.muzzleFemale[5] = new PlaneRenderer(this, 18, 7);
		this.muzzleFemale[6] = new PlaneRenderer(this, 9, 14);
		this.muzzleFemale[7] = new PlaneRenderer(this, 14, 14);
		this.muzzleFemale[8] = new PlaneRenderer(this, 11, 12);
		this.muzzleFemale[9] = new PlaneRenderer(this, 12, 12);
		this.muzzleMale[0] = new PlaneRenderer(this, 10, 13);
		this.muzzleMale[1] = new PlaneRenderer(this, 10, 13);
		this.muzzleMale[2] = new PlaneRenderer(this, 18, 7);
		this.muzzleMale[3] = new PlaneRenderer(this, 10, 13);
		this.muzzleMale[4] = new PlaneRenderer(this, 13, 13);
	}

	protected void initBodyTextures() {
		this.Body = new ModelPart(this, 16, 16);
		this.bodyPiece[0] = new PlaneRenderer(this, 24, 0);
		this.bodyPiece[1] = new PlaneRenderer(this, 24, 0);
		this.bodyPiece[2] = new PlaneRenderer(this, 32, 20);
		this.bodyPiece[2].mirrorxy = true;
		this.bodyPiece[3] = new PlaneRenderer(this, 56, 0);
		this.bodyPiece[4] = new PlaneRenderer(this, 4, 0);
		this.bodyPiece[5] = new PlaneRenderer(this, 4, 0);
		this.bodyPiece[6] = new PlaneRenderer(this, 36, 16);
		this.bodyPiece[7] = new PlaneRenderer(this, 36, 16);
		this.bodyPiece[8] = new PlaneRenderer(this, 36, 16);
		this.bodyPiece[9] = new PlaneRenderer(this, 32, 0);
		this.bodyPiece[10] = new PlaneRenderer(this, 32, 0);
		this.bodyPiece[11] = new PlaneRenderer(this, 32, 0);
		this.bodyPiece[11].mirror = true;
		this.bodyPiece[12] = new PlaneRenderer(this, 32, 0);
		this.bodyPiece[13] = new PlaneRenderer(this, 32, 0);
		this.bodyPieceNeck[0] = new PlaneRenderer(this, 0, 16);
		this.bodyPieceNeck[1] = new PlaneRenderer(this, 0, 16);
		this.bodyPieceNeck[2] = new PlaneRenderer(this, 0, 16);
		this.bodyPieceNeck[3] = new PlaneRenderer(this, 0, 16);
	}

	protected void initLegTextures() {
		this.rightarm = new ModelPart(this, 40, 16);
		this.leftArm = new ModelPart(this, 40, 16);
		this.leftArm.flipped = true;
		this.rightLeg = new ModelPart(this, 0, 16);
		this.leftLeg = new ModelPart(this, 0, 16);
		this.leftLeg.flipped = true;
		this.steveArm = new ModelPart(this, 40, 16);
		this.unicornArm = new ModelPart(this, 40, 16);
	}

	private void initTailTextures() {
		this.tail[0] = new PlaneRenderer(this, 32, 0);
		this.tail[1] = new PlaneRenderer(this, 36, 0);
		this.tail[2] = new PlaneRenderer(this, 32, 0);
		this.tail[3] = new PlaneRenderer(this, 36, 0);
		this.tail[4] = new PlaneRenderer(this, 32, 0);
		this.tail[5] = new PlaneRenderer(this, 32, 0);
		this.tail[6] = new PlaneRenderer(this, 36, 4);
		this.tail[7] = new PlaneRenderer(this, 32, 4);
		this.tail[8] = new PlaneRenderer(this, 36, 4);
		this.tail[9] = new PlaneRenderer(this, 32, 4);
		this.tail[10] = new PlaneRenderer(this, 32, 0);
		this.tail[11] = new PlaneRenderer(this, 36, 0);
		this.tail[12] = new PlaneRenderer(this, 32, 0);
		this.tail[13] = new PlaneRenderer(this, 36, 0);
		this.tail[14] = new PlaneRenderer(this, 32, 0);
		this.tail[15] = new PlaneRenderer(this, 32, 0);
		this.tail[16] = new PlaneRenderer(this, 36, 4);
		this.tail[17] = new PlaneRenderer(this, 32, 4);
		this.tail[18] = new PlaneRenderer(this, 36, 4);
		this.tail[19] = new PlaneRenderer(this, 32, 4);
		this.tail[20] = new PlaneRenderer(this, 32, 0);
	}

	private void initWingTextures() {
		this.leftWing[0] = new ModelPart(this, 56, 16);
		this.leftWing[0].flipped = true;
		this.leftWing[1] = new ModelPart(this, 56, 16);
		this.leftWing[1].flipped = true;
		this.leftWing[2] = new ModelPart(this, 56, 16);
		this.leftWing[2].flipped = true;
		this.rightWing[0] = new ModelPart(this, 56, 16);
		this.rightWing[1] = new ModelPart(this, 56, 16);
		this.rightWing[2] = new ModelPart(this, 56, 16);
		this.leftWingExt[0] = new ModelPart(this, 56, 19);
		this.leftWingExt[0].flipped = true;
		this.leftWingExt[1] = new ModelPart(this, 56, 19);
		this.leftWingExt[1].flipped = true;
		this.leftWingExt[2] = new ModelPart(this, 56, 19);
		this.leftWingExt[2].flipped = true;
		this.leftWingExt[3] = new ModelPart(this, 56, 19);
		this.leftWingExt[3].flipped = true;
		this.leftWingExt[4] = new ModelPart(this, 56, 19);
		this.leftWingExt[4].flipped = true;
		this.leftWingExt[5] = new ModelPart(this, 56, 19);
		this.leftWingExt[5].flipped = true;
		this.leftWingExt[6] = new ModelPart(this, 56, 19);
		this.leftWingExt[6].flipped = true;
		this.rightWingExt[0] = new ModelPart(this, 56, 19);
		this.rightWingExt[0].flipped = true;
		this.rightWingExt[1] = new ModelPart(this, 56, 19);
		this.rightWingExt[1].flipped = true;
		this.rightWingExt[2] = new ModelPart(this, 56, 19);
		this.rightWingExt[2].flipped = true;
		this.rightWingExt[3] = new ModelPart(this, 56, 19);
		this.rightWingExt[3].flipped = true;
		this.rightWingExt[4] = new ModelPart(this, 56, 19);
		this.rightWingExt[4].flipped = true;
		this.rightWingExt[5] = new ModelPart(this, 56, 19);
		this.rightWingExt[5].flipped = true;
		this.rightWingExt[6] = new ModelPart(this, 56, 19);
		this.rightWingExt[6].flipped = true;
	}

	protected void initPositions(float yOffset, float stretch) {
		this.initHeadPositions(yOffset, stretch);
		this.initMuzzlePositions(yOffset, stretch);
		this.initBodyPositions(yOffset, stretch);
		this.initLegPositions(yOffset, stretch);
		this.initTailPositions(yOffset, stretch);
		this.initWingPositions(yOffset, stretch);
	}

	protected void initHeadPositions(float yOffset, float stretch) {
		this.cloak.addBox(-5.0F, 0.0F, -1.0F, 10, 16, 1, stretch);
		this.head.addBox(-4.0F + HEAD_CENTRE_X, -4.0F + HEAD_CENTRE_Y, -4.0F + HEAD_CENTRE_Z, 8, 8, 8, stretch);
		this.head.setPivot(HEAD_RP_X, HEAD_RP_Y + yOffset, HEAD_RP_Z);
		this.headpiece[0].addBox(-4.0F + HEAD_CENTRE_X, -6.0F + HEAD_CENTRE_Y, 1.0F + HEAD_CENTRE_Z, 2, 2, 2, stretch);
		this.headpiece[0].setPivot(HEAD_RP_X, HEAD_RP_Y + yOffset, HEAD_RP_Z);
		this.headpiece[1].addBox(2.0F + HEAD_CENTRE_X, -6.0F + HEAD_CENTRE_Y, 1.0F + HEAD_CENTRE_Z, 2, 2, 2, stretch);
		this.headpiece[1].setPivot(HEAD_RP_X, HEAD_RP_Y + yOffset, HEAD_RP_Z);
		this.headpiece[2].addBox(-0.5F + HEAD_CENTRE_X, -10.0F + HEAD_CENTRE_Y, -1.5F + HEAD_CENTRE_Z, 1, 4, 1, stretch);
		this.headpiece[2].setPivot(HEAD_RP_X, HEAD_RP_Y + yOffset, HEAD_RP_Z);
		this.helmet.addBox(-4.0F + HEAD_CENTRE_X, -4.0F + HEAD_CENTRE_Y, -4.0F + HEAD_CENTRE_Z, 8, 8, 8, stretch + 0.5F);
		this.helmet.setPivot(HEAD_RP_X, HEAD_RP_Y + yOffset, HEAD_RP_Z);
	}

	private void initMuzzlePositions(float yOffset, float stretch) {
		this.muzzleFemale[0].addBackPlane(-2.0F + HEAD_CENTRE_X, 2.0F + HEAD_CENTRE_Y, -5.0F + HEAD_CENTRE_Z, 4, 2, 0, stretch);
		this.muzzleFemale[0].setRotationPoint(HEAD_RP_X, HEAD_RP_Y + yOffset, HEAD_RP_Z);
		this.muzzleFemale[1].addBackPlane(-1.0F + HEAD_CENTRE_X, 1.0F + HEAD_CENTRE_Y, -5.0F + HEAD_CENTRE_Z, 2, 1, 0, stretch);
		this.muzzleFemale[1].setRotationPoint(HEAD_RP_X, HEAD_RP_Y + yOffset, HEAD_RP_Z);
		this.muzzleFemale[2].addTopPlane(-2.0F + HEAD_CENTRE_X, 2.0F + HEAD_CENTRE_Y, -5.0F + HEAD_CENTRE_Z, 1, 0, 1, stretch);
		this.muzzleFemale[2].setRotationPoint(HEAD_RP_X, HEAD_RP_Y + yOffset, HEAD_RP_Z);
		this.muzzleFemale[3].addTopPlane(1.0F + HEAD_CENTRE_X, 2.0F + HEAD_CENTRE_Y, -5.0F + HEAD_CENTRE_Z, 1, 0, 1, stretch);
		this.muzzleFemale[3].setRotationPoint(HEAD_RP_X, HEAD_RP_Y + yOffset, HEAD_RP_Z);
		this.muzzleFemale[4].addTopPlane(-1.0F + HEAD_CENTRE_X, 1.0F + HEAD_CENTRE_Y, -5.0F + HEAD_CENTRE_Z, 2, 0, 1, stretch);
		this.muzzleFemale[4].setRotationPoint(HEAD_RP_X, HEAD_RP_Y + yOffset, HEAD_RP_Z);
		this.muzzleFemale[5].addBottomPlane(-2.0F + HEAD_CENTRE_X, 4.0F + HEAD_CENTRE_Y, -5.0F + HEAD_CENTRE_Z, 4, 0, 1, stretch);
		this.muzzleFemale[5].setRotationPoint(HEAD_RP_X, HEAD_RP_Y + yOffset, HEAD_RP_Z);
		this.muzzleFemale[6].addSidePlane(-2.0F + HEAD_CENTRE_X, 2.0F + HEAD_CENTRE_Y, -5.0F + HEAD_CENTRE_Z, 0, 2, 1, stretch);
		this.muzzleFemale[6].setRotationPoint(HEAD_RP_X, HEAD_RP_Y + yOffset, HEAD_RP_Z);
		this.muzzleFemale[7].addSidePlane(2.0F + HEAD_CENTRE_X, 2.0F + HEAD_CENTRE_Y, -5.0F + HEAD_CENTRE_Z, 0, 2, 1, stretch);
		this.muzzleFemale[7].setRotationPoint(HEAD_RP_X, HEAD_RP_Y + yOffset, HEAD_RP_Z);
		this.muzzleFemale[8].addSidePlane(-1.0F + HEAD_CENTRE_X, 1.0F + HEAD_CENTRE_Y, -5.0F + HEAD_CENTRE_Z, 0, 1, 1, stretch);
		this.muzzleFemale[8].setRotationPoint(HEAD_RP_X, HEAD_RP_Y + yOffset, HEAD_RP_Z);
		this.muzzleFemale[9].addSidePlane(1.0F + HEAD_CENTRE_X, 1.0F + HEAD_CENTRE_Y, -5.0F + HEAD_CENTRE_Z, 0, 1, 1, stretch);
		this.muzzleFemale[9].setRotationPoint(HEAD_RP_X, HEAD_RP_Y + yOffset, HEAD_RP_Z);
		this.muzzleMale[0].addBackPlane(-2.0F + HEAD_CENTRE_X, 1.0F + HEAD_CENTRE_Y, -5.0F + HEAD_CENTRE_Z, 4, 3, 0, stretch);
		this.muzzleMale[0].setRotationPoint(HEAD_RP_X, HEAD_RP_Y + yOffset, HEAD_RP_Z);
		this.muzzleMale[1].addTopPlane(-2.0F + HEAD_CENTRE_X, 1.0F + HEAD_CENTRE_Y, -5.0F + HEAD_CENTRE_Z, 4, 0, 1, stretch);
		this.muzzleMale[1].setRotationPoint(HEAD_RP_X, HEAD_RP_Y + yOffset, HEAD_RP_Z);
		this.muzzleMale[2].addBottomPlane(-2.0F + HEAD_CENTRE_X, 4.0F + HEAD_CENTRE_Y, -5.0F + HEAD_CENTRE_Z, 4, 0, 1, stretch);
		this.muzzleMale[2].setRotationPoint(HEAD_RP_X, HEAD_RP_Y + yOffset, HEAD_RP_Z);
		this.muzzleMale[3].addSidePlane(-2.0F + HEAD_CENTRE_X, 1.0F + HEAD_CENTRE_Y, -5.0F + HEAD_CENTRE_Z, 0, 3, 1, stretch);
		this.muzzleMale[3].setRotationPoint(HEAD_RP_X, HEAD_RP_Y + yOffset, HEAD_RP_Z);
		this.muzzleMale[4].addSidePlane(2.0F + HEAD_CENTRE_X, 1.0F + HEAD_CENTRE_Y, -5.0F + HEAD_CENTRE_Z, 0, 3, 1, stretch);
		this.muzzleMale[4].setRotationPoint(HEAD_RP_X, HEAD_RP_Y + yOffset, HEAD_RP_Z);
	}

	protected void initBodyPositions(float yOffset, float stretch) {
		this.Body.addBox(-4.0F, 4.0F, -2.0F, 8, 8, 4, stretch);
		this.Body.setPivot(HEAD_RP_X, HEAD_RP_Y + yOffset, HEAD_RP_Z);
		this.bodyPiece[0].addSidePlane(-4.0F + BODY_CENTRE_X, -4.0F + BODY_CENTRE_Y, -4.0F + BODY_CENTRE_Z, 0, 8, 8, stretch);
		this.bodyPiece[0].setRotationPoint(HEAD_RP_X, HEAD_RP_Y + yOffset, HEAD_RP_Z);
		this.bodyPiece[1].addSidePlane(4.0F + BODY_CENTRE_X, -4.0F + BODY_CENTRE_Y, -4.0F + BODY_CENTRE_Z, 0, 8, 8, stretch);
		this.bodyPiece[1].setRotationPoint(HEAD_RP_X, HEAD_RP_Y + yOffset, HEAD_RP_Z);
		this.bodyPiece[2].addTopPlane(-4.0F + BODY_CENTRE_X, -4.0F + BODY_CENTRE_Y, -4.0F + BODY_CENTRE_Z, 8, 0, 12, stretch);
		this.bodyPiece[2].setRotationPoint(HEAD_RP_X, HEAD_RP_Y + yOffset, HEAD_RP_Z);
		this.bodyPiece[3].addBottomPlane(-4.0F + BODY_CENTRE_X, 4.0F + BODY_CENTRE_Y, -4.0F + BODY_CENTRE_Z, 8, 0, 8, stretch);
		this.bodyPiece[3].setRotationPoint(HEAD_RP_X, HEAD_RP_Y + yOffset, HEAD_RP_Z);
		this.bodyPiece[4].addSidePlane(-4.0F + BODY_CENTRE_X, -4.0F + BODY_CENTRE_Y, 4.0F + BODY_CENTRE_Z, 0, 8, 4, stretch);
		this.bodyPiece[4].setRotationPoint(HEAD_RP_X, HEAD_RP_Y + yOffset, HEAD_RP_Z);
		this.bodyPiece[5].addSidePlane(4.0F + BODY_CENTRE_X, -4.0F + BODY_CENTRE_Y, 4.0F + BODY_CENTRE_Z, 0, 8, 4, stretch);
		this.bodyPiece[5].setRotationPoint(HEAD_RP_X, HEAD_RP_Y + yOffset, HEAD_RP_Z);
		this.bodyPiece[6].addBackPlane(-4.0F + BODY_CENTRE_X, -4.0F + BODY_CENTRE_Y, 8.0F + BODY_CENTRE_Z, 8, 4, 0, stretch);
		this.bodyPiece[6].setRotationPoint(HEAD_RP_X, HEAD_RP_Y + yOffset, HEAD_RP_Z);
		this.bodyPiece[7].addBackPlane(-4.0F + BODY_CENTRE_X, 0.0F + BODY_CENTRE_Y, 8.0F + BODY_CENTRE_Z, 8, 4, 0, stretch);
		this.bodyPiece[7].setRotationPoint(HEAD_RP_X, HEAD_RP_Y + yOffset, HEAD_RP_Z);
		this.bodyPiece[8].addBottomPlane(-4.0F + BODY_CENTRE_X, 4.0F + BODY_CENTRE_Y, 4.0F + BODY_CENTRE_Z, 8, 0, 4, stretch);
		this.bodyPiece[8].setRotationPoint(HEAD_RP_X, HEAD_RP_Y + yOffset, HEAD_RP_Z);
		this.bodyPiece[9].addTopPlane(-1.0F + BODY_CENTRE_X, 2.0F + BODY_CENTRE_Y, 2.0F + BODY_CENTRE_Z, 2, 0, 6, stretch);
		this.bodyPiece[9].setRotationPoint(HEAD_RP_X, HEAD_RP_Y + yOffset, HEAD_RP_Z);
		this.bodyPiece[10].addBottomPlane(-1.0F + BODY_CENTRE_X, 4.0F + BODY_CENTRE_Y, 2.0F + BODY_CENTRE_Z, 2, 0, 6, stretch);
		this.bodyPiece[10].setRotationPoint(HEAD_RP_X, HEAD_RP_Y + yOffset, HEAD_RP_Z);
		this.bodyPiece[11].addSidePlane(-1.0F + BODY_CENTRE_X, 2.0F + BODY_CENTRE_Y, 2.0F + BODY_CENTRE_Z, 0, 2, 6, stretch);
		this.bodyPiece[11].setRotationPoint(HEAD_RP_X, HEAD_RP_Y + yOffset, HEAD_RP_Z);
		this.bodyPiece[12].addSidePlane(1.0F + BODY_CENTRE_X, 2.0F + BODY_CENTRE_Y, 2.0F + BODY_CENTRE_Z, 0, 2, 6, stretch);
		this.bodyPiece[12].setRotationPoint(HEAD_RP_X, HEAD_RP_Y + yOffset, HEAD_RP_Z);
		this.bodyPiece[13].addBackPlane(-1.0F + BODY_CENTRE_X, 2.0F + BODY_CENTRE_Y, 8.0F + BODY_CENTRE_Z, 2, 2, 0, stretch);
		this.bodyPiece[13].setRotationPoint(HEAD_RP_X, HEAD_RP_Y + yOffset, HEAD_RP_Z);
		this.bodyPieceNeck[0].addBackPlane(-2.0F + BODY_CENTRE_X, -6.8F + BODY_CENTRE_Y, -8.8F + BODY_CENTRE_Z, 4, 4, 0, stretch);
		this.bodyPieceNeck[0].setRotationPoint(HEAD_RP_X, HEAD_RP_Y + yOffset, HEAD_RP_Z);
		this.bodyPieceNeck[1].addBackPlane(-2.0F + BODY_CENTRE_X, -6.8F + BODY_CENTRE_Y, -4.8F + BODY_CENTRE_Z, 4, 4, 0, stretch);
		this.bodyPieceNeck[1].setRotationPoint(HEAD_RP_X, HEAD_RP_Y + yOffset, HEAD_RP_Z);
		this.bodyPieceNeck[2].addSidePlane(-2.0F + BODY_CENTRE_X, -6.8F + BODY_CENTRE_Y, -8.8F + BODY_CENTRE_Z, 0, 4, 4, stretch);
		this.bodyPieceNeck[2].setRotationPoint(HEAD_RP_X, HEAD_RP_Y + yOffset, HEAD_RP_Z);
		this.bodyPieceNeck[3].addSidePlane(2.0F + BODY_CENTRE_X, -6.8F + BODY_CENTRE_Y, -8.8F + BODY_CENTRE_Z, 0, 4, 4, stretch);
		this.bodyPieceNeck[3].setRotationPoint(HEAD_RP_X, HEAD_RP_Y + yOffset, HEAD_RP_Z);
		this.bodyPieceNeck[0].rotateAngleX = this.NeckRotX;
		this.bodyPieceNeck[1].rotateAngleX = this.NeckRotX;
		this.bodyPieceNeck[2].rotateAngleX = this.NeckRotX;
		this.bodyPieceNeck[3].rotateAngleX = this.NeckRotX;
	}

	protected void initLegPositions(float yOffset, float stretch) {
		this.rightarm.addBox(-2.0F + THIRDP_ARM_CENTRE_X, -6.0F + THIRDP_ARM_CENTRE_Y, -2.0F + THIRDP_ARM_CENTRE_Z, 4, 12, 4, stretch);
		this.rightarm.setPivot(-3.0F, 8.0F + yOffset, 0.0F);
		this.leftArm.addBox(-2.0F + THIRDP_ARM_CENTRE_X, -6.0F + THIRDP_ARM_CENTRE_Y, -2.0F + THIRDP_ARM_CENTRE_Z, 4, 12, 4, stretch);
		this.leftArm.setPivot(3.0F, 8.0F + yOffset, 0.0F);
		this.rightLeg.addBox(-2.0F + THIRDP_ARM_CENTRE_X, -6.0F + THIRDP_ARM_CENTRE_Y, -2.0F + THIRDP_ARM_CENTRE_Z, 4, 12, 4, stretch);
		this.rightLeg.setPivot(-3.0F, 0.0F + yOffset, 0.0F);
		this.leftLeg.addBox(-2.0F + THIRDP_ARM_CENTRE_X, -6.0F + THIRDP_ARM_CENTRE_Y, -2.0F + THIRDP_ARM_CENTRE_Z, 4, 12, 4, stretch);
		this.leftLeg.setPivot(3.0F, 0.0F + yOffset, 0.0F);
		this.steveArm.addBox(-2.0F + FIRSTP_ARM_CENTRE_X, -6.0F + FIRSTP_ARM_CENTRE_Y, -2.0F + FIRSTP_ARM_CENTRE_Z, 4, 12, 4, stretch);
		this.steveArm.setPivot(-5.0F, 2.0F + yOffset, 0.0F);
		this.unicornArm.addBox(-2.0F + FIRSTP_ARM_CENTRE_X, -6.0F + FIRSTP_ARM_CENTRE_Y, -2.0F + FIRSTP_ARM_CENTRE_Z, 4, 12, 4, stretch);
		this.unicornArm.setPivot(-5.0F, 2.0F + yOffset, 0.0F);
	}

	private void initTailPositions(float yOffset, float stretch) {
		this.tail[0].addTopPlane(-2.0F, 1.0F, 2.0F, 4, 0, 4, stretch);
		this.tail[0].setRotationPoint(TAIL_RP_X, TAIL_RP_Y + yOffset, TAIL_RP_Z);
		this.tail[1].addSidePlane(-2.0F, 1.0F, 2.0F, 0, 4, 4, stretch);
		this.tail[1].setRotationPoint(TAIL_RP_X, TAIL_RP_Y + yOffset, TAIL_RP_Z);
		this.tail[2].addBackPlane(-2.0F, 1.0F, 2.0F, 4, 4, 0, stretch);
		this.tail[2].setRotationPoint(TAIL_RP_X, TAIL_RP_Y + yOffset, TAIL_RP_Z);
		this.tail[3].addSidePlane(2.0F, 1.0F, 2.0F, 0, 4, 4, stretch);
		this.tail[3].setRotationPoint(TAIL_RP_X, TAIL_RP_Y + yOffset, TAIL_RP_Z);
		this.tail[4].addBackPlane(-2.0F, 1.0F, 6.0F, 4, 4, 0, stretch);
		this.tail[4].setRotationPoint(TAIL_RP_X, TAIL_RP_Y + yOffset, TAIL_RP_Z);
		this.tail[5].addTopPlane(-2.0F, 5.0F, 2.0F, 4, 0, 4, stretch);
		this.tail[5].setRotationPoint(TAIL_RP_X, TAIL_RP_Y + yOffset, TAIL_RP_Z);
		this.tail[6].addSidePlane(-2.0F, 5.0F, 2.0F, 0, 4, 4, stretch);
		this.tail[6].setRotationPoint(TAIL_RP_X, TAIL_RP_Y + yOffset, TAIL_RP_Z);
		this.tail[7].addBackPlane(-2.0F, 5.0F, 2.0F, 4, 4, 0, stretch);
		this.tail[7].setRotationPoint(TAIL_RP_X, TAIL_RP_Y + yOffset, TAIL_RP_Z);
		this.tail[8].addSidePlane(2.0F, 5.0F, 2.0F, 0, 4, 4, stretch);
		this.tail[8].setRotationPoint(TAIL_RP_X, TAIL_RP_Y + yOffset, TAIL_RP_Z);
		this.tail[9].addBackPlane(-2.0F, 5.0F, 6.0F, 4, 4, 0, stretch);
		this.tail[9].setRotationPoint(TAIL_RP_X, TAIL_RP_Y + yOffset, TAIL_RP_Z);
		this.tail[10].addTopPlane(-2.0F, 9.0F, 2.0F, 4, 0, 4, stretch);
		this.tail[10].setRotationPoint(TAIL_RP_X, TAIL_RP_Y + yOffset, TAIL_RP_Z);
		this.tail[11].addSidePlane(-2.0F, 9.0F, 2.0F, 0, 4, 4, stretch);
		this.tail[11].setRotationPoint(TAIL_RP_X, TAIL_RP_Y + yOffset, TAIL_RP_Z);
		this.tail[12].addBackPlane(-2.0F, 9.0F, 2.0F, 4, 4, 0, stretch);
		this.tail[12].setRotationPoint(TAIL_RP_X, TAIL_RP_Y + yOffset, TAIL_RP_Z);
		this.tail[13].addSidePlane(2.0F, 9.0F, 2.0F, 0, 4, 4, stretch);
		this.tail[13].setRotationPoint(TAIL_RP_X, TAIL_RP_Y + yOffset, TAIL_RP_Z);
		this.tail[14].addBackPlane(-2.0F, 9.0F, 6.0F, 4, 4, 0, stretch);
		this.tail[14].setRotationPoint(TAIL_RP_X, TAIL_RP_Y + yOffset, TAIL_RP_Z);
		this.tail[15].addTopPlane(-2.0F, 13.0F, 2.0F, 4, 0, 4, stretch);
		this.tail[15].setRotationPoint(TAIL_RP_X, TAIL_RP_Y + yOffset, TAIL_RP_Z);
		this.tail[16].addSidePlane(-2.0F, 13.0F, 2.0F, 0, 4, 4, stretch);
		this.tail[16].setRotationPoint(TAIL_RP_X, TAIL_RP_Y + yOffset, TAIL_RP_Z);
		this.tail[17].addBackPlane(-2.0F, 13.0F, 2.0F, 4, 4, 0, stretch);
		this.tail[17].setRotationPoint(TAIL_RP_X, TAIL_RP_Y + yOffset, TAIL_RP_Z);
		this.tail[18].addSidePlane(2.0F, 13.0F, 2.0F, 0, 4, 4, stretch);
		this.tail[18].setRotationPoint(TAIL_RP_X, TAIL_RP_Y + yOffset, TAIL_RP_Z);
		this.tail[19].addBackPlane(-2.0F, 13.0F, 6.0F, 4, 4, 0, stretch);
		this.tail[19].setRotationPoint(TAIL_RP_X, TAIL_RP_Y + yOffset, TAIL_RP_Z);
		this.tail[20].addTopPlane(-2.0F, 17.0F, 2.0F, 4, 0, 4, stretch);
		this.tail[20].setRotationPoint(TAIL_RP_X, TAIL_RP_Y + yOffset, TAIL_RP_Z);
	}

	private void initWingPositions(float yOffset, float stretch) {
		this.leftWing[0].addBox(4.0F, 5.0F, 2.0F, 2, 6, 2, stretch);
		this.leftWing[0].setPivot(HEAD_RP_X, WING_FOLDED_RP_Y + yOffset, WING_FOLDED_RP_Z);
		this.leftWing[0].rotationX = ROTATE_90;
		this.leftWing[1].addBox(4.0F, 5.0F, 4.0F, 2, 8, 2, stretch);
		this.leftWing[1].setPivot(HEAD_RP_X, WING_FOLDED_RP_Y + yOffset, WING_FOLDED_RP_Z);
		this.leftWing[1].rotationX = ROTATE_90;
		this.leftWing[2].addBox(4.0F, 5.0F, 6.0F, 2, 6, 2, stretch);
		this.leftWing[2].setPivot(HEAD_RP_X, WING_FOLDED_RP_Y + yOffset, WING_FOLDED_RP_Z);
		this.leftWing[2].rotationX = ROTATE_90;
		this.rightWing[0].addBox(-6.0F, 5.0F, 2.0F, 2, 6, 2, stretch);
		this.rightWing[0].setPivot(HEAD_RP_X, WING_FOLDED_RP_Y + yOffset, WING_FOLDED_RP_Z);
		this.rightWing[0].rotationX = ROTATE_90;
		this.rightWing[1].addBox(-6.0F, 5.0F, 4.0F, 2, 8, 2, stretch);
		this.rightWing[1].setPivot(HEAD_RP_X, WING_FOLDED_RP_Y + yOffset, WING_FOLDED_RP_Z);
		this.rightWing[1].rotationX = ROTATE_90;
		this.rightWing[2].addBox(-6.0F, 5.0F, 6.0F, 2, 6, 2, stretch);
		this.rightWing[2].setPivot(HEAD_RP_X, WING_FOLDED_RP_Y + yOffset, WING_FOLDED_RP_Z);
		this.rightWing[2].rotationX = ROTATE_90;
		this.leftWingExt[0].addBox(0.0F, 0.0F, 0.0F, 1, 8, 2, stretch + 0.1F);
		this.leftWingExt[0].setPivot(LEFT_WING_EXT_RP_X, LEFT_WING_EXT_RP_Y + yOffset, LEFT_WING_EXT_RP_Z);
		this.leftWingExt[1].addBox(0.0F, 8.0F, 0.0F, 1, 6, 2, stretch + 0.1F);
		this.leftWingExt[1].setPivot(LEFT_WING_EXT_RP_X, LEFT_WING_EXT_RP_Y + yOffset, LEFT_WING_EXT_RP_Z);
		this.leftWingExt[2].addBox(0.0F, -1.2F, -0.2F, 1, 8, 2, stretch - 0.2F);
		this.leftWingExt[2].setPivot(LEFT_WING_EXT_RP_X, LEFT_WING_EXT_RP_Y + yOffset, LEFT_WING_EXT_RP_Z);
		this.leftWingExt[3].addBox(0.0F, 1.8F, 1.3F, 1, 8, 2, stretch - 0.1F);
		this.leftWingExt[3].setPivot(LEFT_WING_EXT_RP_X, LEFT_WING_EXT_RP_Y + yOffset, LEFT_WING_EXT_RP_Z);
		this.leftWingExt[4].addBox(0.0F, 5.0F, 2.0F, 1, 8, 2, stretch);
		this.leftWingExt[4].setPivot(LEFT_WING_EXT_RP_X, LEFT_WING_EXT_RP_Y + yOffset, LEFT_WING_EXT_RP_Z);
		this.leftWingExt[5].addBox(0.0F, 0.0F, -0.2F, 1, 6, 2, stretch + 0.3F);
		this.leftWingExt[5].setPivot(LEFT_WING_EXT_RP_X, LEFT_WING_EXT_RP_Y + yOffset, LEFT_WING_EXT_RP_Z);
		this.leftWingExt[6].addBox(0.0F, 0.0F, 0.2F, 1, 3, 2, stretch + 0.2F);
		this.leftWingExt[6].setPivot(LEFT_WING_EXT_RP_X, LEFT_WING_EXT_RP_Y + yOffset, LEFT_WING_EXT_RP_Z);
		this.rightWingExt[0].addBox(0.0F, 0.0F, 0.0F, 1, 8, 2, stretch + 0.1F);
		this.rightWingExt[0].setPivot(RIGHT_WING_EXT_RP_X, RIGHT_WING_EXT_RP_Y + yOffset, RIGHT_WING_EXT_RP_Z);
		this.rightWingExt[1].addBox(0.0F, 8.0F, 0.0F, 1, 6, 2, stretch + 0.1F);
		this.rightWingExt[1].setPivot(RIGHT_WING_EXT_RP_X, RIGHT_WING_EXT_RP_Y + yOffset, RIGHT_WING_EXT_RP_Z);
		this.rightWingExt[2].addBox(0.0F, -1.2F, -0.2F, 1, 8, 2, stretch - 0.2F);
		this.rightWingExt[2].setPivot(RIGHT_WING_EXT_RP_X, RIGHT_WING_EXT_RP_Y + yOffset, RIGHT_WING_EXT_RP_Z);
		this.rightWingExt[3].addBox(0.0F, 1.8F, 1.3F, 1, 8, 2, stretch - 0.1F);
		this.rightWingExt[3].setPivot(RIGHT_WING_EXT_RP_X, RIGHT_WING_EXT_RP_Y + yOffset, RIGHT_WING_EXT_RP_Z);
		this.rightWingExt[4].addBox(0.0F, 5.0F, 2.0F, 1, 8, 2, stretch);
		this.rightWingExt[4].setPivot(RIGHT_WING_EXT_RP_X, RIGHT_WING_EXT_RP_Y + yOffset, RIGHT_WING_EXT_RP_Z);
		this.rightWingExt[5].addBox(0.0F, 0.0F, -0.2F, 1, 6, 2, stretch + 0.3F);
		this.rightWingExt[5].setPivot(RIGHT_WING_EXT_RP_X, RIGHT_WING_EXT_RP_Y + yOffset, RIGHT_WING_EXT_RP_Z);
		this.rightWingExt[6].addBox(0.0F, 0.0F, 0.2F, 1, 3, 2, stretch + 0.2F);
		this.rightWingExt[6].setPivot(RIGHT_WING_EXT_RP_X, RIGHT_WING_EXT_RP_Y + yOffset, RIGHT_WING_EXT_RP_Z);
	}

	@Override
	public void specials(EntityRenderDispatcher dispatcher, PlayerEntity player) {
		if (!this.isSleeping) {
			if (this.isUnicorn) {
				if (this.aimedBow) {
					this.renderDrop(dispatcher, player, this.unicornArm, 1.0F, 0.15F, 0.9375F, 0.0625F);
				} else if (this.size == Pony.Size.FILLY) {
					this.renderDrop(dispatcher, player, this.unicornArm, 1.0F, 0.35F, 0.5375F, -0.8F);
				} else {
					this.renderDrop(dispatcher, player, this.unicornArm, 1.0F, 0.35F, 0.5375F, -0.45F);
				}
			} else if (this.size == Pony.Size.FILLY) {
				this.renderDrop(dispatcher, player, this.rightarm, 1.0F, 0.08F, 0.8375F, 0.0625F);
			} else {
				this.renderDrop(dispatcher, player, this.rightarm, 1.0F, -0.0625F, 0.8375F, 0.0625F);
			}

			if (this.heldItemRight != 0 && this.isUnicorn) {
				GL11.glPushMatrix();
				this.head.translate(0.0625F);
				MineLPGlow.renderGlow(player, this.size, this.isSneaking && !this.isFlying, this.glowColor);
				GL11.glPopMatrix();
			}
		}

		if (this.size == Pony.Size.FILLY) {
			GL11.glPushMatrix();
			GL11.glTranslatef(0.0F, 0.76F, 0.0F);
			GL11.glScalef(0.9F, 0.9F, 0.9F);
			this.renderPumpkin(dispatcher, player, this.head, 0.625F, 0.0F, -0.08F, -0.15F);
			GL11.glPopMatrix();
		} else if (this.size == Pony.Size.STALLION) {
			GL11.glPushMatrix();
			GL11.glTranslatef(0.0F, -0.17F, -0.04F);
			if (this.isSleeping) {
				GL11.glTranslatef(0.0F, 0.0F, -0.1F);
			}

			this.renderPumpkin(dispatcher, player, this.head, 0.625F, 0.0F, -0.08F, -0.15F);
			GL11.glPopMatrix();
		} else if (this.size == Pony.Size.ALICORN) {
			GL11.glPushMatrix();
			GL11.glTranslatef(0.0F, -0.17F, -0.02F);
			this.renderPumpkin(dispatcher, player, this.head, 0.625F, 0.0F, -0.08F, -0.15F);
			GL11.glPopMatrix();
		} else {
			this.renderPumpkin(dispatcher, player, this.head, 0.625F, 0.0F, -0.08F, -0.15F);
		}

	}

	@Override
	protected void renderCloak(PlayerEntity player, float par2) {
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0F, 0.24F, 0.0F);
		if (this.size == Pony.Size.FILLY) {
			GL11.glTranslatef(0.0F, 0.67F, -0.04F);
			GL11.glScalef(0.6F, 0.6F, 0.6F);
		} else if (this.size == Pony.Size.STALLION) {
			GL11.glTranslatef(0.0F, -0.14F, -0.1F);
			GL11.glScalef(1.15F, 1.2F, 1.2F);
			if (this.isSneaking && !this.isFlying) {
				GL11.glTranslatef(0.0F, 0.03F, 0.0F);
			}
		} else if (this.size == Pony.Size.ALICORN) {
			GL11.glTranslatef(0.0F, -0.09F, 0.0F);
			GL11.glScalef(1.0F, 1.0F, 1.0F);
			if (this.isSneaking && !this.isFlying) {
				GL11.glTranslatef(0.0F, 0.03F, 0.0F);
			}
		}

		if (this.isSneaking && !this.isFlying) {
			GL11.glTranslatef(0.0F, 0.4F, -0.12F);
		}

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

		GL11.glRotatef(2.0F + f13 / 12.0F + f12, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(f14 / 2.0F, 0.0F, 0.0F, 1.0F);
		GL11.glRotatef(-f14 / 2.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
		GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
		this.cloak.render(0.0625F);
		GL11.glPopMatrix();
	}
}
