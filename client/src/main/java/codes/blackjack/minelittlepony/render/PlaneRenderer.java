package codes.blackjack.minelittlepony.render;

import com.mojang.blaze3d.platform.MemoryTracker;
import com.mojang.blaze3d.vertex.BufferBuilder;
import net.minecraft.client.render.model.Model;
import net.minecraft.client.render.model.ModelPart;
import net.minecraft.client.render.model.Quad;
import net.minecraft.client.render.model.Vertex;
import org.lwjgl.opengl.GL11;

public class PlaneRenderer {
	public float textureWidth = 64.0F;
	public float textureHeight = 32.0F;
	private Vertex[] corners;
	private Quad[] faces;
	private int textureOffsetX;
	private int textureOffsetY;
	public float rotationPointX;
	public float rotationPointY;
	public float rotationPointZ;
	public float rotateAngleX;
	public float rotateAngleY;
	public float rotateAngleZ;
	public float field_35977_i;
	public float field_35975_j;
	public float field_35976_k;
	public float field_35973_l;
	public float field_35974_m;
	public float field_35972_n;
	private boolean compiled = false;
	private int displayList = 0;
	public boolean mirror = false;
	public boolean mirrory = false;
	public boolean mirrorxy = false;
	public boolean showModel = true;
	public boolean isHidden = false;

	public PlaneRenderer(Model modelbase, int i, int j) {
		this.textureOffsetX = i;
		this.textureOffsetY = j;

		// Are we sure?
		modelbase.parts.add(this);
	}

	public void addBackPlane(float f, float f1, float f2, int i, int j, int k) {
		this.addBackPlane(f, f1, f2, i, j, k, 0.0F);
	}

	public void addSidePlane(float f, float f1, float f2, int i, int j, int k) {
		this.addSidePlane(f, f1, f2, i, j, k, 0.0F);
	}

	public void addTopPlane(float f, float f1, float f2, int i, int j, int k) {
		this.addTopPlane(f, f1, f2, i, j, k, 0.0F);
	}

	public void addBottomPlane(float f, float f1, float f2, int i, int j, int k) {
		this.addBottomPlane(f, f1, f2, i, j, k, 0.0F);
	}

	public void addBackPlane(float f, float f1, float f2, int i, int j, int k, float f3) {
		this.field_35977_i = f;
		this.field_35975_j = f1;
		this.field_35976_k = f2;
		this.field_35973_l = f + (float) i;
		this.field_35974_m = f1 + (float) j;
		this.field_35972_n = f2 + (float) k;
		this.corners = new Vertex[8];
		this.faces = new Quad[1];
		float f4 = f + (float) i;
		float f5 = f1 + (float) j;
		float f6 = f2 + (float) k;
		f -= f3;
		f1 -= f3;
		f2 -= f3;
		f4 += f3;
		f5 += f3;
		f6 += f3;
		if (this.mirror) {
			float f7 = f4;
			f4 = f;
			f = f7;
		}

		Vertex positiontexturevertex = new Vertex(f, f1, f2, 0.0F, 0.0F);
		Vertex positiontexturevertex1 = new Vertex(f4, f1, f2, 0.0F, 8.0F);
		Vertex positiontexturevertex2 = new Vertex(f4, f5, f2, 8.0F, 8.0F);
		Vertex positiontexturevertex3 = new Vertex(f, f5, f2, 8.0F, 0.0F);
		Vertex positiontexturevertex4 = new Vertex(f, f1, f6, 0.0F, 0.0F);
		Vertex positiontexturevertex5 = new Vertex(f4, f1, f6, 0.0F, 8.0F);
		Vertex positiontexturevertex6 = new Vertex(f4, f5, f6, 8.0F, 8.0F);
		Vertex positiontexturevertex7 = new Vertex(f, f5, f6, 8.0F, 0.0F);
		this.corners[0] = positiontexturevertex;
		this.corners[1] = positiontexturevertex1;
		this.corners[2] = positiontexturevertex2;
		this.corners[3] = positiontexturevertex3;
		this.corners[4] = positiontexturevertex4;
		this.corners[5] = positiontexturevertex5;
		this.corners[6] = positiontexturevertex6;
		this.corners[7] = positiontexturevertex7;
		this.faces[0] = new Quad(new Vertex[]{positiontexturevertex1, positiontexturevertex, positiontexturevertex3, positiontexturevertex2}, this.textureOffsetX, this.textureOffsetY, this.textureOffsetX + i, this.textureOffsetY + j, this.textureWidth, this.textureHeight);
		if (this.mirror) {
			this.faces[0].flip();
		}

	}

	public void addSidePlane(float f, float f1, float f2, int i, int j, int k, float f3) {
		this.field_35977_i = f;
		this.field_35975_j = f1;
		this.field_35976_k = f2;
		this.field_35973_l = f + (float) i;
		this.field_35974_m = f1 + (float) j;
		this.field_35972_n = f2 + (float) k;
		this.corners = new Vertex[8];
		this.faces = new Quad[1];
		float f4 = f + (float) i;
		float f5 = f1 + (float) j;
		float f6 = f2 + (float) k;
		f -= f3;
		f1 -= f3;
		f2 -= f3;
		f4 += f3;
		f5 += f3;
		f6 += f3;
		if (this.mirror) {
			float f7 = f4;
			f4 = f;
			f = f7;
		}

		Vertex positiontexturevertex = new Vertex(f, f1, f2, 0.0F, 0.0F);
		Vertex positiontexturevertex1 = new Vertex(f4, f1, f2, 0.0F, 8.0F);
		Vertex positiontexturevertex2 = new Vertex(f4, f5, f2, 8.0F, 8.0F);
		Vertex positiontexturevertex3 = new Vertex(f, f5, f2, 8.0F, 0.0F);
		Vertex positiontexturevertex4 = new Vertex(f, f1, f6, 0.0F, 0.0F);
		Vertex positiontexturevertex5 = new Vertex(f4, f1, f6, 0.0F, 8.0F);
		Vertex positiontexturevertex6 = new Vertex(f4, f5, f6, 8.0F, 8.0F);
		Vertex positiontexturevertex7 = new Vertex(f, f5, f6, 8.0F, 0.0F);
		this.corners[0] = positiontexturevertex;
		this.corners[1] = positiontexturevertex1;
		this.corners[2] = positiontexturevertex2;
		this.corners[3] = positiontexturevertex3;
		this.corners[4] = positiontexturevertex4;
		this.corners[5] = positiontexturevertex5;
		this.corners[6] = positiontexturevertex6;
		this.corners[7] = positiontexturevertex7;
		this.faces[0] = new Quad(new Vertex[]{positiontexturevertex5, positiontexturevertex1, positiontexturevertex2, positiontexturevertex6}, this.textureOffsetX, this.textureOffsetY, this.textureOffsetX + k, this.textureOffsetY + j, this.textureWidth, this.textureHeight);
		if (this.mirror) {
			this.faces[0].flip();
		}

	}

	public void addTopPlane(float f, float f1, float f2, int i, int j, int k, float f3) {
		this.field_35977_i = f;
		this.field_35975_j = f1;
		this.field_35976_k = f2;
		this.field_35973_l = f + (float) i;
		this.field_35974_m = f1 + (float) j;
		this.field_35972_n = f2 + (float) k;
		this.corners = new Vertex[8];
		this.faces = new Quad[1];
		float f4 = f + (float) i;
		float f5 = f1 + (float) j;
		float f6 = f2 + (float) k;
		f -= f3;
		f1 -= f3;
		f2 -= f3;
		f4 += f3;
		f5 += f3;
		f6 += f3;
		if (this.mirror) {
			float f7 = f4;
			f4 = f;
			f = f7;
		}

		if (this.mirrory) {
			float f7 = f6;
			f6 = f2;
			f2 = f7;
		}

		if (this.mirrorxy) {
			float f7 = f6;
			f6 = f2;
			f2 = f7;
			f7 = f4;
			f4 = f;
			f = f7;
		}

		Vertex positiontexturevertex = new Vertex(f, f1, f2, 0.0F, 0.0F);
		Vertex positiontexturevertex1 = new Vertex(f4, f1, f2, 0.0F, 8.0F);
		Vertex positiontexturevertex2 = new Vertex(f4, f5, f2, 8.0F, 8.0F);
		Vertex positiontexturevertex3 = new Vertex(f, f5, f2, 8.0F, 0.0F);
		Vertex positiontexturevertex4 = new Vertex(f, f1, f6, 0.0F, 0.0F);
		Vertex positiontexturevertex5 = new Vertex(f4, f1, f6, 0.0F, 8.0F);
		Vertex positiontexturevertex6 = new Vertex(f4, f5, f6, 8.0F, 8.0F);
		Vertex positiontexturevertex7 = new Vertex(f, f5, f6, 8.0F, 0.0F);
		this.corners[0] = positiontexturevertex;
		this.corners[1] = positiontexturevertex1;
		this.corners[2] = positiontexturevertex2;
		this.corners[3] = positiontexturevertex3;
		this.corners[4] = positiontexturevertex4;
		this.corners[5] = positiontexturevertex5;
		this.corners[6] = positiontexturevertex6;
		this.corners[7] = positiontexturevertex7;
		this.faces[0] = new Quad(new Vertex[]{positiontexturevertex5, positiontexturevertex4, positiontexturevertex, positiontexturevertex1}, this.textureOffsetX, this.textureOffsetY, this.textureOffsetX + i, this.textureOffsetY + k, this.textureWidth, this.textureHeight);
		if (this.mirror || this.mirrory) {
			this.faces[0].flip();
		}

	}

	public void addBottomPlane(float f, float f1, float f2, int i, int j, int k, float f3) {
		this.field_35977_i = f;
		this.field_35975_j = f1;
		this.field_35976_k = f2;
		this.field_35973_l = f + (float) i;
		this.field_35974_m = f1 + (float) j;
		this.field_35972_n = f2 + (float) k;
		this.corners = new Vertex[8];
		this.faces = new Quad[1];
		float f4 = f + (float) i;
		float f5 = f1 + (float) j;
		float f6 = f2 + (float) k;
		f -= f3;
		f1 -= f3;
		f2 -= f3;
		f4 += f3;
		f5 += f3;
		f6 += f3;
		if (this.mirror) {
			float f7 = f4;
			f4 = f;
			f = f7;
		}

		if (this.mirrory) {
			float f7 = f6;
			f6 = f2;
			f2 = f7;
		}

		if (this.mirrorxy) {
			float f7 = f6;
			f6 = f2;
			f2 = f7;
			f7 = f4;
			f4 = f;
			f = f7;
		}

		Vertex positiontexturevertex = new Vertex(f, f1, f2, 0.0F, 0.0F);
		Vertex positiontexturevertex1 = new Vertex(f4, f1, f2, 0.0F, 8.0F);
		Vertex positiontexturevertex2 = new Vertex(f4, f5, f2, 8.0F, 8.0F);
		Vertex positiontexturevertex3 = new Vertex(f, f5, f2, 8.0F, 0.0F);
		Vertex positiontexturevertex4 = new Vertex(f, f1, f6, 0.0F, 0.0F);
		Vertex positiontexturevertex5 = new Vertex(f4, f1, f6, 0.0F, 8.0F);
		Vertex positiontexturevertex6 = new Vertex(f4, f5, f6, 8.0F, 8.0F);
		Vertex positiontexturevertex7 = new Vertex(f, f5, f6, 8.0F, 0.0F);
		this.corners[0] = positiontexturevertex;
		this.corners[1] = positiontexturevertex1;
		this.corners[2] = positiontexturevertex2;
		this.corners[3] = positiontexturevertex3;
		this.corners[4] = positiontexturevertex4;
		this.corners[5] = positiontexturevertex5;
		this.corners[6] = positiontexturevertex6;
		this.corners[7] = positiontexturevertex7;
		this.faces[0] = new Quad(new Vertex[]{positiontexturevertex2, positiontexturevertex3, positiontexturevertex7, positiontexturevertex6}, this.textureOffsetX, this.textureOffsetY, this.textureOffsetX + i, this.textureOffsetY + k, this.textureWidth, this.textureHeight);
		if (this.mirror || this.mirrory) {
			this.faces[0].flip();
		}

	}

	public void setRotationPoint(float f, float f1, float f2) {
		this.rotationPointX = f;
		this.rotationPointY = f1;
		this.rotationPointZ = f2;
	}

	public void render(float f) {
		if (!this.isHidden) {
			if (this.showModel) {
				if (!this.compiled) {
					this.compileDisplayList(f);
				}

				if (this.rotateAngleX == 0.0F && this.rotateAngleY == 0.0F && this.rotateAngleZ == 0.0F) {
					if (this.rotationPointX == 0.0F && this.rotationPointY == 0.0F && this.rotationPointZ == 0.0F) {
						GL11.glCallList(this.displayList);
					} else {
						GL11.glTranslatef(this.rotationPointX * f, this.rotationPointY * f, this.rotationPointZ * f);
						GL11.glCallList(this.displayList);
						GL11.glTranslatef(-this.rotationPointX * f, -this.rotationPointY * f, -this.rotationPointZ * f);
					}
				} else {
					GL11.glPushMatrix();
					GL11.glTranslatef(this.rotationPointX * f, this.rotationPointY * f, this.rotationPointZ * f);
					if (this.rotateAngleZ != 0.0F) {
						GL11.glRotatef(this.rotateAngleZ * 57.29578F, 0.0F, 0.0F, 1.0F);
					}

					if (this.rotateAngleY != 0.0F) {
						GL11.glRotatef(this.rotateAngleY * 57.29578F, 0.0F, 1.0F, 0.0F);
					}

					if (this.rotateAngleX != 0.0F) {
						GL11.glRotatef(this.rotateAngleX * 57.29578F, 1.0F, 0.0F, 0.0F);
					}

					GL11.glCallList(this.displayList);
					GL11.glPopMatrix();
				}

			}
		}
	}

	public void renderWithRotation(float f) {
		if (!this.isHidden) {
			if (this.showModel) {
				if (!this.compiled) {
					this.compileDisplayList(f);
				}

				GL11.glPushMatrix();
				GL11.glTranslatef(this.rotationPointX * f, this.rotationPointY * f, this.rotationPointZ * f);
				if (this.rotateAngleY != 0.0F) {
					GL11.glRotatef(this.rotateAngleY * 57.29578F, 0.0F, 1.0F, 0.0F);
				}

				if (this.rotateAngleX != 0.0F) {
					GL11.glRotatef(this.rotateAngleX * 57.29578F, 1.0F, 0.0F, 0.0F);
				}

				if (this.rotateAngleZ != 0.0F) {
					GL11.glRotatef(this.rotateAngleZ * 57.29578F, 0.0F, 0.0F, 1.0F);
				}

				GL11.glCallList(this.displayList);
				GL11.glPopMatrix();
			}
		}
	}

	public void postRender(float f) {
		if (!this.isHidden) {
			if (this.showModel) {
				if (!this.compiled) {
					this.compileDisplayList(f);
				}

				if (this.rotateAngleX == 0.0F && this.rotateAngleY == 0.0F && this.rotateAngleZ == 0.0F) {
					if (this.rotationPointX != 0.0F || this.rotationPointY != 0.0F || this.rotationPointZ != 0.0F) {
						GL11.glTranslatef(this.rotationPointX * f, this.rotationPointY * f, this.rotationPointZ * f);
					}
				} else {
					GL11.glTranslatef(this.rotationPointX * f, this.rotationPointY * f, this.rotationPointZ * f);
					if (this.rotateAngleZ != 0.0F) {
						GL11.glRotatef(this.rotateAngleZ * 57.29578F, 0.0F, 0.0F, 1.0F);
					}

					if (this.rotateAngleY != 0.0F) {
						GL11.glRotatef(this.rotateAngleY * 57.29578F, 0.0F, 1.0F, 0.0F);
					}

					if (this.rotateAngleX != 0.0F) {
						GL11.glRotatef(this.rotateAngleX * 57.29578F, 1.0F, 0.0F, 0.0F);
					}
				}

			}
		}
	}

	private void compileDisplayList(float f) {
		this.displayList = MemoryTracker.getLists(1);
		GL11.glNewList(this.displayList, 4864);
		BufferBuilder tessellator = BufferBuilder.INSTANCE;

		for (int i = 0; i < this.faces.length; ++i) {
			this.faces[i].compile(tessellator, f);
		}

		GL11.glEndList();
		this.compiled = true;
	}

	public PlaneRenderer setTextureSize(int i, int j) {
		this.textureWidth = (float) i;
		this.textureHeight = (float) j;
		return this;
	}

	public void setToModel(ModelPart modelrenderer) {
		this.rotationPointX = modelrenderer.pivotX;
		this.rotationPointY = modelrenderer.pivotY;
		this.rotationPointZ = modelrenderer.pivotZ;
		this.rotateAngleX = modelrenderer.rotationX;
		this.rotateAngleY = modelrenderer.rotationY;
		this.rotateAngleZ = modelrenderer.rotationZ;
	}
}
