package com.ashokram.jenkins.manager;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The Class ConfigurationManager.
 *
 * @author Ashok Ram. G
 * @since 1.0
 */
public class ConfigurationManager
{

	/** The Constant log. */
	private static final Log log = LogFactory.getLog(ConfigurationManager.class);

	/** The configuration. */
	private static Configuration configuration;

	/** The configuration manager. */
	private static ConfigurationManager configurationManager;

	/**
	 * Gets the conf.
	 *
	 * @return the conf
	 * @author Ashok Ram. G
	 * @since 1.0
	 */
	public static ConfigurationManager getConf() {
		if (configurationManager == null) {
			configurationManager = new ConfigurationManager();
		}
		return configurationManager;
	}

	/**
	 * Instantiates a new configuration manager.
	 * 
	 * @author Ashok Ram. G
	 * @since 1.0
	 */
	private ConfigurationManager() {
		try {
			if (configuration == null) {
				configuration = loadConfig();
			}
		} catch (Exception e) {
			log.error("Exception in loading config", e);
		}
	}

	/**
	 * Gets the config as string.
	 *
	 * @param key
	 *            the key
	 * @return the config as string
	 * @author Ashok Ram. G
	 * @since 1.0
	 */
	public String getConfigAsString(String key) {
		return getConfigAsString(key, null);
	}

	/**
	 * Gets the config as string.
	 *
	 * @param key
	 *            the key
	 * @param defaultValue
	 *            the default value
	 * @return the config as string
	 * @author Ashok Ram. G
	 * @since 1.0
	 */
	public String getConfigAsString(String key, String defaultValue) {
		return configuration.getString(key, defaultValue);
	}

	/**
	 * Load config.
	 *
	 * @return the configuration
	 * @throws ConfigurationException
	 *             the configuration exception
	 * @author Ashok Ram. G
	 * @since 1.0
	 */
	private Configuration loadConfig() throws ConfigurationException {
		Parameters params = new Parameters();
		FileBasedConfigurationBuilder<FileBasedConfiguration> builder = new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class).configure(params.properties().setFileName("config.properties"));
		return builder.getConfiguration();
	}
}