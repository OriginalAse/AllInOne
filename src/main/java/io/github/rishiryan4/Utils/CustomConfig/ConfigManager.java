package io.github.rishiryan4.Utils.CustomConfig;

import io.github.rishiryan4.Main;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    private FileConfiguration commandConfig;

    public ConfigManager() {
        File messageConfigFile = new File(Main.getInstance().getDataFolder(), "commands.yml");

        if (!messageConfigFile.exists()) {
            messageConfigFile.getParentFile().mkdirs();
            Main.getInstance().saveResource("commands.yml", false);
        }

        commandConfig = new YamlConfiguration();

        try {
            commandConfig.load(messageConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getCommandConfig() { return commandConfig; }
}
