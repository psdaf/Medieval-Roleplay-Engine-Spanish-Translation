package rpsystem.Commands;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import rpsystem.Main;

public class SitCommand {

    Main main = null;

    public SitCommand(Main plugin) {
        main = plugin;
    }

    public void makePlayerSit(CommandSender sender) {
        Player player = (Player) sender;
        Location sitLocation = getSitLocationUnderPlayer(player);
        sitPlayer(player, sitLocation);
    }

    private Location getSitLocationUnderPlayer(Player player) {
        World world = player.getWorld();
        Double xpos = player.getLocation().getX();
        Double ypos = player.getLocation().getY() - 1;
        Double zpos = player.getLocation().getZ();
        return new Location(world, xpos, ypos, zpos);
    }

    private Entity spawnChairEntity(Location location) {
        Arrow arrow = location.getWorld().spawnArrow(location, new Vector(0, 1, 0), 0, 0);
        arrow.setGravity(false);
        arrow.setInvulnerable(true);
        arrow.setPickupStatus(Arrow.PickupStatus.DISALLOWED);
        return arrow;
    }

    private void sitPlayer(Player player, Location sitLocation) {
        Entity chairEntity = spawnChairEntity(sitLocation);
        player.teleport(sitLocation);
        chairEntity.addPassenger(player);
    }
}
