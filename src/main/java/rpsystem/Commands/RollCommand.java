package rpsystem.Commands;

import com.bernardomg.tabletop.dice.history.RollHistory;
import com.bernardomg.tabletop.dice.interpreter.DiceInterpreter;
import com.bernardomg.tabletop.dice.interpreter.DiceRoller;
import com.bernardomg.tabletop.dice.parser.DefaultDiceParser;
import com.bernardomg.tabletop.dice.parser.DiceParser;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpsystem.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class RollCommand {

    Main main = null;

    static final DiceParser parser = new DefaultDiceParser();
    static final DiceInterpreter<RollHistory> roller = new DiceRoller();

    static final String usageMsg = ChatColor.RED + "Uso: /roll (número-de-dados)d(número-de-lados)+(modificador)";
    public static final String invalidSyntaxMsg = ChatColor.RED + "Argumentos inválidos, tiene que ser una notación de dados estándar (2d6+12)";
    public static final String noPermMsg = ChatColor.RED + "Para usar este comando, necesitas el permiso: 'rp.roll'";

    public RollCommand(Main plugin) {
        main = plugin;
    }

    public static void rollDice(CommandSender sender, String[] args) {
        // player check
        if (!(sender instanceof Player)) {
            return;
        }

        Player player = (Player) sender;

        if (player.hasPermission("rp.roll") || player.hasPermission("rp.default")) {

            // zero args check
            if (args.length < 1) {
                player.sendMessage(usageMsg);
                return;
            }

            // Invalid syntax check
            if (!Pattern.matches("^(\\d+)?d(\\d+)([+-]\\d+)?$", args[0])) {
                player.sendMessage(invalidSyntaxMsg);
                return;
            }

            player.sendMessage(processRolls(parser.parse(args[0], roller)));
        } else {
            player.sendMessage(noPermMsg);
        }
    }

    private static String processRolls(RollHistory rolls) {
        StringBuilder messageBuilder = new StringBuilder(ChatColor.GREEN + "Tiraste un ");

        rolls.getRollResults().forEach(rollResult -> {

            List<String> results = new ArrayList<>();

            rollResult.getAllRolls().forEach(roll -> results.add(String.valueOf(roll)));

            messageBuilder.append(String.join(", ", results));

            if (results.size() > 1) {
                messageBuilder.append(", con un valor total de ").append(rollResult.getTotalRoll());
            }

            messageBuilder.append(".");
        });

        return messageBuilder.toString();
    }
}
