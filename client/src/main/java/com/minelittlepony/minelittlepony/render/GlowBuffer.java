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
	private final ShaderProgram blurShader;

	private int glowFboId = -1;
	private int glowTextureId = -1;
	private int glowDepthBufferId = -1;

	private int width = -1;
	private int height = -1;

	public GlowBuffer() {
		this.glowShader = new ShaderProgram(
			"/shaders/minelp_glow.vert",
			"/shaders/minelp_glow.frag"
		);

		this.blurShader = new ShaderProgram(
			"/shaders/minelp_blur.vert",
			"/shaders/minelp_blur.frag"
		);

		this.glowShader.bindAttributeLocation(0, "gl_Vertex");
		this.blurShader.bindAttributeLocation(0, "gl_Vertex");

		MineLPEntry.LOGGER.info("Max texture size supported: {}", glGetInteger(GL_MAX_TEXTURE_SIZE));
	}

	public void resize(int width, int height) {
		clearGlError();
		if (this.glowFboId != -1) {
			glDeleteFramebuffers(this.glowFboId);
			glDeleteTextures(this.glowTextureId);
			glDeleteRenderbuffers(this.glowDepthBufferId);
		}

		this.width = width;
		this.height = height;

		this.glowTextureId = glGenTextures();
		this.glowFboId = glGenFramebuffers();
		this.glowDepthBufferId = glGenRenderbuffers();

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

		glBindRenderbuffer(GL_RENDERBUFFER, this.glowDepthBufferId);
		glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT, this.width, this.height);

		glBindFramebuffer(GL_FRAMEBUFFER, fboId);
		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, textureId, 0);
		glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, this.glowDepthBufferId);

		if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) {
			glBindFramebuffer(GL_FRAMEBUFFER, 0);
			glBindTexture(GL_TEXTURE_2D, 0);
			glBindRenderbuffer(GL_RENDERBUFFER, 0);
			glDeleteFramebuffers(this.glowFboId);
			glDeleteTextures(this.glowTextureId);
			glDeleteRenderbuffers(this.glowDepthBufferId);

			throw new IllegalStateException("Failed to create glow framebuffer!");
		}

		glBindFramebuffer(GL_FRAMEBUFFER, 0);
		glBindTexture(GL_TEXTURE_2D, 0);
		glBindRenderbuffer(GL_RENDERBUFFER, 0);
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
		glBlendFunc(GL_SRC_ALPHA, GL_ONE);
		glDisable(GL_DEPTH_TEST);

		glBindTexture(GL_TEXTURE_2D, this.glowTextureId);
		this.blurShader.use();
		this.blurShader.uniform1i("glowTexture", 0);
		this.blurShader.uniform2f("resolution", this.width, this.height);
		this.blurShader.uniform1f("blurRadius", 2.0f); // Reasonable blur radius
		drawFullScreenQuad();
		glUseProgram(0);

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

		// Copy depth from main framebuffer to glow framebuffer
		glBindFramebuffer(GL_READ_FRAMEBUFFER, 0);
		glBindFramebuffer(GL_DRAW_FRAMEBUFFER, this.glowFboId);
		glBlitFramebuffer(0, 0, this.width, this.height, 0, 0, this.width, this.height, GL_DEPTH_BUFFER_BIT, GL_NEAREST);

		// Switch back to glow framebuffer for rendering
		glBindFramebuffer(GL_FRAMEBUFFER, this.glowFboId);

		this.glowShader.use();
		this.glowShader.uniform4f("glowColor", new Vector4f(1.0F, 0.0F, 0.0F, 1.0F));
		this.glowShader.uniform1i("texture", 0); // Bind to texture unit 0

		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_DEPTH_TEST);
		glDepthFunc(GL_LEQUAL);

		// Add a small depth offset to prevent z-fighting
		glEnable(GL_POLYGON_OFFSET_FILL);
		glPolygonOffset(0.0f, -1.0f);
	}

	public void finishRenderingGlow() {
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
		this.glowShader.unuse();
		glDisable(GL_BLEND);
		glDisable(GL_POLYGON_OFFSET_FILL);
		// glDisable(GL_DEPTH_TEST);
	}

	public void renderGlow(Runnable r) {
		this.startRenderingGlow();
		r.run();
		this.finishRenderingGlow();
	}

	public void cleanup() {
		this.glowShader.cleanup();
		this.blurShader.cleanup();
		glDeleteFramebuffers(this.glowFboId);
		glDeleteTextures(this.glowTextureId);
		glDeleteRenderbuffers(this.glowDepthBufferId);
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
		Window window = new Window(MixinExtMinecraft.getMinecraft().options, this.width, this.height);
		int scaledWidth = (int) window.scaledWidth;
		int scaledHeight = (int) window.scaledHeight;

		BufferBuilder bufferBuilder = BufferBuilder.INSTANCE;
		bufferBuilder.start();
		bufferBuilder.vertex(0, scaledHeight, 0, 0, 0);
		bufferBuilder.vertex(scaledWidth, scaledHeight, 0, 1, 0);
		bufferBuilder.vertex(scaledWidth, 0, 0, 1, 1);
		bufferBuilder.vertex(0, 0, 0, 0, 1);
		bufferBuilder.end();
	}
}
