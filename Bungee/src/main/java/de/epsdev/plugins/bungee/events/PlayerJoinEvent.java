package de.epsdev.plugins.bungee.events;

import de.epsdev.bungeeautoserver.api.EPS_API;
import de.epsdev.bungeeautoserver.api.ServerManager;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.event.*;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PlayerJoinEvent implements Listener {

    @EventHandler
    public void onPostLogin(PostLoginEvent event) {
        ProxiedPlayer proxiedPlayer = event.getPlayer();

        String server = ServerManager.connectToServer(EPS_API.DEFAULT_SERVER, proxiedPlayer.getDisplayName());

        if(server.equals("null")){
            proxiedPlayer.disconnect(new ComponentBuilder("The server you are trying to connect to is full!")
                    .color(ChatColor.RED).append("\n\n Please try again in a few minutes.").color(ChatColor.YELLOW).bold(true).underlined(true)
                    .create());
        }
    }

}
