package de.epsdev.plugins.bungee.events;

import de.epsdev.bungeeautoserver.api.PlayerManager;

import de.epsdev.plugins.bungee.Bungee;
import de.epsdev.plugins.bungee.schedulers.PlayerCountTimer;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;


public class PingEvent implements Listener {

    @EventHandler
    public void onPing(ProxyPingEvent event){
        event.getResponse().setPlayers(new ServerPing.Players(-1, PlayerCountTimer.playerCount, new ServerPing.PlayerInfo[0]));
    }

}
