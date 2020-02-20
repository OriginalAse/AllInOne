package io.github.rishiryan4;

import com.google.common.reflect.ClassPath;
import io.github.rishiryan4.Utils.Utils;
import io.github.rishiryan4.Utils.Config;
import io.github.rishiryan4.Utils.CustomConfig.ConfigManager;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class Main extends JavaPlugin {

    private static Main instance;
    private static ConfigManager configFiles;
    private static FileConfiguration commandConfig;
    private static Utils utils;

    private SimpleCommandMap commandMap;

    @Override
    public void onEnable() {
        instance = this;
        configFiles = new Config().getConfigs();
        commandConfig = configFiles.getCommandConfig();
        utils = new Utils();

        try {
            registerAllCommands();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void registerAllCommands() throws IOException {
        ClassPath cp = ClassPath.from(getClass().getClassLoader());
        cp.getTopLevelClassesRecursive("io.github.rishiryan4.Commands").forEach(classInfo -> {
            try {
                Object cmd = Class.forName(classInfo.getName()).getConstructor().newInstance();

                if (!(cmd instanceof BukkitCommand)) return;

                if (commandMap == null) {
                    String version = getServer().getClass().getPackage().getName().split("\\.")[3];
                    Class craftServerClass = Class.forName("org.bukkit.craftbukkit." + version + ".CraftServer");
                    Field commandMapField = craftServerClass.getDeclaredField("commandMap");
                    commandMapField.setAccessible(true);
                    commandMap = (SimpleCommandMap) commandMapField.get(craftServerClass.cast(getServer()));
                }
                commandMap.register(getDescription().getName(), (BukkitCommand) cmd);
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchFieldException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });
    }

    public static Main getInstance() {
        return instance;
    }

    public static ConfigManager getConfigFiles() {
        return configFiles;
    }

    public static FileConfiguration getCommandConfig() {
        return commandConfig;
    }

    public static Utils getUtils() {
        return utils;
    }
}
