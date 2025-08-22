package codes.blackjack.minelittlepony.models;


import codes.blackjack.minelittlepony.render.PlaneRenderer;
import net.minecraft.client.render.model.ModelPart;

public class pm_newPony extends pm_newPonyAdv {
	@Override
	protected void initHeadTextures() {
		this.cloak = new ModelPart(this, 0, 0);
		this.head = new ModelPart(this, 0, 0);
		this.headpiece[0] = new ModelPart(this, 12, 16);
		this.headpiece[1] = new ModelPart(this, 12, 16);
		this.headpiece[1].flipped = true;
		this.headpiece[2] = new ModelPart(this, 56, 0);
		this.helmet = new ModelPart(this, 32, 0);
	}

	@Override
	protected void initBodyTextures() {
		this.Body = new ModelPart(this, 16, 16);
		this.bodyPiece[0] = new PlaneRenderer(this, 24, 0);
		this.bodyPiece[1] = new PlaneRenderer(this, 24, 0);
		this.bodyPiece[2] = new PlaneRenderer(this, 24, 0);
		this.bodyPiece[3] = new PlaneRenderer(this, 24, 0);
		this.bodyPiece[3].mirrory = true;
		this.bodyPiece[4] = new PlaneRenderer(this, 0, 20);
		this.bodyPiece[5] = new PlaneRenderer(this, 0, 20);
		this.bodyPiece[6] = new PlaneRenderer(this, 24, 0);
		this.bodyPiece[7] = new PlaneRenderer(this, 24, 0);
		this.bodyPiece[7].mirrory = true;
		this.bodyPiece[8] = new PlaneRenderer(this, 24, 0);
		this.bodyPiece[9] = new PlaneRenderer(this, 32, 0);
		this.bodyPiece[10] = new PlaneRenderer(this, 32, 0);
		this.bodyPiece[10].mirrory = true;
		this.bodyPiece[11] = new PlaneRenderer(this, 32, 0);
		this.bodyPiece[11].mirror = true;
		this.bodyPiece[12] = new PlaneRenderer(this, 32, 0);
		this.bodyPiece[13] = new PlaneRenderer(this, 32, 0);
		this.bodyPieceNeck[0] = new PlaneRenderer(this, 24, 0);
		this.bodyPieceNeck[1] = new PlaneRenderer(this, 24, 0);
		this.bodyPieceNeck[2] = new PlaneRenderer(this, 24, 0);
		this.bodyPieceNeck[3] = new PlaneRenderer(this, 24, 0);
	}

	@Override
	protected void initLegTextures() {
		this.rightarm = new ModelPart(this, 40, 16);
		this.leftArm = new ModelPart(this, 40, 16);
		this.leftArm.flipped = true;
		this.rightLeg = new ModelPart(this, 40, 16);
		this.leftLeg = new ModelPart(this, 40, 16);
		this.leftLeg.flipped = true;
		this.steveArm = new ModelPart(this, 40, 16);
		this.unicornArm = new ModelPart(this, 40, 16);
	}

	@Override
	protected void initBodyPositions(float yOffset, float stretch) {
		this.Body.addBox(-4.0F, 4.0F, -2.0F, 8, 8, 4, stretch);
		this.Body.setPivot(HEAD_RP_X, HEAD_RP_Y + yOffset, HEAD_RP_Z);
		this.bodyPiece[0].addSidePlane(-4.0F + BODY_CENTRE_X, -4.0F + BODY_CENTRE_Y, -4.0F + BODY_CENTRE_Z, 0, 8, 8, stretch);
		this.bodyPiece[0].setRotationPoint(HEAD_RP_X, HEAD_RP_Y + yOffset, HEAD_RP_Z);
		this.bodyPiece[1].addSidePlane(4.0F + BODY_CENTRE_X, -4.0F + BODY_CENTRE_Y, -4.0F + BODY_CENTRE_Z, 0, 8, 8, stretch);
		this.bodyPiece[1].setRotationPoint(HEAD_RP_X, HEAD_RP_Y + yOffset, HEAD_RP_Z);
		this.bodyPiece[2].addTopPlane(-4.0F + BODY_CENTRE_X, -4.0F + BODY_CENTRE_Y, -4.0F + BODY_CENTRE_Z, 8, 0, 8, stretch);
		this.bodyPiece[2].setRotationPoint(HEAD_RP_X, HEAD_RP_Y + yOffset, HEAD_RP_Z);
		this.bodyPiece[3].addBottomPlane(-4.0F + BODY_CENTRE_X, 4.0F + BODY_CENTRE_Y, -4.0F + BODY_CENTRE_Z, 8, 0, 8, stretch);
		this.bodyPiece[3].setRotationPoint(HEAD_RP_X, HEAD_RP_Y + yOffset, HEAD_RP_Z);
		this.bodyPiece[4].addSidePlane(-4.0F + BODY_CENTRE_X, -4.0F + BODY_CENTRE_Y, 4.0F + BODY_CENTRE_Z, 0, 8, 4, stretch);
		this.bodyPiece[4].setRotationPoint(HEAD_RP_X, HEAD_RP_Y + yOffset, HEAD_RP_Z);
		this.bodyPiece[5].addSidePlane(4.0F + BODY_CENTRE_X, -4.0F + BODY_CENTRE_Y, 4.0F + BODY_CENTRE_Z, 0, 8, 4, stretch);
		this.bodyPiece[5].setRotationPoint(HEAD_RP_X, HEAD_RP_Y + yOffset, HEAD_RP_Z);
		this.bodyPiece[6].addTopPlane(-4.0F + BODY_CENTRE_X, -4.0F + BODY_CENTRE_Y, 4.0F + BODY_CENTRE_Z, 8, 0, 4, stretch);
		this.bodyPiece[6].setRotationPoint(HEAD_RP_X, HEAD_RP_Y + yOffset, HEAD_RP_Z);
		this.bodyPiece[7].addBottomPlane(-4.0F + BODY_CENTRE_X, 4.0F + BODY_CENTRE_Y, 4.0F + BODY_CENTRE_Z, 8, 0, 4, stretch);
		this.bodyPiece[7].setRotationPoint(HEAD_RP_X, HEAD_RP_Y + yOffset, HEAD_RP_Z);
		this.bodyPiece[8].addBackPlane(-4.0F + BODY_CENTRE_X, -4.0F + BODY_CENTRE_Y, 8.0F + BODY_CENTRE_Z, 8, 8, 0, stretch);
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
}
