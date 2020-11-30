package rpsystem.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpsystem.Main;
import rpsystem.Subsystems.UtilitySubsystem;

import static rpsystem.Subsystems.UtilitySubsystem.sendMessageToPlayersWithinDistance;

public class HelpCommand {

    Main main = null;

    public HelpCommand(Main plugin) {
        main = plugin;
    }

    public void showListOfCommands(CommandSender sender) {

        // player check
        if (!(sender instanceof Player)) {
            return;
        }

        Player player = (Player) sender;

        if (player.hasPermission("rp.rphelp") || player.hasPermission("rp.default")) {

            player.sendMessage(ChatColor.AQUA + " == Motor de Roleplay Medieval " + main.version + " Comandos == ");
            player.sendMessage(ChatColor.AQUA + "/rphelp - Muestra una lista de comandos útiles.");
            player.sendMessage(ChatColor.AQUA + "/card help - Muestra una lista de comandos útiles sobre las tarjetas de personaje.");
            player.sendMessage(ChatColor.AQUA + "/bird - Envía un pájaro mensajero a otro jugador.");
            player.sendMessage(ChatColor.AQUA + "/local o /rp - Entra al chat local.");
            player.sendMessage(ChatColor.AQUA + "/global o /ooc - Entra al chat global.");
            player.sendMessage(ChatColor.AQUA + "/emote o /me - Envía un emoticón a los jugadores cercanos.");
            player.sendMessage(ChatColor.AQUA + "/roll o /dice - Tira un dado.");
            player.sendMessage(ChatColor.AQUA + "/title - Renombra un libro no escrito.");
            player.sendMessage(ChatColor.AQUA + "/yell - Envia un mensaje a jugadores lejanos (gritar).");
            player.sendMessage(ChatColor.AQUA + "/whisper - Envía un mensaje a jugadores muy cercanos (susurrar).");

        }
        else {
            player.sendMessage(ChatColor.RED + "Para usar este comando, necesitas el permiso: 'rp.rphelp'");
        }

    }

}
