package codes.blackjack.minelittlepony.models;

import codes.blackjack.minelittlepony.AniParams;
import codes.blackjack.minelittlepony.render.MineLPGlow;
import codes.blackjack.minelittlepony.render.ModelPlayer;
import codes.blackjack.minelittlepony.render.PlaneRenderer;
import codes.blackjack.minelittlepony.Pony;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.model.ModelPart;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

public class pm_newPonyAdv extends ModelPlayer {
	private boolean rainboom;
	private float WingRotateAngleZ;
	protected float NeckRotX = 0.166F;
	private int tailstop;
	protected ModelPart cloak;
	public ModelPart head;
	protected ModelPart[] headpiece;
	protected ModelPart helmet;
	public ModelPart Body;
	protected PlaneRenderer[] Bodypiece;
	protected PlaneRenderer[] BodypieceNeck;
	private PlaneRenderer[] MuzzleFemale;
	private PlaneRenderer[] MuzzleMale;
	public ModelPart rightarm;
	public ModelPart LeftArm;
	public ModelPart RightLeg;
	public ModelPart LeftLeg;
	protected ModelPart SteveArm;
	protected ModelPart unicornarm;
	private PlaneRenderer[] Tail;
	private ModelPart[] LeftWing;
	private ModelPart[] RightWing;
	private ModelPart[] LeftWingExt;
	private ModelPart[] RightWingExt;
	protected float HEAD_CENTRE_X;
	protected float HEAD_CENTRE_Y = -1.0F;
	protected float HEAD_CENTRE_Z = -2.0F;
	protected float BODY_CENTRE_X;
	protected float BODY_CENTRE_Y = 8.0F;
	protected float BODY_CENTRE_Z = 6.0F;
	protected float THIRDP_ARM_CENTRE_X;
	protected float THIRDP_ARM_CENTRE_Y = 10.0F;
	protected float THIRDP_ARM_CENTRE_Z;
	protected float FIRSTP_ARM_CENTRE_X = -1.0F;
	protected float FIRSTP_ARM_CENTRE_Y = 4.0F;
	protected float FIRSTP_ARM_CENTRE_Z;
	protected float HEAD_RP_X;
	protected float HEAD_RP_Y;
	protected float HEAD_RP_Z;
	protected float BODY_RP_Y_SNEAK = 7.0F;
	protected float BODY_RP_Y_NOTSNEAK;
	protected float BODY_RP_Z_SNEAK = -4.0F;
	protected float BODY_RP_Z_NOTSNEAK;
	private float FRONT_LEG_RP_Y_SNEAK = 7.0F;
	protected float FRONT_LEG_RP_Y_NOTSNEAK = 8.0F;
	private float WING_FOLDED_RP_Y = 13.0F;
	private float WING_FOLDED_RP_Z = -3.0F;
	private float LEFT_WING_RP_Y_SNEAK = 10.5F;
	private float LEFT_WING_RP_Y_NOTSNEAK = 5.5F;
	private float LEFT_WING_RP_Z_SNEAK = 2.0F;
	private float LEFT_WING_RP_Z_NOTSNEAK = 3.0F;
	private float RIGHT_WING_RP_Y_SNEAK = 11.5F;
	private float RIGHT_WING_RP_Y_NOTSNEAK = 6.5F;
	private float RIGHT_WING_RP_Z_SNEAK = 2.0F;
	private float RIGHT_WING_RP_Z_NOTSNEAK = 3.0F;
	private float TAIL_RP_X;
	private float TAIL_RP_Y = 0.8F;
	private float TAIL_RP_Z;
	private float TAIL_RP_Z_SNEAK = 10.0F;
	private float TAIL_RP_Z_NOTSNEAK = 14.0F;
	private float LEFT_WING_EXT_RP_X = 4.5F;
	private float LEFT_WING_EXT_RP_Y = 5.0F;
	private float LEFT_WING_EXT_RP_Z = 6.0F;
	private float RIGHT_WING_EXT_RP_X = -4.5F;
	private float RIGHT_WING_EXT_RP_Y = 5.0F;
	private float RIGHT_WING_EXT_RP_Z = 6.0F;
	protected float BODY_ROTATE_ANGLE_X_SNEAK = 0.4F;
	protected float BODY_ROTATE_ANGLE_X_NOTSNEAK;
	private float EXT_WING_ROTATE_ANGLE_X = 2.5F;
	private float LEFT_WING_ROTATE_ANGLE_Z_SNEAK = -6.0F;
	private float RIGHT_WING_ROTATE_ANGLE_Z_SNEAK = 6.0F;
	private float SNEAK_LEG_X_ROTATION_ADJUSTMENT = 0.4F;
	private float ROTATE_270 = 4.712F;
	private float ROTATE_90 = 1.571F;
	protected float RIDING_SHIFT_Y = -10.0F;
	protected float RIDING_SHIFT_Z = -10.0F;

	public pm_newPonyAdv(String texture) {
		super(texture);
	}

	public void init() {
		this.init(0.0F);
	}

	public void init(float yOffset) {
		this.init(yOffset, 0.0F);
	}

	public void init(float yOffset, float stretch) {
		this.initTextures();
		this.initPositions(yOffset, stretch);
	}

	public void animate(AniParams aniparams, PlayerEntity entityplayer) {
		this.animate(aniparams);
	}

	public void animate(AniParams aniparams) {
		this.checkRainboom(aniparams.swing);
		this.rotateHead(aniparams.horz, aniparams.vert);
		this.swingTailZ(aniparams.move, aniparams.swing);
		float bodySwingRotation = 0.0F;

		if (this.handSwingProgress > -9990.0F && !this.isUnicorn) {
			bodySwingRotation = MathHelper.sin(MathHelper.sqrt(this.handSwingProgress) /* might be floor??? */ * 3.141593F * 2.0F) * 0.2F;
		}

		this.Body.rotationY = bodySwingRotation * 0.2F;

		for (PlaneRenderer renderer : this.Bodypiece) {
			renderer.rotateAngleY = bodySwingRotation * 0.2F;
		}

		for (PlaneRenderer planeRenderer : this.BodypieceNeck) {
			planeRenderer.rotateAngleY = bodySwingRotation * 0.2F;
		}

		for (ModelPart part : this.LeftWing) {
			part.rotationY = bodySwingRotation * 0.2F;
		}

		for (ModelPart modelPart : this.RightWing) {
			modelPart.rotationY = bodySwingRotation * 0.2F;
		}

		this.tailstop = 0;
		this.tailstop = this.Tail.length - this.wantTail * 5;
		if (this.tailstop <= 1) {
			this.tailstop = 0;
		}

		for (int j1 = 0; j1 < this.tailstop; ++j1) {
			this.Tail[j1].rotateAngleY = bodySwingRotation;
		}

		this.setLegs(aniparams.move, aniparams.swing);
		this.holdItem();
		this.swingItem(this.handSwingProgress);
		if (this.issneak && !this.isFlying) {
			this.adjustBody(this.BODY_ROTATE_ANGLE_X_SNEAK, this.BODY_RP_Y_SNEAK, this.BODY_RP_Z_SNEAK);
			this.animatePegasusWingsSneaking();
			this.sneakLegs();
			this.setHead(0.0F, 6.0F, -2.0F);
			this.sneakTail();
		} else {
			this.adjustBody(this.BODY_ROTATE_ANGLE_X_NOTSNEAK, this.BODY_RP_Y_NOTSNEAK, this.BODY_RP_Z_NOTSNEAK);
			if (this.isPegasus) {
				this.animatePegasusWingsNotSneaking(aniparams.tick);
			}

			this.RightLeg.pivotY = this.FRONT_LEG_RP_Y_NOTSNEAK;
			this.LeftLeg.pivotY = this.FRONT_LEG_RP_Y_NOTSNEAK;
			this.swingArms(aniparams.tick);
			this.setHead(0.0F, 0.0F, 0.0F);
			this.tailstop = 0;
			this.tailstop = this.Tail.length - this.wantTail * 5;
			if (this.tailstop <= 1) {
				this.tailstop = 0;
			}

			for (int k6 = 0; k6 < this.tailstop; ++k6) {
				this.setRotationPoint(this.Tail[k6], this.TAIL_RP_X, this.TAIL_RP_Y, this.TAIL_RP_Z_NOTSNEAK);
				if (this.rainboom) {
					this.Tail[k6].rotateAngleX = this.ROTATE_90 + 0.1F * MathHelper.sin(aniparams.move);
				} else {
					this.Tail[k6].rotateAngleX = 0.5F * aniparams.swing;
				}
			}

			if (!this.rainboom) {
				this.swingTailX(aniparams.tick);
			}
		}

		if (this.rainboom) {
			this.tailstop = 0;
			this.tailstop = this.Tail.length - this.wantTail * 5;
			if (this.tailstop <= 1) {
				this.tailstop = 0;
			}

			for (int k1 = 0; k1 < this.tailstop; ++k1) {
				this.Tail[k1].rotationPointY += 6.0F;
				++this.Tail[k1].rotationPointZ;
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
			for (PlaneRenderer planeRenderer : this.MuzzleMale) {
				this.setRotationPoint(planeRenderer, posX, posY, posZ);
			}
		} else {
			for (PlaneRenderer planeRenderer : this.MuzzleFemale) {
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
			for (PlaneRenderer planeRenderer : this.MuzzleMale) {
				planeRenderer.rotateAngleY = headRotateAngleY;
				planeRenderer.rotateAngleX = headRotateAngleX;
			}
		} else {
			for (PlaneRenderer planeRenderer : this.MuzzleFemale) {
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
				rightArmRotateAngleX = this.ROTATE_270;
				leftArmRotateAngleX = this.ROTATE_270;
				rightLegRotateAngleX = this.ROTATE_90;
				leftLegRotateAngleX = this.ROTATE_90;
			} else {
				rightArmRotateAngleX = MathHelper.sin(0.0F - swing * 0.5F);
				leftArmRotateAngleX = MathHelper.sin(0.0F - swing * 0.5F);
				rightLegRotateAngleX = MathHelper.sin(swing * 0.5F);
				leftLegRotateAngleX = MathHelper.sin(swing * 0.5F);
			}

			this.rightarm.rotationY = 0.2F;
			this.SteveArm.rotationY = 0.2F;
			this.LeftArm.rotationY = -0.2F;
			this.RightLeg.rotationY = -0.2F;
			this.LeftLeg.rotationY = 0.2F;
		} else {
			rightArmRotateAngleX = MathHelper.cos(move * 0.6662F + 3.141593F) * 0.45F * swing;
			leftArmRotateAngleX = MathHelper.cos(move * 0.6662F) * 0.45F * swing;
			rightLegRotateAngleX = MathHelper.cos(move * 0.6662F) * 0.45F * swing;
			leftLegRotateAngleX = MathHelper.cos(move * 0.6662F + 3.141593F) * 0.45F * swing;
			this.rightarm.rotationY = 0.0F;
			this.SteveArm.rotationY = 0.0F;
			this.unicornarm.rotationY = 0.0F;
			this.LeftArm.rotationY = 0.0F;
			this.RightLeg.rotationY = 0.0F;
			this.LeftLeg.rotationY = 0.0F;
		}

		this.rightarm.rotationX = rightArmRotateAngleX;
		this.SteveArm.rotationX = rightArmRotateAngleX;
		this.unicornarm.rotationX = 0.0F;
		this.LeftArm.rotationX = leftArmRotateAngleX;
		this.RightLeg.rotationX = rightLegRotateAngleX;
		this.LeftLeg.rotationX = leftLegRotateAngleX;
		this.rightarm.rotationZ = 0.0F;
		this.SteveArm.rotationZ = 0.0F;
		this.unicornarm.rotationZ = 0.0F;
		this.LeftArm.rotationZ = 0.0F;
	}

	protected void adjustLegs() {
		float sinBodyRotateAngleYFactor = MathHelper.sin(this.Body.rotationY) * 5.0F;
		float cosBodyRotateAngleYFactor = MathHelper.cos(this.Body.rotationY) * 5.0F;
		float legOutset = 4.0F;
		if (this.issneak && !this.isFlying) {
			legOutset = 0.0F;
		}

		if (this.isSleeping) {
			legOutset = 2.6F;
		}

		if (this.rainboom) {
			this.rightarm.pivotZ = sinBodyRotateAngleYFactor + 2.0F;
			this.SteveArm.pivotZ = sinBodyRotateAngleYFactor + 2.0F;
			this.LeftArm.pivotZ = 0.0F - sinBodyRotateAngleYFactor + 2.0F;
		} else {
			this.rightarm.pivotZ = sinBodyRotateAngleYFactor + 1.0F;
			this.SteveArm.pivotZ = sinBodyRotateAngleYFactor + 1.0F;
			this.LeftArm.pivotZ = 0.0F - sinBodyRotateAngleYFactor + 1.0F;
		}

		this.rightarm.pivotX = 0.0F - cosBodyRotateAngleYFactor - 1.0F + legOutset;
		this.SteveArm.pivotX = 0.0F - cosBodyRotateAngleYFactor;
		this.LeftArm.pivotX = cosBodyRotateAngleYFactor + 1.0F - legOutset;
		this.RightLeg.pivotX = 0.0F - cosBodyRotateAngleYFactor - 1.0F + legOutset;
		this.LeftLeg.pivotX = cosBodyRotateAngleYFactor + 1.0F - legOutset;
		ModelPart var10000 = this.rightarm;
		var10000.rotationY += this.Body.rotationY;
		var10000 = this.LeftArm;
		var10000.rotationY += this.Body.rotationY;
		var10000.rotationX += this.Body.rotationY;
		this.rightarm.pivotY = 8.0F;
		this.LeftArm.pivotY = 8.0F;
		this.RightLeg.pivotZ = 10.0F;
		this.LeftLeg.pivotZ = 10.0F;
	}

	private void swingTailZ(float move, float swing) {
		this.tailstop = 0;
		this.tailstop = this.Tail.length - this.wantTail * 5;
		if (this.tailstop <= 1) {
			this.tailstop = 0;
		}

		for (int j = 0; j < this.tailstop; ++j) {
			if (this.rainboom) {
				this.Tail[j].rotateAngleZ = 0.0F;
			} else {
				this.Tail[j].rotateAngleZ = MathHelper.cos(move * 0.8F) * 0.2F * swing;
			}
		}

	}

	private void swingTailX(float tick) {
		float sinTickFactor = MathHelper.sin(tick * 0.067F) * 0.05F;
		this.tailstop = 0;
		this.tailstop = this.Tail.length - this.wantTail * 5;
		if (this.tailstop <= 1) {
			this.tailstop = 0;
		}

		for (int l6 = 0; l6 < this.tailstop; ++l6) {
			PlaneRenderer var10000 = this.Tail[l6];
			var10000.rotateAngleX += sinTickFactor;
		}

	}

	protected void holdItem() {
		if (this.heldItemRight != 0 && !this.rainboom && !this.isUnicorn) {
			this.rightarm.rotationX = this.rightarm.rotationX * 0.5F - 0.3141593F;
			this.SteveArm.rotationX = this.SteveArm.rotationX * 0.5F - 0.3141593F;
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
				ModelPart var10000 = this.unicornarm;
				var10000.rotationX = (float) (var10000.rotationX - (f22 * 1.2 + f33));
				var10000.rotationY += this.Body.rotationY * 2.0F;
				this.unicornarm.rotationZ = f28 * -0.4F;
			} else {
				ModelPart var9 = this.rightarm;
				var9.rotationX = (float) (var9.rotationX - (f22 * 1.2 + f33));
				var9.rotationY += this.Body.rotationY * 2.0F;
				this.rightarm.rotationZ = f28 * -0.4F;
				var9 = this.SteveArm;
				var9.rotationX = (float) (var9.rotationX - (f22 * 1.2 + f33));
				var9.rotationY += this.Body.rotationY * 2.0F;
				this.SteveArm.rotationZ = f28 * -0.4F;
			}
		}

	}

	protected void swingArms(float tick) {
		if (this.heldItemRight != 0 && !this.isSleeping) {
			float cosTickFactor = MathHelper.cos(tick * 0.09F) * 0.05F + 0.05F;
			float sinTickFactor = MathHelper.sin(tick * 0.067F) * 0.05F;
			if (!this.isUnicorn) {
				ModelPart var10000 = this.rightarm;
				var10000.rotationZ += cosTickFactor;
				var10000.rotationX += sinTickFactor;
				var10000 = this.SteveArm;
				var10000.rotationZ += cosTickFactor;
				var10000.rotationX += sinTickFactor;
			} else {
				ModelPart var7 = this.unicornarm;
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

		for (PlaneRenderer planeRenderer : this.Bodypiece) {
			planeRenderer.rotateAngleX = rotateAngleX;
			planeRenderer.rotationPointY = rotationPointY;
			planeRenderer.rotationPointZ = rotationPointZ;
		}

	}

	private void adjustNeck(float rotateAngleX, float rotationPointY, float rotationPointZ) {
		for (PlaneRenderer planeRenderer : this.BodypieceNeck) {
			planeRenderer.rotateAngleX = this.NeckRotX + rotateAngleX;
			planeRenderer.rotationPointY = rotationPointY;
			planeRenderer.rotationPointZ = rotationPointZ;
		}

	}

	protected void sneakLegs() {
		ModelPart var10000 = this.rightarm;
		var10000.rotationX -= this.SNEAK_LEG_X_ROTATION_ADJUSTMENT;
		var10000 = this.SteveArm;
		var10000.rotationX += this.SNEAK_LEG_X_ROTATION_ADJUSTMENT;
		var10000 = this.unicornarm;
		var10000.rotationX += this.SNEAK_LEG_X_ROTATION_ADJUSTMENT;
		var10000 = this.LeftArm;
		var10000.rotationX -= this.SNEAK_LEG_X_ROTATION_ADJUSTMENT;
		this.RightLeg.pivotY = this.FRONT_LEG_RP_Y_SNEAK;
		this.LeftLeg.pivotY = this.FRONT_LEG_RP_Y_SNEAK;
	}

	private void sneakTail() {
		this.tailstop = 0;
		this.tailstop = this.Tail.length - this.wantTail * 5;
		if (this.tailstop <= 1) {
			this.tailstop = 0;
		}

		for (int i7 = 0; i7 < this.tailstop; ++i7) {
			this.setRotationPoint(this.Tail[i7], this.TAIL_RP_X, this.TAIL_RP_Y, this.TAIL_RP_Z_SNEAK);
			this.Tail[i7].rotateAngleX = 0.0F;
		}

	}

	protected void ponySleep() {
		this.rightarm.rotationX = this.ROTATE_270;
		this.LeftArm.rotationX = this.ROTATE_270;
		this.RightLeg.rotationX = this.ROTATE_90;
		this.LeftLeg.rotationX = this.ROTATE_90;
		float headPosX;
		float headPosY;
		float headPosZ;
		headPosY = 2.0F;
		if (this.issneak) {
			headPosZ = -1.0F;
		} else {
			headPosZ = 1.0F;
		}
		headPosX = 1.0F;

		this.setHead(headPosX, headPosY, headPosZ);
		this.shiftRotationPoint(this.rightarm, 0.0F, 2.0F, 6.0F);
		this.shiftRotationPoint(this.LeftArm, 0.0F, 2.0F, 6.0F);
		this.shiftRotationPoint(this.RightLeg, 0.0F, 2.0F, -8.0F);
		this.shiftRotationPoint(this.LeftLeg, 0.0F, 2.0F, -8.0F);
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
		this.rightarm.rotationX = this.ROTATE_270 + this.head.rotationX;
		ModelPart var10000 = this.rightarm;
		var10000.rotationZ += MathHelper.cos(tick * 0.09F) * 0.05F + 0.05F;
		var10000.rotationX += MathHelper.sin(tick * 0.067F) * 0.05F;
		this.shiftRotationPoint(this.rightarm, 0.0F, 0.0F, 1.0F);
	}

	private void aimBowUnicorn(float tick) {
		this.unicornarm.rotationZ = 0.0F;
		this.unicornarm.rotationY = -0.06F + this.head.rotationY;
		this.unicornarm.rotationX = this.ROTATE_270 + this.head.rotationX;
		ModelPart var10000 = this.unicornarm;
		var10000.rotationZ += MathHelper.cos(tick * 0.09F) * 0.05F + 0.05F;
		var10000.rotationX += MathHelper.sin(tick * 0.067F) * 0.05F;
	}

	private void animatePegasusWingsSneaking() {
		for (ModelPart modelPart : this.LeftWingExt) {
			modelPart.pivotY = this.LEFT_WING_RP_Y_SNEAK;
			modelPart.pivotZ = this.LEFT_WING_RP_Z_SNEAK;
			modelPart.rotationX = this.EXT_WING_ROTATE_ANGLE_X;
			modelPart.rotationZ = this.LEFT_WING_ROTATE_ANGLE_Z_SNEAK;
		}

		for (int k5 = 0; k5 < this.LeftWingExt.length; ++k5) {
			this.RightWingExt[k5].pivotY = this.RIGHT_WING_RP_Y_SNEAK;
			this.RightWingExt[k5].pivotZ = this.RIGHT_WING_RP_Z_SNEAK;
			this.RightWingExt[k5].rotationX = this.EXT_WING_ROTATE_ANGLE_X;
			this.RightWingExt[k5].rotationZ = this.RIGHT_WING_ROTATE_ANGLE_Z_SNEAK;
		}

	}

	private void animatePegasusWingsNotSneaking(float tick) {
		if (!this.isFlying) {
			for (ModelPart part : this.LeftWing) {
				part.pivotY = this.WING_FOLDED_RP_Y;
				part.pivotZ = this.WING_FOLDED_RP_Z;
			}

			for (ModelPart modelPart : this.RightWing) {
				modelPart.pivotY = this.WING_FOLDED_RP_Y;
				modelPart.pivotZ = this.WING_FOLDED_RP_Z;
			}
		} else {
			this.WingRotateAngleZ = MathHelper.sin(tick * 0.536F);

			for (ModelPart part : this.LeftWingExt) {
				part.rotationX = this.EXT_WING_ROTATE_ANGLE_X;
				part.rotationZ = -this.WingRotateAngleZ - this.ROTATE_270 - 0.4F;
				part.pivotY = this.LEFT_WING_RP_Y_NOTSNEAK;
				part.pivotZ = this.LEFT_WING_RP_Z_NOTSNEAK;
			}

			for (ModelPart modelPart : this.RightWingExt) {
				modelPart.rotationX = this.EXT_WING_ROTATE_ANGLE_X;
				modelPart.rotationZ = this.WingRotateAngleZ + this.ROTATE_270 + 0.4F;
				modelPart.pivotY = this.RIGHT_WING_RP_Y_NOTSNEAK;
				modelPart.pivotZ = this.RIGHT_WING_RP_Z_NOTSNEAK;
			}
		}

	}

	private void fixSpecialRotations() {
		this.LeftWingExt[2].rotationX -= 0.85F;
		this.LeftWingExt[3].rotationX -= 0.75F;
		this.LeftWingExt[4].rotationX -= 0.5F;
		this.LeftWingExt[6].rotationX -= 0.85F;
		this.RightWingExt[2].rotationX -= 0.85F;
		this.RightWingExt[3].rotationX -= 0.75F;
		this.RightWingExt[4].rotationX -= 0.5F;
		this.RightWingExt[6].rotationX -= 0.85F;
		this.Bodypiece[9].rotateAngleX += 0.5F;
		this.Bodypiece[10].rotateAngleX += 0.5F;
		this.Bodypiece[11].rotateAngleX += 0.5F;
		this.Bodypiece[12].rotateAngleX += 0.5F;
		this.Bodypiece[13].rotateAngleX += 0.5F;
	}

	public void shiftRotationPoint(PlaneRenderer aPlaneRenderer, float shiftX, float shiftY, float shiftZ) {
		aPlaneRenderer.rotationPointX += shiftX;
		aPlaneRenderer.rotationPointY += shiftY;
		aPlaneRenderer.rotationPointZ += shiftZ;
	}

	protected void shiftRotationPoint(ModelPart aRenderer, float shiftX, float shiftY, float shiftZ) {
		aRenderer.pivotX += shiftX;
		aRenderer.pivotY += shiftY;
		aRenderer.pivotZ += shiftZ;
	}

	private void setRotationPoint(PlaneRenderer aPlaneRenderer, float setX, float setY, float setZ) {
		aPlaneRenderer.rotationPointX = setX;
		aPlaneRenderer.rotationPointY = setY;
		aPlaneRenderer.rotationPointZ = setZ;
	}

	protected void setRotationPoint(ModelPart aRenderer, float setX, float setY, float setZ) {
		aRenderer.pivotX = setX;
		aRenderer.pivotY = setY;
		aRenderer.pivotZ = setZ;
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

			if (this.size == 0) {
				if (this.issneak && !this.isFlying && !this.isArmour) {
					GL11.glTranslatef(0.0F, -0.12F, 0.0F);
				}

				if (this.isSleeping && !this.isArmour) {
					GL11.glTranslatef(0.0F, -1.0F, 0.25F);
				}

				GL11.glPushMatrix();
				GL11.glTranslatef(0.0F, 0.76F, 0.0F);
				GL11.glScalef(0.9F, 0.9F, 0.9F);
				this.renderHead();
				if (this.issneak && !this.isFlying) {
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
				if (this.issneak && !this.isFlying) {
					GL11.glTranslatef(0.0F, 0.12F, 0.0F);
				}

				if (this.rainboom) {
					GL11.glTranslatef(0.0F, -0.08F, 0.0F);
				}

				this.renderLegs();
				GL11.glPopMatrix();
			} else if (this.size == 2) {
				if (this.isSleeping && !this.isArmour) {
					GL11.glTranslatef(0.0F, -0.47F, 0.2F);
				}

				GL11.glPushMatrix();
				GL11.glTranslatef(0.0F, -0.17F, -0.04F);
				if (this.isSleeping && !this.isArmour) {
					GL11.glTranslatef(0.0F, 0.0F, -0.1F);
				}

				if (this.issneak && !this.isFlying) {
					GL11.glTranslatef(0.0F, 0.15F, 0.0F);
				}

				this.renderHead();
				GL11.glPopMatrix();
				GL11.glPushMatrix();
				GL11.glTranslatef(0.0F, -0.15F, -0.07F);
				if (this.issneak && !this.isFlying) {
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
			} else if (this.size == 3) {
				if (this.isSleeping && !this.isArmour) {
					GL11.glTranslatef(0.0F, -0.43F, 0.25F);
				}

				GL11.glPushMatrix();
				GL11.glTranslatef(0.0F, -0.15F, 0.01F);
				if (this.issneak && !this.isFlying) {
					GL11.glTranslatef(0.0F, 0.05F, 0.0F);
				}

				this.renderHead();
				GL11.glPopMatrix();
				GL11.glPushMatrix();
				GL11.glTranslatef(0.0F, -0.19F, -0.01F);
				GL11.glScalef(1.0F, 1.1F, 1.0F);
				if (this.issneak && !this.isFlying) {
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
			this.SteveArm.render(this.scale);
		}

	}

	protected void renderHead() {
		this.head.render(this.scale);
		this.headpiece[0].render(this.scale);
		this.headpiece[1].render(this.scale);
		if (this.isUnicorn) {
			this.headpiece[2].render(this.scale);
		}

		if (Pony.showSnuzzles() == 1) {
			if (this.isMale) {
				for (PlaneRenderer planeRenderer : this.MuzzleMale) {
					planeRenderer.render(this.scale);
				}
			} else {
				for (PlaneRenderer planeRenderer : this.MuzzleFemale) {
					planeRenderer.render(this.scale);
				}
			}
		}

		this.helmet.render(this.scale);
	}

	protected void renderNeck() {
		for (PlaneRenderer planeRenderer : this.BodypieceNeck) {
			planeRenderer.render(this.scale);
		}

	}

	protected void renderBody() {
		this.Body.render(this.scale);

		for (PlaneRenderer planeRenderer : this.Bodypiece) {
			planeRenderer.render(this.scale);
		}

		if (this.isPegasus) {
			if (!this.isFlying && !this.issneak) {
				for (ModelPart part : this.LeftWing) {
					part.render(this.scale);
				}

				for (ModelPart modelPart : this.RightWing) {
					modelPart.render(this.scale);
				}
			} else {
				for (ModelPart part : this.LeftWingExt) {
					part.render(this.scale);
				}

				for (ModelPart modelPart : this.RightWingExt) {
					modelPart.render(this.scale);
				}
			}
		}

	}

	protected void renderTail() {
		int tailstop = 0;
		tailstop = this.Tail.length - this.wantTail * 5;
		if (tailstop <= 1) {
			tailstop = 0;
		}

		for (int k = 0; k < tailstop; ++k) {
			this.Tail[k].render(this.scale);
		}

	}

	protected void renderLegs() {
		this.LeftArm.render(this.scale);
		this.rightarm.render(this.scale);
		this.LeftLeg.render(this.scale);
		this.RightLeg.render(this.scale);
	}

	protected void initTextures() {
		this.Tail = new PlaneRenderer[21];
		this.headpiece = new ModelPart[3];
		this.MuzzleFemale = new PlaneRenderer[10];
		this.MuzzleMale = new PlaneRenderer[5];
		this.Bodypiece = new PlaneRenderer[14];
		this.BodypieceNeck = new PlaneRenderer[4];
		this.LeftWing = new ModelPart[3];
		this.RightWing = new ModelPart[3];
		this.LeftWingExt = new ModelPart[7];
		this.RightWingExt = new ModelPart[7];
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
		this.MuzzleFemale[0] = new PlaneRenderer(this, 10, 14);
		this.MuzzleFemale[1] = new PlaneRenderer(this, 11, 13);
		this.MuzzleFemale[2] = new PlaneRenderer(this, 9, 14);
		this.MuzzleFemale[3] = new PlaneRenderer(this, 14, 14);
		this.MuzzleFemale[4] = new PlaneRenderer(this, 11, 12);
		this.MuzzleFemale[5] = new PlaneRenderer(this, 18, 7);
		this.MuzzleFemale[6] = new PlaneRenderer(this, 9, 14);
		this.MuzzleFemale[7] = new PlaneRenderer(this, 14, 14);
		this.MuzzleFemale[8] = new PlaneRenderer(this, 11, 12);
		this.MuzzleFemale[9] = new PlaneRenderer(this, 12, 12);
		this.MuzzleMale[0] = new PlaneRenderer(this, 10, 13);
		this.MuzzleMale[1] = new PlaneRenderer(this, 10, 13);
		this.MuzzleMale[2] = new PlaneRenderer(this, 18, 7);
		this.MuzzleMale[3] = new PlaneRenderer(this, 10, 13);
		this.MuzzleMale[4] = new PlaneRenderer(this, 13, 13);
	}

	protected void initBodyTextures() {
		this.Body = new ModelPart(this, 16, 16);
		this.Bodypiece[0] = new PlaneRenderer(this, 24, 0);
		this.Bodypiece[1] = new PlaneRenderer(this, 24, 0);
		this.Bodypiece[2] = new PlaneRenderer(this, 32, 20);
		this.Bodypiece[2].mirrorxy = true;
		this.Bodypiece[3] = new PlaneRenderer(this, 56, 0);
		this.Bodypiece[4] = new PlaneRenderer(this, 4, 0);
		this.Bodypiece[5] = new PlaneRenderer(this, 4, 0);
		this.Bodypiece[6] = new PlaneRenderer(this, 36, 16);
		this.Bodypiece[7] = new PlaneRenderer(this, 36, 16);
		this.Bodypiece[8] = new PlaneRenderer(this, 36, 16);
		this.Bodypiece[9] = new PlaneRenderer(this, 32, 0);
		this.Bodypiece[10] = new PlaneRenderer(this, 32, 0);
		this.Bodypiece[11] = new PlaneRenderer(this, 32, 0);
		this.Bodypiece[11].mirror = true;
		this.Bodypiece[12] = new PlaneRenderer(this, 32, 0);
		this.Bodypiece[13] = new PlaneRenderer(this, 32, 0);
		this.BodypieceNeck[0] = new PlaneRenderer(this, 0, 16);
		this.BodypieceNeck[1] = new PlaneRenderer(this, 0, 16);
		this.BodypieceNeck[2] = new PlaneRenderer(this, 0, 16);
		this.BodypieceNeck[3] = new PlaneRenderer(this, 0, 16);
	}

	protected void initLegTextures() {
		this.rightarm = new ModelPart(this, 40, 16);
		this.LeftArm = new ModelPart(this, 40, 16);
		this.LeftArm.flipped = true;
		this.RightLeg = new ModelPart(this, 0, 16);
		this.LeftLeg = new ModelPart(this, 0, 16);
		this.LeftLeg.flipped = true;
		this.SteveArm = new ModelPart(this, 40, 16);
		this.unicornarm = new ModelPart(this, 40, 16);
	}

	private void initTailTextures() {
		this.Tail[0] = new PlaneRenderer(this, 32, 0);
		this.Tail[1] = new PlaneRenderer(this, 36, 0);
		this.Tail[2] = new PlaneRenderer(this, 32, 0);
		this.Tail[3] = new PlaneRenderer(this, 36, 0);
		this.Tail[4] = new PlaneRenderer(this, 32, 0);
		this.Tail[5] = new PlaneRenderer(this, 32, 0);
		this.Tail[6] = new PlaneRenderer(this, 36, 4);
		this.Tail[7] = new PlaneRenderer(this, 32, 4);
		this.Tail[8] = new PlaneRenderer(this, 36, 4);
		this.Tail[9] = new PlaneRenderer(this, 32, 4);
		this.Tail[10] = new PlaneRenderer(this, 32, 0);
		this.Tail[11] = new PlaneRenderer(this, 36, 0);
		this.Tail[12] = new PlaneRenderer(this, 32, 0);
		this.Tail[13] = new PlaneRenderer(this, 36, 0);
		this.Tail[14] = new PlaneRenderer(this, 32, 0);
		this.Tail[15] = new PlaneRenderer(this, 32, 0);
		this.Tail[16] = new PlaneRenderer(this, 36, 4);
		this.Tail[17] = new PlaneRenderer(this, 32, 4);
		this.Tail[18] = new PlaneRenderer(this, 36, 4);
		this.Tail[19] = new PlaneRenderer(this, 32, 4);
		this.Tail[20] = new PlaneRenderer(this, 32, 0);
	}

	private void initWingTextures() {
		this.LeftWing[0] = new ModelPart(this, 56, 16);
		this.LeftWing[0].flipped = true;
		this.LeftWing[1] = new ModelPart(this, 56, 16);
		this.LeftWing[1].flipped = true;
		this.LeftWing[2] = new ModelPart(this, 56, 16);
		this.LeftWing[2].flipped = true;
		this.RightWing[0] = new ModelPart(this, 56, 16);
		this.RightWing[1] = new ModelPart(this, 56, 16);
		this.RightWing[2] = new ModelPart(this, 56, 16);
		this.LeftWingExt[0] = new ModelPart(this, 56, 19);
		this.LeftWingExt[0].flipped = true;
		this.LeftWingExt[1] = new ModelPart(this, 56, 19);
		this.LeftWingExt[1].flipped = true;
		this.LeftWingExt[2] = new ModelPart(this, 56, 19);
		this.LeftWingExt[2].flipped = true;
		this.LeftWingExt[3] = new ModelPart(this, 56, 19);
		this.LeftWingExt[3].flipped = true;
		this.LeftWingExt[4] = new ModelPart(this, 56, 19);
		this.LeftWingExt[4].flipped = true;
		this.LeftWingExt[5] = new ModelPart(this, 56, 19);
		this.LeftWingExt[5].flipped = true;
		this.LeftWingExt[6] = new ModelPart(this, 56, 19);
		this.LeftWingExt[6].flipped = true;
		this.RightWingExt[0] = new ModelPart(this, 56, 19);
		this.RightWingExt[0].flipped = true;
		this.RightWingExt[1] = new ModelPart(this, 56, 19);
		this.RightWingExt[1].flipped = true;
		this.RightWingExt[2] = new ModelPart(this, 56, 19);
		this.RightWingExt[2].flipped = true;
		this.RightWingExt[3] = new ModelPart(this, 56, 19);
		this.RightWingExt[3].flipped = true;
		this.RightWingExt[4] = new ModelPart(this, 56, 19);
		this.RightWingExt[4].flipped = true;
		this.RightWingExt[5] = new ModelPart(this, 56, 19);
		this.RightWingExt[5].flipped = true;
		this.RightWingExt[6] = new ModelPart(this, 56, 19);
		this.RightWingExt[6].flipped = true;
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
		this.head.addBox(-4.0F + this.HEAD_CENTRE_X, -4.0F + this.HEAD_CENTRE_Y, -4.0F + this.HEAD_CENTRE_Z, 8, 8, 8, stretch);
		this.head.setPivot(this.HEAD_RP_X, this.HEAD_RP_Y + yOffset, this.HEAD_RP_Z);
		this.headpiece[0].addBox(-4.0F + this.HEAD_CENTRE_X, -6.0F + this.HEAD_CENTRE_Y, 1.0F + this.HEAD_CENTRE_Z, 2, 2, 2, stretch);
		this.headpiece[0].setPivot(this.HEAD_RP_X, this.HEAD_RP_Y + yOffset, this.HEAD_RP_Z);
		this.headpiece[1].addBox(2.0F + this.HEAD_CENTRE_X, -6.0F + this.HEAD_CENTRE_Y, 1.0F + this.HEAD_CENTRE_Z, 2, 2, 2, stretch);
		this.headpiece[1].setPivot(this.HEAD_RP_X, this.HEAD_RP_Y + yOffset, this.HEAD_RP_Z);
		this.headpiece[2].addBox(-0.5F + this.HEAD_CENTRE_X, -10.0F + this.HEAD_CENTRE_Y, -1.5F + this.HEAD_CENTRE_Z, 1, 4, 1, stretch);
		this.headpiece[2].setPivot(this.HEAD_RP_X, this.HEAD_RP_Y + yOffset, this.HEAD_RP_Z);
		this.helmet.addBox(-4.0F + this.HEAD_CENTRE_X, -4.0F + this.HEAD_CENTRE_Y, -4.0F + this.HEAD_CENTRE_Z, 8, 8, 8, stretch + 0.5F);
		this.helmet.setPivot(this.HEAD_RP_X, this.HEAD_RP_Y + yOffset, this.HEAD_RP_Z);
	}

	private void initMuzzlePositions(float yOffset, float stretch) {
		this.MuzzleFemale[0].addBackPlane(-2.0F + this.HEAD_CENTRE_X, 2.0F + this.HEAD_CENTRE_Y, -5.0F + this.HEAD_CENTRE_Z, 4, 2, 0, stretch);
		this.MuzzleFemale[0].setRotationPoint(this.HEAD_RP_X, this.HEAD_RP_Y + yOffset, this.HEAD_RP_Z);
		this.MuzzleFemale[1].addBackPlane(-1.0F + this.HEAD_CENTRE_X, 1.0F + this.HEAD_CENTRE_Y, -5.0F + this.HEAD_CENTRE_Z, 2, 1, 0, stretch);
		this.MuzzleFemale[1].setRotationPoint(this.HEAD_RP_X, this.HEAD_RP_Y + yOffset, this.HEAD_RP_Z);
		this.MuzzleFemale[2].addTopPlane(-2.0F + this.HEAD_CENTRE_X, 2.0F + this.HEAD_CENTRE_Y, -5.0F + this.HEAD_CENTRE_Z, 1, 0, 1, stretch);
		this.MuzzleFemale[2].setRotationPoint(this.HEAD_RP_X, this.HEAD_RP_Y + yOffset, this.HEAD_RP_Z);
		this.MuzzleFemale[3].addTopPlane(1.0F + this.HEAD_CENTRE_X, 2.0F + this.HEAD_CENTRE_Y, -5.0F + this.HEAD_CENTRE_Z, 1, 0, 1, stretch);
		this.MuzzleFemale[3].setRotationPoint(this.HEAD_RP_X, this.HEAD_RP_Y + yOffset, this.HEAD_RP_Z);
		this.MuzzleFemale[4].addTopPlane(-1.0F + this.HEAD_CENTRE_X, 1.0F + this.HEAD_CENTRE_Y, -5.0F + this.HEAD_CENTRE_Z, 2, 0, 1, stretch);
		this.MuzzleFemale[4].setRotationPoint(this.HEAD_RP_X, this.HEAD_RP_Y + yOffset, this.HEAD_RP_Z);
		this.MuzzleFemale[5].addBottomPlane(-2.0F + this.HEAD_CENTRE_X, 4.0F + this.HEAD_CENTRE_Y, -5.0F + this.HEAD_CENTRE_Z, 4, 0, 1, stretch);
		this.MuzzleFemale[5].setRotationPoint(this.HEAD_RP_X, this.HEAD_RP_Y + yOffset, this.HEAD_RP_Z);
		this.MuzzleFemale[6].addSidePlane(-2.0F + this.HEAD_CENTRE_X, 2.0F + this.HEAD_CENTRE_Y, -5.0F + this.HEAD_CENTRE_Z, 0, 2, 1, stretch);
		this.MuzzleFemale[6].setRotationPoint(this.HEAD_RP_X, this.HEAD_RP_Y + yOffset, this.HEAD_RP_Z);
		this.MuzzleFemale[7].addSidePlane(2.0F + this.HEAD_CENTRE_X, 2.0F + this.HEAD_CENTRE_Y, -5.0F + this.HEAD_CENTRE_Z, 0, 2, 1, stretch);
		this.MuzzleFemale[7].setRotationPoint(this.HEAD_RP_X, this.HEAD_RP_Y + yOffset, this.HEAD_RP_Z);
		this.MuzzleFemale[8].addSidePlane(-1.0F + this.HEAD_CENTRE_X, 1.0F + this.HEAD_CENTRE_Y, -5.0F + this.HEAD_CENTRE_Z, 0, 1, 1, stretch);
		this.MuzzleFemale[8].setRotationPoint(this.HEAD_RP_X, this.HEAD_RP_Y + yOffset, this.HEAD_RP_Z);
		this.MuzzleFemale[9].addSidePlane(1.0F + this.HEAD_CENTRE_X, 1.0F + this.HEAD_CENTRE_Y, -5.0F + this.HEAD_CENTRE_Z, 0, 1, 1, stretch);
		this.MuzzleFemale[9].setRotationPoint(this.HEAD_RP_X, this.HEAD_RP_Y + yOffset, this.HEAD_RP_Z);
		this.MuzzleMale[0].addBackPlane(-2.0F + this.HEAD_CENTRE_X, 1.0F + this.HEAD_CENTRE_Y, -5.0F + this.HEAD_CENTRE_Z, 4, 3, 0, stretch);
		this.MuzzleMale[0].setRotationPoint(this.HEAD_RP_X, this.HEAD_RP_Y + yOffset, this.HEAD_RP_Z);
		this.MuzzleMale[1].addTopPlane(-2.0F + this.HEAD_CENTRE_X, 1.0F + this.HEAD_CENTRE_Y, -5.0F + this.HEAD_CENTRE_Z, 4, 0, 1, stretch);
		this.MuzzleMale[1].setRotationPoint(this.HEAD_RP_X, this.HEAD_RP_Y + yOffset, this.HEAD_RP_Z);
		this.MuzzleMale[2].addBottomPlane(-2.0F + this.HEAD_CENTRE_X, 4.0F + this.HEAD_CENTRE_Y, -5.0F + this.HEAD_CENTRE_Z, 4, 0, 1, stretch);
		this.MuzzleMale[2].setRotationPoint(this.HEAD_RP_X, this.HEAD_RP_Y + yOffset, this.HEAD_RP_Z);
		this.MuzzleMale[3].addSidePlane(-2.0F + this.HEAD_CENTRE_X, 1.0F + this.HEAD_CENTRE_Y, -5.0F + this.HEAD_CENTRE_Z, 0, 3, 1, stretch);
		this.MuzzleMale[3].setRotationPoint(this.HEAD_RP_X, this.HEAD_RP_Y + yOffset, this.HEAD_RP_Z);
		this.MuzzleMale[4].addSidePlane(2.0F + this.HEAD_CENTRE_X, 1.0F + this.HEAD_CENTRE_Y, -5.0F + this.HEAD_CENTRE_Z, 0, 3, 1, stretch);
		this.MuzzleMale[4].setRotationPoint(this.HEAD_RP_X, this.HEAD_RP_Y + yOffset, this.HEAD_RP_Z);
	}

	protected void initBodyPositions(float yOffset, float stretch) {
		this.Body.addBox(-4.0F, 4.0F, -2.0F, 8, 8, 4, stretch);
		this.Body.setPivot(this.HEAD_RP_X, this.HEAD_RP_Y + yOffset, this.HEAD_RP_Z);
		this.Bodypiece[0].addSidePlane(-4.0F + this.BODY_CENTRE_X, -4.0F + this.BODY_CENTRE_Y, -4.0F + this.BODY_CENTRE_Z, 0, 8, 8, stretch);
		this.Bodypiece[0].setRotationPoint(this.HEAD_RP_X, this.HEAD_RP_Y + yOffset, this.HEAD_RP_Z);
		this.Bodypiece[1].addSidePlane(4.0F + this.BODY_CENTRE_X, -4.0F + this.BODY_CENTRE_Y, -4.0F + this.BODY_CENTRE_Z, 0, 8, 8, stretch);
		this.Bodypiece[1].setRotationPoint(this.HEAD_RP_X, this.HEAD_RP_Y + yOffset, this.HEAD_RP_Z);
		this.Bodypiece[2].addTopPlane(-4.0F + this.BODY_CENTRE_X, -4.0F + this.BODY_CENTRE_Y, -4.0F + this.BODY_CENTRE_Z, 8, 0, 12, stretch);
		this.Bodypiece[2].setRotationPoint(this.HEAD_RP_X, this.HEAD_RP_Y + yOffset, this.HEAD_RP_Z);
		this.Bodypiece[3].addBottomPlane(-4.0F + this.BODY_CENTRE_X, 4.0F + this.BODY_CENTRE_Y, -4.0F + this.BODY_CENTRE_Z, 8, 0, 8, stretch);
		this.Bodypiece[3].setRotationPoint(this.HEAD_RP_X, this.HEAD_RP_Y + yOffset, this.HEAD_RP_Z);
		this.Bodypiece[4].addSidePlane(-4.0F + this.BODY_CENTRE_X, -4.0F + this.BODY_CENTRE_Y, 4.0F + this.BODY_CENTRE_Z, 0, 8, 4, stretch);
		this.Bodypiece[4].setRotationPoint(this.HEAD_RP_X, this.HEAD_RP_Y + yOffset, this.HEAD_RP_Z);
		this.Bodypiece[5].addSidePlane(4.0F + this.BODY_CENTRE_X, -4.0F + this.BODY_CENTRE_Y, 4.0F + this.BODY_CENTRE_Z, 0, 8, 4, stretch);
		this.Bodypiece[5].setRotationPoint(this.HEAD_RP_X, this.HEAD_RP_Y + yOffset, this.HEAD_RP_Z);
		this.Bodypiece[6].addBackPlane(-4.0F + this.BODY_CENTRE_X, -4.0F + this.BODY_CENTRE_Y, 8.0F + this.BODY_CENTRE_Z, 8, 4, 0, stretch);
		this.Bodypiece[6].setRotationPoint(this.HEAD_RP_X, this.HEAD_RP_Y + yOffset, this.HEAD_RP_Z);
		this.Bodypiece[7].addBackPlane(-4.0F + this.BODY_CENTRE_X, 0.0F + this.BODY_CENTRE_Y, 8.0F + this.BODY_CENTRE_Z, 8, 4, 0, stretch);
		this.Bodypiece[7].setRotationPoint(this.HEAD_RP_X, this.HEAD_RP_Y + yOffset, this.HEAD_RP_Z);
		this.Bodypiece[8].addBottomPlane(-4.0F + this.BODY_CENTRE_X, 4.0F + this.BODY_CENTRE_Y, 4.0F + this.BODY_CENTRE_Z, 8, 0, 4, stretch);
		this.Bodypiece[8].setRotationPoint(this.HEAD_RP_X, this.HEAD_RP_Y + yOffset, this.HEAD_RP_Z);
		this.Bodypiece[9].addTopPlane(-1.0F + this.BODY_CENTRE_X, 2.0F + this.BODY_CENTRE_Y, 2.0F + this.BODY_CENTRE_Z, 2, 0, 6, stretch);
		this.Bodypiece[9].setRotationPoint(this.HEAD_RP_X, this.HEAD_RP_Y + yOffset, this.HEAD_RP_Z);
		this.Bodypiece[10].addBottomPlane(-1.0F + this.BODY_CENTRE_X, 4.0F + this.BODY_CENTRE_Y, 2.0F + this.BODY_CENTRE_Z, 2, 0, 6, stretch);
		this.Bodypiece[10].setRotationPoint(this.HEAD_RP_X, this.HEAD_RP_Y + yOffset, this.HEAD_RP_Z);
		this.Bodypiece[11].addSidePlane(-1.0F + this.BODY_CENTRE_X, 2.0F + this.BODY_CENTRE_Y, 2.0F + this.BODY_CENTRE_Z, 0, 2, 6, stretch);
		this.Bodypiece[11].setRotationPoint(this.HEAD_RP_X, this.HEAD_RP_Y + yOffset, this.HEAD_RP_Z);
		this.Bodypiece[12].addSidePlane(1.0F + this.BODY_CENTRE_X, 2.0F + this.BODY_CENTRE_Y, 2.0F + this.BODY_CENTRE_Z, 0, 2, 6, stretch);
		this.Bodypiece[12].setRotationPoint(this.HEAD_RP_X, this.HEAD_RP_Y + yOffset, this.HEAD_RP_Z);
		this.Bodypiece[13].addBackPlane(-1.0F + this.BODY_CENTRE_X, 2.0F + this.BODY_CENTRE_Y, 8.0F + this.BODY_CENTRE_Z, 2, 2, 0, stretch);
		this.Bodypiece[13].setRotationPoint(this.HEAD_RP_X, this.HEAD_RP_Y + yOffset, this.HEAD_RP_Z);
		this.BodypieceNeck[0].addBackPlane(-2.0F + this.BODY_CENTRE_X, -6.8F + this.BODY_CENTRE_Y, -8.8F + this.BODY_CENTRE_Z, 4, 4, 0, stretch);
		this.BodypieceNeck[0].setRotationPoint(this.HEAD_RP_X, this.HEAD_RP_Y + yOffset, this.HEAD_RP_Z);
		this.BodypieceNeck[1].addBackPlane(-2.0F + this.BODY_CENTRE_X, -6.8F + this.BODY_CENTRE_Y, -4.8F + this.BODY_CENTRE_Z, 4, 4, 0, stretch);
		this.BodypieceNeck[1].setRotationPoint(this.HEAD_RP_X, this.HEAD_RP_Y + yOffset, this.HEAD_RP_Z);
		this.BodypieceNeck[2].addSidePlane(-2.0F + this.BODY_CENTRE_X, -6.8F + this.BODY_CENTRE_Y, -8.8F + this.BODY_CENTRE_Z, 0, 4, 4, stretch);
		this.BodypieceNeck[2].setRotationPoint(this.HEAD_RP_X, this.HEAD_RP_Y + yOffset, this.HEAD_RP_Z);
		this.BodypieceNeck[3].addSidePlane(2.0F + this.BODY_CENTRE_X, -6.8F + this.BODY_CENTRE_Y, -8.8F + this.BODY_CENTRE_Z, 0, 4, 4, stretch);
		this.BodypieceNeck[3].setRotationPoint(this.HEAD_RP_X, this.HEAD_RP_Y + yOffset, this.HEAD_RP_Z);
		this.BodypieceNeck[0].rotateAngleX = this.NeckRotX;
		this.BodypieceNeck[1].rotateAngleX = this.NeckRotX;
		this.BodypieceNeck[2].rotateAngleX = this.NeckRotX;
		this.BodypieceNeck[3].rotateAngleX = this.NeckRotX;
	}

	protected void initLegPositions(float yOffset, float stretch) {
		this.rightarm.addBox(-2.0F + this.THIRDP_ARM_CENTRE_X, -6.0F + this.THIRDP_ARM_CENTRE_Y, -2.0F + this.THIRDP_ARM_CENTRE_Z, 4, 12, 4, stretch);
		this.rightarm.setPivot(-3.0F, 8.0F + yOffset, 0.0F);
		this.LeftArm.addBox(-2.0F + this.THIRDP_ARM_CENTRE_X, -6.0F + this.THIRDP_ARM_CENTRE_Y, -2.0F + this.THIRDP_ARM_CENTRE_Z, 4, 12, 4, stretch);
		this.LeftArm.setPivot(3.0F, 8.0F + yOffset, 0.0F);
		this.RightLeg.addBox(-2.0F + this.THIRDP_ARM_CENTRE_X, -6.0F + this.THIRDP_ARM_CENTRE_Y, -2.0F + this.THIRDP_ARM_CENTRE_Z, 4, 12, 4, stretch);
		this.RightLeg.setPivot(-3.0F, 0.0F + yOffset, 0.0F);
		this.LeftLeg.addBox(-2.0F + this.THIRDP_ARM_CENTRE_X, -6.0F + this.THIRDP_ARM_CENTRE_Y, -2.0F + this.THIRDP_ARM_CENTRE_Z, 4, 12, 4, stretch);
		this.LeftLeg.setPivot(3.0F, 0.0F + yOffset, 0.0F);
		this.SteveArm.addBox(-2.0F + this.FIRSTP_ARM_CENTRE_X, -6.0F + this.FIRSTP_ARM_CENTRE_Y, -2.0F + this.FIRSTP_ARM_CENTRE_Z, 4, 12, 4, stretch);
		this.SteveArm.setPivot(-5.0F, 2.0F + yOffset, 0.0F);
		this.unicornarm.addBox(-2.0F + this.FIRSTP_ARM_CENTRE_X, -6.0F + this.FIRSTP_ARM_CENTRE_Y, -2.0F + this.FIRSTP_ARM_CENTRE_Z, 4, 12, 4, stretch);
		this.unicornarm.setPivot(-5.0F, 2.0F + yOffset, 0.0F);
	}

	private void initTailPositions(float yOffset, float stretch) {
		this.Tail[0].addTopPlane(-2.0F, 1.0F, 2.0F, 4, 0, 4, stretch);
		this.Tail[0].setRotationPoint(this.TAIL_RP_X, this.TAIL_RP_Y + yOffset, this.TAIL_RP_Z);
		this.Tail[1].addSidePlane(-2.0F, 1.0F, 2.0F, 0, 4, 4, stretch);
		this.Tail[1].setRotationPoint(this.TAIL_RP_X, this.TAIL_RP_Y + yOffset, this.TAIL_RP_Z);
		this.Tail[2].addBackPlane(-2.0F, 1.0F, 2.0F, 4, 4, 0, stretch);
		this.Tail[2].setRotationPoint(this.TAIL_RP_X, this.TAIL_RP_Y + yOffset, this.TAIL_RP_Z);
		this.Tail[3].addSidePlane(2.0F, 1.0F, 2.0F, 0, 4, 4, stretch);
		this.Tail[3].setRotationPoint(this.TAIL_RP_X, this.TAIL_RP_Y + yOffset, this.TAIL_RP_Z);
		this.Tail[4].addBackPlane(-2.0F, 1.0F, 6.0F, 4, 4, 0, stretch);
		this.Tail[4].setRotationPoint(this.TAIL_RP_X, this.TAIL_RP_Y + yOffset, this.TAIL_RP_Z);
		this.Tail[5].addTopPlane(-2.0F, 5.0F, 2.0F, 4, 0, 4, stretch);
		this.Tail[5].setRotationPoint(this.TAIL_RP_X, this.TAIL_RP_Y + yOffset, this.TAIL_RP_Z);
		this.Tail[6].addSidePlane(-2.0F, 5.0F, 2.0F, 0, 4, 4, stretch);
		this.Tail[6].setRotationPoint(this.TAIL_RP_X, this.TAIL_RP_Y + yOffset, this.TAIL_RP_Z);
		this.Tail[7].addBackPlane(-2.0F, 5.0F, 2.0F, 4, 4, 0, stretch);
		this.Tail[7].setRotationPoint(this.TAIL_RP_X, this.TAIL_RP_Y + yOffset, this.TAIL_RP_Z);
		this.Tail[8].addSidePlane(2.0F, 5.0F, 2.0F, 0, 4, 4, stretch);
		this.Tail[8].setRotationPoint(this.TAIL_RP_X, this.TAIL_RP_Y + yOffset, this.TAIL_RP_Z);
		this.Tail[9].addBackPlane(-2.0F, 5.0F, 6.0F, 4, 4, 0, stretch);
		this.Tail[9].setRotationPoint(this.TAIL_RP_X, this.TAIL_RP_Y + yOffset, this.TAIL_RP_Z);
		this.Tail[10].addTopPlane(-2.0F, 9.0F, 2.0F, 4, 0, 4, stretch);
		this.Tail[10].setRotationPoint(this.TAIL_RP_X, this.TAIL_RP_Y + yOffset, this.TAIL_RP_Z);
		this.Tail[11].addSidePlane(-2.0F, 9.0F, 2.0F, 0, 4, 4, stretch);
		this.Tail[11].setRotationPoint(this.TAIL_RP_X, this.TAIL_RP_Y + yOffset, this.TAIL_RP_Z);
		this.Tail[12].addBackPlane(-2.0F, 9.0F, 2.0F, 4, 4, 0, stretch);
		this.Tail[12].setRotationPoint(this.TAIL_RP_X, this.TAIL_RP_Y + yOffset, this.TAIL_RP_Z);
		this.Tail[13].addSidePlane(2.0F, 9.0F, 2.0F, 0, 4, 4, stretch);
		this.Tail[13].setRotationPoint(this.TAIL_RP_X, this.TAIL_RP_Y + yOffset, this.TAIL_RP_Z);
		this.Tail[14].addBackPlane(-2.0F, 9.0F, 6.0F, 4, 4, 0, stretch);
		this.Tail[14].setRotationPoint(this.TAIL_RP_X, this.TAIL_RP_Y + yOffset, this.TAIL_RP_Z);
		this.Tail[15].addTopPlane(-2.0F, 13.0F, 2.0F, 4, 0, 4, stretch);
		this.Tail[15].setRotationPoint(this.TAIL_RP_X, this.TAIL_RP_Y + yOffset, this.TAIL_RP_Z);
		this.Tail[16].addSidePlane(-2.0F, 13.0F, 2.0F, 0, 4, 4, stretch);
		this.Tail[16].setRotationPoint(this.TAIL_RP_X, this.TAIL_RP_Y + yOffset, this.TAIL_RP_Z);
		this.Tail[17].addBackPlane(-2.0F, 13.0F, 2.0F, 4, 4, 0, stretch);
		this.Tail[17].setRotationPoint(this.TAIL_RP_X, this.TAIL_RP_Y + yOffset, this.TAIL_RP_Z);
		this.Tail[18].addSidePlane(2.0F, 13.0F, 2.0F, 0, 4, 4, stretch);
		this.Tail[18].setRotationPoint(this.TAIL_RP_X, this.TAIL_RP_Y + yOffset, this.TAIL_RP_Z);
		this.Tail[19].addBackPlane(-2.0F, 13.0F, 6.0F, 4, 4, 0, stretch);
		this.Tail[19].setRotationPoint(this.TAIL_RP_X, this.TAIL_RP_Y + yOffset, this.TAIL_RP_Z);
		this.Tail[20].addTopPlane(-2.0F, 17.0F, 2.0F, 4, 0, 4, stretch);
		this.Tail[20].setRotationPoint(this.TAIL_RP_X, this.TAIL_RP_Y + yOffset, this.TAIL_RP_Z);
	}

	private void initWingPositions(float yOffset, float stretch) {
		this.LeftWing[0].addBox(4.0F, 5.0F, 2.0F, 2, 6, 2, stretch);
		this.LeftWing[0].setPivot(this.HEAD_RP_X, this.WING_FOLDED_RP_Y + yOffset, this.WING_FOLDED_RP_Z);
		this.LeftWing[0].rotationX = this.ROTATE_90;
		this.LeftWing[1].addBox(4.0F, 5.0F, 4.0F, 2, 8, 2, stretch);
		this.LeftWing[1].setPivot(this.HEAD_RP_X, this.WING_FOLDED_RP_Y + yOffset, this.WING_FOLDED_RP_Z);
		this.LeftWing[1].rotationX = this.ROTATE_90;
		this.LeftWing[2].addBox(4.0F, 5.0F, 6.0F, 2, 6, 2, stretch);
		this.LeftWing[2].setPivot(this.HEAD_RP_X, this.WING_FOLDED_RP_Y + yOffset, this.WING_FOLDED_RP_Z);
		this.LeftWing[2].rotationX = this.ROTATE_90;
		this.RightWing[0].addBox(-6.0F, 5.0F, 2.0F, 2, 6, 2, stretch);
		this.RightWing[0].setPivot(this.HEAD_RP_X, this.WING_FOLDED_RP_Y + yOffset, this.WING_FOLDED_RP_Z);
		this.RightWing[0].rotationX = this.ROTATE_90;
		this.RightWing[1].addBox(-6.0F, 5.0F, 4.0F, 2, 8, 2, stretch);
		this.RightWing[1].setPivot(this.HEAD_RP_X, this.WING_FOLDED_RP_Y + yOffset, this.WING_FOLDED_RP_Z);
		this.RightWing[1].rotationX = this.ROTATE_90;
		this.RightWing[2].addBox(-6.0F, 5.0F, 6.0F, 2, 6, 2, stretch);
		this.RightWing[2].setPivot(this.HEAD_RP_X, this.WING_FOLDED_RP_Y + yOffset, this.WING_FOLDED_RP_Z);
		this.RightWing[2].rotationX = this.ROTATE_90;
		this.LeftWingExt[0].addBox(0.0F, 0.0F, 0.0F, 1, 8, 2, stretch + 0.1F);
		this.LeftWingExt[0].setPivot(this.LEFT_WING_EXT_RP_X, this.LEFT_WING_EXT_RP_Y + yOffset, this.LEFT_WING_EXT_RP_Z);
		this.LeftWingExt[1].addBox(0.0F, 8.0F, 0.0F, 1, 6, 2, stretch + 0.1F);
		this.LeftWingExt[1].setPivot(this.LEFT_WING_EXT_RP_X, this.LEFT_WING_EXT_RP_Y + yOffset, this.LEFT_WING_EXT_RP_Z);
		this.LeftWingExt[2].addBox(0.0F, -1.2F, -0.2F, 1, 8, 2, stretch - 0.2F);
		this.LeftWingExt[2].setPivot(this.LEFT_WING_EXT_RP_X, this.LEFT_WING_EXT_RP_Y + yOffset, this.LEFT_WING_EXT_RP_Z);
		this.LeftWingExt[3].addBox(0.0F, 1.8F, 1.3F, 1, 8, 2, stretch - 0.1F);
		this.LeftWingExt[3].setPivot(this.LEFT_WING_EXT_RP_X, this.LEFT_WING_EXT_RP_Y + yOffset, this.LEFT_WING_EXT_RP_Z);
		this.LeftWingExt[4].addBox(0.0F, 5.0F, 2.0F, 1, 8, 2, stretch);
		this.LeftWingExt[4].setPivot(this.LEFT_WING_EXT_RP_X, this.LEFT_WING_EXT_RP_Y + yOffset, this.LEFT_WING_EXT_RP_Z);
		this.LeftWingExt[5].addBox(0.0F, 0.0F, -0.2F, 1, 6, 2, stretch + 0.3F);
		this.LeftWingExt[5].setPivot(this.LEFT_WING_EXT_RP_X, this.LEFT_WING_EXT_RP_Y + yOffset, this.LEFT_WING_EXT_RP_Z);
		this.LeftWingExt[6].addBox(0.0F, 0.0F, 0.2F, 1, 3, 2, stretch + 0.2F);
		this.LeftWingExt[6].setPivot(this.LEFT_WING_EXT_RP_X, this.LEFT_WING_EXT_RP_Y + yOffset, this.LEFT_WING_EXT_RP_Z);
		this.RightWingExt[0].addBox(0.0F, 0.0F, 0.0F, 1, 8, 2, stretch + 0.1F);
		this.RightWingExt[0].setPivot(this.RIGHT_WING_EXT_RP_X, this.RIGHT_WING_EXT_RP_Y + yOffset, this.RIGHT_WING_EXT_RP_Z);
		this.RightWingExt[1].addBox(0.0F, 8.0F, 0.0F, 1, 6, 2, stretch + 0.1F);
		this.RightWingExt[1].setPivot(this.RIGHT_WING_EXT_RP_X, this.RIGHT_WING_EXT_RP_Y + yOffset, this.RIGHT_WING_EXT_RP_Z);
		this.RightWingExt[2].addBox(0.0F, -1.2F, -0.2F, 1, 8, 2, stretch - 0.2F);
		this.RightWingExt[2].setPivot(this.RIGHT_WING_EXT_RP_X, this.RIGHT_WING_EXT_RP_Y + yOffset, this.RIGHT_WING_EXT_RP_Z);
		this.RightWingExt[3].addBox(0.0F, 1.8F, 1.3F, 1, 8, 2, stretch - 0.1F);
		this.RightWingExt[3].setPivot(this.RIGHT_WING_EXT_RP_X, this.RIGHT_WING_EXT_RP_Y + yOffset, this.RIGHT_WING_EXT_RP_Z);
		this.RightWingExt[4].addBox(0.0F, 5.0F, 2.0F, 1, 8, 2, stretch);
		this.RightWingExt[4].setPivot(this.RIGHT_WING_EXT_RP_X, this.RIGHT_WING_EXT_RP_Y + yOffset, this.RIGHT_WING_EXT_RP_Z);
		this.RightWingExt[5].addBox(0.0F, 0.0F, -0.2F, 1, 6, 2, stretch + 0.3F);
		this.RightWingExt[5].setPivot(this.RIGHT_WING_EXT_RP_X, this.RIGHT_WING_EXT_RP_Y + yOffset, this.RIGHT_WING_EXT_RP_Z);
		this.RightWingExt[6].addBox(0.0F, 0.0F, 0.2F, 1, 3, 2, stretch + 0.2F);
		this.RightWingExt[6].setPivot(this.RIGHT_WING_EXT_RP_X, this.RIGHT_WING_EXT_RP_Y + yOffset, this.RIGHT_WING_EXT_RP_Z);
	}

	public void specials(EntityRenderDispatcher rendermanager, PlayerEntity entityplayer) {
		if (!this.isSleeping) {
			if (this.isUnicorn) {
				if (this.aimedBow) {
					this.renderDrop(rendermanager, entityplayer, this.unicornarm, 1.0F, 0.15F, 0.9375F, 0.0625F);
				} else if (this.size == 0) {
					this.renderDrop(rendermanager, entityplayer, this.unicornarm, 1.0F, 0.35F, 0.5375F, -0.8F);
				} else {
					this.renderDrop(rendermanager, entityplayer, this.unicornarm, 1.0F, 0.35F, 0.5375F, -0.45F);
				}
			} else if (this.size == 0) {
				this.renderDrop(rendermanager, entityplayer, this.rightarm, 1.0F, 0.08F, 0.8375F, 0.0625F);
			} else {
				this.renderDrop(rendermanager, entityplayer, this.rightarm, 1.0F, -0.0625F, 0.8375F, 0.0625F);
			}

			if (this.heldItemRight != 0 && this.isUnicorn) {
				GL11.glPushMatrix();
				this.head.translate(0.0625F);
				MineLPGlow.renderGlow(entityplayer, this.size, this.issneak && !this.isFlying, this.glowColor);
				GL11.glPopMatrix();
			}
		}

		if (this.size == 0) {
			GL11.glPushMatrix();
			GL11.glTranslatef(0.0F, 0.76F, 0.0F);
			GL11.glScalef(0.9F, 0.9F, 0.9F);
			this.renderPumpkin(rendermanager, entityplayer, this.head, 0.625F, 0.0F, -0.08F, -0.15F);
			GL11.glPopMatrix();
		} else if (this.size == 2) {
			GL11.glPushMatrix();
			GL11.glTranslatef(0.0F, -0.17F, -0.04F);
			if (this.isSleeping) {
				GL11.glTranslatef(0.0F, 0.0F, -0.1F);
			}

			this.renderPumpkin(rendermanager, entityplayer, this.head, 0.625F, 0.0F, -0.08F, -0.15F);
			GL11.glPopMatrix();
		} else if (this.size == 3) {
			GL11.glPushMatrix();
			GL11.glTranslatef(0.0F, -0.17F, -0.02F);
			this.renderPumpkin(rendermanager, entityplayer, this.head, 0.625F, 0.0F, -0.08F, -0.15F);
			GL11.glPopMatrix();
		} else {
			this.renderPumpkin(rendermanager, entityplayer, this.head, 0.625F, 0.0F, -0.08F, -0.15F);
		}

	}

	protected void renderCloak(PlayerEntity player, float par2) {
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0F, 0.24F, 0.0F);
		if (this.size == 0) {
			GL11.glTranslatef(0.0F, 0.67F, -0.04F);
			GL11.glScalef(0.6F, 0.6F, 0.6F);
		} else if (this.size == 2) {
			GL11.glTranslatef(0.0F, -0.14F, -0.1F);
			GL11.glScalef(1.15F, 1.2F, 1.2F);
			if (this.issneak && !this.isFlying) {
				GL11.glTranslatef(0.0F, 0.03F, 0.0F);
			}
		} else if (this.size == 3) {
			GL11.glTranslatef(0.0F, -0.09F, 0.0F);
			GL11.glScalef(1.0F, 1.0F, 1.0F);
			if (this.issneak && !this.isFlying) {
				GL11.glTranslatef(0.0F, 0.03F, 0.0F);
			}
		}

		if (this.issneak && !this.isFlying) {
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
