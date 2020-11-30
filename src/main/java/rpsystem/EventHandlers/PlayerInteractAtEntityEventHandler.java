package rpsystem.EventHandlers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import rpsystem.Objects.CharacterCard;
import rpsystem.Main;

import static org.bukkit.Bukkit.getPlayer;

public class PlayerInteractAtEntityEventHandler {

    Main main = null;

    public PlayerInteractAtEntityEventHandler(Main plugin) {
        main = plugin;
    }

    public void handle(PlayerInteractAtEntityEvent event) {
        if (event.getRightClicked() instanceof Player) {

            Player target = (Player) event.getRightClicked();
            CharacterCard card = main.utilities.getCard(target.getUniqueId());

            Player player = event.getPlayer();

            if (!main.playersWithRightClickCooldown.contains(player.getUniqueId())) {
                main.playersWithRightClickCooldown.add(player.getUniqueId());

                if (player.hasPermission("rp.card.show.others") || player.hasPermission("rp.card.*") || player.hasPermission("rp.default")) {
                    player.sendMessage(ChatColor.BOLD + "" + ChatColor.AQUA + "\n == " + "Tarjeta de Personaje de " + Bukkit.getOfflinePlayer(card.getPlayerUUID()).getName() + " == ");
                    player.sendMessage(ChatColor.AQUA + "Nombre: " + card.getName());
                    player.sendMessage(ChatColor.AQUA + "Raza: " + card.getRace());
                    player.sendMessage(ChatColor.AQUA + "Subcultura: " + card.getSubculture());
                    player.sendMessage(ChatColor.AQUA + "Edad: " + card.getAge());
                    player.sendMessage(ChatColor.AQUA + "Género: " + card.getGender());
                    player.sendMessage(ChatColor.AQUA + "Religión: " + card.getReligion());

                    int seconds = 2;
                    main.getServer().getScheduler().runTaskLater(main, new Runnable() {
                        @Override
                        public void run() {
                            main.playersWithRightClickCooldown.remove(player.getUniqueId());

                        }
                    }, seconds * 20);
                }

            }

        }
    }

}
