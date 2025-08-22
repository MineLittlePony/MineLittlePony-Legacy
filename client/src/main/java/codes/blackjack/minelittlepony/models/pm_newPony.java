package codes.blackjack.minelittlepony.models;


import codes.blackjack.minelittlepony.render.PlaneRenderer;
import net.minecraft.client.render.model.ModelPart;

public class pm_newPony extends pm_newPonyAdv {
	public pm_newPony(String texture) {
		super(texture);
	}

	protected void initHeadTextures() {
		this.cloak = new ModelPart(this, 0, 0);
		this.head = new ModelPart(this, 0, 0);
		this.headpiece[0] = new ModelPart(this, 12, 16);
		this.headpiece[1] = new ModelPart(this, 12, 16);
		this.headpiece[1].flipped = true;
		this.headpiece[2] = new ModelPart(this, 56, 0);
		this.helmet = new ModelPart(this, 32, 0);
	}

	protected void initBodyTextures() {
		this.Body = new ModelPart(this, 16, 16);
		this.Bodypiece[0] = new PlaneRenderer(this, 24, 0);
		this.Bodypiece[1] = new PlaneRenderer(this, 24, 0);
		this.Bodypiece[2] = new PlaneRenderer(this, 24, 0);
		this.Bodypiece[3] = new PlaneRenderer(this, 24, 0);
		this.Bodypiece[3].mirrory = true;
		this.Bodypiece[4] = new PlaneRenderer(this, 0, 20);
		this.Bodypiece[5] = new PlaneRenderer(this, 0, 20);
		this.Bodypiece[6] = new PlaneRenderer(this, 24, 0);
		this.Bodypiece[7] = new PlaneRenderer(this, 24, 0);
		this.Bodypiece[7].mirrory = true;
		this.Bodypiece[8] = new PlaneRenderer(this, 24, 0);
		this.Bodypiece[9] = new PlaneRenderer(this, 32, 0);
		this.Bodypiece[10] = new PlaneRenderer(this, 32, 0);
		this.Bodypiece[10].mirrory = true;
		this.Bodypiece[11] = new PlaneRenderer(this, 32, 0);
		this.Bodypiece[11].mirror = true;
		this.Bodypiece[12] = new PlaneRenderer(this, 32, 0);
		this.Bodypiece[13] = new PlaneRenderer(this, 32, 0);
		this.BodypieceNeck[0] = new PlaneRenderer(this, 24, 0);
		this.BodypieceNeck[1] = new PlaneRenderer(this, 24, 0);
		this.BodypieceNeck[2] = new PlaneRenderer(this, 24, 0);
		this.BodypieceNeck[3] = new PlaneRenderer(this, 24, 0);
	}

	protected void initLegTextures() {
		this.rightarm = new ModelPart(this, 40, 16);
		this.LeftArm = new ModelPart(this, 40, 16);
		this.LeftArm.flipped = true;
		this.RightLeg = new ModelPart(this, 40, 16);
		this.LeftLeg = new ModelPart(this, 40, 16);
		this.LeftLeg.flipped = true;
		this.SteveArm = new ModelPart(this, 40, 16);
		this.unicornarm = new ModelPart(this, 40, 16);
	}

	protected void initBodyPositions(float yOffset, float stretch) {
		this.Body.addBox(-4.0F, 4.0F, -2.0F, 8, 8, 4, stretch);
		this.Body.setPivot(this.HEAD_RP_X, this.HEAD_RP_Y + yOffset, this.HEAD_RP_Z);
		this.Bodypiece[0].addSidePlane(-4.0F + this.BODY_CENTRE_X, -4.0F + this.BODY_CENTRE_Y, -4.0F + this.BODY_CENTRE_Z, 0, 8, 8, stretch);
		this.Bodypiece[0].setRotationPoint(this.HEAD_RP_X, this.HEAD_RP_Y + yOffset, this.HEAD_RP_Z);
		this.Bodypiece[1].addSidePlane(4.0F + this.BODY_CENTRE_X, -4.0F + this.BODY_CENTRE_Y, -4.0F + this.BODY_CENTRE_Z, 0, 8, 8, stretch);
		this.Bodypiece[1].setRotationPoint(this.HEAD_RP_X, this.HEAD_RP_Y + yOffset, this.HEAD_RP_Z);
		this.Bodypiece[2].addTopPlane(-4.0F + this.BODY_CENTRE_X, -4.0F + this.BODY_CENTRE_Y, -4.0F + this.BODY_CENTRE_Z, 8, 0, 8, stretch);
		this.Bodypiece[2].setRotationPoint(this.HEAD_RP_X, this.HEAD_RP_Y + yOffset, this.HEAD_RP_Z);
		this.Bodypiece[3].addBottomPlane(-4.0F + this.BODY_CENTRE_X, 4.0F + this.BODY_CENTRE_Y, -4.0F + this.BODY_CENTRE_Z, 8, 0, 8, stretch);
		this.Bodypiece[3].setRotationPoint(this.HEAD_RP_X, this.HEAD_RP_Y + yOffset, this.HEAD_RP_Z);
		this.Bodypiece[4].addSidePlane(-4.0F + this.BODY_CENTRE_X, -4.0F + this.BODY_CENTRE_Y, 4.0F + this.BODY_CENTRE_Z, 0, 8, 4, stretch);
		this.Bodypiece[4].setRotationPoint(this.HEAD_RP_X, this.HEAD_RP_Y + yOffset, this.HEAD_RP_Z);
		this.Bodypiece[5].addSidePlane(4.0F + this.BODY_CENTRE_X, -4.0F + this.BODY_CENTRE_Y, 4.0F + this.BODY_CENTRE_Z, 0, 8, 4, stretch);
		this.Bodypiece[5].setRotationPoint(this.HEAD_RP_X, this.HEAD_RP_Y + yOffset, this.HEAD_RP_Z);
		this.Bodypiece[6].addTopPlane(-4.0F + this.BODY_CENTRE_X, -4.0F + this.BODY_CENTRE_Y, 4.0F + this.BODY_CENTRE_Z, 8, 0, 4, stretch);
		this.Bodypiece[6].setRotationPoint(this.HEAD_RP_X, this.HEAD_RP_Y + yOffset, this.HEAD_RP_Z);
		this.Bodypiece[7].addBottomPlane(-4.0F + this.BODY_CENTRE_X, 4.0F + this.BODY_CENTRE_Y, 4.0F + this.BODY_CENTRE_Z, 8, 0, 4, stretch);
		this.Bodypiece[7].setRotationPoint(this.HEAD_RP_X, this.HEAD_RP_Y + yOffset, this.HEAD_RP_Z);
		this.Bodypiece[8].addBackPlane(-4.0F + this.BODY_CENTRE_X, -4.0F + this.BODY_CENTRE_Y, 8.0F + this.BODY_CENTRE_Z, 8, 8, 0, stretch);
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
}
