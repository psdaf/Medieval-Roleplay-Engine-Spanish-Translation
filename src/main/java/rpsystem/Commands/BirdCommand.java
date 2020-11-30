package rpsystem.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpsystem.Main;

import static org.bukkit.Bukkit.getServer;
import static rpsystem.Subsystems.UtilitySubsystem.createStringFromFirstArgOnwards;

public class BirdCommand {

    Main main = null;

    public BirdCommand(Main plugin) {
        main = plugin;
    }

    public void sendBird(CommandSender sender, String[] args) {
        // player check
        if (!(sender instanceof Player)) {
            return;
        }

        Player player = (Player) sender;

        if (player.hasPermission("rp.bird") || player.hasPermission("rp.default")) {
            if (main.playersWithBusyBirds.contains(player.getUniqueId())) {
                player.sendMessage(ChatColor.RED + "¡Tu pájaro ya se encuentra en una misión!");
                return;
            }

            // zero args check
            if (args.length < 2) {
                player.sendMessage(ChatColor.RED + "Uso: /bird (nombre-jugador) (mensaje)");
                return;
            }

            Player targetPlayer = getServer().getPlayer(args[0]);

            if (targetPlayer == null) {
                player.sendMessage(ChatColor.RED + "¡Ese jugador no está online!");
                return;
            }

            String message = createStringFromFirstArgOnwards(args, 1);

            if (!(player.getLocation().getWorld().getName().equalsIgnoreCase(targetPlayer.getLocation().getWorld().getName()))) {
                player.sendMessage(ChatColor.RED + "No puedes enviar un pájaro a un jugador que se encuentre en otro mundo.");
                return;
            }

            double distance = player.getLocation().distance(targetPlayer.getLocation());
            int blocksPerSecond = 20;
            int seconds = (int)distance/blocksPerSecond;

            getServer().getScheduler().runTaskLater(main, new Runnable() {
                @Override
                public void run() {
                    targetPlayer.sendMessage(ChatColor.GREEN + "¡Un pájaro descendió y dejó un mensaje a tus pies! Fue enviado por " + player.getName() + ". Dice:");
                    targetPlayer.sendMessage(ChatColor.GREEN + "" + ChatColor.ITALIC + "'" + message + "'");
                    player.sendMessage(ChatColor.GREEN + "Tu pájaro ha llegado a " + targetPlayer.getName() + "!");
                    main.playersWithBusyBirds.remove(player.getUniqueId());

                }
            }, seconds * 20);

            player.sendMessage(ChatColor.GREEN + "El pájaro partió con tu mensaje.");
            main.playersWithBusyBirds.add(player.getUniqueId());
        }
        else {
            player.sendMessage(ChatColor.RED + "Para poder usar este comando, necesitas el siguiente permiso: 'rp.bird'");
        }

    }
}
