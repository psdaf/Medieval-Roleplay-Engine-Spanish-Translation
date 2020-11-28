package rpsystem.Commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import rpsystem.Main;

public class TitleCommand {
    Main main = null;

    public TitleCommand(Main plugin) {
        main = plugin;
    }

    public void titleBook(CommandSender sender, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            // check permission
            if (player.hasPermission("rp.title") || player.hasPermission("rp.default")) {

                // check if they're holding a book
                if (player.getInventory().getItemInMainHand().getType() == Material.WRITABLE_BOOK) {

                    // args check
                    if (args.length > 0) {

                        String newTitle = main.utilities.createStringFromArgs(args);

                        ItemStack book = player.getInventory().getItemInMainHand();

                        ItemMeta meta = book.getItemMeta();

                        meta.setDisplayName(newTitle);

                        book.setItemMeta(meta);

                        player.getInventory().setItemInMainHand(book);

                        player.sendMessage(ChatColor.GREEN + "¡Título añadido!");
                    }
                    else {
                        player.sendMessage(ChatColor.RED + "Uso: /title (nuevo título)");
                    }

                }
                else {
                    player.sendMessage(ChatColor.RED + "¡Tienes que sostener un libro y pluma para usar este comando!");
                }

            }
            else {
                player.sendMessage(ChatColor.RED + "Para usar este comando, necesitas el permiso: 'rp.title'");
            }
        }

    }
}
