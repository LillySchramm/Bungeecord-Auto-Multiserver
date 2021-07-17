package de.epsdev.plugins.bungee.schedulers;

import de.epsdev.bungeeautoserver.api.RemoteServer;
import de.epsdev.bungeeautoserver.api.ServerManager;
import de.epsdev.plugins.bungee.Bungee;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class PlayerCountTimer {

    public static int playerCount = 0;

    public static void run(){
        Bungee.plugin.getProxy().getScheduler().schedule(Bungee.plugin, () -> {
            playerCount = getTotalPlayers();
        }, 0L, 1L, TimeUnit.SECONDS);

    }

    private static int getTotalPlayers(){
        int total = 0;

        for (ArrayList<RemoteServer> servers : ServerManager.servers.values()){
            for (RemoteServer remoteServer : servers){
                total += remoteServer.getCurrent_players();
            }
        }

        return total;
    }


}
