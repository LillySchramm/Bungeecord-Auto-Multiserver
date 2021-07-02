package de.epsdev.plugins.bungee;

import de.epsdev.bungeeautoserver.api.EPS_API;
import de.epsdev.bungeeautoserver.api.ServerManager;
import de.epsdev.bungeeautoserver.api.enums.OperationType;
import de.epsdev.bungeeautoserver.api.interfaces.ServerStatusEmitter;
import de.epsdev.plugins.bungee.events.PlayerJoinEvent;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ListenerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class Bungee extends Plugin {

    @Override
    public void onEnable() {
        removeAll();

        //Register Events

        getProxy().getPluginManager().registerListener(this,new PlayerJoinEvent());

        // API stuff

        EPS_API eps_api = new EPS_API(OperationType.SERVER);

        ServerManager.statusEmitter = new ServerStatusEmitter() {
            @Override
            public void onConnect(String s, InetSocketAddress inetSocketAddress) {
                addServer(s,inetSocketAddress, "MOTD", false);
            }

            @Override
            public void onDisconnect(String s) {
                removeServer(s);
            }
        };

        eps_api.init();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static void addServer(String name, InetSocketAddress address, String motd, boolean restricted) {
        ProxyServer.getInstance().getServers().put(name, ProxyServer.getInstance().constructServerInfo(name, address, motd, restricted));
        System.out.println(EPS_API.PREFIX + "Connected " + name + address.getHostName());
    }
    public static void removeServer(String name) {
        /*for (ProxiedPlayer p : ProxyServer.getInstance().getServerInfo(name).getPlayers()) {
            p.disconnect(new TextComponent("This server was forcefully closed.\nPlease report this error in the bug report section of the forums."));
        }*/


        ProxyServer.getInstance().getServers().remove(name);
        System.out.println(EPS_API.PREFIX + "Removed " + name);
    }

    public static void removeAll(){
        ArrayList<String> s = new ArrayList<>(ProxyServer.getInstance().getServers().keySet());
        for (String server : s) {
            removeServer(server);
        }

        System.out.println(ProxyServer.getInstance().getServers().keySet());
    }
}
