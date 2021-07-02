package de.epsdev.plugins.bungee.events;

import de.epsdev.bungeeautoserver.api.PlayerManager;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

// Renamed from 'PlayerDisconnectEvent' bc of net.md_5.bungee.api.event.PlayerDisconnectEvent
public class PlayerDisconnectFromProxyEvent implements Listener {

    @EventHandler
    void onDisconnect(PlayerDisconnectEvent event){
        PlayerManager.onPlayerDisconnect(event.getPlayer().getDisplayName());
    }

}
