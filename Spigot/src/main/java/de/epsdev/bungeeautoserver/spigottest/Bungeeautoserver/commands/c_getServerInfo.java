package de.epsdev.bungeeautoserver.spigottest.Bungeeautoserver.commands;

import de.epsdev.bungeeautoserver.api.ServerInfo;
import de.epsdev.bungeeautoserver.api.ServerManager;
import de.epsdev.bungeeautoserver.api.packages.RequestChangePlayerServerPackage;
import de.epsdev.bungeeautoserver.api.packages.RequestServerStatusPackage;
import de.epsdev.bungeeautoserver.spigottest.Bungeeautoserver.BungeecordAutoConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class c_getServerInfo implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){
            Player player = (Player) sender;

            if(player.hasPermission("bungee.status")){
                if(args.length == 1){
                    BungeecordAutoConfig.eps_api.connection.send(new RequestServerStatusPackage(args[0]));
                }

                BungeecordAutoConfig.plugin.getServer().getScheduler().scheduleSyncDelayedTask(BungeecordAutoConfig.plugin,
                        () -> {
                            if(BungeecordAutoConfig.eps_api.serverInfo.containsKey(args[0])){
                                List<ServerInfo> infos = BungeecordAutoConfig.eps_api.serverInfo.get(args[0]);

                                player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Report");
                                player.sendMessage(ChatColor.GREEN + "Number of active server with type " + ChatColor.BOLD
                                        + args[0] + ChatColor.RESET + ChatColor.GREEN + ": " + infos.size());
                                int[] totals = ServerManager.calcTotals(infos);
                                player.sendMessage(ChatColor.GREEN + "Totals: " + totals[1] + "/" + totals[0] + " (" + totals[2] + "%)");

                                for (ServerInfo serverInfo : infos){
                                    ChatColor color = ChatColor.GREEN;

                                    if(serverInfo.closed) color = ChatColor.RED;

                                    player.sendMessage(color + serverInfo.name + " " +
                                            serverInfo.curPlayers + "/" +
                                            serverInfo.maxPlayers);
                                }

                            }else {
                                player.sendMessage(ChatColor.RED + "This server type does not exist.");
                            }
                        },20L);

            }else {
                player.sendMessage(ChatColor.RED + "You don't have the required permissions to do that.");
            }
        }

        return true;
    }


}
