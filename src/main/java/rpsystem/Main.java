package rpsystem;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import rpsystem.EventHandlers.AsyncPlayerChatEventHandler;
import rpsystem.EventHandlers.PlayerInteractAtEntityEventHandler;
import rpsystem.EventHandlers.PlayerJoinEventHandler;
import rpsystem.Objects.CharacterCard;
import rpsystem.Subsystems.CommandSubsystem;
import rpsystem.Subsystems.StorageSubsystem;
import rpsystem.Subsystems.UtilitySubsystem;
import rpsystem.bStats.Metrics;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

public class Main extends JavaPlugin implements Listener {

    // version
    public String version = "v1.5.0.1";

    // subsystems
    public StorageSubsystem storage = new StorageSubsystem(this);
    public CommandSubsystem commands = new CommandSubsystem(this);
    public UtilitySubsystem utilities = new UtilitySubsystem(this);

    // saved
    public ArrayList<CharacterCard> cards = new ArrayList<>();

    // temporary
    public ArrayList<UUID> playersWithBusyBirds = new ArrayList<>();
    public ArrayList<UUID> playersSpeakingInLocalChat = new ArrayList<>();
    public ArrayList<UUID> playersOnNameChangeCooldown = new ArrayList<>();
    public ArrayList<UUID> playersWithRightClickCooldown = new ArrayList<>();

    @Override
    public void onEnable() {
        System.out.println("Activando Plugin Motor de Roleplay Medieval....");
        this.getServer().getPluginManager().registerEvents(this, this);

        if (storage.oldSaveFolderPresent()) {
            storage.legacyLoadCards();
            storage.deleteLegacyFiles(new File("./plugins/medieval-roleplay-engine/"));
            storage.saveCardFileNames();
            storage.saveCards();
        }
        else {
            storage.loadCards();
        }

        int pluginId = 8996;

        Metrics metrics = new Metrics(this, pluginId);

        System.out.println("Plugin Motor de Roleplay Medieval activado.");
    }

    @Override
    public void onDisable() {
        System.out.println("Desactivando plugin Motor de Roleplay Medieval....");
        storage.saveCardFileNames();
        storage.saveCards();
        System.out.println("Motor de Roleplay Medieval desactivado.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        return commands.interpretCommand(sender, label, args);
    }

    @EventHandler()
    public void onJoin(PlayerJoinEvent event) {
        PlayerJoinEventHandler handler = new PlayerJoinEventHandler(this);
        handler.handle(event);
    }

    @EventHandler()
    public void onChat(AsyncPlayerChatEvent event) {
        AsyncPlayerChatEventHandler handler = new AsyncPlayerChatEventHandler(this);
        handler.handle(event);
    }

    @EventHandler()
    public void onRightClick(PlayerInteractAtEntityEvent event) {
        PlayerInteractAtEntityEventHandler handler = new PlayerInteractAtEntityEventHandler(this);
        handler.handle(event);
    }

}
