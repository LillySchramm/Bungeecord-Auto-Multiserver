package de.epsdev.bungeeautoserver.spigottest.Bungeeautoserver.events;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class e_OnCommand implements Listener {
    private static final String[] FORBIDDEN_COMMANDS = {"reload", "rl"};

    @EventHandler
    void onCommand(PlayerCommandPreprocessEvent event) {
        for (String forbidden : FORBIDDEN_COMMANDS) {
            if(event.getMessage().startsWith("/" + forbidden)){
                event.getPlayer().sendMessage(ChatColor.RED + "This command has been disabled by Auto Server");
                event.setCancelled(true);
                break;
            }
        }
    }
}
