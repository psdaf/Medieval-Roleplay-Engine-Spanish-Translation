package rpsystem.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpsystem.Main;
import rpsystem.Subsystems.UtilitySubsystem;

import static rpsystem.Subsystems.UtilitySubsystem.sendMessageToPlayersWithinDistance;

public class YellCommand {

    Main main = null;

    public YellCommand(Main plugin) {
        main = plugin;
    }

    public void sendLoudMessage(CommandSender sender, String[] args) {
        // player check
        if (!(sender instanceof Player)) {
            return;
        }

        Player player = (Player) sender;

        if (player.hasPermission("rp.yell") || player.hasPermission("rp.default")) {

            if (args.length > 0) {
                String message = ChatColor.RED + "" + String.format("%s grita: \"%s\"", main.utilities.getCard(player.getUniqueId()).getName(), main.utilities.createStringFromArgs(args));

                sendMessageToPlayersWithinDistance(player, message, 50);
            }
            else {
                player.sendMessage(ChatColor.RED + "Uso: /yell (mensaje)");
            }

        }
        else {
            player.sendMessage(ChatColor.RED + "Para usar este comando, necesitas el permiso: 'rp.yell'");
        }

    }

}
