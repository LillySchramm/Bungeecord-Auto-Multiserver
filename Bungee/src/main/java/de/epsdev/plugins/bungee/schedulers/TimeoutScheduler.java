package de.epsdev.plugins.bungee.schedulers;

import de.epsdev.bungeeautoserver.api.RemoteServer;
import de.epsdev.bungeeautoserver.api.ServerManager;
import de.epsdev.plugins.bungee.Bungee;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class TimeoutScheduler {

    public static final int TIMEOUT_TICKS = 20;
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
                ServerManager.getRemoteServerByName(name).timeout_ticks ++;

                if(ServerManager.getRemoteServerByName(name).timeout_ticks > TIMEOUT_TICKS)
                    ServerManager.removeServer(ServerManager.getRemoteServerByName(name));
            }else {
                ServerManager.getRemoteServerByName(name).timeout_ticks = 0;
            }
        });
    }
}
