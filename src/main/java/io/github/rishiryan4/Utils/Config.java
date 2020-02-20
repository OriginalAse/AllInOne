package io.github.rishiryan4.Utils;

import io.github.rishiryan4.Utils.CustomConfig.ConfigManager;

public class Config {

    private static ConfigManager configManager;

    public Config() { configManager = new ConfigManager(); }

    public static ConfigManager getConfigs() { return configManager; }

}
