package com.minelittlepony.minelittlepony.render;

import com.minelittlepony.minelittlepony.AniParams;
import com.minelittlepony.minelittlepony.PMAPI;
import com.minelittlepony.minelittlepony.Pony;
import com.minelittlepony.minelittlepony.config.PonySettings;
import com.minelittlepony.minelittlepony.mixin.MixinExtLivingEntity;
import com.minelittlepony.minelittlepony.mixin.MixinExtMinecraft;
import com.minelittlepony.minelittlepony.mixin.MixinExtPlayerEntityRenderer;
import com.mojang.blaze3d.vertex.BufferBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.TextRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import org.lwjgl.opengl.GL11;

public class RenderPony extends PlayerEntityRenderer {
	private static final AniParams ani;
	private PlayerModel pm;

	public RenderPony() {
		this.model = PMAPI.newPonyAdv.model;
		this.shadowSize = PMAPI.newPonyAdv.shadowSize;
		this.pm = PMAPI.newPonyAdv;
		this.dispatcher = EntityRenderDispatcher.INSTANCE;
	}

	@Override
	protected int bindTexture(PlayerEntity player, int armorSlot, float partialTick) {
		boolean ponyArmor;
		this.pm = this.getModel(player);
		ItemStack armorInSlot = player.inventory.getArmor(3 - armorSlot);
		if (armorInSlot != null) {
			Item armorItem = armorInSlot.getItem();
			if (armorItem instanceof ArmorItem) {
				ArmorItem armorPiece = (ArmorItem) armorItem;
				ModelArmor.slot = armorSlot;
				String[] filePrefixes = MixinExtPlayerEntityRenderer.getArmorVariants();
				String[] path = this.checkPonyVersion(this.pm.armor.path + filePrefixes[armorPiece.materialId] + "_" + this.pm.armor.subimage() + ".png");
				this.bindTexture(path[1]);
				ponyArmor = Boolean.parseBoolean(path[2]);

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

		if (PonySettings.isPonyArmor()) {
			if (this.pm.name.equals("newPony") || this.pm.name.equals("newPonyAdv")) {
				String ponypath = path.replace(".png", "_pony.png");
				if (Minecraft.class.getResource(ponypath) != null) {
					returnPath[1] = ponypath;
					returnPath[2] = "true";
				}
			}
		}

		return returnPath;
	}

	@Override
	public void render(PlayerEntity player, double par2, double par4, double par6, float par8, float par9) {
		ItemStack heldItem = player.inventory.getMainHandStack();
		Pony thePony = Pony.getPonyFromRegistry(player, this.dispatcher.textureManager);
		player.skin = thePony.skinUrl;
		((MixinExtLivingEntity) player).setTexture(thePony.texture);

		this.pm = this.getModel(player);
		this.model = this.pm.model;
		this.pm.armor.modelArmorChestplate.heldItemRight = this.pm.armor.modelArmor.heldItemRight = this.pm.model.heldItemRight = heldItem == null ? 0 : 1;

		if (heldItem != null && player.getItemUseTimer() > 0) {
			UseAction var11 = heldItem.getUseAction();
			if (var11 == UseAction.BLOCK) {
				this.pm.armor.modelArmorChestplate.heldItemRight = this.pm.armor.modelArmor.heldItemRight = this.pm.model.heldItemRight = 3;
			} else if (var11 == UseAction.BOW) {
				this.pm.armor.modelArmorChestplate.aimedBow = this.pm.armor.modelArmor.aimedBow = this.pm.model.aimedBow = true;
			}
		}

		this.pm.armor.modelArmorChestplate.isSneaking = this.pm.armor.modelArmor.isSneaking = this.pm.model.isSneaking = player.isSneaking();

		boolean isJumping = ((MixinExtLivingEntity) player).isJumping();
		this.pm.armor.modelArmorChestplate.isFlying = this.pm.armor.modelArmor.isFlying = this.pm.model.isFlying = thePony.isFlying = thePony.isPegasusFlying(player.x, player.y, player.z, player.fallDistance, isJumping, this.dispatcher.world);
		this.pm.armor.modelArmorChestplate.isPegasus = this.pm.armor.modelArmor.isPegasus = this.pm.model.isPegasus = thePony.isPegasus();
		this.pm.armor.modelArmorChestplate.isUnicorn = this.pm.armor.modelArmor.isUnicorn = this.pm.model.isUnicorn = thePony.isUnicorn();
		this.pm.armor.modelArmorChestplate.isMale = this.pm.armor.modelArmor.isMale = this.pm.model.isMale = thePony.isMale();
		this.pm.armor.modelArmorChestplate.size = this.pm.armor.modelArmor.size = this.pm.model.size = thePony.size();
		this.pm.model.glowColor = thePony.glowColor();
		this.pm.armor.modelArmorChestplate.isSleeping = this.pm.armor.modelArmor.isSleeping = this.pm.model.isSleeping = player.isSleeping();
		// FIXME: This might be onGround but we don't have it?
		// this.pm.armor.modelArmorChestplate.h = this.pm.armor.modelArmor.h = this.pm.model.h;
		this.pm.model.wantTail = thePony.wantTail();

		super.render(player, par2, par4, par6, par8, par9);

		this.pm.armor.modelArmorChestplate.aimedBow = this.pm.armor.modelArmor.aimedBow = this.pm.model.aimedBow = false;
		this.pm.armor.modelArmorChestplate.isSneaking = this.pm.armor.modelArmor.isSneaking = this.pm.model.isSneaking = false;
		this.pm.armor.modelArmorChestplate.heldItemRight = this.pm.armor.modelArmor.heldItemRight = this.pm.model.heldItemRight = 0;
	}

	protected void renderNameTag(PlayerEntity player, double par2, double par4, double par6) {
		if (Minecraft.isDisplayGui() && player != this.dispatcher.camera) {
			float var8 = 1.6F;
			float var9 = 0.016666668F * var8;
			float distanceToCamera = player.getDistanceTo(this.dispatcher.camera);
			float viewDistance = player.isSneaking() ? 32.0F : 64.0F;

			if (distanceToCamera < viewDistance) {
				String thePony = player.name;
				if (!player.isSneaking()) {
					if (player.isSleeping()) {
						this.renderNameTag(player, thePony, par2, par4 - 1.5F, par6, 64);
					} else {
						this.renderNameTag(player, thePony, par2, par4, par6, 64);
					}
				} else {
					TextRenderer textRenderer = this.getTextRenderer();
					GL11.glPushMatrix();
					GL11.glTranslatef((float) par2 + 0.0F, (float) par4 + 2.3F, (float) par6);
					GL11.glNormal3f(0.0F, 1.0F, 0.0F);
					GL11.glRotatef(-this.dispatcher.cameraYaw, 0.0F, 1.0F, 0.0F);
					GL11.glRotatef(this.dispatcher.cameraPitch, 1.0F, 0.0F, 0.0F);
					GL11.glScalef(-var9, -var9, var9);
					GL11.glDisable(GL11.GL_LIGHTING);
					GL11.glTranslatef(0.0F, 0.25F / var9, 0.0F);
					GL11.glDepthMask(false);
					GL11.glEnable(GL11.GL_BLEND);
					GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
					BufferBuilder bufferBuilder = BufferBuilder.INSTANCE;
					GL11.glDisable(GL11.GL_TEXTURE_2D);
					bufferBuilder.start();
					int nameWidth = textRenderer.getWidth(thePony) / 2;
					bufferBuilder.color(0.0F, 0.0F, 0.0F, 0.25F);
					bufferBuilder.vertex((-nameWidth - 1), -1.0F, 0.0F);
					bufferBuilder.vertex((-nameWidth - 1), 8.0F, 0.0F);
					bufferBuilder.vertex((nameWidth + 1), 8.0F, 0.0F);
					bufferBuilder.vertex((nameWidth + 1), -1.0F, 0.0F);
					bufferBuilder.end();
					GL11.glEnable(GL11.GL_TEXTURE_2D);
					GL11.glDepthMask(true);
					textRenderer.draw(thePony, -textRenderer.getWidth(thePony) / 2, 0, 553648127);
					GL11.glEnable(GL11.GL_LIGHTING);
					GL11.glDisable(GL11.GL_BLEND);
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					GL11.glPopMatrix();
				}
			}
		}

	}

	@Override
	protected void renderDecoration(PlayerEntity par1EntityPlayer, float par2) {
		this.getModel(par1EntityPlayer).model.specials(this.dispatcher, par1EntityPlayer);
		if (par1EntityPlayer.name.equals("deadmau5") && this.bindHttpTexture(par1EntityPlayer.skin, null)) {
			this.getModel(par1EntityPlayer).model.renderEars(par1EntityPlayer, par2);
		}

		if (this.bindHttpTexture(par1EntityPlayer.cloak, null)) {
			this.getModel(par1EntityPlayer).model.renderCloak(par1EntityPlayer, par2);
		}

	}

	@Override
	protected void scale(PlayerEntity player, float par2) {
		float scale = this.pm.globalScale;
		GL11.glScalef(scale, scale, scale);
	}

	@Override
	public void renderPlayerRightHandModel() {
		// TODO: This is a hack!
		PlayerEntity player = MixinExtMinecraft.getMinecraft().player;
		Pony thePony = Pony.getPonyFromRegistry(player, MixinExtMinecraft.getMinecraft().textureManager);
		player.skin = thePony.skinUrl;
		((MixinExtLivingEntity) player).setTexture(thePony.texture);

		this.pm = PMAPI.human;
		this.pm.model.handSwingProgress = 0.0F;
		this.pm.model.animate(ani);
		this.pm.model.render(ani, false);
	}

	private PlayerModel getModel(PlayerEntity player) {
		return Pony.getPonyFromRegistry(player, this.dispatcher.textureManager)
					.getModel();
	}

	static {
		ani = new AniParams(0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
	}
}
