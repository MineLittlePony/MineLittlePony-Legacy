package codes.blackjack.minelittlepony;

import net.minecraft.client.Minecraft;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class PonyConfig {
	private static final Properties defaults = new Properties();

	private String basePath;
	private String path;
	private Properties config;

	public PonyConfig(Minecraft instance) {
		System.out.println("[Mine Little Pony] Attempting to load/create the configuration.");
		this.loadConfig();
	}

	private void loadConfig() {
		this.config = new Properties(defaults);

		try {
			this.basePath = Minecraft.getRunDirectory().getCanonicalPath() + File.separatorChar + "mods" + File.separatorChar;
			this.path = this.basePath + "MineLittlePony" + File.separatorChar;
			File cfg = new File(this.path + "MineLittlePony.properties");
			if (cfg.exists()) {
				System.out.println("[Mine Little Pony] Config file found, loading...");
				this.config.load(Files.newInputStream(Paths.get(this.path + "MineLittlePony.properties")));
			} else {
				System.out.println("[Mine Little Pony] No config file found, creating...");
				this.createConfig(cfg);
			}
		} catch (Exception e) {
			this.displayErrorMessage(e.toString());
		}

	}

	private void createConfig(File cfg) {
		File baseFolder = new File(this.basePath);
		if (!baseFolder.exists()) {
			System.out.println("[Mine Little Pony] No mods folder found, creating...");
			baseFolder.mkdir();
		}

		File folder = new File(this.path);
		if (!folder.exists()) {
			System.out.println("[Mine Little Pony] No Mine Little Pony folder found, creating...");
			folder.mkdir();
		}

		try {
			cfg.createNewFile();
			this.config.setProperty("ponylevel", "2");
			this.config.setProperty("sizes", "1");
			this.config.setProperty("ponyarmor", "1");
			this.config.setProperty("snuzzles", "1");
			this.config.store(Files.newOutputStream(Paths.get(this.path + "MineLittlePony.properties")), "Mine Little Pony");
		} catch (Exception e) {
			this.displayErrorMessage(e.toString());
		}

	}

	public void setProperty(String prop, float value) {
		String s = String.valueOf(value);
		this.config.setProperty(prop, s);
		this.saveConfig();
	}

	public void setProperty(String prop, int value) {
		String s = String.valueOf(value);
		this.config.setProperty(prop, s);
		this.saveConfig();
	}

	public void setProperty(String prop, boolean value) {
		String s = String.valueOf(value);
		this.config.setProperty(prop, s);
		this.saveConfig();
	}

	public String getStringProperty(String prop) {
		return this.config.getProperty(prop);
	}

	public float getFloatProperty(String prop) {
		String s = this.config.getProperty(prop);
		return Float.parseFloat(s);
	}

	public int getIntProperty(String prop) {
		String s = this.config.getProperty(prop);
		return Integer.parseInt(s);
	}

	public boolean getBoolProperty(String prop) {
		String s = this.config.getProperty(prop);
		return Boolean.parseBoolean(s);
	}

	private void saveConfig() {
		try {
			this.config.store(Files.newOutputStream(Paths.get(this.path + "MineLittlePony.properties")), null);
		} catch (Exception e) {
			this.displayErrorMessage(e.toString());
		}

	}

	private void displayErrorMessage(String error) {
		System.out.println("[Mine Little Pony] ERROR: " + error);
	}

	static {
		defaults.setProperty("ponylevel", "2");
		defaults.setProperty("sizes", "1");
		defaults.setProperty("ponyarmor", "1");
		defaults.setProperty("snuzzles", "1");
	}
}
