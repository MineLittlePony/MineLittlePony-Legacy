package codes.blackjack.minelittlepony.config;

import codes.blackjack.minelittlepony.MineLPEntry;
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
		MineLPEntry.LOGGER.info("Attempting to load/create the configuration.");
		this.loadConfig();
	}

	private void loadConfig() {
		this.config = new Properties(defaults);

		try {
			this.basePath = Minecraft.getRunDirectory().getCanonicalPath() + File.separatorChar + "mods" + File.separatorChar;
			this.path = this.basePath + "MineLittlePony" + File.separatorChar;
			File cfg = new File(this.path + "MineLittlePony.properties");
			if (cfg.exists()) {
				MineLPEntry.LOGGER.info("Config file found, loading...");
				this.config.load(Files.newInputStream(Paths.get(this.path + "MineLittlePony.properties")));
			} else {
				MineLPEntry.LOGGER.info("No config file found, creating...");
				this.createConfig(cfg);
			}
		} catch (Exception e) {
			this.displayErrorMessage(e.toString());
		}

	}

	private void createConfig(File cfg) {
		File baseFolder = new File(this.basePath);
		if (!baseFolder.exists()) {
			MineLPEntry.LOGGER.info("No mods folder found, creating...");
			baseFolder.mkdir();
		}

		File folder = new File(this.path);
		if (!folder.exists()) {
			MineLPEntry.LOGGER.info("No Mine Little Pony folder found, creating...");
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
		MineLPEntry.LOGGER.error("ERROR: {}", error);
	}

	static {
		defaults.setProperty("ponylevel", "2");
		defaults.setProperty("sizes", "1");
		defaults.setProperty("ponyarmor", "1");
		defaults.setProperty("snuzzles", "1");
	}
}
