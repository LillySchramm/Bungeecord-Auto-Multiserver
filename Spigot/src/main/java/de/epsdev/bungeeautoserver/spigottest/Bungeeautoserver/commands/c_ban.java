package de.epsdev.bungeeautoserver.spigottest.Bungeeautoserver.commands;

import de.epsdev.bungeeautoserver.api.packages.RequestBanPackage;
import de.epsdev.bungeeautoserver.spigottest.Bungeeautoserver.BungeecordAutoConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.Instant;

public class c_ban implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){
            Player player = (Player) sender;

            if(player.hasPermission("bungee.ban")) {
                if(!(args.length < 3)){
                    Player target = Bukkit.getPlayer(args[0]);

                    long cur_time = Instant.now().getEpochSecond();
                    int hours = Integer.parseInt(args[1]);

                    String reason = "";

                    for (int i = 2; i < args.length; i++) {
                        reason += args[i] + " ";
                    }

                    BungeecordAutoConfig.eps_api.connection.send(
                            new RequestBanPackage(
                                    player.getUniqueId().toString(),
                                    cur_time + (long) hours * 60 * 60,
                                    reason
                            )
                    );

                    target.kickPlayer("You got baned by server staff.");
                }else {
                    player.sendMessage(ChatColor.RED + "<playername> <ban time (h)> <reason>");
                }
            }else {
                player.sendMessage(ChatColor.RED + "You don't have the required permissions to do that.");
            }
        }

        return true;
    }
}
