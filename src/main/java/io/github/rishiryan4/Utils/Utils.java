package io.github.rishiryan4.Utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static io.github.rishiryan4.Main.getCommandConfig;

public class Utils {

    public String setPlaceholders(Player player, String message) {
        String placeholdered = message;
        if (Bukkit.getPluginManager().getPlugin("PlacholderAPI") != null) {
            Object placeholderAPIClass = null;
            try {
                placeholderAPIClass = Class.forName("me.clip.placeholderapi.PlaceholderAPI");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Method method = null;
            try {
                method = placeholderAPIClass.getClass().getMethod("setPlaceholders", Player.class, String.class);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            try {
                placeholdered = method.invoke(placeholderAPIClass, player, message).toString();
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return placeholdered;
    }
    public String tr(String msg, Player player) {
        return setPlaceholders(player, ChatColor.translateAlternateColorCodes('&', msg));
    }

    public boolean sentFromConsole(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(getCommandConfig().getString("ExecutedFromConsoleCommandMessage"));
            return false;
        }
        return true;
    }

    public boolean setPermCheckAndEnabled(Player player, String permission, String permissionMessage, String command) {
        if (!getCommandConfig().getBoolean(command + ".Enabled")) {
            player.sendMessage(tr(getCommandConfig().getString("DisabledCommandMessage"), player));
            return false;
        }

        if (!player.hasPermission(permission)) {
            player.sendMessage(tr(permissionMessage, player));
            return false;
        }
        return true;
    }
    public boolean setTypeAndTarget(Player player, Player[] target, String[] args, String[] type, String permission, String permissionMessage)  {
        if (args.length < 1) {
            target[0] = player;
            type[0] = "Self";
        } else {
            target[0] = Bukkit.getPlayer(args[0]);

            if (target[0] == player) type[0] = "Self"; else {
                if (!player.hasPermission(permission + ".others")) {
                    player.sendMessage(tr(permissionMessage, player));
                    return false;
                }

                type[0] = "Others";
            }
        }
        return true;
    }
}
