package de.epsdev.plugins.bungee.events;

import de.epsdev.bungeeautoserver.api.EPS_API;
import de.epsdev.bungeeautoserver.api.ServerManager;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.event.*;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerJoinEvent implements Listener {

    @EventHandler
    public void onPostLogin(PostLoginEvent event) {
        String server = ServerManager.connectToServer(EPS_API.DEFAULT_SERVER);
        System.out.println(server);
        System.out.println(ProxyServer.getInstance().getServerInfo(server));
        event.getPlayer().connect(
                ProxyServer.getInstance().getServerInfo(server)
        );
        System.out.println("ssssssssssssssss");
    }

    @EventHandler
    public void onPostLogin2(PreLoginEvent event) {


    }

}
