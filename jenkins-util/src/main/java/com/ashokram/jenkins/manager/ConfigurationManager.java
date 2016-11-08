package com.ashokram.jenkins.manager;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ConfigurationManager {

    private static final Log log = LogFactory.getLog(ConfigurationManager.class);

    private static Configuration configuration;

    private static ConfigurationManager configurationManager;

    public static ConfigurationManager getConf() {
        if (configurationManager == null) {
            configurationManager = new ConfigurationManager();
        }
        return configurationManager;
    }

    private ConfigurationManager() {
        try {
            if (configuration == null) {
                configuration = loadConfig();
            }
        } catch (Exception e) {
            log.error("Exception in loading config", e);
        }
    }

    public String getConfigAsString(String key) {
        return getConfigAsString(key, null);
    }

    public String getConfigAsString(String key, String defaultValue) {
        return configuration.getString(key, defaultValue);
    }

    private Configuration loadConfig() throws ConfigurationException {
        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder = new FileBasedConfigurationBuilder<FileBasedConfiguration>(
                PropertiesConfiguration.class).configure(params.properties().setFileName("config.properties"));
        return builder.getConfiguration();
    }
}