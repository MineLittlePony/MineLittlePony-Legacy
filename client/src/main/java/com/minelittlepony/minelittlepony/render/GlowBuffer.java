package com.minelittlepony.minelittlepony.render;

import com.minelittlepony.minelittlepony.MineLPEntry;
import com.minelittlepony.minelittlepony.mixin.MixinExtMinecraft;
import com.minelittlepony.minelittlepony.util.ShaderProgram;
import com.mojang.blaze3d.vertex.BufferBuilder;
import net.minecraft.client.render.Window;
import org.lwjgl.util.vector.Vector4f;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glGetInteger;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL30.*;

public class GlowBuffer {
	public static final GlowBuffer INSTANCE = new GlowBuffer();

	private final ShaderProgram glowShader;

	private int glowFboId = -1;
	private int glowTextureId = -1;

	private int width = -1;
	private int height = -1;

	public GlowBuffer() {
		this.glowShader = new ShaderProgram(
			"/shaders/minelp_glow.vert",
			"/shaders/minelp_glow.frag"
		);

		this.glowShader.bindAttributeLocation(0, "gl_Vertex");

		MineLPEntry.LOGGER.info("GlowBuffer initialized");
		MineLPEntry.LOGGER.info("Max texture size supported: {}", glGetInteger(GL_MAX_TEXTURE_SIZE));
	}

	public void resize(int width, int height) {
		clearGlError();
		if (this.glowFboId != -1) {
			glDeleteFramebuffers(this.glowFboId);
			glDeleteTextures(this.glowTextureId);
		}

		this.width = width;
		this.height = height;

		this.glowTextureId = glGenTextures();
		this.glowFboId = glGenFramebuffers();

		MineLPEntry.LOGGER.info("Resizing GlowBuffer to {}x{}", width, height);

		allocateFbo(this.glowFboId, this.glowTextureId);
	}

	private void allocateFbo(int fboId, int textureId) {
		clearGlError();
		glBindTexture(GL_TEXTURE_2D, textureId);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, this.width, this.height, 0, GL_RGBA, GL_UNSIGNED_BYTE, (ByteBuffer) null);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

		glBindFramebuffer(GL_FRAMEBUFFER, fboId);
		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, textureId, 0);

		if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) {
			glBindFramebuffer(GL_FRAMEBUFFER, 0);
			glBindTexture(GL_TEXTURE_2D, 0);
			glDeleteFramebuffers(this.glowFboId);
			glDeleteTextures(this.glowTextureId);

			throw new IllegalStateException("Failed to create glow framebuffer!");
		}

		glBindFramebuffer(GL_FRAMEBUFFER, 0);
		glBindTexture(GL_TEXTURE_2D, 0);
		checkGlError();
	}

	public void reset() {
		int mcWidth = MixinExtMinecraft.getMinecraft().width;
		int mcHeight = MixinExtMinecraft.getMinecraft().height;

		if (this.width != mcWidth || this.height != mcHeight) {
			this.resize(mcWidth, mcHeight);
		}

		glBindFramebuffer(GL_FRAMEBUFFER, this.glowFboId);
		glClearColor(0, 0, 0, 0);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
	}

	public void flush() {
		clearGlError();

		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glDisable(GL_DEPTH_TEST);

		glBindFramebuffer(GL_FRAMEBUFFER, 0);
		glUseProgram(0);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE);
		glBindTexture(GL_TEXTURE_2D, this.glowTextureId);

		// Render multiple times with slight offsets to create glow
		int[] offsets = {0, 1, -1, 2, -2, 1, -1, 2, -2};
		for (int offset : offsets) {
			glPushMatrix();
			glTranslatef(offset, offset, 0);
			glColor4f(1.0f, 1.0f, 1.0f, 0.2f);
			drawFullScreenQuad();
			glPopMatrix();
		}

		glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		glDisable(GL_BLEND);

		checkGlError();

		// Clean up
		glBindTexture(GL_TEXTURE_2D, 0);
		glDisable(GL_BLEND);
		glDisable(GL_TEXTURE_2D);
	}

	public void startRenderingGlow() {
		glBindFramebuffer(GL_FRAMEBUFFER, this.glowFboId);

		this.glowShader.use();
		this.glowShader.uniform4f("glowColor", new Vector4f(1.0F, 0.0F, 0.0F, 1.0F));
		this.glowShader.uniform1i("texture", 0); // Bind to texture unit 0

		// glColor4d(1.0, 1.0, 1.0, 1.0);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}

	public void finishRenderingGlow() {
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
		this.glowShader.unuse();
		glDisable(GL_BLEND);
	}

	public void renderGlow(Runnable r) {
		this.startRenderingGlow();
		r.run();
		this.finishRenderingGlow();
	}

	public void cleanup() {
		this.glowShader.cleanup();
		glDeleteFramebuffers(this.glowFboId);
		glDeleteTextures(this.glowTextureId);
	}

	private static void clearGlError() {
		glGetError();
	}

	private static void checkGlError() {
		int error = glGetError();

		if (error != GL_NO_ERROR) {
			throw new IllegalStateException("GL error: " + error);
		}
	}

	private void drawFullScreenQuad() {
		Window window = new net.minecraft.client.render.Window(MixinExtMinecraft.getMinecraft().options, this.width, this.height);
		int scaledWidth = (int) window.scaledWidth;
		int scaledHeight = (int) window.scaledHeight;

		BufferBuilder bufferBuilder = com.mojang.blaze3d.vertex.BufferBuilder.INSTANCE;
		bufferBuilder.start();
		bufferBuilder.vertex(0, scaledHeight, 0, 0, 0);
		bufferBuilder.vertex(scaledWidth, scaledHeight, 0, 1, 0);
		bufferBuilder.vertex(scaledWidth, 0, 0, 1, 1);
		bufferBuilder.vertex(0, 0, 0, 0, 1);
		bufferBuilder.end();
	}
}
