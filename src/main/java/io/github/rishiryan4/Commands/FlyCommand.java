package io.github.rishiryan4.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import static io.github.rishiryan4.Main.*;

public class FlyCommand extends BukkitCommand {

    public FlyCommand() {
        super("fly");

        setDescription("Toggle Fly Mode");
        setPermission("aoi.fly");
        setPermissionMessage(getCommandConfig().getString("NoPermCommandMessage"));
    }

    @Override
    public boolean execute(CommandSender sender, String alias, String[] args) {

        if (!getUtils().sentFromConsole(sender)) return false;

        Player player = (Player) sender;

        if (!getUtils().setPermCheckAndEnabled(player, getPermission(), getPermissionMessage(), "Fly")) return false;

        Player[] target = new Player[1];
        String[] type = new String[1];

        if (!getUtils().setTypeAndTarget(player, target, args, type, getPermission(), getPermissionMessage())) return false;

        if (target[0].getAllowFlight() || target[0].isFlying()) {
            target[0].setFlying(false);
            target[0].setAllowFlight(false);
            player.sendMessage(getUtils().tr(getCommandConfig().getString("Fly.Messages." + type[0] + ".FlightDisable"), target[0]));
        } else {
            target[0].setAllowFlight(true);
            target[0].setFlying(true);
            player.sendMessage(getUtils().tr(getCommandConfig().getString("Fly.Messages." + type[0] + ".FlightEnable"), target[0]));
        }

        return false;
    }

}
