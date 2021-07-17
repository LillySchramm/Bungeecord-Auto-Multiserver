package de.epsdev.bungeeautoserver.spigottest.Bungeeautoserver.commands;

import de.epsdev.bungeeautoserver.api.packages.RequestBroadcastPackage;
import de.epsdev.bungeeautoserver.spigottest.Bungeeautoserver.BungeecordAutoConfig;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class c_broadcast implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;

            String message = "";

            for (String s : args) {
                message += s + " ";
            }

            if(player.hasPermission("bungee.broadcast")){
                BungeecordAutoConfig.eps_api.connection.send(new RequestBroadcastPackage(
                        message
                ));
            }else {
                player.sendMessage(ChatColor.RED + "You don't have the required permissions to do that.");
            }
        }
        return true;
    }
}
