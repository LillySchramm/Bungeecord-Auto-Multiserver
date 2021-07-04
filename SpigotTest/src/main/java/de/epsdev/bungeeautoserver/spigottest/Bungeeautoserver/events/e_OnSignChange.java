package de.epsdev.bungeeautoserver.spigottest.Bungeeautoserver.events;

import de.epsdev.bungeeautoserver.api.ServerManager;
import de.epsdev.bungeeautoserver.spigottest.Bungeeautoserver.SignManager;
import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class e_OnSignChange implements Listener {

    @EventHandler
    void onSignChange(SignChangeEvent event){
        String[] lines = event.getLines();
        Player player = event.getPlayer();

        if(lines[0].startsWith("Bungee:")){
            if(player.hasPermission("bungee.editsign")){
                String servername = lines[0].replace("Bungee:", "");

                event.setLine(0, ChatColor.GREEN + SignManager.center(servername));
                event.setLine(1, ChatColor.GREEN + SignManager.center("-1/-1"));
                event.setLine(3, ChatColor.GREEN + SignManager.center(">Click To Join<"));

                SignManager.addSign((Sign) event.getBlock().getState());

            }else {
                player.sendMessage(ChatColor.RED + "You don't have the required permissions to do that.");
                event.setCancelled(true);
            }
        }
    }



}
