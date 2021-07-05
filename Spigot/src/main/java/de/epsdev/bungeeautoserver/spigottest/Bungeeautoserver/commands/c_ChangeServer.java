package de.epsdev.bungeeautoserver.spigottest.Bungeeautoserver.commands;

import de.epsdev.bungeeautoserver.api.packages.RequestChangePlayerServerPackage;
import de.epsdev.bungeeautoserver.spigottest.Bungeeautoserver.BungeecordAutoConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class c_ChangeServer implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){
            Player player = (Player) sender;

            if(player.hasPermission("bungee.changeserver")){
                String server = "";

                if(args.length == 1){
                    server = args[0];
                }else if(args.length == 2){
                    player = Bukkit.getPlayer(args[0]);
                    server = args[1];
                }else {
                    player.sendMessage(ChatColor.RED + "Usage: <player?> <server>");
                    return true;
                }

                BungeecordAutoConfig.eps_api.connection.send(new RequestChangePlayerServerPackage(
                        server, player.getDisplayName(), false));

            }else {
                player.sendMessage(ChatColor.RED + "You don't have the required permissions to do that.");
            }
        }

        return true;
    }
}
