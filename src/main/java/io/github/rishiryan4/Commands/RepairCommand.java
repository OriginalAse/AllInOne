package io.github.rishiryan4.Commands;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static io.github.rishiryan4.Main.*;


public class RepairCommand extends BukkitCommand {

    public RepairCommand() {
        super("repair");

        setDescription("Repair a damaged item.");
        setPermission("aoi.repair");
        setPermissionMessage(getCommandConfig().getString("NoPermCommandMessage"));
    }

    @Override
    public boolean execute(CommandSender sender, String alias, String[] args) {

        if (!getUtils().sentFromConsole(sender)) return false;

        Player player = (Player) sender;

        if (!getUtils().setPermCheckAndEnabled(player, getPermission(), getPermissionMessage(), "Repair")) return false;

        Player[] target = new Player[1];
        String[] type = new String[1];

        if (!getUtils().setTypeAndTarget(player, target, args, type, getPermission(), getPermissionMessage())) return false;

        ItemStack repairItem = target[0].getItemInHand();

        if (repairItem == null || repairItem.equals(new ItemStack(Material.AIR))) {
            player.sendMessage(getUtils().tr(getCommandConfig().getString("Repair.Messages." + type[0] + ".NoItemSelected"), target[0]));
            return false;
        }

        if (repairItem.getType().getMaxDurability() < 1) {
            player.sendMessage(getUtils().tr(getCommandConfig().getString("Repair.Messages." + type[0] + ".ItemCannotBeRepaired"), target[0]));
            return false;
        }

        repairItem.setDurability(repairItem.getType().getMaxDurability());

        target[0].setItemInHand(repairItem);

        return false;
    }
}
