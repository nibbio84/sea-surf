package it.nerdammer.seasurf;

import org.apache.log4j.Logger;

import it.nerdammer.seasurf.config.SeaSurfConfig;

class ConfigHandler {
	
	private static Logger logger = Logger.getLogger(ConfigHandler.class);
	
	private static SeaSurfConfig config;
	
	public static SeaSurfConfig getConfig() {
		if(config==null) {
			throw new IllegalStateException("Sea-surf not initialized");
		}
		return config;
	}
	
	public static void init() {
		logger.debug("Loading sea-surf configuration using default configuration");
		ConfigLoader loader = new ConfigLoader();
		config = loader.loadConfig();
		
		logger.info("Sea-surf configuration loaded");
	}
	
	public static void init(ConfigLoader loader) {
		logger.debug("Loading sea-surf configuration using custom loader");
		
		config = loader.loadConfig();
		
		logger.info("Sea-surf configuration loaded");
	}
	
}
