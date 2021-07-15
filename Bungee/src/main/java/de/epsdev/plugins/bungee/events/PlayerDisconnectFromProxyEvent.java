package de.epsdev.plugins.bungee.events;

import de.epsdev.bungeeautoserver.api.PlayerManager;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

// Renamed from 'PlayerDisconnectEvent' bc of net.md_5.bungee.api.event.PlayerDisconnectEvent
public class PlayerDisconnectFromProxyEvent implements Listener {

    /*
            Note to self:
                Even though the Bungeecord API is a nearly 1:1 copy of the Bukkit/Spigot API for some reason events need
                to be public here. ( Differing on the use case, and alignment of the stars, a bukkit server might even
                crash if served an 'public void' instead of just 'void' )
     */

    @EventHandler
    public void onDisconnect(PlayerDisconnectEvent event){
        PlayerManager.onPlayerDisconnect(event.getPlayer().getDisplayName());
    }
}
