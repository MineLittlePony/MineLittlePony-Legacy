package com.minelittlepony.minelittlepony.util;

import net.minecraft.client.Minecraft;
import org.apache.commons.io.IOUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ResourceUtil {
	public static BufferedImage readImage(String resourceName) throws IOException {
		URL resource = Minecraft.class.getResource(resourceName);

		if (resource == null) {
			throw new IllegalStateException("Resource not found: " + resourceName);
		}

		return ImageIO.read(resource);
	}

	public static String getResourceAsString(String resourceName) {
		InputStream resourceStream = Minecraft.class.getResourceAsStream(resourceName);

		if (resourceStream == null) {
			throw new IllegalStateException("Resource not found: " + resourceName);
		}

		try {
			return IOUtils.toString(resourceStream);
		} catch (IOException e) {
			throw new IllegalStateException("Failed to read resource " + resourceName, e);
		} finally {
			IOUtils.closeQuietly(resourceStream);
		}
	}
}
