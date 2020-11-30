package rpsystem.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpsystem.Objects.CharacterCard;
import rpsystem.Main;

import java.util.ArrayList;

import static org.bukkit.Bukkit.getPlayer;
import static org.bukkit.Bukkit.getServer;
import static rpsystem.Subsystems.UtilitySubsystem.createStringFromFirstArgOnwards;
import static rpsystem.Subsystems.UtilitySubsystem.findUUIDBasedOnPlayerName;

public class CardCommand {

    Main main = null;

    public CardCommand(Main plugin) {
        main = plugin;
    }

    public static void showCard(CommandSender sender, String[] args, ArrayList<CharacterCard> cards) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("rp.card.show") || player.hasPermission("rp.card.*") || player.hasPermission("rp.default")) {
                for (CharacterCard card : cards) {
                    if (card.getPlayerUUID() != null) {
                        if (card.getPlayerUUID().equals(player.getUniqueId())) {
                            player.sendMessage(ChatColor.BOLD + "" + ChatColor.AQUA + "\n----------\n" + "Tarjeta de Personaje de " + Bukkit.getOfflinePlayer(card.getPlayerUUID()).getName() + "\n----------\n");
                            player.sendMessage(ChatColor.AQUA + "Nombre: " + card.getName());
                            player.sendMessage(ChatColor.AQUA + "Raza: " + card.getRace());
                            player.sendMessage(ChatColor.AQUA + "Subcultura: " + card.getSubculture());
                            player.sendMessage(ChatColor.AQUA + "Edad: " + card.getAge());
                            player.sendMessage(ChatColor.AQUA + "Género: " + card.getGender());
                            player.sendMessage(ChatColor.AQUA + "Religión: " + card.getReligion());
                            return;
                        }
                    }
                }
            }
            else {
                player.sendMessage(ChatColor.RED + "Para poder usar este comando, necesitas el permiso: 'rp.card.show'");
            }

        }
    }

    public static void showHelpMessage(CommandSender sender) {
        if (sender instanceof Player) {

            Player player = (Player) sender;

            if (player.hasPermission("rp.card.help") || player.hasPermission("rp.card.*") || player.hasPermission("rp.default")) {
                sender.sendMessage(ChatColor.BOLD + "" + ChatColor.AQUA + " == " + "Comando de Tarjeta de Personaje" + " == ");
                sender.sendMessage(ChatColor.AQUA + "/card - Mira tu Tarjeta de Personaje.");
                sender.sendMessage(ChatColor.AQUA + "/card (player) - Mira la Tarjeta de Personaje de un jugador específico.");
                sender.sendMessage(ChatColor.AQUA + "/card name (name) - Cambia el nombre de tu personaje.");
                sender.sendMessage(ChatColor.AQUA + "/card race (race) - Cambia la raza de tu personaje.");
                sender.sendMessage(ChatColor.AQUA + "/card subculture (subculture) - Cambia la subcultura de tu personaje.");
                sender.sendMessage(ChatColor.AQUA + "/card age (age) - Cambia la edad de tu personaje.");
                sender.sendMessage(ChatColor.AQUA + "/card gender (gender) - Cambia el género de tu personaje.");
                sender.sendMessage(ChatColor.AQUA + "/card religion (religion) - Cambia la religión de tu personaje.");
            }
            else {
                player.sendMessage(ChatColor.RED + "Para usar este comando, necesitas el permiso: 'rp.card.help'");
            }
        }

    }

    public void changeName(CommandSender sender, String[] args, ArrayList<CharacterCard> cards) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("rp.card.name") || player.hasPermission("rp.card.*") || player.hasPermission("rp.default")) {
                for (CharacterCard card : cards) {

                        if (card.getPlayerUUID().equals(player.getUniqueId())) {

                            if (!main.playersOnNameChangeCooldown.contains(player.getUniqueId())) {

                                if (args.length > 1) {
                                    card.setName(createStringFromFirstArgOnwards(args, 1));
                                    player.sendMessage(ChatColor.GREEN + "¡Nombre listo! Escribe /card para ver los cambios.");

                                    // cooldown
                                    main.playersOnNameChangeCooldown.add(player.getUniqueId());

                                    int seconds = 300;
                                    getServer().getScheduler().runTaskLater(main, new Runnable() {
                                        @Override
                                        public void run() {
                                            main.playersOnNameChangeCooldown.remove(player.getUniqueId());
                                            player.sendMessage(ChatColor.GREEN + "Ya puedes cambiar el nombre de tu personaje otra vez.");
                                        }
                                    }, seconds * 20);
                                }
                                else {
                                    player.sendMessage(ChatColor.RED + "Uso: /card name (nombre-personaje)");
                                }
                            }
                            else {
                                player.sendMessage(ChatColor.RED + "¡Tienes que esperar antes de cambiar tu nombre otra vez!");
                            }
                        }

                }
            }
            else {
                player.sendMessage(ChatColor.RED + "Para usar este comando, necesitas el permiso: 'rp.card.name'");
            }

        }
    }

    public static void changeRace(CommandSender sender, String[] args, ArrayList<CharacterCard> cards) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("rp.card.race") || player.hasPermission("rp.card.*") || player.hasPermission("rp.default")) {
                for (CharacterCard card : cards) {

                    if (card.getPlayerUUID().equals(player.getUniqueId())) {

                        if (args.length > 1) {
                            card.setRace(args[1]);
                            player.sendMessage(ChatColor.GREEN + "¡Raza lista! Escribe /card para ver los cambios.");
                        }
                        else {
                            player.sendMessage(ChatColor.RED + "Uso: /card race (raza-de-personaje)");
                        }
                    }
                }
            }
            else {
                player.sendMessage(ChatColor.RED + "Para usar este comando, necesitas el permiso: 'rp.card.race'");
            }

        }
    }

    public static void changeSubculture(CommandSender sender, String[] args, ArrayList<CharacterCard> cards) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("rp.card.subculture") || player.hasPermission("rp.card.*") || player.hasPermission("rp.default")) {
                for (CharacterCard card : cards) {

                    if (card.getPlayerUUID().equals(player.getUniqueId())) {

                        if (args.length > 1) {
                            card.setSubculture(args[1]);
                            player.sendMessage(ChatColor.GREEN + "¡Subcultura hecha! Escribe /card para ver los cambios.");
                        }
                        else {
                            player.sendMessage(ChatColor.RED + "Uso: /card subculture (subcultura-de-personaje)");
                        }
                    }
                }
            }
            else {
                player.sendMessage(ChatColor.RED + "Para usar este comando, necesitas el permiso: 'rp.card.subculture'");
            }

        }
    }

    public static void changeReligion(CommandSender sender, String[] args, ArrayList<CharacterCard> cards) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("rp.card.religion") || player.hasPermission("rp.card.*") || player.hasPermission("rp.default")) {
                for (CharacterCard card : cards) {

                    if (card.getPlayerUUID().equals(player.getUniqueId())) {

                        if (args.length > 1) {
                            card.setReligion(args[1]);
                            player.sendMessage(ChatColor.GREEN + "¡Religión hecha! Escribe /card para ver los cambios.");
                        }
                        else {
                            player.sendMessage(ChatColor.RED + "Uso: /card religion (religión-de-personaje)");
                        }
                    }
                }
            }
            else {
                player.sendMessage(ChatColor.RED + "Para usar el comando, necesitas el permiso: 'rp.card.religion'");
            }

        }
    }

    public static void changeAge(CommandSender sender, String[] args, ArrayList<CharacterCard> cards) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("rp.card.age") || player.hasPermission("rp.card.*") || player.hasPermission("rp.default")) {
                for (CharacterCard card : cards) {

                    if (card.getPlayerUUID().equals(player.getUniqueId())) {

                        if (args.length > 1) {
                            card.setAge(Integer.parseInt(args[1]));
                            player.sendMessage(ChatColor.GREEN + "¡Edad hecha! Escribe /card para ver los cambios.");
                        }
                        else {
                            player.sendMessage(ChatColor.RED + "Uso: /card age (edad-de-personaje)");
                        }
                    }
                }
            }
            else {
                player.sendMessage(ChatColor.RED + "Para usar el comando, necesitas el permiso: 'rp.card.age'");
            }

        }
    }

    public static void changeGender(CommandSender sender, String[] args, ArrayList<CharacterCard> cards) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("rp.card.gender") || player.hasPermission("rp.card.*") || player.hasPermission("rp.default")) {
                for (CharacterCard card : cards) {

                    if (card.getPlayerUUID().equals(player.getUniqueId())) {

                        if (args.length > 1) {
                            card.setGender(args[1]);
                            player.sendMessage(ChatColor.GREEN + "¡Género hecho! Escribe /card para ver los cambios.");
                        }
                        else {
                            player.sendMessage(ChatColor.RED + "Uso: /card gender (género-de-personaje)");
                        }
                    }
                }
            }
            else {
                player.sendMessage(ChatColor.RED + "Para usar este comando, necesitas el permiso: 'rp.card.gender'");
            }

        }
    }

    public static void showPlayerInfo(CommandSender sender, String[] args, ArrayList<CharacterCard> cards) {
        if (sender instanceof Player) {

            Player player = (Player) sender;

            if (player.hasPermission("rp.card.show.others") || player.hasPermission("rp.card.*") || player.hasPermission("rp.default")) {
                for (CharacterCard card : cards) {
                    if (args.length > 0) {
                        if (card.getPlayerUUID().equals(findUUIDBasedOnPlayerName(args[0]))) {
                            sender.sendMessage(ChatColor.BOLD + "" + ChatColor.AQUA + "\n----------\n" + "Tarjeta de Personaje de " + Bukkit.getOfflinePlayer(card.getPlayerUUID()).getName() + "\n----------\n");
                            sender.sendMessage(ChatColor.AQUA + "Nombre: " + card.getName());
                            sender.sendMessage(ChatColor.AQUA + "Raza: " + card.getRace());
                            sender.sendMessage(ChatColor.AQUA + "Subcultura: " + card.getSubculture());
                            sender.sendMessage(ChatColor.AQUA + "Edad: " + card.getAge());
                            sender.sendMessage(ChatColor.AQUA + "Género: " + card.getGender());
                            sender.sendMessage(ChatColor.AQUA + "Religión: " + card.getReligion());
                            return;
                        }
                    }
                }

                player.sendMessage(ChatColor.RED + "¡El jugador no fue encontrado!");

            }
            else {
                player.sendMessage(ChatColor.RED + "Para usar este comando, necesitas el permiso: 'rp.card.show.others'");
            }

        }

    }
}
