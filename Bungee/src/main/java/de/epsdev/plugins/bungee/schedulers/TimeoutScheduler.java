package de.epsdev.plugins.bungee.schedulers;

import de.epsdev.bungeeautoserver.api.EPS_API;
import de.epsdev.bungeeautoserver.api.RemoteServer;
import de.epsdev.bungeeautoserver.api.ServerManager;
import de.epsdev.plugins.bungee.Bungee;
import net.md_5.bungee.api.Callback;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.ServerPing;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class TimeoutScheduler {

    public static boolean running = false;

    public static void run(){
        Bungee.plugin.getProxy().getScheduler().schedule(Bungee.plugin, () -> {
            if(!running){
                running = true;

                for(ArrayList<RemoteServer> remoteServers : ServerManager.servers.values()){
                    for (RemoteServer remoteServer : remoteServers){
                        checkPing(remoteServer.getName());
                    }
                }

                running = false;
            }

        }, 0L, 1L, TimeUnit.SECONDS);

    }

    private static void checkPing(String name){
        Bungee.plugin.getProxy().getServers().get(name).ping((result, error) -> {
            if(error!=null){
                System.out.println(EPS_API.PREFIX + "Server '" + name + "' has disconnected.");
                ServerManager.removeServer(ServerManager.getRemoteServerByName(name));
            }
        });
    }
}
