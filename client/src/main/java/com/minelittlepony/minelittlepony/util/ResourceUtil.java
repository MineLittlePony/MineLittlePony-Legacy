package com.minelittlepony.minelittlepony.util;

import net.minecraft.client.Minecraft;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class ResourceUtil {
	public static BufferedImage readImage(String resourceName) throws IOException {
		URL resource = Minecraft.class.getResource(resourceName);

		if (resource == null) {
			throw new IllegalStateException("Resource not found: " + resourceName);
		}

		return ImageIO.read(resource);
	}
}
