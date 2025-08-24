package com.minelittlepony.minelittlepony.render;

import com.mojang.blaze3d.platform.MemoryTracker;
import com.mojang.blaze3d.vertex.BufferBuilder;
import net.minecraft.client.render.model.Model;
import net.minecraft.client.render.model.ModelPart;
import net.minecraft.client.render.model.Quad;
import net.minecraft.client.render.model.Vertex;
import org.lwjgl.opengl.GL11;

public class PlaneRenderer {
	private float textureWidth = 64.0F;
	private float textureHeight = 32.0F;
	private Vertex[] corners;
	private Quad[] faces;
	private final int textureOffsetX;
	private final int textureOffsetY;
	public float rotationPointX;
	public float rotationPointY;
	public float rotationPointZ;
	public float rotateAngleX;
	public float rotateAngleY;
	public float rotateAngleZ;
	private boolean compiled;
	private int displayList;
	public boolean mirror;
	public boolean mirrory;
	public boolean mirrorxy;
	private static final boolean showModel = true;
	private boolean isHidden;

	public PlaneRenderer(Model modelbase, int texOffsetX, int texOffsetY) {
		this.textureOffsetX = texOffsetX;
		this.textureOffsetY = texOffsetY;

		// Are we sure?
		//noinspection unchecked
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
		this.corners = new Vertex[8];
		this.faces = new Quad[1];
		float f4 = f + i;
		float f5 = f1 + j;
		float f6 = f2 + k;
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

		Vertex vertex0 = new Vertex(f, f1, f2, 0.0F, 0.0F);
		Vertex vertex1 = new Vertex(f4, f1, f2, 0.0F, 8.0F);
		Vertex vertex2 = new Vertex(f4, f5, f2, 8.0F, 8.0F);
		Vertex vertex3 = new Vertex(f, f5, f2, 8.0F, 0.0F);
		Vertex vertex4 = new Vertex(f, f1, f6, 0.0F, 0.0F);
		Vertex vertex5 = new Vertex(f4, f1, f6, 0.0F, 8.0F);
		Vertex vertex6 = new Vertex(f4, f5, f6, 8.0F, 8.0F);
		Vertex vertex7 = new Vertex(f, f5, f6, 8.0F, 0.0F);

		this.corners[0] = vertex0;
		this.corners[1] = vertex1;
		this.corners[2] = vertex2;
		this.corners[3] = vertex3;
		this.corners[4] = vertex4;
		this.corners[5] = vertex5;
		this.corners[6] = vertex6;
		this.corners[7] = vertex7;

		this.faces[0] = new Quad(
			new Vertex[]{ vertex1, vertex0, vertex3, vertex2 },
			this.textureOffsetX, this.textureOffsetY,
			this.textureOffsetX + i, this.textureOffsetY + j,
			this.textureWidth, this.textureHeight
		);

		if (this.mirror) {
			this.faces[0].flip();
		}
	}

	public void addSidePlane(float f, float f1, float f2, int i, int j, int k, float f3) {
		this.corners = new Vertex[8];
		this.faces = new Quad[1];
		float f4 = f + i;
		float f5 = f1 + j;
		float f6 = f2 + k;
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

		Vertex vertex0 = new Vertex(f, f1, f2, 0.0F, 0.0F);
		Vertex vertex1 = new Vertex(f4, f1, f2, 0.0F, 8.0F);
		Vertex vertex2 = new Vertex(f4, f5, f2, 8.0F, 8.0F);
		Vertex vertex3 = new Vertex(f, f5, f2, 8.0F, 0.0F);
		Vertex vertex4 = new Vertex(f, f1, f6, 0.0F, 0.0F);
		Vertex vertex5 = new Vertex(f4, f1, f6, 0.0F, 8.0F);
		Vertex vertex6 = new Vertex(f4, f5, f6, 8.0F, 8.0F);
		Vertex vertex7 = new Vertex(f, f5, f6, 8.0F, 0.0F);

		this.corners[0] = vertex0;
		this.corners[1] = vertex1;
		this.corners[2] = vertex2;
		this.corners[3] = vertex3;
		this.corners[4] = vertex4;
		this.corners[5] = vertex5;
		this.corners[6] = vertex6;
		this.corners[7] = vertex7;

		this.faces[0] = new Quad(
			new Vertex[]{ vertex5, vertex1, vertex2, vertex6 },
			this.textureOffsetX, this.textureOffsetY,
			this.textureOffsetX + k, this.textureOffsetY + j,
			this.textureWidth, this.textureHeight
		);

		if (this.mirror) {
			this.faces[0].flip();
		}
	}

	public void addTopPlane(float f, float f1, float f2, int i, int j, int k, float f3) {
		this.corners = new Vertex[8];
		this.faces = new Quad[1];
		float f4 = f + i;
		float f5 = f1 + j;
		float f6 = f2 + k;
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

		Vertex vertex0 = new Vertex(f, f1, f2, 0.0F, 0.0F);
		Vertex vertex1 = new Vertex(f4, f1, f2, 0.0F, 8.0F);
		Vertex vertex2 = new Vertex(f4, f5, f2, 8.0F, 8.0F);
		Vertex vertex3 = new Vertex(f, f5, f2, 8.0F, 0.0F);
		Vertex vertex4 = new Vertex(f, f1, f6, 0.0F, 0.0F);
		Vertex vertex5 = new Vertex(f4, f1, f6, 0.0F, 8.0F);
		Vertex vertex6 = new Vertex(f4, f5, f6, 8.0F, 8.0F);
		Vertex vertex7 = new Vertex(f, f5, f6, 8.0F, 0.0F);

		this.corners[0] = vertex0;
		this.corners[1] = vertex1;
		this.corners[2] = vertex2;
		this.corners[3] = vertex3;
		this.corners[4] = vertex4;
		this.corners[5] = vertex5;
		this.corners[6] = vertex6;
		this.corners[7] = vertex7;

		this.faces[0] = new Quad(
			new Vertex[]{ vertex5, vertex4, vertex0, vertex1 },
			this.textureOffsetX, this.textureOffsetY,
			this.textureOffsetX + i, this.textureOffsetY + k,
			this.textureWidth, this.textureHeight
		);

		if (this.mirror || this.mirrory) {
			this.faces[0].flip();
		}
	}

	public void addBottomPlane(float f, float f1, float f2, int i, int j, int k, float f3) {
		this.corners = new Vertex[8];
		this.faces = new Quad[1];
		float f4 = f + i;
		float f5 = f1 + j;
		float f6 = f2 + k;
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

		Vertex vertex0 = new Vertex(f, f1, f2, 0.0F, 0.0F);
		Vertex vertex1 = new Vertex(f4, f1, f2, 0.0F, 8.0F);
		Vertex vertex2 = new Vertex(f4, f5, f2, 8.0F, 8.0F);
		Vertex vertex3 = new Vertex(f, f5, f2, 8.0F, 0.0F);
		Vertex vertex4 = new Vertex(f, f1, f6, 0.0F, 0.0F);
		Vertex vertex5 = new Vertex(f4, f1, f6, 0.0F, 8.0F);
		Vertex vertex7 = new Vertex(f4, f5, f6, 8.0F, 8.0F);
		Vertex vertex8 = new Vertex(f, f5, f6, 8.0F, 0.0F);

		this.corners[0] = vertex0;
		this.corners[1] = vertex1;
		this.corners[2] = vertex2;
		this.corners[3] = vertex3;
		this.corners[4] = vertex4;
		this.corners[5] = vertex5;
		this.corners[6] = vertex7;
		this.corners[7] = vertex8;

		this.faces[0] = new Quad(
			new Vertex[]{ vertex2, vertex3, vertex8, vertex7 },
			this.textureOffsetX, this.textureOffsetY,
			this.textureOffsetX + i, this.textureOffsetY + k,
			this.textureWidth, this.textureHeight
		);

		if (this.mirror || this.mirrory) {
			this.faces[0].flip();
		}
	}

	public void setRotationPoint(float x, float y, float z) {
		this.rotationPointX = x;
		this.rotationPointY = y;
		this.rotationPointZ = z;
	}

	public void render(float f) {
		if (!this.isHidden) {
			if (this.showModel) { // FIXME: Why is this static?!
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

		for (Quad face : this.faces) {
			face.compile(tessellator, f);
		}

		GL11.glEndList();
		this.compiled = true;
	}

	public PlaneRenderer setTextureSize(int i, int j) {
		this.textureWidth = i;
		this.textureHeight = j;
		return this;
	}

	public void setToModel(ModelPart modelPart) {
		this.rotationPointX = modelPart.pivotX;
		this.rotationPointY = modelPart.pivotY;
		this.rotationPointZ = modelPart.pivotZ;
		this.rotateAngleX = modelPart.rotationX;
		this.rotateAngleY = modelPart.rotationY;
		this.rotateAngleZ = modelPart.rotationZ;
	}
}
