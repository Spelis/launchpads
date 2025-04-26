package launchpads;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class Launchpads extends JavaPlugin implements TabExecutor {
    @Override
    public void onEnable() {
        getCommand("launch").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 4) return false;

        List<Entity> targets = Bukkit.selectEntities(sender, args[0]);
        if (targets.isEmpty()) {
            sender.sendMessage("No entities found.");
            return true;
        }

        double x, y, z;
        try {
            x = Double.parseDouble(args[1]);
            y = Double.parseDouble(args[2]);
            z = Double.parseDouble(args[3]);
        } catch (NumberFormatException e) {
            sender.sendMessage("Invalid velocity numbers.");
            return true;
        }

        Vector velocity = new Vector(x, y, z);
        for (Entity target : targets) {
            target.setVelocity(velocity);
        }

        sender.sendMessage("Launched " + targets.size() + " entities.");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return new ArrayList<>();
    }
}
