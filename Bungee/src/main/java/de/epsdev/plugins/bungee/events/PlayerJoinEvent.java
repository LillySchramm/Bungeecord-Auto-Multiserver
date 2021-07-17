package de.epsdev.plugins.bungee.events;

import de.epsdev.bungeeautoserver.api.EPS_API;
import de.epsdev.bungeeautoserver.api.ServerManager;

import de.epsdev.plugins.bungee.Bungee;
import de.epsdev.bungeeautoserver.api.ban.Ban;

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

        Ban ban = Ban.getBanned(proxiedPlayer.getUniqueId().toString());

        if(ban != null){
            if(!ban.getBanString().equals("")){
                proxiedPlayer.disconnect(new ComponentBuilder("You are banned!\nReason: " + ban.reason + " \n\n"
                        ).color(ChatColor.RED).append(ban.getBanString()).color(ChatColor.YELLOW).create());

                return;
            }
        }

        String server = ServerManager.connectToServer(EPS_API.DEFAULT_SERVER, proxiedPlayer.getDisplayName());

        if(server.equals("null")){
            proxiedPlayer.disconnect(new ComponentBuilder("The server you are trying to connect to is full!")
                    .color(ChatColor.RED).append("\n\n Please try again in a few minutes.").color(ChatColor.YELLOW).bold(true).underlined(true)
                    .create());
        }

    }

}
