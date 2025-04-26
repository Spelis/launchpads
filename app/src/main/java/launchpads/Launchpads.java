package launchpads;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class Launchpads extends JavaPlugin implements TabExecutor {

    @Override
    public void onEnable() {
        getCommand("launch").setExecutor(this);
        getCommand("launchto").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("launch")) {
            if (!sender.hasPermission("launcher.launch")) {
                sender.sendMessage("§cNo permission.");
                return true;
            }
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

        if (command.getName().equalsIgnoreCase("launchto")) {
            if (!sender.hasPermission("launcher.launchto")) {
                sender.sendMessage("§cNo permission.");
                return true;
            }
            if (args.length != 5) return false;

            List<Entity> targets = Bukkit.selectEntities(sender, args[0]);
            if (targets.isEmpty()) {
                sender.sendMessage("No entities found.");
                return true;
            }

            double x, y, z;
            int ticks;
            try {
                x = Double.parseDouble(args[1]);
                y = Double.parseDouble(args[2]);
                z = Double.parseDouble(args[3]);
                ticks = Integer.parseInt(args[4]);
            } catch (NumberFormatException e) {
                sender.sendMessage("Invalid input.");
                return true;
            }

            if (ticks <= 0) {
                sender.sendMessage("Ticks must be > 0.");
                return true;
            }

            for (Entity target : targets) {
                Vector velocity = calculateVelocity(target.getLocation().toVector(), new Vector(x, y, z), ticks);
                target.setVelocity(velocity);
            }

            sender.sendMessage("Calculated and launched " + targets.size() + " entities.");
            return true;
        }

        return false;
    }

    private Vector calculateVelocity(Vector start, Vector end, int ticks) {
        double gravity = 0.08;
        double t = ticks / 20.0;

        double vx = (end.getX() - start.getX()) / t;
        double vz = (end.getZ() - start.getZ()) / t;
        double vy = (end.getY() - start.getY()) / t + 0.5 * gravity * t;

        return new Vector(vx, vy, vz);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return new ArrayList<>();
    }
}
