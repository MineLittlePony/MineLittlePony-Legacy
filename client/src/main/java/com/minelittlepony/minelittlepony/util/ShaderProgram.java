package com.minelittlepony.minelittlepony.util;

import org.lwjgl.util.vector.Vector4f;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

public class ShaderProgram {
	private final int programId;

	public ShaderProgram(String vertexShaderPath, String fragmentShaderPath) {
		String vertexShaderSource = ResourceUtil.getResourceAsString(vertexShaderPath);
		String fragmentShaderSource = ResourceUtil.getResourceAsString(fragmentShaderPath);

		int vertexId = compileShader(GL_VERTEX_SHADER, vertexShaderSource);
		int fragmentId = compileShader(GL_FRAGMENT_SHADER, fragmentShaderSource);

		this.programId = glCreateProgram();

		glAttachShader(programId, vertexId);
		glAttachShader(programId, fragmentId);
		glLinkProgram(programId);

		if (glGetProgrami(programId, GL_LINK_STATUS) == GL_FALSE) {
			String infoLog = glGetProgramInfoLog(programId, 4096);
			glDeleteProgram(programId);
			glDeleteShader(vertexId);
			glDeleteShader(fragmentId);

			throw new IllegalStateException("Failed to compile shader program: " + infoLog);
		}

		glDetachShader(programId, vertexId);
		glDetachShader(programId, fragmentId);
		glDeleteShader(vertexId);
		glDeleteShader(fragmentId);
	}

	public void uniform4f(String name, Vector4f value) {
		int location = glGetUniformLocation(this.programId, name);

		glUniform4f(location, value.x, value.y, value.z, value.w);
	}

	public void uniform1i(String name, boolean value) {
		int location = glGetUniformLocation(this.programId, name);

		glUniform1i(location, value ? 1 : 0);
	}

	public void uniform1i(String name, int value) {
		int location = glGetUniformLocation(this.programId, name);

		glUniform1i(location, value);
	}

	public void uniform1f(String name, float value) {
		int location = glGetUniformLocation(this.programId, name);

		glUniform1f(location, value);
	}

	public void uniform2f(String name, float x, float y) {
		int location = glGetUniformLocation(this.programId, name);

		glUniform2f(location, x, y);
	}

	public void use() {
		glUseProgram(this.programId);
	}

	public void unuse() {
		glUseProgram(0);
	}

	public void cleanup() {
		glDeleteProgram(this.programId);
	}

	public int getProgramId() {
		return this.programId;
	}

	public void bindAttributeLocation(int location, String name) {
		glBindAttribLocation(this.programId, location, name);
	}

	private static int compileShader(int type, String source) {
		int shader = glCreateShader(type);
		glShaderSource(shader, source);
		glCompileShader(shader);

		if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
			String infoLog = glGetShaderInfoLog(shader, 4096);
			glDeleteShader(shader);

			throw new IllegalStateException("Failed to compile shader: " + infoLog);
		}

		return shader;
	}
}
