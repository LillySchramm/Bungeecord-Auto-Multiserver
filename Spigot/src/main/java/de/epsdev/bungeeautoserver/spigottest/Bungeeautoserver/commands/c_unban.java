package de.epsdev.bungeeautoserver.spigottest.Bungeeautoserver.commands;

import de.epsdev.bungeeautoserver.api.packages.RequestUnbanPackage;
import de.epsdev.bungeeautoserver.spigottest.Bungeeautoserver.BungeecordAutoConfig;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class c_unban implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){
            Player player = (Player) sender;

            if(player.hasPermission("bungee.ban")) {
                if(args.length == 1){
                    BungeecordAutoConfig.eps_api.connection.send(
                            new RequestUnbanPackage(
                                    args[0]
                            )
                    );

                }else {
                    player.sendMessage(ChatColor.RED + "<playername> <ban time (h)> <reason>");
                }
            }else {
                player.sendMessage(ChatColor.RED + "You don't have the required permissions to do that.");
            }
        }else{
            BungeecordAutoConfig.eps_api.connection.send(
                    new RequestUnbanPackage(
                        args[0]
                    )
            );
        }

        return true;
    }
}
