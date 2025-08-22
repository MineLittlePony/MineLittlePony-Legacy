package codes.blackjack.minelittlepony.config;

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

	public PonyConfig() {
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
			if (cfg.createNewFile()) {
				this.config = new Properties(defaults);
				this.config.store(Files.newOutputStream(Paths.get(this.path + "MineLittlePony.properties")), "Mine Little Pony");
			}
		} catch (Exception e) {
			this.displayErrorMessage(e.toString());
		}

	}

	public int getIntProperty(String prop) {
		String s = this.config.getProperty(prop);
		return Integer.parseInt(s);
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
