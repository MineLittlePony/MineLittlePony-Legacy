package com.minelittlepony.minelittlepony.render;

import com.minelittlepony.minelittlepony.AniParams;
import com.minelittlepony.minelittlepony.Pony;
import net.minecraft.block.Block;
import net.minecraft.client.render.BlockRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.model.Model;
import net.minecraft.client.render.model.ModelPart;
import net.minecraft.entity.Entity;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import org.lwjgl.opengl.GL11;

public abstract class ModelPlayer extends Model {
	protected final float scale = 0.0625F;
	public boolean isSneaking;
	public boolean isArmour;
	public int glowColor = -12303190;
	public boolean isPegasus;
	public boolean isUnicorn;
	public boolean isMale;
	public int wantTail;
	public Pony.Size size;
	public boolean isFlying;
	public boolean isSleeping;
	public int heldItemRight;
	public boolean aimedBow;

	protected ModelPlayer() {
	}

	public abstract void init();

	public abstract void init(float var1);

	public abstract void init(float var1, float var2);

	protected abstract void animate(AniParams var1, PlayerEntity var2);

	public abstract void animate(AniParams var1);

	public abstract void render(AniParams var1, boolean var2);

	public abstract void specials(EntityRenderDispatcher var1, PlayerEntity var2);

	@Override
	public void render(Entity player, float move, float moveSwing, float loop, float right, float down, float scale) {
		AniParams ani = new AniParams(move, moveSwing, loop, right, down);
		this.animate(ani, (PlayerEntity) player);
		this.render(ani, true);
	}

	protected void renderPumpkin(EntityRenderDispatcher dispatcher, PlayerEntity player, ModelPart box, float scale, float posX, float posY, float posZ) {
		ItemStack pumpkin = player.inventory.getArmor(3);
		if (pumpkin != null && pumpkin.getItem().id < 256) {
			GL11.glPushMatrix();
			if (box != null) {
				box.translate(0.0625F);
			}

			if (BlockRenderer.isGui3D(Block.BY_ID[pumpkin.itemId].getRenderType())) {
				GL11.glTranslatef(posX, posY, posZ);
				GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
				GL11.glScalef(scale, -scale, scale);
			}

			dispatcher.heldItemRenderer.render(player, pumpkin, 0);
			GL11.glPopMatrix();
		}
	}

	protected void renderEars(PlayerEntity player, float par2) {
	}

	protected void renderCloak(PlayerEntity player, float par2) {
	}

	protected void renderHeldItem(EntityRenderDispatcher dispatcher, PlayerEntity player, ModelPart box, float scaleFactor, float posX, float posY, float posZ) {
		ItemStack heldItem = player.inventory.getMainHandStack();
		if (heldItem != null) {
			GL11.glPushMatrix();
			if (box != null) {
				box.translate(scaleFactor * 0.0625F);
			}

			GL11.glTranslatef(posX, posY, posZ);
			if (player.fishingBobber != null) {
				heldItem = new ItemStack(Item.STICK);
			}

			UseAction useAction = null;
			if (player.getItemUseTimer() > 0) {
				useAction = heldItem.getUseAction();
			}

			if (heldItem.itemId < 256 && BlockRenderer.isGui3D(Block.BY_ID[heldItem.itemId].getRenderType())) {
				GL11.glTranslatef(0.0F, 0.1875F, -0.3125F);
				GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
				float scale0 = 0.375F * scaleFactor;
				GL11.glScalef(scale0, -scale0, scale0);
			} else if (heldItem.itemId == Item.BOW.id) {
				GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
				GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
				float scale3 = 0.625F * scaleFactor;
				GL11.glScalef(scale3, -scale3, scale3);
				GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
			} else if (Item.BY_ID[heldItem.itemId].isHandheld() /* isFull3D */) {
				if (Item.BY_ID[heldItem.itemId].shouldRotate()) {
					GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
					GL11.glTranslatef(0.0F, -0.125F, 0.0F);
				}

				if (player.getItemUseTimer() > 0 && useAction == UseAction.BLOCK) {
					GL11.glTranslatef(0.05F, 0.0F, -0.1F);
					GL11.glRotatef(-50.0F, 0.0F, 1.0F, 0.0F);
					GL11.glRotatef(-10.0F, 1.0F, 0.0F, 0.0F);
					GL11.glRotatef(-60.0F, 0.0F, 0.0F, 1.0F);
				}

				GL11.glTranslatef(0.0F, 0.1875F, 0.0F);
				float scale1 = 0.625F * scaleFactor;
				GL11.glScalef(scale1, -scale1, scale1);
				GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
			} else {
				GL11.glTranslatef(0.25F, 0.1875F, -0.1875F);
				float scale2 = 0.375F * scaleFactor;
				GL11.glScalef(scale2, scale2, scale2);
				GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
				GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);
			}

			if (heldItem.itemId == Item.POTION.id) {
				// I don't know what the intent was here.
				for (int j = 0; j <= 1; ++j) {
					int k = heldItem.getItem().getDisplayColor(heldItem.getDamage(), j);
					float f9 = (j >> 16 & 255) / 255.0F;
					float f10 = (j >> 8 & 255) / 255.0F;
					float f11 = (j & 255) / 255.0F;
					GL11.glColor4f(f9, f10, f11, 1.0F);
					dispatcher.heldItemRenderer.render(player, heldItem, j);
				}
			} else {
				dispatcher.heldItemRenderer.render(player, heldItem, 0);
			}

			GL11.glPopMatrix();
		}
	}
}
